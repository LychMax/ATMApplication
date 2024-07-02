package src.main.java.display;

import src.main.java.handlers.ATMHandler;


public class ATMConsole {

    private final ATMHandler atmHandler;

    public ATMConsole(ATMHandler atmHandler) {
        this.atmHandler = atmHandler;
    }

    public void display() {
        System.out.println("Enter card number: ");
        atmHandler.checkCard();
    }
}
