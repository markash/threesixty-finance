package threesixty.financial.base.shared.account;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.github.markash.threesixty.financial.shared.accounts.AccountMonthEndSummary;
import com.github.markash.threesixty.financial.shared.accounts.AccountMonthEndSummaryTablePageData.AccountMonthEndSummaryTableRowData;
import com.github.markash.threesixty.financial.shared.date.DateUtils;

public class AccountMonthEndSummaryTableDataConverter implements Function<AccountMonthEndSummary, AccountMonthEndSummaryTableRowData> {

	@Override
	public AccountMonthEndSummaryTableRowData apply(final AccountMonthEndSummary summary) {
		
		AccountMonthEndSummaryTableRowData data = new AccountMonthEndSummaryTableRowData();
		
		if (summary != null) {
			
			if (summary.getAccount() != null) {
				
				Account account = summary.getAccount();
				data.setAccountId(account.getId());
				data.setAccountName(account.getName());
			}
			
			if (summary.getSub() != null) {
				
				Account sub = summary.getSub();
				data.setSubAccountId(sub.getId());
				data.setSubAccountName(sub.getName());
			}
			
			Set<MonthEndAmount> monthEnds = summary.getMonthEnds();
			if (monthEnds != null) {
				
				int count = 1;
				for(MonthEndAmount monthEnd : monthEnds.stream().sorted(new MonthEndAmount.MonthEndDescending()).limit(2).collect(Collectors.toList())) {
					
					switch (count++) {
					case 1: 
						data.setCurrentDate(DateUtils.convertToDate(monthEnd.getMonthEnd()));
						data.setCurrentValue(monthEnd.getAmount());
						break;
					case 2:
						data.setLastMonthDate(DateUtils.convertToDate(monthEnd.getMonthEnd()));
						data.setLastMonthValue(monthEnd.getAmount());
						break;
					default:
						break;
					}
				}
			}
		}
		return data;
	}
}
