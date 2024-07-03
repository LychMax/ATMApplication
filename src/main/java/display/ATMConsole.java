package src.main.java.display;

import src.main.java.handlers.ATMHandler;


public class ATMConsole {

    private final ATMHandler atmHandler;

    public ATMConsole(ATMHandler atmHandler) {
        this.atmHandler = atmHandler;
    }

    public void startConsole() {
        atmHandler.checkCardNumber();

        atmHandler.checkCardAuthorizationData();

        int choice;

        do {
            System.out.println("1. Check balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            choice = atmHandler.consoleChoice();

            switch (choice) {
                case 1:
                    atmHandler.checkCardBalance();
                    break;
                case 2:
                    atmHandler.withdrawFromCardBalance();
                    break;
                case 3:
                    atmHandler.depositToCardBalance();
                    break;
                case 4:
                    atmHandler.checkCardNumber();
                    break;
            }
        } while (choice != 4);

    }

    public void sendMessageToScreen(String message) {
        System.out.println(message);
    }
}

