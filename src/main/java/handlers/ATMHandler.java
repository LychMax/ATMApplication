package src.main.java.handlers;

import src.main.java.display.ATMConsole;
import src.main.java.repository.ATMDataFileProvider;

import java.math.BigDecimal;
import java.util.Scanner;

public class ATMHandler {

    BankAccountHandler bankAccountHandler;
    ATMConsole atmConsole;
    private String cardNumber;
    Scanner scanner = new Scanner(System.in);
    ATMDataFileProvider atmDataFileProvider;

    public ATMHandler(BankAccountHandler bankAccountHandler, ATMConsole atmConsole,
                      ATMDataFileProvider atmDataFileProvider) {
        this.bankAccountHandler = bankAccountHandler;
        this.atmConsole = atmConsole;
        this.atmDataFileProvider = atmDataFileProvider;
    }

    public void checkCardNumber() {

        boolean cardFoundStatus = false;


        do {
            try {
                atmConsole.sendMessageToScreen("Enter card number");

                String cardNumber = scanner.nextLine();

                if (isValidFormat(cardNumber)) {
                    cardFoundStatus = bankAccountHandler.checkCardExistence(cardNumber);
                } else {
                    atmConsole.sendMessageToScreen("Invalid card number");
                }

                if (cardFoundStatus) {
                    atmConsole.sendMessageToScreen("Card found");
                    this.cardNumber = cardNumber;

                    if (checkAccountBlocking()) {
                        atmConsole.sendMessageToScreen("Account blocked");

                        atmConsole.sendMessageToScreen("Choose an option: ");
                        atmConsole.sendMessageToScreen("1. Exit");
                        int choice = consoleChoice();

                        switch (choice) {
                            case 1:
                                checkCardNumber();
                                break;
                        }
                    }

                } else {
                    atmConsole.sendMessageToScreen("Card number not found");
                }

            } catch (Exception ex) {
                atmConsole.sendMessageToScreen("Error");
            }
        } while (!cardFoundStatus);
    }


    public void checkCardAuthorizationData() {
        int attempts = 0;

        do {
            atmConsole.sendMessageToScreen("Enter pin conde");

            String cardPinCode = scanner.nextLine();

            if (cardPinCode.length() != 4) {
                atmConsole.sendMessageToScreen("Invalid pin length. PIN should be 4 digits.");
                continue;
            }

            if (!cardPinCode.matches("\\d+")) {
                atmConsole.sendMessageToScreen("Invalid characters entered. PIN should only contain digits.");
                continue;
            }

            boolean pinValidated = bankAccountHandler.checkCardPinCode(cardNumber, cardPinCode);
            if (pinValidated) {
                atmConsole.sendMessageToScreen("Pin code invalidated");
                return;
            } else {
                attempts++;
                atmConsole.sendMessageToScreen("Invalid pin. Attempt left " + (3 - attempts));
            }
        } while (attempts < 3);

        atmConsole.sendMessageToScreen("Limit of attempts exceeded. Your account is blocked for a day. ");

        bankAccountHandler.blockAccount(cardNumber);

        atmConsole.sendMessageToScreen("Choose an option: ");
        atmConsole.sendMessageToScreen("1. Exit");
        int choice = consoleChoice();

        switch (choice) {
            case 1:
                checkCardNumber();
                break;
        }
    }

    public int consoleChoice() {

        int choice = Integer.parseInt(scanner.nextLine());

        return choice;
    }

    public boolean checkAccountBlocking() {

        long currentBlockTime = bankAccountHandler.currentAccountBlockTime(cardNumber);

        return System.currentTimeMillis() - currentBlockTime <= 86400000;
    }


    public static boolean isValidFormat(String cardNumber) {

        String regex = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";

        return cardNumber.matches(regex);
    }

    public void checkCardBalance() {
        if (this.cardNumber == null) {
            atmConsole.sendMessageToScreen("Card number not authorized. Please authorize first.");
        } else {
            BigDecimal balance = bankAccountHandler.checkCardBalance(this.cardNumber);
            atmConsole.sendMessageToScreen("Your balance is: " + balance);
        }
    }

    public void withdrawFromCardBalance() {

        if (this.cardNumber == null) {
            atmConsole.sendMessageToScreen("Card number not authorized. Please authorize first.");
            return;
        }

        atmConsole.sendMessageToScreen("Enter amount to withdraw: ");
        BigDecimal withdrawAmount;
        try {
            withdrawAmount = new BigDecimal(scanner.nextLine());
        } catch (NumberFormatException e) {
            atmConsole.sendMessageToScreen("Invalid amount entered.");
            return;
        }

        BigDecimal accountBalance = bankAccountHandler.checkCardBalance(cardNumber);
        BigDecimal atmBalance = atmDataFileProvider.getBalance();

        if (withdrawAmount.compareTo(atmBalance) > 0) {
            atmConsole.sendMessageToScreen("ATM has insufficient funds.");
        } else if (withdrawAmount.compareTo(accountBalance) > 0) {
            atmConsole.sendMessageToScreen("Insufficient funds in your account.");
        } else {
            BigDecimal newAccountBalance = accountBalance.subtract(withdrawAmount);
            BigDecimal newATMBalance = atmBalance.subtract(withdrawAmount);

            bankAccountHandler.updateCardBalance(cardNumber, newAccountBalance);
            atmDataFileProvider.saveBalance(newATMBalance);
        }
    }

    public void depositToCardBalance() {

        if (this.cardNumber == null) {
            atmConsole.sendMessageToScreen("Card number not authorized. Please authorize first.");
            return;
        }

        atmConsole.sendMessageToScreen("Enter amount to deposit: ");
        BigDecimal depositAmount;
        try {
            depositAmount = new BigDecimal(scanner.nextLine());
        } catch (NumberFormatException e) {
            atmConsole.sendMessageToScreen("Invalid amount entered.");
            return;
        }

        BigDecimal cardReplenishmentLimit = bankAccountHandler.cardReplenishmentLimit(cardNumber);

        if (depositAmount.compareTo(cardReplenishmentLimit) > 0) {
            atmConsole.sendMessageToScreen("Deposit amount exceeds card replenishment limit.");
        } else {
            BigDecimal accountBalance = bankAccountHandler.checkCardBalance(cardNumber);
            BigDecimal newAccountBalance = accountBalance.add(depositAmount);

            bankAccountHandler.updateCardBalance(cardNumber, newAccountBalance);

            BigDecimal atmBalance = atmDataFileProvider.getBalance();
            BigDecimal newATMBalance = atmBalance.add(depositAmount);

            atmDataFileProvider.saveBalance(newATMBalance);
        }
    }
}
