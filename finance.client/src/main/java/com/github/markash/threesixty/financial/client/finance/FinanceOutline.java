package com.github.markash.threesixty.financial.client.finance;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

import com.github.markash.threesixty.financial.client.accounts.AccountMonthEndSummaryTablePage;
import com.github.markash.threesixty.financial.shared.Icons;

import threesixty.financial.base.client.account.AccountsTablePage;
import threesixty.financial.base.client.dashboard.DashboardNodePage;

/**
 * 
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 */
@Order(1000)
public class FinanceOutline extends AbstractOutline {

	@Override
	protected void execCreateChildPages(List<IPage<?>> pageList) {
		super.execCreateChildPages(pageList);
		pageList.add(new DashboardNodePage());
		pageList.add(new AccountsTablePage());
		pageList.add(new AccountMonthEndSummaryTablePage());
	}

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Finance");
	}

	@Override
	protected String getConfiguredIconId() {
		return Icons.LineAwesome.MAP;
	}
}
