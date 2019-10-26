package threesixty.financial.base.client.dashboard.account;

import org.eclipse.scout.rt.client.ui.desktop.bench.layout.BenchLayoutData;
import org.eclipse.scout.rt.client.ui.desktop.bench.layout.FlexboxLayoutData;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tilefield.AbstractTileField;
import org.eclipse.scout.rt.client.ui.tile.AbstractTileGrid;
import org.eclipse.scout.rt.platform.classid.ClassId;

import threesixty.financial.base.client.dashboard.AbstractDashboardForm;
import threesixty.financial.base.client.dashboard.IDashboardTile;
import threesixty.financial.base.client.dashboard.account.AccountDashboardForm.MainBox.TileField;

public class AccountDashboardForm extends AbstractDashboardForm<FlexboxLayoutData> {
    
    
    @Override
    protected String getConfiguredDisplayViewId() {

        return VIEW_ID_N;
    }
    
    @Override
    protected FlexboxLayoutData getLayoutDataForUpdate() {

        return getDesktop()
                .getBenchLayoutData()
                .getCenter()
                .getNorth()
                .copy();
    }

    @Override
    protected void storeLayoutData(
            final FlexboxLayoutData layoutData) {
        
        BenchLayoutData benchLayoutData = getDesktop().getBenchLayoutData().copy();
        benchLayoutData.getCenter().withNorth(layoutData);
        getDesktop().setBenchLayoutData(benchLayoutData);
        
    }

    public TileField getTileField() {
        return getFieldByClass(TileField.class);
    }
    
    @ClassId("fcf36822-2d2d-4a23-a32d-74f7385370ea")
    public class MainBox extends AbstractGroupBox {
        
        public class TileField extends AbstractTileField<TileField.TileGrid> {
            
            @Override
            protected int getConfiguredGridW() { return FULL_WIDTH; }
            
            @Override
            protected boolean getConfiguredLabelVisible() { return false; }
            
            public class TileGrid extends AbstractTileGrid<IDashboardTile> {
                
            }
        }
    }
    
    @Override
    protected void initFormInternal() {
        super.initFormInternal();
        
        AccountDashboardTile tile = new AccountDashboardTile("Accounts");
        tile.setLabel("Test");
        tile.toggleCssClass("disabled", true);
        
        getTileField().getTileGrid().addTile(tile);
    }
}
