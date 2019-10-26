package com.github.markash.threesixty.financial.shared.accounts;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import com.github.markash.threesixty.financial.shared.accounts.AccountMonthEndSummaryTablePageData.AccountMonthEndSummaryTableRowData;
import com.github.markash.threesixty.financial.shared.date.DateUtils;

public class AccountMonthEndSummaryTableDataConverterTests {

	@Test
	public void testConvert() {
		
		Account a = new Account(1L, "A", null);
		Account b = new Account(2L, "B", 1L);
		
		MonthEndAmount[] monthEnds = new MonthEndAmount[] {
				new MonthEndAmount(LocalDate.parse("2019-05-31"), new BigDecimal("200")),
				new MonthEndAmount(LocalDate.parse("2019-06-30"), new BigDecimal("400")),
				new MonthEndAmount(LocalDate.parse("2019-07-31"), new BigDecimal("100"))
		};
		
		AccountMonthEndSummary summary = new AccountMonthEndSummary(a, b, monthEnds);
		
		AccountMonthEndSummaryTableRowData row = new AccountMonthEndSummaryTableDataConverter().apply(summary);
		
		Assert.assertNotNull(row);
		Assert.assertEquals(a.getId(), row.getAccountId());
		Assert.assertEquals(a.getName(), row.getAccountName());
		
		Assert.assertEquals(b.getId(), row.getSubAccountId());
		Assert.assertEquals(b.getName(), row.getSubAccountName());
		
		Assert.assertEquals(DateUtils.convertToDate(monthEnds[1].getMonthEnd()), row.getLastMonthDate());
		Assert.assertEquals(monthEnds[1].getAmount(), row.getLastMonthValue());
		
		Assert.assertEquals(DateUtils.convertToDate(monthEnds[2].getMonthEnd()), row.getCurrentDate());
		Assert.assertEquals(monthEnds[2].getAmount(), row.getCurrentValue());
	}
}
