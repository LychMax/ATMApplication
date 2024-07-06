package src.main.java.starter;

import src.main.java.display.ATMConsole;
import src.main.java.handler.ATMHandler;
import src.main.java.handler.BankAccountHandler;

public class ATMStarter {
    public static void startATMWithConsoleInterface() {
        BankAccountHandler bankAccountHandler = new BankAccountHandler(ProviderStarter.initAccountDataProvider());
        ATMHandler atmHandler = new ATMHandler(bankAccountHandler, ProviderStarter.initATMDataProvider());

        ATMConsole atmConsole = new ATMConsole(atmHandler);

        atmConsole.startATM();
    }
}
