package com.github.markash.threesixty.financial.client.accounts;

import org.eclipse.scout.rt.client.dto.Data;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBigDecimalColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import com.github.markash.threesixty.financial.client.accounts.AccountMonthEndSummaryTablePage.Table;
import com.github.markash.threesixty.financial.shared.accounts.AccountMonthEndSummaryTablePageData;
import com.github.markash.threesixty.financial.shared.accounts.IAccountMonthEndSummaryService;

@Data(AccountMonthEndSummaryTablePageData.class)
public class AccountMonthEndSummaryTablePage extends AbstractPageWithTable<Table> {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("AccountMonthEndSummaryTablePage");
	}

	@Override
	protected void execLoadData(SearchFilter filter) {
		importPageData(BEANS.get(IAccountMonthEndSummaryService.class).getAccountMonthEndSummaryTableData(filter));
	}

	public class Table extends AbstractTable {

		public SubAccountNameColumn getSubAccountNameColumn() {
			return getColumnSet().getColumnByClass(SubAccountNameColumn.class);
		}

		public LastMonthDateColumn getLastMonthDateColumn() {
			return getColumnSet().getColumnByClass(LastMonthDateColumn.class);
		}

		public LastMonthValueColumn getLastMonthValueColumn() {
			return getColumnSet().getColumnByClass(LastMonthValueColumn.class);
		}

		public CurrentDateColumn getCurrentDateColumn() {
			return getColumnSet().getColumnByClass(CurrentDateColumn.class);
		}

		public CurrentValueColumn getCurrentValueColumn() {
			return getColumnSet().getColumnByClass(CurrentValueColumn.class);
		}

		public SubAccountIdColumn getSubAccountIdColumn() {
			return getColumnSet().getColumnByClass(SubAccountIdColumn.class);
		}

		public AccountNameColumn getAccountNameColumn() {
			return getColumnSet().getColumnByClass(AccountNameColumn.class);
		}

		public AccountIdColumn getAccountIdColumn() {
			return getColumnSet().getColumnByClass(AccountIdColumn.class);
		}

		@Order(1000)
		public class AccountIdColumn extends AbstractLongColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("AccountId");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(2000)
		public class AccountNameColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("AccountName");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(3000)
		public class SubAccountIdColumn extends AbstractLongColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("SubAccountId");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(4000)
		public class SubAccountNameColumn extends AbstractStringColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("SubAccountName");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(5000)
		public class LastMonthDateColumn extends AbstractDateColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("LastMonth");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(6000)
		public class LastMonthValueColumn extends AbstractBigDecimalColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("LastValue");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(7000)
		public class CurrentDateColumn extends AbstractDateColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("CurrentDate");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}

		@Order(8000)
		public class CurrentValueColumn extends AbstractBigDecimalColumn {
			@Override
			protected String getConfiguredHeaderText() {
				return TEXTS.get("CurrentValue");
			}

			@Override
			protected int getConfiguredWidth() {
				return 100;
			}
		}
	}
}
