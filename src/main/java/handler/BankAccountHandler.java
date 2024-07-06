package src.main.java.handler;

import src.main.java.model.AccountData;
import src.main.java.provider.AccountDataProvider;

import java.math.BigDecimal;

public class BankAccountHandler {

    private static final long BLOCKING_DURATION = 86400000L;

    private final AccountDataProvider accountDataProvider;

    public BankAccountHandler(final AccountDataProvider accountDataProvider) {
        this.accountDataProvider = accountDataProvider;
    }

    public boolean isCardExist(String cardNumber) {
        return accountDataProvider.getAccountData(cardNumber) != null;
    }

    public boolean checkCardPinCode(String cardNumber, String pinCode) {
        AccountData accountData = accountDataProvider.getAccountData(cardNumber);

        return accountData.getPinCode().equals(pinCode);
    }

    public void blockAccount(String cardNumber) {
        AccountData accountData = accountDataProvider.getAccountData(cardNumber);
        accountData.setBlockedUntil(System.currentTimeMillis() + BLOCKING_DURATION);

        accountDataProvider.saveAccount(accountData);
    }

    public BigDecimal getCardBalance(String cardNumber) {
        return accountDataProvider.getAccountData(cardNumber).getBalance();
    }

    public void updateCardBalance(String cardNumber, BigDecimal newCardBalance) {
        AccountData accountData = accountDataProvider.getAccountData(cardNumber);
        accountData.setBalance(newCardBalance);

        accountDataProvider.saveAccount(accountData);
    }

    public BigDecimal getCardLimit(String cardNumber) {
        return accountDataProvider.getAccountData(cardNumber).getCreditLimit();
    }

    public boolean isAccountBlocked(String cardNumber) {
        AccountData accountData = accountDataProvider.getAccountData(cardNumber);

        return System.currentTimeMillis() <= accountData.getBlockedUntil();
    }
}
