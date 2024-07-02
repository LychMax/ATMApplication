package src.main.java.handlers;

import java.util.Scanner;

public class ATMHandler {

    BankAccountHandler bankAccountHandler;

    public ATMHandler(BankAccountHandler bankAccountHandler) {
        this.bankAccountHandler = bankAccountHandler;
    }

    public void checkCard() {
        try (Scanner scanner = new Scanner(System.in)) {
            String cardNumber = scanner.nextLine();

            boolean cardFound;

            if (isValidFormat(cardNumber)) {
                cardFound = bankAccountHandler.cardCheck(cardNumber);
            } else {
                System.out.println("Invalid card number");
                return;
            }

            if (cardFound) {
                System.out.println("Card number found");

                String pinCode = scanner.nextLine();

                boolean pinValidated = bankAccountHandler.pinCheck(cardNumber, pinCode);
                if (pinValidated) {
                    System.out.println("PIN check successful.");
                } else {
                    System.out.println("Invalid PIN or error checking PIN.");
                }

            } else {
                System.out.println("Card number not found");
            }

        } catch (Exception ex) {
            System.out.println("Error");
        }
    }

    public static boolean isValidFormat(String cardNumber) {

        String regex = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";

        return cardNumber.matches(regex);
    }
}
