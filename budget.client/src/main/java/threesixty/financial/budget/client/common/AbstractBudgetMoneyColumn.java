package threesixty.financial.budget.client.common;

import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractBigDecimalColumn;
import org.eclipse.scout.rt.platform.text.TEXTS;

/**
 * Abstract money column
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 */
public abstract class AbstractBudgetMoneyColumn extends AbstractBigDecimalColumn {
    
    @Override
    protected String getConfiguredHeaderText() {
        return TEXTS.get("Money");
    }

    @Override
    protected int getConfiguredWidth() {
        return 150;
    }
    
    @Override
    protected int getConfiguredFractionDigits() { 
        return super.getConfiguredFractionDigits(); 
    }
}