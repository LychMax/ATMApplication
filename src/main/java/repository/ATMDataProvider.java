package src.main.java.repository;

import src.main.java.model.ATM;
import src.main.java.model.Account;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ATMDataProvider {

    private final String fileName;

    public ATMDataProvider(String fileName) {
        this.fileName = fileName;
    }

    public List<ATM> getATM() {
        List<ATM> atm = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split("\\s+");
                if (splitLine.length >= 1) {
                    BigDecimal atmBalance;
                    try {
                        String atmBalanceStr = splitLine[2];
                        atmBalance = new BigDecimal(atmBalanceStr);
                    } catch (NumberFormatException e) {
                        continue;
                    }
                    atm.add(new ATM(atmBalance));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return atm;
    }

    public void saveBalance(List<ATM> atms) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (ATM atm : atms) {
                bufferedWriter.write(String.format("%s\n",
                        atm.getAtmBalance()));
            }
        } catch (Exception ex) {
            System.out.print("Error loading ATM: " + ex.getMessage());
        }
    }
}
