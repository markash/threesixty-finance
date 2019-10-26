package com.github.markash.threesixty.financial.database.accounts;

import junit.framework.TestCase;
import org.junit.Assert;

import com.github.markash.threesixty.financial.shared.accounts.Account;
import com.github.markash.threesixty.financial.shared.accounts.AccountMonthEndSummary;
import com.github.markash.threesixty.financial.shared.accounts.MonthEndAmount;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AccountRepositoryHelperTest extends TestCase {
	
	
	public void testMergeAccountMonthEndSummary() {
		
		Account a = new Account(1L, "A", null);
		Account b = new Account(2L, "B", 1L);
		Account c = new Account(3L, "C", 1L);
		
		List<AccountMonthEndSummary> summaries = Arrays.asList(
			new AccountMonthEndSummary(a, b, new MonthEndAmount(LocalDate.parse("2019-05-31"), new BigDecimal("100"))),
			new AccountMonthEndSummary(a, b, new MonthEndAmount(LocalDate.parse("2019-06-30"), new BigDecimal("200"))),
			new AccountMonthEndSummary(a, b, new MonthEndAmount(LocalDate.parse("2019-07-31"), new BigDecimal("50"))),
			
			new AccountMonthEndSummary(a, c, new MonthEndAmount(LocalDate.parse("2019-05-31"), new BigDecimal("150"))),
			
			new AccountMonthEndSummary(a, b, new MonthEndAmount(LocalDate.parse("2019-05-31"), new BigDecimal("100"))),
			new AccountMonthEndSummary(a, b, new MonthEndAmount(LocalDate.parse("2019-06-30"), new BigDecimal("200"))),
			new AccountMonthEndSummary(a, b, new MonthEndAmount(LocalDate.parse("2019-07-31"), new BigDecimal("50")))
		);
		
		List<AccountMonthEndSummary> expected = Arrays.asList(
				new AccountMonthEndSummary(
						a, 
						b, 
						new MonthEndAmount(LocalDate.parse("2019-05-31"), new BigDecimal("200")),
						new MonthEndAmount(LocalDate.parse("2019-06-30"), new BigDecimal("400")),
						new MonthEndAmount(LocalDate.parse("2019-07-31"), new BigDecimal("100"))),
				
				new AccountMonthEndSummary(
						a, 
						c, 
						new MonthEndAmount(LocalDate.parse("2019-05-31"), new BigDecimal("150")))
		);
		
		Map<Long, AccountMonthEndSummary> monthEnds = new HashMap<>();
		
		IAccountRepositoryHelper helper = new AccountRepositoryHelper();		
		summaries.forEach(summary -> helper.mergeAccountMonthEndSummary(monthEnds, summary));
	
		List<AccountMonthEndSummary> actuals = new ArrayList<>(monthEnds.values());

		Assert.assertEquals(expected.size(), actuals.size());
		
		for(AccountMonthEndSummary summary : expected) {
			
			AccountMonthEndSummary actual = actuals
					.stream()
					.filter(s -> s.getSub().equals(summary.getSub()) && s.getAccount().equals(summary.getAccount()))
					.findFirst()
					.orElse(null);
			
			Assert.assertNotNull(actual);
			Assert.assertEquals(summary.getMonthEnds().size(), actual.getMonthEnds().size());
			
			for (MonthEndAmount monthEnd : summary.getMonthEnds()) {
				
				MonthEndAmount actualMonthEnd = actual.getMonthEnds()
						.stream()
						.filter(m -> m.getMonthEnd().isEqual(monthEnd.getMonthEnd()))
						.findFirst()
						.orElse(null);
				
				Assert.assertNotNull(actualMonthEnd);
				Assert.assertEquals("Month End: " + monthEnd.getMonthEnd().format(DateTimeFormatter.ISO_DATE), monthEnd.getAmount(), actualMonthEnd.getAmount());
			}
					
		}
		
	}

}
