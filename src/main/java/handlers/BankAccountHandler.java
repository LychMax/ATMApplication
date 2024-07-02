package src.main.java.handlers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class BankAccountHandler {

    public List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split(" ");
                String cardName = splitLine[0];
                String pinCode = splitLine[1];
                double balance = Double.parseDouble(splitLine[2]);
                boolean status = Boolean.parseBoolean(splitLine[3]);
                long blockTimesTemp = Long.parseLong(splitLine[4]);

                accounts.add(new Account(cardName, pinCode, balance, status, blockTimesTemp));
            }
        } catch (Exception ex) {
            System.out.print("Error loading accounts: " + ex.getMessage());
        }
        return accounts;
    }

}
