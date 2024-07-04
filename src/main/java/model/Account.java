package src.main.java.model;

import java.math.BigDecimal;

public class Account {
    private String cardNumber;
    private String pinCode;
    private BigDecimal balance;
    private long blockUntil;
    private BigDecimal cardLimit;

    public Account(String cardNumber, String pinCode, BigDecimal balance, long blockUntil, BigDecimal cardLimit) {
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.balance = balance;
        this.blockUntil = blockUntil;
        this.cardLimit = cardLimit;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public long getBlockUntil() {
        return blockUntil;
    }

    public void setBlockUntil(long blockUntil) {
        this.blockUntil = blockUntil;
    }

    public BigDecimal getLimit() {
        return cardLimit;
    }

    public void setLimit(BigDecimal limit) {
        this.cardLimit = limit;
    }
}
