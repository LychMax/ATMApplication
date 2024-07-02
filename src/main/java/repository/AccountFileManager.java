package src.main.java.repository;

import src.main.java.models.Account;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountFileManager {

    private final String fileName;

    public AccountFileManager(String fileName) {
        this.fileName = fileName;
    }

    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split(" ");
                String cardNumber = splitLine[0];
                String pinCode = splitLine[1];
                BigDecimal balance = new BigDecimal(splitLine[2]);
                long blockUntil = Long.parseLong(splitLine[3]);
                BigDecimal cardLimit = new BigDecimal(splitLine[4]);

                accounts.add(new Account(cardNumber, pinCode, balance, blockUntil, cardLimit));
            }
        } catch (Exception ex) {
            System.out.print("Error loading accounts: " + ex.getMessage());
        }
        return accounts;
    }

    public void saveAccounts(List<Account> accounts) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (Account account : accounts) {
                bufferedWriter.write(String.format("%s %s %.2f %b %d%d",
                        account.getCardNumber(), account.getPinCode(),
                        account.getBalance(), account.getBlockUntill(), account.getLimit()));
            }
        } catch (Exception ex) {
            System.out.print("Error loading accounts: " + ex.getMessage());
        }
    }
}
