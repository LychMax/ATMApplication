package src.main.java.provider;

import src.main.java.exception.FaildSaveFileException;
import src.main.java.exception.InvalidReadingFileException;
import src.main.java.model.AccountData;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AccountDataProvider {

    private final String fileName;

    public AccountDataProvider(final String fileName) {
        this.fileName = fileName;
    }

    public AccountData getAccountData(String cardNumber) {
        AccountData accountData = null;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split(" ");
                if (splitLine.length != 5) {
                    throw new InvalidReadingFileException(fileName);
                }

                if (!cardNumber.equals(splitLine[0])) {
                    continue;
                }
                String pinCode = splitLine[1];
                BigDecimal balance = new BigDecimal(splitLine[2]);
                long blockUntil = Long.parseLong(splitLine[3]);
                BigDecimal cardLimit = new BigDecimal(splitLine[4]);

                accountData = new AccountData(cardNumber, pinCode, balance, blockUntil, cardLimit);
                break;
            }
        } catch (InvalidReadingFileException | IOException ex) {
            throw new InvalidReadingFileException("Unexpected exception during account data file reading. ex = " + ex);
        }
        return accountData;
    }

    public void saveAccount(AccountData accountData) {
        String accountDataStr = String.format("%s %s %s %d %s",
                accountData.getCardNumber(), accountData.getPinCode(),
                accountData.getBalance().stripTrailingZeros().toPlainString(),
                accountData.getBlockedUntil(),
                accountData.getCreditLimit().stripTrailingZeros().toPlainString());
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            lines.removeIf(line -> line.startsWith(accountData.getCardNumber().concat(" ")));
            lines.add(accountDataStr);
            Files.write(Paths.get(fileName), lines);
        } catch (FaildSaveFileException | IOException ex) {
            throw new FaildSaveFileException("Exception during account data file saving. Ex= " + ex);
        }
    }
}
