package threesixty.financial.budget.server.budgets;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.ISqlService;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import com.github.markash.threesixty.financial.server.sql.SQLs;
import com.github.markash.threesixty.financial.shared.accounts.Account;
import com.github.markash.threesixty.financial.shared.accounts.IAccountsService;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;

import threesixty.financial.budget.shared.budgets.BudgetItem;
import threesixty.financial.budget.shared.budgets.BudgetTablePageData;
import threesixty.financial.budget.shared.budgets.BudgetsTablePageData;
import threesixty.financial.budget.shared.budgets.IBudgetService;

public class BudgetService implements IBudgetService {

    @Override
    public BudgetsTablePageData getBudgetsTableData(
            final SearchFilter filter) {
        
        BudgetsTablePageData pageData = new BudgetsTablePageData();
        
        SQL.selectInto(
                SQLs.Budget.SELECT_INTO_PAGE, 
                new NVPair(SQLs.Budget.PARAM_PAGE, pageData));
        
        return pageData;
    }
    
    @Override
    public BudgetTablePageData getBudgetTableData(
            final Long budgetId,
            final SearchFilter filter) {
        
        BudgetTablePageData pageData = new BudgetTablePageData();
        
        SQL.selectInto(
                SQLs.BudgetItem.SELECT_SINGLE_BY_BUDGET_ID_INTO_PAGE, 
                new NVPair("budgetId", budgetId),
                new NVPair(SQLs.BudgetItem.PARAM_PAGE, pageData));
        
        return pageData;
    }
    
    @Override
    public void createBudgetForMonthEnd(
            final Long budgetId) {
        
        SQL.callStoredProcedure(
                SQLs.Budget.MATERIALIZE_NEW_BUDGET, 
                new NVPair(SQLs.Budget.PARAM_BUDGET_ID, budgetId));
    }
    
    @Override
    public void processBudgetItemChanges(
            final List<BudgetItem> changes) throws ProcessingException {
        
        try {
            
            Map<Long, Account> accounts = 
                    BEANS.get(IAccountsService.class)
                        .retrieveAccounts()
                        .stream()
                        .collect(Collectors.toMap(Account::getId, Function.identity()));
                    
            Connection connection = BEANS.get(ISqlService.class).getConnection();
            
            // Create an in-memory data table.  
            SQLServerDataTable budgetItems = new SQLServerDataTable();
              
            // Define metadata for the data table.  
            budgetItems.addColumnMetadata(BudgetItem.COL_BUDGET_ID,         java.sql.Types.INTEGER);
            budgetItems.addColumnMetadata(BudgetItem.COL_ACCOUNT_ID,        java.sql.Types.INTEGER);
            budgetItems.addColumnMetadata(BudgetItem.COL_AMOUNT,            java.sql.Types.DECIMAL);
            budgetItems.addColumnMetadata(BudgetItem.COL_COMMENT,           java.sql.Types.NVARCHAR);
            budgetItems.addColumnMetadata(BudgetItem.COL_PROCESS,           java.sql.Types.NVARCHAR);
            
            for(BudgetItem change : changes) {
                
                //Confirm the sign of the budget item
                if (accounts.containsKey(change.getAccountId())) {
                    
                    Account account = accounts.get(change.getAccountId());
                    
                    if (account.isNegativeSign() && change.isPositiveBudgetAmount()) {
                        
                        change.invertBudgetAmountSign();
                    }
                }
                
                
                //Add the budget item to the changed rows
                budgetItems.addRow(
                        change.getBudgetId(), 
                        change.getAccountId(), 
                        change.getBudgetAmount(), 
                        change.getComment(),
                        change.getProcess().name()
                        );   
            }
                          
            try (SQLServerPreparedStatement pStmt = (SQLServerPreparedStatement) connection.prepareStatement(SQLs.BudgetItem.BUDGET_ITEM_PROCESS)) {
                
                pStmt.setStructured(1, "dbo.BudgetItemType", budgetItems);  
                pStmt.execute();
                
                connection.commit();
            }
        } catch (Exception e) {
            throw new ProcessingException("Database call to " + SQLs.BudgetItem.BUDGET_ITEM_PROCESS + " failed", e);
        }
    }
    
    public BudgetItem retrieveBudgetItem(
            final Long budgetId,
            final Long accountId) {
        
        BeanArrayHolder<BudgetItem> valueObject = new BeanArrayHolder<>(BudgetItem.class);
                
        SQL.selectInto(
                SQLs.BudgetItem.ValueObject.SELECT_SINGLE_INTO_VALUE_OBJECT, 
                new NVPair("budgetId", budgetId),
                new NVPair("accountId", accountId),
                new NVPair("valueObject", valueObject));
        
        if (valueObject.getBeans().length > 0) {
            
            return valueObject.getBeans()[0];
        }
        
        throw new ProcessingException("Budget item budgetId=" + budgetId + ", accountId=" + accountId + " not found.");
    }
}
