package src.main.java.repository;

import src.main.java.models.Account;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountFileDataProvider {

    private final String fileName;

    public AccountFileDataProvider(String fileName) {
        this.fileName = fileName;
    }

    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split("\\s+");
                if (splitLine.length >= 5) {
                    String cardNumber = splitLine[0];
                    String pinCode = splitLine[1];
                    BigDecimal balance;
                    try {
                        String balanceStr = splitLine[2].replace(",", ".");
                        balance = new BigDecimal(balanceStr);
                    } catch (NumberFormatException e) {
                        continue;
                    }
                    long blockUntil;
                    try {
                        blockUntil = Long.parseLong(splitLine[3]);
                    } catch (NumberFormatException e) {
                        continue;
                    }
                    BigDecimal cardLimit;
                    try {
                        String cardLimitStr = splitLine[4].replace(",", ".");
                        cardLimit = new BigDecimal(cardLimitStr);
                    } catch (NumberFormatException e) {
                        continue;
                    }

                    accounts.add(new Account(cardNumber, pinCode, balance, blockUntil, cardLimit));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return accounts;
    }


    public void saveAccounts(List<Account> accounts) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (Account account : accounts) {
                bufferedWriter.write(String.format("%s %s %.2f %d %.2f%n",
                        account.getCardNumber(), account.getPinCode(),
                        account.getBalance(), account.getBlockUntil(), account.getLimit()));
            }
        } catch (Exception ex) {
            System.out.print("Error loading accounts: " + ex.getMessage());
        }
    }

    public Account findAccountByCardNumber(String cardNumberToFind) {
        List<Account> accounts = getAccounts();
        for (Account account : accounts) {
            if (account.getCardNumber().equals(cardNumberToFind)) {
                return account;
            }
        }
        return null;
    }

    public Account checkAccountPinCode(String cardNumberToCheck, String pinCodeToCheck) {
        List<Account> accounts = getAccounts();
        for (Account account : accounts) {
            if (account.getCardNumber().equals(cardNumberToCheck) && account.getPinCode().equals(pinCodeToCheck)) {
                return account;
            }
        }
        return null;
    }

    public BigDecimal checkAccountBalance(String cardNumberToCheck) {
        List<Account> accounts = getAccounts();
        for (Account account : accounts) {
            if (account.getCardNumber().equals(cardNumberToCheck)) {
                return account.getBalance();
            }
        }
        return null;
    }

    public void updateAccountBalance(String cardNumberToUpdate, BigDecimal balanceToUpdate) {
        List<Account> accounts = getAccounts();
        for (Account account : accounts) {
            if (account.getCardNumber().equals(cardNumberToUpdate)) {
                account.setBalance(balanceToUpdate);
            }
        }
        saveAccounts(accounts);
    }

    public BigDecimal checkAccountLimit(String cardNumberToCheck) {
        List<Account> accounts = getAccounts();
        for (Account account : accounts) {
            if (account.getCardNumber().equals(cardNumberToCheck)) {
                return account.getLimit();
            }
        }
        return null;
    }

    public void blockAccount(String cardNumberToBlock) {
        List<Account> accounts = getAccounts();
        for (Account account : accounts) {
            if (account.getCardNumber().equals(cardNumberToBlock)) {
                account.setBlockUntil(System.currentTimeMillis() + 86400000);
            }
        }
        saveAccounts(accounts);
    }

    public long checkAccountBlockUntil(String cardNumberToCheck) {
        List<Account> accounts = getAccounts();
        for (Account account : accounts) {
            if (account.getCardNumber().equals(cardNumberToCheck)) {
                return account.getBlockUntil();
            }
        }
        return 0;
    }
}
