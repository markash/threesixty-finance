package threesixty.financial.base.client.dashboard;

import org.eclipse.scout.rt.client.ui.desktop.bench.layout.FlexboxLayoutData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;

public abstract class AbstractDashboardForm<T extends FlexboxLayoutData> extends AbstractForm {
    
    @Override
    protected int getConfiguredDisplayHint() {
        return DISPLAY_HINT_VIEW;
    }

    @Override
    protected String getConfiguredDisplayViewId() {
        return VIEW_ID_CENTER;
    }

    @Override
    protected String getConfiguredCssClass() {
        return "bench-layout-form";
    }

    @Override
    protected boolean getConfiguredAskIfNeedSave() {
        return false;
    }

    protected int getCongfiguredMainBoxGridColumnCount() {
        return 1;
    }

    protected abstract T getLayoutDataForUpdate();

    protected abstract void storeLayoutData(T layoutData);
}
