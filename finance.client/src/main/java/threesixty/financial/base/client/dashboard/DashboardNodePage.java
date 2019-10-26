package threesixty.financial.base.client.dashboard;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchColumnData;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchLayoutData;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.FlexboxLayoutData;
import org.eclipse.scout.rt.client.ui.form.IForm;

import threesixty.financial.base.client.dashboard.account.AccountDashboardForm;

public class DashboardNodePage extends AbstractDashboardNodePage {

    @Override
    protected BenchLayoutData getConfiguredBenchLayoutData() {
        
        return new BenchLayoutData()
                .withCenter(
                        new BenchColumnData()
                            .withNorth(new FlexboxLayoutData())
                            .withCenter(new FlexboxLayoutData())
                            .withSouth(new FlexboxLayoutData())
                            );
    }
    
    @Override
    protected void execCreatePageForms(
            final List<IForm> formList) {

        AccountDashboardForm accountDashboardForm = new AccountDashboardForm();
        accountDashboardForm.start();
        
        formList.add(accountDashboardForm);
    }
}
