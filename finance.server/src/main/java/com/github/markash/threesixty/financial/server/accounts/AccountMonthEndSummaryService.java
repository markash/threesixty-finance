package com.github.markash.threesixty.financial.server.accounts;

import com.github.markash.threesixty.financial.database.accounts.IAccountRepository;
import com.github.markash.threesixty.financial.shared.accounts.AccountMonthEndSummaryTableDataConverter;
import com.github.markash.threesixty.financial.shared.accounts.AccountMonthEndSummaryTablePageData;
import com.github.markash.threesixty.financial.shared.accounts.AccountMonthEndSummaryTablePageData.AccountMonthEndSummaryTableRowData;
import com.github.markash.threesixty.financial.shared.accounts.IAccountMonthEndSummaryService;

import java.time.LocalDate;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

public class AccountMonthEndSummaryService implements IAccountMonthEndSummaryService {

	@Override
	public AccountMonthEndSummaryTablePageData getAccountMonthEndSummaryTableData(SearchFilter filter) {

		LocalDate[] monthEndDates = new LocalDate[] {
				LocalDate.parse("2019-05-31"),
				LocalDate.parse("2019-06-30") 
		};
		
		AccountMonthEndSummaryTablePageData pageData = new AccountMonthEndSummaryTablePageData();
		pageData.setRows(BEANS.get(IAccountRepository.class)
				.getAccountMonthEndSummaries(monthEndDates)
				.stream()
				.map(summary -> new AccountMonthEndSummaryTableDataConverter().apply(summary))
				.toArray(AccountMonthEndSummaryTableRowData[]::new));

		return pageData;
	}
}
