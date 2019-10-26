package threesixty.financial.budget.client.common;

import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractSmartColumn;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;

import com.github.markash.threesixty.financial.shared.accounts.AccountLookupCall;

public abstract class AbstractAccountCodeSmartColumn extends AbstractSmartColumn<Long> {
    @Override
    protected String getConfiguredHeaderText() { return TEXTS.get("AccountCode"); }

    @Override
    protected java.lang.Class<? extends ILookupCall<Long>> getConfiguredLookupCall() { return AccountLookupCall.class; };
    
    @Override
    protected int getConfiguredWidth() { return 20; }
}
