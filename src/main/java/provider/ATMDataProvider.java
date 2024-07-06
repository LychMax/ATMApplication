package src.main.java.provider;

import src.main.java.exception.ATMDataNotFoundException;
import src.main.java.exception.FaildSaveFileException;
import src.main.java.exception.InvalidReadingFileException;
import src.main.java.model.ATMData;

import java.io.*;
import java.math.BigDecimal;

public class ATMDataProvider {

    private final String fileName;

    public ATMDataProvider(final String fileName) {
        this.fileName = fileName;
    }

    public ATMData getATMData() {
        String atmDataStr;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            atmDataStr = bufferedReader.readLine();
        } catch (IOException ex) {
            throw new InvalidReadingFileException("Unexpected exception during ATM data file reading. ex = " + ex);
        }

        if (atmDataStr == null) {
            throw new ATMDataNotFoundException();
        }

        String[] splitLine = atmDataStr.split(" ");
        if (splitLine.length != 1) {
            throw new InvalidReadingFileException("Unexpected ATM data format.");
        }

        try {
            return new ATMData(new BigDecimal(splitLine[0]));
        } catch (Exception ex) {
            throw new InvalidReadingFileException("Exception during ATM balance parsing. balanceStr = "
                    + splitLine[0] + " ex = " + ex);
        }
    }

    public void updateATMData(ATMData atmData) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName))) {
            bufferedWriter.write(String.format("%s\n",
                    atmData.getBalance().stripTrailingZeros().toPlainString()));
        } catch (FaildSaveFileException | IOException ex) {
           throw new FaildSaveFileException("Exception during ATM data file update. Ex= " + ex);
        }
    }
}
