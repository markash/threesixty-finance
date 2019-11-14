package threesixty.financial.budget.client.budgets;

import java.util.Date;

import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import threesixty.financial.base.shared.Icons;
import threesixty.financial.budget.client.budgets.BudgetsTablePage.Table;
import threesixty.financial.budget.client.common.AbstractBudgetDateColumn;
import threesixty.financial.budget.client.common.AbstractBudgetMoneyColumn;
import threesixty.financial.budget.client.common.AbstractIdColumn;
import threesixty.financial.budget.shared.budgets.BudgetsTablePageData;
import threesixty.financial.budget.shared.budgets.IBudgetService;

@Data(BudgetsTablePageData.class)
public class BudgetsTablePage extends AbstractPageWithTable<Table> {

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Budgets");
    }

    @Override
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(IBudgetService.class).getBudgetsTableData(filter));
    }

    @Override
    protected IPage<?> execCreateChildPage(final ITableRow row) {

        Long budgetId = (Long) row.getCell(getTable().getBudgetIdColumn()).getValue();
        Date date = (Date) row.getCell(getTable().getMonthEndDateColumn()).getValue();

        return new BudgetTablePage(budgetId, date);
    }

    public class Table extends AbstractTable {

        protected void createNewBudget() {

            ITableRow row = getTable().getSelectedRow();

            Long budgetId = (Long) row.getCell(getTable().getBudgetIdColumn()).getValue();

            BEANS.get(IBudgetService.class).createBudgetForMonthEnd(budgetId);
        }

        @Override
        protected boolean getConfiguredAutoResizeColumns() {
            return true;
        }

        public IncomeColumn getIncomeColumn() {
            return getColumnSet().getColumnByClass(IncomeColumn.class);
        }

        public MonthEndDateColumn getMonthEndDateColumn() {
            return getColumnSet().getColumnByClass(MonthEndDateColumn.class);
        }

        public BudgetIdColumn getBudgetIdColumn() {
            return getColumnSet().getColumnByClass(BudgetIdColumn.class);
        }

        @Order(100)
        public class MonthEndDateColumn extends AbstractBudgetDateColumn {
        }

        @Order(200)
        public class BudgetIdColumn extends AbstractIdColumn {
        }

        @Order(300)
        public class MonthEndIdColumn extends AbstractLongColumn {
        }

        @Order(400)
        public class ProcessedDateTimeColumn extends AbstractDateTimeColumn {
            @Override
            protected boolean getConfiguredVisible() {
                return false;
            }
        }

        @Order(500)
        public class IncomeColumn extends AbstractBudgetMoneyColumn {

            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Income");
            }

            @Override
            protected int getConfiguredWidth() {
                return 30;
            }
        }

        @Order(100)
        public class MaintainBudgetItemsMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
                return TEXTS.get("MaintainBudgetItems");
            }

            @Override
            protected String getConfiguredTooltipText() {
                return TEXTS.get("MaintainBudgetItemsTooltip");
            }

            @Override
            protected String getConfiguredIconId() {
                return Icons.PencilBold;
            }

            @Override
            protected void execAction() {

                getTable().createNewBudget();

                new MessageBox()
                    .withHeader(TEXTS.get("MaintainBudgetItemsHeader"))
                    .withBody(TEXTS.get("MaintainBudgetItemsBody"))
                    .withYesButtonText(TEXTS.get("Ok")).show();
            }
        }

    }
}
