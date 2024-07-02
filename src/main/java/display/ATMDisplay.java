package src.main.java.display;

import src.main.java.handlers.ATMHandler;

import java.util.Scanner;

public class ATMDisplay {

    private ATMHandler atmHandler;

    public ATMDisplay(ATMHandler atmHandler) {
        this.atmHandler = atmHandler;
    }


    public void display() {
        System.out.println("Enter card number: ");
        atmHandler.readCardNumber();
    }
}
