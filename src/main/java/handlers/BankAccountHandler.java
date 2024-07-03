package src.main.java.handlers;

import src.main.java.models.Account;
import src.main.java.repository.AccountFileDataProvider;

import java.math.BigDecimal;
import java.util.List;


public class BankAccountHandler {

    AccountFileDataProvider accountFileDataProvider;
    private Account session;

    public BankAccountHandler(AccountFileDataProvider accountFileDataProvider) {
        this.accountFileDataProvider = accountFileDataProvider;
    }

    public boolean checkCardExistence(String cardNumber) {

        session = accountFileDataProvider.findAccountByCardNumber(cardNumber);

        return session != null;
    }

    public boolean checkCardPinCode(String cardNumber, String pinCode) {

        session = accountFileDataProvider.checkAccountPinCode(cardNumber, pinCode);

        return session!= null;
    }

    public BigDecimal checkCardBalance(String cardNumber) {

       BigDecimal balance = accountFileDataProvider.checkAccountBalance(cardNumber);

       return balance;
    }

    public void updateCardBalance (String cardNumber, BigDecimal newCardBalance) {
        accountFileDataProvider.updateAccountBalance(cardNumber, newCardBalance);
    }

    public BigDecimal cardReplenishmentLimit (String cardNumber) {
        BigDecimal cardReplenishmentLimit = accountFileDataProvider.checkAccountLimit(cardNumber);

        return cardReplenishmentLimit;
    }

    public void blockAccount(String cardNumber) {
        accountFileDataProvider.blockAccount(cardNumber);

    }

    public long currentAccountBlockTime(String cardNumber) {
        long currentAccountBlockTime = accountFileDataProvider.checkAccountBlockUntil(cardNumber);
        return currentAccountBlockTime;
    }
}
