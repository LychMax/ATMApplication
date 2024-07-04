package src.main.java.starter;

import src.main.java.repository.ATMDataProvider;
import src.main.java.repository.AccountDataProvider;

public class ProviderStarter {

    AccountDataProvider accountDataProvider = new AccountDataProvider(
            "accounts.txt");
    ATMDataProvider atmDataProvider = new ATMDataProvider(
            "atm.txt");
}
