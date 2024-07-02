package src.main.java.repository;

import java.math.BigDecimal;

public class Account {
    private String cardNumber;
    private String pinCode;
    private BigDecimal balance;
    private long blockUntill;

    public Account(String cardNumber, BigDecimal balance, String pinCode, long blockUntill) {
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.pinCode = pinCode;
        this.blockUntill = blockUntill;
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

    public long getBlockUntill() {
        return blockUntill;
    }

    public void setBlockUntill(long blockUntill) {
        this.blockUntill = blockUntill;
    }
}
