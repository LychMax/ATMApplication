package src.main.java.model;

import java.math.BigDecimal;

public class ATM {
    private BigDecimal atmBalance;

    public ATM(BigDecimal atmBalance) {
        this.atmBalance = atmBalance;
    }

    public BigDecimal getAtmBalance() {
        return atmBalance;
    }

    public void setAtmBalance(BigDecimal atmBalance) {
        this.atmBalance = atmBalance;
    }
}
