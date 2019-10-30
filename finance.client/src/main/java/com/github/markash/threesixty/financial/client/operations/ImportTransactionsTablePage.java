package com.github.markash.threesixty.financial.client.operations;

import java.util.List;

import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.filechooser.FileChooser;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBigDecimalColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.status.IStatus;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import com.github.markash.threesixty.financial.client.operations.ImportTransactionsTablePage.Table;
import com.github.markash.threesixty.financial.shared.Icons;
import com.github.markash.threesixty.financial.shared.database.DatabaseException;
import com.github.markash.threesixty.financial.shared.operations.IImportTransactionsService;
import com.github.markash.threesixty.financial.shared.operations.ImportTransactionsTablePageData;

@Data(ImportTransactionsTablePageData.class)
public class ImportTransactionsTablePage extends AbstractPageWithTable<Table> {

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("ImportTransactions");
    }

    @Override
    protected void execLoadData(SearchFilter filter) {
        importPageData(BEANS.get(IImportTransactionsService.class).getImportTransactionsTableData(filter));
    }

    public class Table extends AbstractTable {

        @Override
        protected boolean getConfiguredAutoResizeColumns() {
            return true;
        }

        
        /**
         * Allocate the transaction to an account in the ledger
         */
        public void allocateTransaction() {
            
            ITableRow row = getSelectedRow();
            
            AllocateForm form = new AllocateForm();
            form.getTransactionIdField().setValue((Long) row.getCell(getTransactionIdColumn()).getValue());
            form.getDateField().setValue((java.util.Date) row.getCell(getDateColumn()).getValue());
            form.getDescriptionField().setValue((String) row.getCell(getDescriptionColumn()).getValue());
            form.getAmountField().setValue((java.math.BigDecimal) row.getCell(getAmountColumn()).getValue());
            
            
            
            form.startAllocate();
            
            reloadPage("Transaction allocated.");
        }
        
        
        
        public TransactionIdColumn getTransactionIdColumn() {
            return getColumnSet().getColumnByClass(TransactionIdColumn.class);
        }
        
        public DateColumn getDateColumn() {
            return getColumnSet().getColumnByClass(DateColumn.class);
        }

        public DescriptionColumn getDescriptionColumn() {
            return getColumnSet().getColumnByClass(DescriptionColumn.class);
        }

        public AmountColumn getAmountColumn() {
            return getColumnSet().getColumnByClass(AmountColumn.class);
        }

        public BalanceColumn getBalanceColumn() {
            return getColumnSet().getColumnByClass(BalanceColumn.class);
        }

        public AccountColumn getAllocateColumn() {
            return getColumnSet().getColumnByClass(AccountColumn.class);
        }

        @Order(10)
        public class TransactionIdColumn extends AbstractLongColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Id");
            }

            @Override
            protected int getConfiguredWidth() {
                return 10;
            }
            
        }

        @Order(20)
        public class DateColumn extends AbstractDateColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Date");
            }

            @Override
            protected int getConfiguredWidth() {
                return 15;
            }
        }

        @Order(30)
        public class DescriptionColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Description");
            }

            @Override
            protected int getConfiguredWidth() {
                return 25;
            }
        }

        @Order(40)
        public class AmountColumn extends AbstractBigDecimalColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Amount");
            }

            @Override
            protected int getConfiguredWidth() {
                return 15;
            }
        }

        @Order(50)
        public class BalanceColumn extends AbstractBigDecimalColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Balance");
            }

            @Override
            protected int getConfiguredWidth() {
                return 15;
            }
        }

        @Order(60)
        public class AccountColumn extends AbstractSmartColumn<Long> {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("Account To");
            }

            @Override
            protected int getConfiguredWidth() {
                return 20;
            }
            
            @Override
            protected void execCompleteEdit(
                    final ITableRow row, 
                    final IFormField editingField) {
                
                new MessageBox().withBody((String) row.getCellValue(1)).show();
                new MessageBox().withBody((String) editingField.getMasterValue()).show();
                
                super.execCompleteEdit(row, editingField);
            }
        }
        
        @Order(20)
        public class AllocateMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
                return TEXTS.get("Allocate");
            }

            protected String getConfiguredIconId() {
                return Icons.Gear;
            }
            
            @Override
            protected void execAction() {
            
                allocateTransaction();
            }
        }
    }    
    
    @Order(10)
    public class ImportTransactionsMenu extends AbstractMenu {

        @Override
        protected String getConfiguredText() {
            return TEXTS.get("Import");
        }

        protected String getConfiguredIconId() {
            return Icons.LineAwesome.Upload;
        }
        
        @Override
        protected void execAction() {
            importTransactions();
        }

        /**
         * Import transactions from a comma-separated file
         */
        public void importTransactions() {

            List<BinaryResource> files = new FileChooser().startChooser();

            try {

                if (files.size() > 0) {
                    BEANS.get(IImportTransactionsService.class).importFile(files.get(0));

                    new MessageBox()
                        .withHeader("Import information")
                        .withBody("Imported transaction history file.")
                        .withSeverity(IStatus.INFO)
                        .withAutoCloseMillis(2000)
                        .withYesButtonText("OK")
                        .show();

                } else {
                    new MessageBox().withHeader("Import warning").withBody("No file select.")
                            .withSeverity(IStatus.WARNING).withAutoCloseMillis(2000).withYesButtonText("OK").show();
                }

            } catch (Exception e) {
                new MessageBox().withHeader("Import error").withBody(e.getMessage()).withSeverity(IStatus.ERROR)
                        .withYesButtonText("OK").show();

            }
        }
    }

    @Order(55)
    public class AutoAllocateMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
            return TEXTS.get("AutoAllocate");
        }

        @Override
        protected String getConfiguredIconId() {
            return Icons.Gear;
        }
        
        @Override
        protected void execAction() {
            
            try {
                
                BEANS.get(IImportTransactionsService.class).autoAllocateTransactionHistory();
                
                reloadPage();
                
            } catch (DatabaseException e) {
                
                new MessageBox()
                    .withHeader(TEXTS.get("AutoAllocateExceptionHeader"))
                    .withBody(TEXTS.get("AutoAllocateExceptionBody", e.getMessage()))
                    .withYesButtonText(TEXTS.get("Ok"))
                    .show();
            }
        }
    }

    
    
    @Order(100)
    public class DeleteTransactionsMenu extends AbstractMenu {
        
        @Override
        protected String getConfiguredText() {
            return TEXTS.get("Delete");
        }
        
        protected String getConfiguredIconId() {
            return Icons.Remove;
        }
        
        @Override
        protected String getConfiguredTooltipText() {
            return TEXTS.get("DeleteTransactions");
        }
        
        @Override
        protected boolean getConfiguredEnabled() {
            
            ITable table = (ITable) getParentPage().getTable();
            
            return table != null && table.getRowCount() > 0;
        }
        
        @Override
        protected void execAction() {
            
            int result = new MessageBox()
                    .withHeader("Delete")
                    .withBody("Delete the transactions history, this cannot be undone.")
                    .withSeverity(IStatus.WARNING)
                    .withYesButtonText("Yes")
                    .withNoButtonText("No")
                    .show();
                
            if (result == MessageBox.YES_OPTION) {
                
                try {
                                      
                    BEANS.get(IImportTransactionsService.class).deleteAllTransactionHistory();
                
                    new MessageBox()
                        .withHeader("Deletion Succeeded")
                        .withBody("Please refresh the table data.")
                        .withSeverity(IStatus.INFO)
                        .withAutoCloseMillis(5000)
                        .withYesButtonText("OK")
                        .show();
                    
                } catch (Throwable e) {
                    new MessageBox()
                        .withHeader("Deletion Failure")
                        .withBody("Unable to delete the transaction history, " + e.getMessage())
                        .withSeverity(IStatus.ERROR)
                        .withAutoCloseMillis(5000)
                        .withYesButtonText("OK")
                        .show();
                }
            }
        }
    }
}
