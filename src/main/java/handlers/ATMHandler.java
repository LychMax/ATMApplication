package src.main.java.handlers;

import java.util.Scanner;

public class ATMHandler {

    BankAccountHandler bankAccountHandler;

    public ATMHandler(BankAccountHandler bankAccountHandler) {
        this.bankAccountHandler = bankAccountHandler;
    }

    public void readCardNumber() {

        try (Scanner scanner = new Scanner(System.in)) {
            String cardNumber = scanner.nextLine();

            if (isValidFormat(cardNumber)) {
                bankAccountHandler.cardCheck(cardNumber);
            }else{
                System.out.println("Invalid card format. ");
            }

        } catch (Exception ex) {

        }
    }

    public static boolean isValidFormat(String cardNumber) {

        String regex = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";

        return cardNumber.matches(regex);
    }
}
