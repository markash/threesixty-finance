package com.github.markash.threesixty.financial.shared.operations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ImportTransactionLine implements Serializable {
    private static final long serialVersionUID = -9124002634906514402L;
    
    public static final String COL_ID = "TransactionId";
    public static final String COL_DATE = "Date";
    public static final String COL_DESCRIPTION = "Description";
    public static final String COL_AMOUNT = "Amount";
    public static final String COL_BALANCE = "Balance";
 
    private LocalDate date;
    private String description;
    private BigDecimal amount;
    private BigDecimal balance;
       
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
