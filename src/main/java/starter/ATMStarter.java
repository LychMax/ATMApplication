package src.main.java.starter;

import src.main.java.display.ATMConsole;
import src.main.java.handler.ATMHandler;
import src.main.java.handler.BankAccountHandler;

public class ATMStarter {

    public static void startATMWithConsoleInterface() {

        ProviderStarter providerStarter =  new ProviderStarter();

        ATMConsole atmConsole = new ATMConsole(null);
        BankAccountHandler bankAccountHandler = new BankAccountHandler(providerStarter.accountDataProvider);

        ATMHandler atmHandler = new ATMHandler(bankAccountHandler, atmConsole, providerStarter.atmDataProvider);
        atmConsole = new ATMConsole(atmHandler);

        atmConsole.startATM();
    }
}
