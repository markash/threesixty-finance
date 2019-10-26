package com.github.markash.threesixty.financial.database.accounts;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import com.github.markash.threesixty.financial.shared.accounts.Account;
import com.github.markash.threesixty.financial.shared.accounts.AccountMonthEndSummary;

public interface IAccountRepositoryHelper {
	/**
	 * Creates an account month end summary from the result set and map of accounts. 
	 * @param rs The result set containing the month end account summaries
	 * @param accounts The map of accounts to sub-account id used to resolve the sub-accounts in the result set
	 * @return The account month end summary
	 * @throws SQL Exception if there is an error
	 */
	AccountMonthEndSummary createAccountMonthEndSummary(
			ResultSet rs, 
			Map<Long, Account> accounts) throws SQLException;
	
	/**
	 * Merge the account month end summary into the map of account month end summaries
	 * @param subAccountSummaries The map of account summary by sub-account id
	 * @param summary The summary to merge into the map of sub-account summaries
	 */
	void mergeAccountMonthEndSummary(
			Map<Long, AccountMonthEndSummary> subAccountSummaries,
			AccountMonthEndSummary summary);
}
