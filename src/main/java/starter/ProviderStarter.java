package src.main.java.starter;

import src.main.java.provider.ATMDataProvider;
import src.main.java.provider.AccountDataProvider;

public class ProviderStarter {
    public static AccountDataProvider initAccountDataProvider() {
        return new AccountDataProvider("accounts.txt");
    }

    public static ATMDataProvider initATMDataProvider() {
        return new ATMDataProvider("atm.txt");
    }
}
