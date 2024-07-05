package src.main.java.handler;

import src.main.java.model.Account;
import src.main.java.repository.AccountDataProvider;

import java.math.BigDecimal;
import java.util.List;

public class BankAccountHandler {

    AccountDataProvider accountDataProvider;

    public BankAccountHandler(AccountDataProvider accountDataProvider) {
        this.accountDataProvider = accountDataProvider;
    }

    public boolean checkCardExistence(String cardNumber) {

        List<Account> accounts = accountDataProvider.getAccounts();
        for (Account account : accounts) {
            if (account.getCardNumber().equals(cardNumber)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCardPinCode(String pinCode) {

        List<Account> accounts = accountDataProvider.getAccounts();
        for (Account account : accounts) {
            if (account.getPinCode().equals(pinCode)) {
                return true;
            }
        }
        return false;
    }

    public void blockAccount(String cardNumber) {
        List<Account> accounts = accountDataProvider.getAccounts();
        for (Account account : accounts) {
            if (account.getCardNumber().equals(cardNumber)) {
                account.setBlockUntil(System.currentTimeMillis() + 86400000);
            }
        }
        accountDataProvider.saveAccounts(accounts);
    }

    public BigDecimal getCardBalance(String cardNumber) {
        List<Account> accounts = accountDataProvider.getAccounts();
        for (Account account : accounts) {
            if (account.getCardNumber().equals(cardNumber)) {
                return account.getBalance();
            }
        }
        return null;
    }

    public void updateCardBalance(String cardNumber, BigDecimal newCardBalance) {
        List<Account> accounts = accountDataProvider.getAccounts();
        for (Account account : accounts) {
            if (account.getCardNumber().equals(cardNumber)) {
                account.setBalance(newCardBalance);
            }
        }
        accountDataProvider.saveAccounts(accounts);
    }

    public BigDecimal getCardLimit(String cardNumber) {
        List<Account> accounts = accountDataProvider.getAccounts();
        for (Account account : accounts) {
            if (account.getCardNumber().equals(cardNumber)) {
                return account.getLimit();
            }
        }
        return null;
    }

    public boolean checkAccountBlocked(String cardNumber) {
        List<Account> accounts = accountDataProvider.getAccounts();
        for (Account account : accounts) {
            if (account.getCardNumber().equals(cardNumber)) {
                long accountBlockUntil = account.getBlockUntil();

                return System.currentTimeMillis() - accountBlockUntil <= 86400000;
            }
        }
        return false;
    }
}
