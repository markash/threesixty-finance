package threesixty.financial.base.client.account;

import java.util.Set;

import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import com.github.markash.threesixty.financial.shared.accounts.AccountTablePageData;

import threesixty.financial.base.client.account.AccountTablePage.Table;
import threesixty.financial.base.client.common.menu.AbstractEditMenu;
import threesixty.financial.base.client.common.menu.AbstractNewMenu;
import threesixty.financial.base.shared.account.IAccountsService;

@Data(AccountTablePageData.class)
public class AccountTablePage extends AbstractPageWithTable<Table> {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Accounts");
	}

	@Override
	protected void execLoadData(SearchFilter filter) {
		importPageData(BEANS.get(IAccountsService.class).getAccountsTableData(filter));
	}

	public class Table extends AbstractTable {
	
	    @Override
        protected boolean getConfiguredAutoResizeColumns() {
            return true;
        }
	    
		public AccountIdColumn getAccountIdColumn() {
			return getColumnSet().getColumnByClass(AccountIdColumn.class);
		}

		public AccountParentIdColumn getAccountParentIdColumn() {
            return getColumnSet().getColumnByClass(AccountParentIdColumn.class);
        }

        public AccountParentNameColumn getAccountParentNameColumn() {
            return getColumnSet().getColumnByClass(AccountParentNameColumn.class);
        }

        public AccountNameColumn getAccountNameColumn() {
			return getColumnSet().getColumnByClass(AccountNameColumn.class);
		}

		@Order(10)
        public class AccountIdColumn extends AbstractLongColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("AccountCode");
            }
            
            @Override
            protected boolean getConfiguredPrimaryKey() {
                return true;
            }
            
            @Override
            protected int getConfiguredWidth() {
                return 10;
            }
        }
		
		@Order(20)
		public class AccountNameColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("AccountName");
			}

			@Override
			protected int getConfiguredWidth() {
				return 30;
			}
		}

        @Order(30)
        public class AccountParentIdColumn extends AbstractLongColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("ParentAccountCode");
            }

            @Override
            protected int getConfiguredWidth() {
                return 10;
            }
        }

        @Order(40)
        public class AccountParentNameColumn extends AbstractStringColumn {
            @Override
            protected String getConfiguredHeaderText() {
                return TEXTS.get("ParentAccountName");
            }

            @Override
            protected int getConfiguredWidth() {
                return 30;
            }
        }
        
        @Order(1000)
        public class EditAccountMenu extends AbstractMenu {
            @Override
            protected String getConfiguredText() {
                return TEXTS.get("ModifyAccount");
            }

            @Override
            protected Set<? extends IMenuType> getConfiguredMenuTypes() {
                return CollectionUtility.hashSet(TreeMenuType.SingleSelection);
            }

            @Override
            protected void execAction() {
                
                ITableRow row = getTable().getSelectedRow();
                
                AccountForm form = new AccountForm();
                
                form.getParentAccountField().setValue((Long) row.getCell(getAccountParentIdColumn()).getValue());
                form.getAccountIdField().setValue((Long) row.getCell(getAccountIdColumn()).getValue());
                form.getAccountNameField().parseAndSetValue((String) row.getCell(getAccountNameColumn()).getValue());
                
                form.startModify();
            }
        }
        
        @Order(1000)
        public class NewMenu extends AbstractNewMenu {
            
            @Override
            protected void execAction() {
                
                AccountForm form = new AccountForm();
                form.startNew();
            }
        }
        
        @Order(2000)
        public class EditMenu extends AbstractEditMenu {
            
            @Override
            protected void execAction() {
                AccountForm form = new AccountForm();
                form.startModify();
            }
        }
	}
}
