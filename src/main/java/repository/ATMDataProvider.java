package src.main.java.repository;

import src.main.java.exception.FaildSaveFileException;
import src.main.java.exception.InvalidReadingFileException;
import src.main.java.model.ATM;

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
                if (splitLine.length != 1) {
                    throw new InvalidReadingFileException("Invalid reading file: " + fileName);
                }
                String atmBalanceStr = splitLine[0];
                BigDecimal atmBalance = new BigDecimal(atmBalanceStr);

                atm.add(new ATM(atmBalance));
            }
        } catch (InvalidReadingFileException | IOException ex) {
            throw new InvalidReadingFileException("Invalid reading file: " + fileName);
        }
        return atm;
    }

    public void saveATM(List<ATM> atms) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            for (ATM atm : atms) {
                bufferedWriter.write(String.format("%s\n",
                        atm.getAtmBalance()));
            }
        } catch (FaildSaveFileException | IOException ex) {
            System.out.print("Error saving accounts " + fileName);
        }
    }
}
