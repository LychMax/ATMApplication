package src.main.java.handler;

import src.main.java.exception.*;
import src.main.java.model.ATMData;
import src.main.java.provider.ATMDataProvider;

import java.math.BigDecimal;

public class ATMHandler {

    private final BankAccountHandler bankAccountHandler;
    private final ATMDataProvider atmDataProvider;

    private String cardNumber;
    private int attempts = 0;

    public ATMHandler(BankAccountHandler bankAccountHandler,
                      ATMDataProvider atmDataProvider) {
        this.bankAccountHandler = bankAccountHandler;
        this.atmDataProvider = atmDataProvider;
    }

    public void checkCardNumber(String cardNumber) {
        if (!isValidFormat(cardNumber)) {
            throw new InvalidCardNumberException("Invalid card number");
        }

        if (!bankAccountHandler.isCardExist(cardNumber)) {
            throw new CardNotFoundException("Card not found");
        }

        if (bankAccountHandler.isAccountBlocked(cardNumber)) {
            throw new AccountBlockedException("Account blocked");
        }

        this.cardNumber = cardNumber;
        this.attempts = 0;
    }

    public void checkCardPinCode(String cardPinCode) {
        if (bankAccountHandler.checkCardPinCode(cardNumber, cardPinCode)) {
            return;
        }

        attempts++;
        if (attempts >= 3) {
            bankAccountHandler.blockAccount(cardNumber);
            throw new PinAttemptsExceededException("Limit of attempts exceeded. Your account is blocked for a day.");
        }

        throw new InvalidPinException("Invalid pin");
    }

    public BigDecimal getCardBalance() {
        return bankAccountHandler.getCardBalance(cardNumber);
    }

    public void withdrawFromCardBalance(BigDecimal withdrawAmount) {

        BigDecimal accountBalance = bankAccountHandler.getCardBalance(cardNumber);
        BigDecimal atmBalance = getATMBalance();

        if (withdrawAmount.compareTo(atmBalance) > 0) {
            throw new InsufficientFundsOnATMException("ATMData has insufficient funds.");
        } else if (withdrawAmount.compareTo(accountBalance) > 0) {
            throw new InsufficientFundsOnAccountException("Insufficient funds in your account.");
        } else {
            BigDecimal newAccountBalance = accountBalance.subtract(withdrawAmount);
            BigDecimal newATMBalance = atmBalance.subtract(withdrawAmount);

            bankAccountHandler.updateCardBalance(cardNumber, newAccountBalance);

            setATMBalance(newATMBalance);
        }
    }

    public void creditToCardBalance(BigDecimal depositAmount) {

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

    private void setATMBalance(BigDecimal newATMBalance) {
        ATMData atmData = atmDataProvider.getATMData();
        atmData.setBalance(newATMBalance);
        atmDataProvider.updateATMData(atmData);
    }

    private BigDecimal getATMBalance() {
        ATMData atmData = atmDataProvider.getATMData();
        if (atmData == null) {
            throw new ATMDataNotFoundException();
        }
        return atmData.getBalance();
    }

    private boolean isValidFormat(String cardNumber) {

        String regex = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";

        return cardNumber.matches(regex);
    }
}
