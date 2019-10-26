package com.github.markash.threesixty.financial.shared.operations;

import java.io.Serializable;
import java.math.BigDecimal;

public class Allocation implements Serializable {
    private static final long serialVersionUID = -9124002634906514402L;

    public static final String COL_ACCOUNT_ID = "AccountId";
    public static final String COL_AMOUNT = "Amount";
    public static final String COL_COMMENT = "Comment";
    
    private long accountId;
    private BigDecimal amount;
    private String comment;
    
    public Allocation(
            final long accountId,
            final BigDecimal amount,
            final String comment) {
        
        this.accountId = accountId;
        this.amount = amount;
        this.comment = comment;
    }
    public long getAccountId() { return accountId; }
    public void setAccountId(final long accountId) { this.accountId = accountId; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(final BigDecimal amount) { this.amount = amount; }
    
    public String getComment() { return comment; }
    public void setComment(final String comment) { this.comment = comment; }
}
