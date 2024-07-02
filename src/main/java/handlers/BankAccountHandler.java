package src.main.java.handlers;

import src.main.java.models.Account;
import src.main.java.repository.ATMBalanceManager;
import src.main.java.repository.AccountFileManager;

import java.math.BigDecimal;
import java.util.List;


public class BankAccountHandler {

    private Account session;

    public BankAccountHandler(String accountDataFileName, String atmBalanceDataFileName) {
        AccountFileManager accountFileManager = new AccountFileManager(accountDataFileName);
        List<Account> accounts = accountFileManager.getAccounts();

        ATMBalanceManager atmBalanceManager = new ATMBalanceManager(atmBalanceDataFileName);
        BigDecimal atmBalance = atmBalanceManager.getBalance();
    }

    public void cardCheck(String cardNumber){

    }

}
