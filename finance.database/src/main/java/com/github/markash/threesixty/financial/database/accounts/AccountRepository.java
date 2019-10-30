package com.github.markash.threesixty.financial.database.accounts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.server.jdbc.ISelectStreamHandler;
import org.eclipse.scout.rt.server.jdbc.ISqlService;
import org.eclipse.scout.rt.server.jdbc.SqlBind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.markash.threesixty.financial.shared.accounts.Account;
import com.github.markash.threesixty.financial.shared.accounts.AccountMonthEndSummary;

public class AccountRepository implements IAccountRepository, IService {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private ISqlService getSQLService() {
		
		return BEANS.get(ISqlService.class);
	}
	
	private IAccountRepositoryHelper getHelper() {
	
		return BEANS.get(AccountRepositoryHelper.class);
	}
	
	public List<Account> getAccounts() {
		
		List<Account> accounts = new ArrayList<>();
		
		getSQLService()
			.selectStreaming(
					"SELECT * FROM Account", 
					new ISelectStreamHandler() {
						
						@Override
						public void handleRow(
								final Connection con, 
								final PreparedStatement stm, 
								final ResultSet rs, 
								final int rowIndex, 
								final List<SqlBind> values) {
							
							try {
								Account account = 
										new Account(
												rs.getLong(Account.COL_ID),
												rs.getString(Account.COL_NAME),
												rs.getLong(Account.COL_PARENT_ID)
												);
								
								accounts.add(account);
							} catch (SQLException e) {
							    log.error("Unable to retrieve accounts", e);
							}
						}
						
						@Override
						public void finished(
								final Connection con, 
								final PreparedStatement stm, 
								final ResultSet rs, 
								final int rowCount) {
							
							accounts
								.forEach(account -> account.setParent(accounts.stream().filter(acc -> acc.getId().equals(account.getParentId())).findFirst().orElse(null)));
						}
					});
		
		return accounts;
	}
	
	public List<AccountMonthEndSummary> getAccountMonthEndSummaries(
			LocalDate...monthEndDates) {
		
		String SQL = "SELECT * FROM vwAccountMonthEndSummary";
		
		if (monthEndDates != null && monthEndDates.length > 0) {
			SQL = SQL + " WHERE " + AccountMonthEndSummary.COL_MONTH_END + " IN (";
			
			int index = 0;
			for (LocalDate monthEndDate : monthEndDates) {
			
				if (index++ > 0) {
					SQL = SQL + ",";
				}
				SQL = SQL + "'" + monthEndDate.format(DateTimeFormatter.ISO_DATE) + "'";
			}
			
			SQL = SQL + ")";
		}
		
		Map<Long, AccountMonthEndSummary> subAccountSummaries = 
				new HashMap<>();
		
		Map<Long, Account> accounts = 
				getAccounts()
				.stream()
				.collect(Collectors.toMap(Account::getId, Function.identity()));
		
		getSQLService()
		.selectStreaming(
				SQL,
				new ISelectStreamHandler() {

					@Override
					public void handleRow(
							final Connection con, 
							final PreparedStatement stm, 
							final ResultSet rs, 
							final int rowIndex,
							List<SqlBind> values) {
						
						try {
							
							IAccountRepositoryHelper helper = getHelper();
							
							AccountMonthEndSummary summary = helper.createAccountMonthEndSummary(rs, accounts);
							helper.mergeAccountMonthEndSummary(subAccountSummaries, summary);
							
						} catch (Exception e) {
							throw new ProcessingException("Error reading vwAccountMonthEndSummary: {}", e.getMessage()); 
						}
					}

					@Override
					public void finished(
							final Connection con, 
							final PreparedStatement stm, 
							final ResultSet rs, 
							final int rowCount) {
						/* Not used*/
					}
				});
		
		return new ArrayList<>(subAccountSummaries.values());
	}
}
