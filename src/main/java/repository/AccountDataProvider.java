package src.main.java.repository;

import src.main.java.exception.FaildSaveFileException;
import src.main.java.exception.InvalidReadingFileException;
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
                if (splitLine.length != 5) {
                    throw new InvalidReadingFileException(fileName);
                }
                String cardNumber = splitLine[0];
                String pinCode = splitLine[1];
                BigDecimal balance = new BigDecimal(splitLine[2]);
                long blockUntil = Long.parseLong(splitLine[3]);
                BigDecimal cardLimit = new BigDecimal(splitLine[4]);

                accounts.add(new Account(cardNumber, pinCode, balance, blockUntil, cardLimit));
            }
        } catch (InvalidReadingFileException | IOException ex) {
            throw new InvalidReadingFileException("Invalid reading file: " + fileName);
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
        } catch (FaildSaveFileException | IOException ex) {
            System.out.print("Error saving accounts " + fileName);
        }
    }
}
