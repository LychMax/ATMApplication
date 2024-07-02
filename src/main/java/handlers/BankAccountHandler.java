package src.main.java.handlers;

import src.main.java.models.Account;
import src.main.java.repository.AccountFileDataProvider;


public class BankAccountHandler {

    AccountFileDataProvider accountFileDataProvider;
    private Account session;

    public BankAccountHandler(AccountFileDataProvider accountFileDataProvider) {
        this.accountFileDataProvider = accountFileDataProvider;
    }

    public boolean cardCheck(String cardNumber) {

        session = accountFileDataProvider.findAccountByCardNumber(cardNumber);

        return session != null;
    }

    public boolean pinCheck(String cardNumber, String pin) {

        session = accountFileDataProvider.checkAccountPinCode(cardNumber, pin);

        return pin.equals(session.getPinCode());
    }
}
