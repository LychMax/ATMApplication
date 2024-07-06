package src.main.java.model;

import java.math.BigDecimal;

public class ATMData {
    private BigDecimal balance;

    public ATMData(final BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
