package threesixty.financial.budget.client.common;

import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractDateColumn;
import org.eclipse.scout.rt.platform.text.TEXTS;

/**
 * Abstract budget date column
 * 
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 */
public abstract class AbstractBudgetDateColumn extends AbstractDateColumn {
    @Override
    protected String getConfiguredHeaderText() { return TEXTS.get("Date"); }

    @Override
    protected int getConfiguredWidth() { return 20; }
    
    @Override
    protected String getConfiguredFormat() { return "dd MMM yyyy"; }
}