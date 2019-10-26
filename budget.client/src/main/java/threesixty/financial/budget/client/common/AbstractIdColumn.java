package threesixty.financial.budget.client.common;

import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;

public class AbstractIdColumn extends AbstractLongColumn {

    @Override
    protected boolean getConfiguredVisible() { return false; }
}