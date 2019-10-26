package com.github.markash.threesixty.financial.shared.operations;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class TransactionHistory implements Serializable {
    private static final long serialVersionUID = -936590625457325016L;

    public static final String COL_ID = "TransactionId";
    public static final String COL_DATE = "Date";
    public static final String COL_DESCRIPTION = "Description";
    public static final String COL_AMOUNT = "Amount";
    public static final String COL_BALANCE = "Balance";
    
    private long id;
    private Date date;
    private String description;
    private BigDecimal amount;
    private BigDecimal balance;
    
    
    public TransactionHistory(
            final long id, 
            final Date date, 
            final String description, 
            final BigDecimal amount, 
            final BigDecimal balance) {
        
        super();
        this.id = id;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.balance = balance;
    }
    

    public long getId() { return id; }
    public void setId(final long id) { this.id = id; }
    
    public Date getDate() { return date; }
    public void setDate(final Date date) { this.date = date; }
    
    public String getDescription() { return description; }
    public void setDescription(final String description) { this.description = description; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(final BigDecimal amount) { this.amount = amount; }
    
    public BigDecimal getBalance() { return balance; }
    public void setBalance(final BigDecimal balance) { this.balance = balance; }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TransactionHistory other = (TransactionHistory) obj;
        return id == other.id;
    }
}
