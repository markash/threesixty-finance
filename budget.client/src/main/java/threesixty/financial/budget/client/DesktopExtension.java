package threesixty.financial.budget.client;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktopExtension;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.platform.Order;

import threesixty.financial.budget.client.budgets.BudgetOutline;

public class DesktopExtension extends AbstractDesktopExtension {

    @Override
    protected List<Class<? extends IOutline>> getConfiguredOutlines() {
        List<Class<? extends IOutline>> outlines = new ArrayList<>();
        outlines.add(BudgetOutline.class);
        return outlines;
    }
    
    @Order(2000)
    public class BudgetOutlineViewButton extends AbstractOutlineViewButton {

      public BudgetOutlineViewButton() {
        super(getCoreDesktop(), BudgetOutline.class);
      }

      @Override
      protected DisplayStyle getConfiguredDisplayStyle() {
        return DisplayStyle.MENU;
      }

      @Override
      protected String getConfiguredKeyStroke() {
        return "ctrl-shift-b";
      }
    }
}
