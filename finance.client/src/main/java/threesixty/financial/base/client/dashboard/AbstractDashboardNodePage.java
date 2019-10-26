package threesixty.financial.base.client.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.IDesktop;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchLayoutData;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

public abstract class AbstractDashboardNodePage extends AbstractPageWithNodes {

    private List<IForm> pageForms;
    private BenchLayoutData benchLayoutData;
    
    @Override
    protected String getConfiguredTitle() {
        
        return TEXTS.get("Dashboard");
    }

    @Override
    protected boolean getConfiguredLeaf() {
        
        return true;
    }
    
    protected BenchLayoutData getConfiguredBenchLayoutData() {
        
        return null;
    }
    
    @Override
    protected boolean getConfiguredTableVisible() {
        
        return false;
    }
    
    public BenchLayoutData getBenchLayoutData() {
        
        return benchLayoutData;
    }

    public void setBenchLayoutData(
            final BenchLayoutData benchLayoutData) {
        
        this.benchLayoutData = benchLayoutData;
    }

    @Override
    protected void execInitPage() {
       
        super.execInitPage();
        setBenchLayoutData(getConfiguredBenchLayoutData());
    }
    
    @Override
    protected void execPageActivated() {
        super.execPageActivated();
        
        IDesktop.CURRENT.get().setBenchLayoutData(getBenchLayoutData());
        
        List<IForm> formList = new ArrayList<>();
        execCreatePageForms(formList);
        this.pageForms = CollectionUtility.arrayList(formList);
    }

    @Override
    protected void execPageDeactivated() {
        
        if (this.pageForms != null) {
            for (IForm form : this.pageForms) {
                form.doClose();
            }
        }
        super.execPageDeactivated();
    }
    
    protected void execCreatePageForms(final List<IForm> formList) {
        
    }
}
