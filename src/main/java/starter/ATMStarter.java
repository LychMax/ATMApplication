package src.main.java.starter;

import src.main.java.display.ATMConsole;
import src.main.java.handlers.ATMHandler;
import src.main.java.handlers.BankAccountHandler;
import src.main.java.repository.ATMDataFileProvider;
import src.main.java.repository.AccountFileDataProvider;

public class ATMStarter {

    public static void startATMConsole() {

        AccountFileDataProvider accountFileDataProvider = new AccountFileDataProvider(
                "/Users/maksimlych/Desktop/Projects/Senla/ATMApplication/accounts.txt");
        ATMDataFileProvider atmDataFileProvider = new ATMDataFileProvider(
                "/Users/maksimlych/Desktop/Projects/Senla/ATMApplication/ATMBalance.txt");

        ATMConsole atmConsole = new ATMConsole(null);
        BankAccountHandler bankAccountHandler = new BankAccountHandler(accountFileDataProvider);

        ATMHandler atmHandler = new ATMHandler(bankAccountHandler, atmConsole, atmDataFileProvider);
        atmConsole = new ATMConsole(atmHandler);

        atmConsole.startConsole();
    }
}
