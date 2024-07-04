package src.main.java.repository;

import src.main.java.model.Account;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountDataProvider {

    private final String fileName;

    public AccountDataProvider(final String fileName) {
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
                        String balanceStr = splitLine[2];
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
                        String cardLimitStr = splitLine[4];
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
                bufferedWriter.write(String.format("%s %s %s %d %s\n",
                        account.getCardNumber(), account.getPinCode(),
                        account.getBalance().stripTrailingZeros().toPlainString(),
                        account.getBlockUntil(), account.getLimit().stripTrailingZeros().toPlainString()));
            }
        } catch (Exception ex) {
            System.out.print("Error loading accounts: " + ex.getMessage());
        }
    }
}
