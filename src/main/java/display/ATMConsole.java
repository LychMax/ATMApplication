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

        boolean isValidCard = false;
        boolean isValidPassword = false;

        try (Scanner scanner = new Scanner(System.in)) {
            while (!isValidCard) {
                try {
                    System.out.println("Enter card number");
                    String cardNumber = scanner.nextLine();

                    if (atmHandler.checkCardNumber(cardNumber)) {
                        System.out.println("You have entered a valid card number");
                        isValidCard = true;
                    }

                } catch (CheckCardNumberException ex) {
                    System.out.println("Error: Card verification error.");
                } catch (InvalidCardNumberException ex) {
                    System.out.println("Error: Invalid card number.");
                } catch (CardNotFoundException ex) {
                    System.out.println("Error: Card number not found.");
                } catch (AccountBlockedException ex) {
                    System.out.println("Account blocked.");
                } catch (Exception ex) {
                    System.out.println("Something went wrong");
                }
            }

            while (!isValidPassword) {
                try {
                    System.out.println("Enter pin code: ");
                    String pinCode = scanner.nextLine();

                    if (atmHandler.checkCardPassword(pinCode)) {
                        System.out.println("You have entered a valid pin code");
                        isValidPassword = true;
                    }
                } catch (InvalidPinException ex) {
                    System.out.println("Error: Invalid PIN. " + ex.getMessage());

                } catch (PinAttemptsExceededException ex) {
                    System.out.println("Error: Password entry attempts exceeded. The account was blocked for a day. ");
                    startATM();
                } catch (Exception ex) {
                    System.out.println("Error: Something went wrong: ");
                }
            }


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
                        BigDecimal cardBalance = atmHandler.getCardBalance();
                        System.out.println("Card balance: " + cardBalance);
                        break;
                    case 2:
                        System.out.println("Enter amount to withdraw: ");
                        BigDecimal withdrawAmount = scanner.nextBigDecimal();

                        atmHandler.withdrawFromCardBalance(withdrawAmount);
                        break;
                    case 3:
                        System.out.println("Enter amount to deposit: ");
                        BigDecimal depositAmount = scanner.nextBigDecimal();

                        atmHandler.depositToCardBalance(depositAmount);
                        break;
                    case 4:
                        System.out.println("Exiting... Thank you for using our ATM.");
                        startATM();
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } while (choice != 4);

        } catch (InputMismatchException ex) {
            System.out.println("sd");
        } catch (InsufficientFundsOnATMException ex) {
            System.out.println("Error: Insufficient funds on ATM.");
        } catch (InsufficientFundsOnAccountException ex) {
            System.out.println("Error: Insufficient funds on account.");
        } catch (AccountLimitExceededException ex) {
            System.out.println("Error: Account limit exceeded.");
        } catch (Exception ex) {
            System.out.println("Something went wrong");
            startATM();
        }
    }
}


