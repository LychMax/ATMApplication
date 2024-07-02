package src.main.java.display;

import src.main.java.handlers.ATMHandler;

import java.util.Scanner;

public class ATMDisplay {

    ATMHandler atmHandler;

    public ATMDisplay(ATMHandler atmHandler) {
        this.atmHandler = atmHandler;
    }

    public void display() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("1. Check balance");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                int choice = Integer.parseInt(scanner.nextLine());

            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
