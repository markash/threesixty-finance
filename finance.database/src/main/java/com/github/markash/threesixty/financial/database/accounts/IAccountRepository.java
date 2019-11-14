package com.github.markash.threesixty.financial.database.accounts;

import java.time.LocalDate;
import java.util.List;

import org.eclipse.scout.rt.platform.ApplicationScoped;

import com.github.markash.threesixty.financial.shared.accounts.AccountMonthEndSummary;

import threesixty.financial.base.shared.account.Account;

@ApplicationScoped
public interface IAccountRepository {
	/**
	 * Retrieve the list of accounts
	 * @return The list of accounts
	 */
	List<Account> getAccounts();

	/**
	 * Retrieve the list of account summaries
	 * @param monthEndDates The month end dates to retrieve summaries for. Specifying no month end returns all the month ends
	 * @return The list of account summaries
	 */
	List<AccountMonthEndSummary> getAccountMonthEndSummaries(LocalDate...monthEndDates);
}
