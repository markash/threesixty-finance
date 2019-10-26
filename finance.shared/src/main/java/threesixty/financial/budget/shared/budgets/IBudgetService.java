package threesixty.financial.budget.shared.budgets;

import java.util.List;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IBudgetService extends IService {

    BudgetsTablePageData getBudgetsTableData(SearchFilter filter);
    
    /**
     * Retrieve the budget table data based upon the budget id and potential search filter
     * @param budgetId The budget id
     * @param filter The potential seach filter *NOT IMPLEMENTED AS YET*
     * @return The budget table data
     */
    BudgetTablePageData getBudgetTableData(
            final Long budgetId, 
            final SearchFilter filter);

    /**
     * Creates a blank list of budget items for the budget
     * @param budgetId The budget identifier
     */
    void createBudgetForMonthEnd(
            final Long budgetId);
    
    void processBudgetItemChanges(final List<BudgetItem> changes);
    
    BudgetItem retrieveBudgetItem(
            final Long budgetId,
            final Long accountId);
    

}
