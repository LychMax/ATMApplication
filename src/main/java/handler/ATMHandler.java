package src.main.java.handler;

import src.main.java.exception.*;
import src.main.java.model.ATM;
import src.main.java.repository.ATMDataProvider;

import java.math.BigDecimal;
import java.util.List;

public class ATMHandler {

    BankAccountHandler bankAccountHandler;
    private String cardNumber;
    ATMDataProvider atmDataProvider;
    int attempts = 0;

    public ATMHandler(BankAccountHandler bankAccountHandler,
                      ATMDataProvider atmDataProvider) {
        this.bankAccountHandler = bankAccountHandler;
        this.atmDataProvider = atmDataProvider;
    }

    public boolean checkCardNumber(String cardNumber) {
        boolean cardFoundStatus;

        try {
            if (isValidFormat(cardNumber)) {
                cardFoundStatus = bankAccountHandler.checkCardExistence(cardNumber);
                if (cardFoundStatus) {
                    if (bankAccountHandler.checkAccountBlocked(cardNumber)) {
                        throw new AccountBlockedException("Account blocked");
                    }
                    this.cardNumber = cardNumber;
                } else {
                    throw new CardNotFoundException("Card not found");
                }
            } else {
                throw new InvalidCardNumberException("Invalid card number");
            }
        } catch (CheckCardNumberException ex) {
            throw new CheckCardNumberException("Something went wrong");
        }
        return cardFoundStatus;
    }


    public boolean checkCardPassword(String cardPinCode) {
        boolean pinValidated = bankAccountHandler.checkCardPinCode(cardPinCode);
        if (!pinValidated) {
            attempts++;
            if (attempts >= 3) {
                bankAccountHandler.blockAccount(cardNumber);

                throw new PinAttemptsExceededException("Limit of attempts exceeded. Your account is blocked for a day.");
            } else {
                throw new InvalidPinException("Attempts remained: " + (3 - attempts));
            }
        }
        return pinValidated;
    }


    public static boolean isValidFormat(String cardNumber) {

        String regex = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";

        return cardNumber.matches(regex);
    }

    public BigDecimal getCardBalance() {

        return bankAccountHandler.getCardBalance(cardNumber);
    }

    public BigDecimal getATMBalance() {
        List<ATM> atms = atmDataProvider.getATM();
        for (ATM atm : atms) {
            BigDecimal atmBalance = atm.getAtmBalance();
            return atmBalance;
        }
        return null;
    }

    public void setATMBalance(BigDecimal newATMBalance) {
        List<ATM> atms = atmDataProvider.getATM();
        for (ATM atm : atms) {
            atm.setAtmBalance(newATMBalance);
            atmDataProvider.saveATM(atms);
        }
    }

    public void withdrawFromCardBalance(BigDecimal withdrawAmount) {

        BigDecimal accountBalance = bankAccountHandler.getCardBalance(cardNumber);

        if (withdrawAmount.compareTo(getATMBalance()) > 0) {
            throw new InsufficientFundsOnATMException("ATM has insufficient funds.");
        } else if (withdrawAmount.compareTo(accountBalance) > 0) {
            throw new InsufficientFundsOnAccountException("Insufficient funds in your account.");
        } else {
            BigDecimal newAccountBalance = accountBalance.subtract(withdrawAmount);
            BigDecimal newATMBalance = getATMBalance().subtract(withdrawAmount);

            bankAccountHandler.updateCardBalance(cardNumber, newAccountBalance);

            setATMBalance(newATMBalance);
        }
    }

    public void depositToCardBalance(BigDecimal depositAmount) {

        BigDecimal cardLimit = bankAccountHandler.getCardLimit(cardNumber);

        if (depositAmount.compareTo(cardLimit) > 0) {
            throw new AccountLimitExceededException("Deposit amount exceeds card replenishment limit.");
        } else {

            BigDecimal accountBalance = bankAccountHandler.getCardBalance(cardNumber);
            BigDecimal newAccountBalance = accountBalance.add(depositAmount);

            bankAccountHandler.updateCardBalance(cardNumber, newAccountBalance);

            BigDecimal newATMBalance = getATMBalance().add(depositAmount);

            setATMBalance(newATMBalance);
        }
    }
}
