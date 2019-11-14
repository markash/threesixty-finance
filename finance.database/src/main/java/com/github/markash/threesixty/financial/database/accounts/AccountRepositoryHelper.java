package com.github.markash.threesixty.financial.database.accounts;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.eclipse.scout.rt.platform.Bean;

import com.github.markash.threesixty.financial.shared.accounts.AccountMonthEndSummary;

import threesixty.financial.base.shared.account.Account;
import threesixty.financial.base.shared.account.MonthEndAmount;

@Bean
public class AccountRepositoryHelper implements IAccountRepositoryHelper {

	
	public AccountMonthEndSummary createAccountMonthEndSummary (
			final ResultSet rs,
			final Map<Long, Account> accounts) throws SQLException {
		
		long accountId = rs.getLong(AccountMonthEndSummary.COL_ACCOUNT_ID);
		long subAccountId = rs.getLong(AccountMonthEndSummary.COL_SUB_ACCOUNT_ID);
		java.sql.Date monthEnd = rs.getDate(AccountMonthEndSummary.COL_MONTH_END);
		BigDecimal amount = rs.getBigDecimal(AccountMonthEndSummary.COL_AMOUNT);
		
		return new AccountMonthEndSummary(
				accounts.get(accountId), 
				accounts.get(subAccountId), 
				new MonthEndAmount(monthEnd, amount));
	}
	
	public void mergeAccountMonthEndSummary(
			final Map<Long, AccountMonthEndSummary> subAccountSummaries,
			final AccountMonthEndSummary summary) {
		
		subAccountSummaries
		.merge(
				summary.getSub().getId(), 
				summary, 
				(a, b) -> {
					a.add(b.getMonthEnds());
					return a;
				}
		);
	}
}
