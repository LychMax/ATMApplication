package src.main.java.repository;

import java.io.*;
import java.math.BigDecimal;

public class ATMDataFileProvider {

    private final String fileName;

    public ATMDataFileProvider(String fileName) {
        this.fileName = fileName;
    }

    public BigDecimal getBalance() {
        BigDecimal balance = BigDecimal.ZERO;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            if ((line = bufferedReader.readLine()) != null) {
                balance = new BigDecimal(line);
            }
        } catch (IOException | NumberFormatException ex) {
            System.out.println("Error loading balance: " + ex.getMessage());
        }
        return balance;
    }

    public void saveBalance(BigDecimal balance) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            bufferedWriter.write(balance.toString());
        } catch (IOException e) {
            System.out.print("Error saving balance: " + e.getMessage());
        }
    }
}
