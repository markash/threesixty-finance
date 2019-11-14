package threesixty.financial.base.client;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

import threesixty.financial.base.client.account.AccountMonthEndSummaryTablePage;
import threesixty.financial.base.client.account.AccountTablePage;
import threesixty.financial.base.client.dashboard.DashboardNodePage;
import threesixty.financial.base.shared.Icons;

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
		pageList.add(new AccountTablePage());
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
