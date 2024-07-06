package src.main.java.model;

import java.math.BigDecimal;

public class AccountData {
    private String cardNumber;
    private String pinCode;
    private BigDecimal balance;
    private long blockedUntil;
    private BigDecimal creditLimit;

    public AccountData(final String cardNumber, final String pinCode,
                       final BigDecimal balance, final long blockedUntil, BigDecimal creditLimit) {
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
        this.balance = balance;
        this.blockedUntil = blockedUntil;
        this.creditLimit = creditLimit;
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

    public long getBlockedUntil() {
        return blockedUntil;
    }

    public void setBlockedUntil(long blockedUntil) {
        this.blockedUntil = blockedUntil;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal limit) {
        this.creditLimit = limit;
    }
}
