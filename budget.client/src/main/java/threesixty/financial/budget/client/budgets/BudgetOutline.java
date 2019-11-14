package threesixty.financial.budget.client.budgets;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.text.TEXTS;

import threesixty.financial.base.shared.Icons;

public class BudgetOutline extends AbstractOutline {

    @Override
    protected String getConfiguredTitle() {
      return TEXTS.get("Budgets");
    }

    @Override
    protected void execCreateChildPages(List<IPage<?>> pageList) {
      BudgetsTablePage budgetsTablePage = new BudgetsTablePage();
      pageList.add(budgetsTablePage);
    }

    @Override
    protected String getConfiguredIconId() {
      return Icons.LineAwesome.MONEY;
    }
}
