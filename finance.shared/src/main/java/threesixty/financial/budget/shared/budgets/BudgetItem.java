package threesixty.financial.budget.shared.budgets;

import java.io.Serializable;
import java.math.BigDecimal;

public class BudgetItem implements Serializable {
    private static final long serialVersionUID = -7229625294238736263L;

    public static final String COL_BUDGET_ID = "BudgetId";
    public static final String COL_ACCOUNT_ID = "AccountId";
    public static final String COL_AMOUNT = "Amount";
    public static final String COL_BUDGET_AMOUNT = "BudgetAmount";
    public static final String COL_LEDGER_AMOUNT = "LedgerAmount";
    public static final String COL_DIFFERENCE_AMOUNT = "DifferenceAmount";
    public static final String COL_COMMENT = "Comment";
    public static final String COL_PROCESS = "Process";
    
    private Long budgetId;
    private Long accountId;
    private BigDecimal budgetAmount;
    private BigDecimal ledgerAmount;
    private BigDecimal differenceAmount;
    private String comment;
    private BudgetItemProcess process;
    
    public BudgetItem() { }
    
    public BudgetItem(
            final Long budgetId,
            final Long accountId,
            final BigDecimal budgetAmount,
            final BigDecimal ledgerAmount,
            final BigDecimal differenceAmount,
            final String comment,
            final BudgetItemProcess process) {
        
        this.budgetId = budgetId;
        this.accountId = accountId;
        this.budgetAmount = budgetAmount;
        this.comment = comment;
        this.process = process;
    }
    
    public Long getBudgetId() { return budgetId; }

    public Long getAccountId() { return accountId; }

    public BigDecimal getBudgetAmount() { return budgetAmount; }

    public BigDecimal getLedgerAmount() { return ledgerAmount; }
    
    public BigDecimal getDifferenceAmount() { return differenceAmount; }
    
    public String getComment() { return comment; }
    
    public BudgetItemProcess getProcess() { return process; }
    
    public void setBudgetId(final Long budgetId) { this.budgetId = budgetId; }
    
    public void setAccountId(final Long accountId) { this.accountId = accountId; }
    
    public void setBudgetAmount(final BigDecimal amount) { this.budgetAmount = amount; }
    
    public void setLedgerAmount(final BigDecimal amount) { this.ledgerAmount = amount; }
    
    public void setDifferenceAmount(final BigDecimal amount) { this.differenceAmount = amount; }
    
    public void setComment(final String comment) { this.comment = comment; }
    
    public void setProcess(final BudgetItemProcess process) { this.process = process; }
    
    public boolean isNegativeBudgetAmount() {
    
        return getBudgetAmount().compareTo(BigDecimal.ZERO) < 0;
    }
    
    public boolean isPositiveBudgetAmount() {
        
        return getBudgetAmount().compareTo(BigDecimal.ZERO) > 0;
    }
    
    public boolean isZeroBudgetAmount() {
        
        return getBudgetAmount().compareTo(BigDecimal.ZERO) == 0;
    }
    
    public void invertBudgetAmountSign() {
        
        this.budgetAmount = getBudgetAmount().multiply(new BigDecimal("-1"));
    }
}
