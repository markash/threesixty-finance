package com.github.markash.threesixty.financial.shared.accounts;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import threesixty.financial.base.shared.account.Account;
import threesixty.financial.base.shared.account.MonthEndAmount;
import threesixty.financial.base.shared.account.MonthEndAmounts;

public class AccountMonthEndSummary implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String COL_ACCOUNT_ID = "AccountId";
	public static final String COL_SUB_ACCOUNT_ID = "SubAccountId";
	public static final String COL_MONTH_END = "MonthEnd";
	public static final String COL_AMOUNT = "Amount";
	
	private Account account;
	private Account sub;
	private MonthEndAmounts monthEnds;
	
	public AccountMonthEndSummary(
			final Account account,
			final Account sub,
			final LocalDate monthEnd,
			final BigDecimal amount) {

		this.account = account;
		this.sub = sub;
		this.monthEnds = new MonthEndAmounts();
		
		if (monthEnd != null && amount != null) {
			monthEnds.add(new MonthEndAmount(monthEnd, amount));
		}
	}
	
	public AccountMonthEndSummary(
			final Account account,
			final Account sub,
			final MonthEndAmount...monthEndAmounts) {

		this.account = account;
		this.sub = sub;
		this.monthEnds = new MonthEndAmounts();
		this.monthEnds.add(monthEndAmounts);
	}
	
	/**
	 * The account
	 * @return Account
	 */
	public Account getAccount() {
		return account;
	}
	/**
	 * Sub account
	 * @return The sub account
	 */
	public Account getSub() {
		return sub;
	}

	/**
	 * The month-end amounts for the sub account within the account
	 * @return Collection of month end amounts
	 */
	public Collection<MonthEndAmount> getMonthEnd() {

		return this.monthEnds.getAmounts();
	}
	
	public void add(
			final java.sql.Date monthEnd, 
			final BigDecimal amount) {

		add(monthEnd.toLocalDate(), amount);
	}
	
	public void add(
			final LocalDate monthEnd, 
			final BigDecimal amount) {

		add(new MonthEndAmount(monthEnd, amount));
	}
	
	public void add(
			final MonthEndAmount...monthEnd) {

		add(new HashSet<>(Arrays.asList(monthEnd)));
	}
	
	public void add(
			final Set<MonthEndAmount> monthEnds) {

		if (monthEnds != null) {

			this.monthEnds.add(monthEnds);
		}
	}
	
	/**
	 * Month ends
	 * @return Unmodifiable set of month ends
	 */
	public Set<MonthEndAmount> getMonthEnds() {
		
		return Collections.unmodifiableSet(this.monthEnds.getAmounts());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AccountMonthEndSummary that = (AccountMonthEndSummary) o;
		return Objects.equals(account, that.account) &&
				Objects.equals(sub, that.sub) &&
				Objects.equals(monthEnds, that.monthEnds);
	}

	@Override
	public int hashCode() {
		return Objects.hash(account, sub, monthEnds);
	}
}
