package threesixty.financial.budget.client.budgets;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import com.github.markash.threesixty.financial.shared.Icons;

import threesixty.financial.budget.client.budgets.BudgetTablePage.Table;
import threesixty.financial.budget.client.common.AbstractBudgetDateColumn;
import threesixty.financial.budget.client.common.AbstractBudgetMoneyColumn;
import threesixty.financial.budget.client.common.AbstractIdColumn;
import threesixty.financial.budget.shared.budgets.BudgetItemProcess;
import threesixty.financial.budget.shared.budgets.BudgetItem;
import threesixty.financial.budget.shared.budgets.BudgetTablePageData;
import threesixty.financial.budget.shared.budgets.IBudgetService;

@Data(BudgetTablePageData.class)
public class BudgetTablePage extends AbstractPageWithTable<Table> {

    private Long budgetId;
    private Date monthEndDate;
    
    public BudgetTablePage(
            final Long budgetId,
            final Date monthEndDate) {
        
        this.budgetId = budgetId;
        this.monthEndDate = monthEndDate;
    }
    
    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Budget");
    }

    
    @Override
    protected void execLoadData(
            final SearchFilter filter) {
               
        importPageData(BEANS.get(IBudgetService.class).getBudgetTableData(this.budgetId, filter));
    }

    public class Table extends AbstractTable {

        /* Use the full client area for the columns */
        @Override
        protected boolean getConfiguredAutoResizeColumns() {
            return true;
        }
        
        /* Decorate parent rows as disabled so that the user cannot
         * update these but rather that the server updates these 
         * with the summation on the child rows.
         */
        @Override
        protected void execDecorateRow(
                final ITableRow row) {
            
            // Enable the row when it has no child nodes, 
            // i.e. enable the detail nodes and not the summary nodes
            row.setEnabled(row.getChildRows().isEmpty());
        }
        
        public BudgetIdColumn getBudgetIdColumn() {
            return getColumnSet().getColumnByClass(BudgetIdColumn.class);
        }
        
        public MonthEndDateColumn getMonthEndDateColumn() {
            return getColumnSet().getColumnByClass(MonthEndDateColumn.class);
        }

        public AccountCodeColumn getAccountCodeColumn() {
            return getColumnSet().getColumnByClass(AccountCodeColumn.class);
        }
        
        public AccountParentNameColumn getAccountParentNameColumn() {
            return getColumnSet().getColumnByClass(AccountParentNameColumn.class);
        }
        
        public BudgetAmountColumn getBudgetAmountColumn() {
            return getColumnSet().getColumnByClass(BudgetAmountColumn.class);
        }
        
        public LedgerAmountColumn getLedgerAmountColumn() {
            return getColumnSet().getColumnByClass(LedgerAmountColumn.class);
        }
        
        public DifferenceAmountColumn getDifferenceAmountColumn() {
            return getColumnSet().getColumnByClass(DifferenceAmountColumn.class);
        }
        
        public CommentColumn getCommentColumn() {
            return getColumnSet().getColumnByClass(CommentColumn.class);
        }
        
        @Order(10)
        public class AccountCodeColumn extends AbstractIdColumn { 
            
            @Override
            protected boolean getConfiguredEditable() { return true; }
            
            @Override
            protected boolean getConfiguredPrimaryKey() {
                return true;
            }
        }

        @Order(20)
        public class BudgetIdColumn extends AbstractIdColumn {}
        
        @Order(40)
        public class MonthEndDateColumn extends AbstractBudgetDateColumn {
            
            @Override
            protected boolean getConfiguredVisible() { return false; }
        }

        @Order(60)
        public class AccountNameColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() { return TEXTS.get("AccountName"); }
            
            @Override
            protected boolean getConfiguredVisible() { return true; }
        }
        
        @Order(70)
        public class AccountParentCodeColumn extends AbstractIdColumn { 
            
            @Override
            protected boolean getConfiguredParentKey() { return true; }
        }
        
        @Order(80)
        public class AccountParentNameColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() { return TEXTS.get("Parent"); }
            
            @Override
            protected boolean getConfiguredVisible() { return false; }
        }
        
        @Order(90)
        public class BudgetAmountColumn extends AbstractBudgetMoneyColumn {
            @Override
            protected String getConfiguredHeaderText() { return TEXTS.get("BudgetAmount"); }
            
            @Override
            protected int getConfiguredWidth() { return 20; }
            
            @Override
            protected boolean getConfiguredEditable() { return true; }
        }
        
        @Order(100)
        public class CommentColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() { return TEXTS.get("Comment"); }
            
            @Override
            protected int getConfiguredWidth() { return 20; }
            
            @Override
            protected boolean getConfiguredEditable() { return true; }
        }
        
        @Order(100)
        public class LedgerAmountColumn extends AbstractBudgetMoneyColumn {
            @Override
            protected String getConfiguredHeaderText() { return TEXTS.get("LedgerAmount"); }
            
            @Override
            protected int getConfiguredWidth() { return 20; }
            
            @Override
            protected boolean getConfiguredEditable() { return false; }
        }
        
        @Order(110)
        public class DifferenceAmountColumn extends AbstractBudgetMoneyColumn {
            @Override
            protected String getConfiguredHeaderText() { return TEXTS.get("DifferenceAmount"); }
            
            @Override
            protected int getConfiguredWidth() { return 20; }
            
            @Override
            protected boolean getConfiguredEditable() { return false; }
        }
        
        @Order(200)
        public class ProcessedDateTimeColumn extends AbstractDateTimeColumn {
            @Override
            protected String getConfiguredHeaderText() { return TEXTS.get("Processed"); }
            
            @Override
            protected boolean getConfiguredVisible() { return false; }
        }   
    }
    
    @Order(100)
    public class AddMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
            return TEXTS.get("Add");
        }

        @Override
        protected String getConfiguredIconId() {
            return Icons.PlusBold;
        }
        
        @Override
        protected void execAction() {
            
            BudgetTablePage.Table table = (BudgetTablePage.Table) getTable();
            
            ITableRow row = table.addRow(true);
            
            table.getMonthEndDateColumn().setValue(row, monthEndDate);
        }
    }
    
    @Order(200)
    public class SaveMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
            return TEXTS.get("Save");
        }

        @Override
        protected String getConfiguredIconId() {
            return Icons.FileSolid;
        }
        
        @Override
        protected void execAction() {
  
            BudgetTablePage.Table table = (BudgetTablePage.Table) getTable();
            
            List<ITableRow> updates = table.getUpdatedRows();
            List<ITableRow> inserts = table.getInsertedRows();
            List<ITableRow> deletes = table.getDeletedRows();
            
            /* Process the budget item changes and summarize the parents */
            List<BudgetItem> changes = mapToProcess(table, updates, BudgetItemProcess.UPDATE);
            
            changes.addAll(mapToProcess(table, inserts, BudgetItemProcess.UPDATE));
            
            changes.addAll(mapToProcess(table, deletes, BudgetItemProcess.DELETE));
            
            BEANS.get(IBudgetService.class).processBudgetItemChanges(changes);
            
            /* Update parent rows as changed */
            List<ITableRow> changedRows = new ArrayList<>(updates);
            changedRows.addAll(inserts);
            changedRows.addAll(deletes);

            updateRows(table, changedRows);
            updateParents(table, changedRows);
        }
        
        private List<BudgetItem> mapToProcess(
                final BudgetTablePage.Table table,
                final List<ITableRow> rows,
                final BudgetItemProcess process) {
            
            return rows
                    .stream()
                    .map(row -> 
                        new BudgetItem (
                                table.getBudgetIdColumn().getValue(row),
                                table.getAccountCodeColumn().getValue(row),
                                table.getBudgetAmountColumn().getValue(row),
                                table.getLedgerAmountColumn().getValue(row),
                                table.getDifferenceAmountColumn().getValue(row),
                                table.getCommentColumn().getValue(row),
                                process
                        )
                    )
                    .collect(Collectors.toList());
        }
        
        private void updateRows(
                final BudgetTablePage.Table table,
                final List<ITableRow> changedRows) {
            
            List<ITableRow> parentRows = changedRows.stream()
                    .map(changedRow -> retrieveUpdatedRow(table, changedRow))
                    .collect(Collectors.toList());
                
                
            table.updateRows(parentRows);
        }
        
        private void updateParents(
                final BudgetTablePage.Table table,
                final List<ITableRow> changedRows) {
            
            List<ITableRow> parentRows = changedRows.stream()
                    .map(changedRow -> retrieveUpdatedParent(table, changedRow))
                    .collect(Collectors.toList());
                
                
            table.updateRows(parentRows);
        }
        
        private ITableRow retrieveUpdatedRow(
                final BudgetTablePage.Table table,
                final ITableRow changedRow) {
            
            
            Long budgetId = (Long) changedRow.getCell(table.getBudgetIdColumn()).getValue();
            Long accountId = (Long) changedRow.getCell(table.getAccountCodeColumn()).getValue();
            
            BudgetItem budgetItem = BEANS.get(IBudgetService.class).retrieveBudgetItem(budgetId, accountId);
            
                
            /* Editable cells should only update t*/
            changedRow.getCellForUpdate(table.getBudgetAmountColumn()).setValue(budgetItem.getBudgetAmount());
            changedRow.getCellForUpdate(table.getLedgerAmountColumn()).setValue(budgetItem.getLedgerAmount());
            changedRow.getCellForUpdate(table.getDifferenceAmountColumn()).setValue(budgetItem.getDifferenceAmount());
            changedRow.getCellForUpdate(table.getCommentColumn()).setValue(budgetItem.getComment());
            
            return changedRow;
        }
        
        private ITableRow retrieveUpdatedParent(
                final BudgetTablePage.Table table,
                final ITableRow changedRow) {
            
            ITableRow parentRow = table.getRowByKey(table.getParentRowKeys(changedRow));
            
            if (parentRow != null) {
                
                retrieveUpdatedRow(table, parentRow);
            }
            
            return parentRow;
        }
    }
}
