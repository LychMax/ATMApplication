package src.main.java.display;

import src.main.java.exception.*;
import src.main.java.handler.ATMHandler;

import java.math.BigDecimal;
import java.util.Scanner;

public class ATMConsole {

    private final ATMHandler atmHandler;

    public ATMConsole(ATMHandler atmHandler) {
        this.atmHandler = atmHandler;
    }

    public void startATM() {
        try (Scanner scanner = new Scanner(System.in)) {
            do {
                if (!enterCardNumber(scanner)) {
                    continue;
                }
                if (!enterPinCode(scanner)) {
                    continue;
                }
                showMainMenu(scanner);
            } while (true);
        } catch (Exception ex) {
            System.out.println("Something went wrong.");
        }
    }

    private boolean enterCardNumber(Scanner scanner) {
        try {
            System.out.println("Enter card number: ");
            String cardNumber = scanner.next();
            atmHandler.checkCardNumber(cardNumber);
            System.out.println("You have entered a valid card number.");
            return true;
        } catch (CheckCardNumberException ex) {
            System.out.println("Card verification error.");
        } catch (InvalidCardNumberException ex) {
            System.out.println("Invalid card number.");
        } catch (CardNotFoundException ex) {
            System.out.println("Card number not found.");
        } catch (AccountBlockedException ex) {
            System.out.println("Account blocked.");
        } catch (Exception ex) {
            System.out.println("Something went wrong.");
        }
        return false;
    }

    private boolean enterPinCode(Scanner scanner) {
        do {
            System.out.println("Enter pin code: ");
            String pinCode = scanner.next();
            try {
                atmHandler.checkCardPinCode(pinCode);
                System.out.println("You have entered a valid pin code.");
                return true;
            } catch (InvalidPinException ex) {
                System.out.println("Invalid PIN. Please try again.");
            } catch (PinAttemptsExceededException ex) {
                System.out.println("Password entry attempts exceeded. The account was blocked for a day.");
                return false;
            } catch (Exception ex) {
                System.out.println("Something went wrong.");
            }
        } while (true);
    }


    private void showMainMenu(Scanner scanner) {
        int choice;
        do {
            System.out.println("1. Check balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    withdraw(scanner);
                    break;
                case 3:
                    deposit(scanner);
                    break;
                case 4:
                    System.out.println("Exiting... Thank you for using our ATM.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 4);
    }

    private void checkBalance() {
        try {
            BigDecimal cardBalance = atmHandler.getCardBalance();
            System.out.println("Card balance: " + cardBalance);
        } catch (Exception ex) {
            System.out.println("Failed to retrieve balance.");
        }
    }

    private void withdraw(Scanner scanner) {
        try {
            System.out.println("Enter amount to withdraw: ");
            BigDecimal withdrawAmount = new BigDecimal(scanner.next());
            atmHandler.withdrawFromCardBalance(withdrawAmount);
            System.out.println("Withdrawal successful.");
        } catch (NumberFormatException ex) {
            System.out.println("Error: Invalid amount entered. Please enter a valid number.");
        } catch (InsufficientFundsOnATMException ex) {
            System.out.println("Error: Insufficient funds on ATM.");
        } catch (InsufficientFundsOnAccountException ex) {
            System.out.println("Error: Insufficient funds on account.");
        }
    }

    private void deposit(Scanner scanner) {
        try {
            System.out.println("Enter amount to deposit: ");
            BigDecimal depositAmount = new BigDecimal(scanner.next());
            atmHandler.creditToCardBalance(depositAmount);
            System.out.println("Deposit successful.");
        } catch (NumberFormatException ex) {
            System.out.println("Error: Invalid amount entered. Please enter a valid number.");
        } catch (AccountLimitExceededException ex) {
            System.out.println("Error: Account limit exceeded.");
        }
    }
}
