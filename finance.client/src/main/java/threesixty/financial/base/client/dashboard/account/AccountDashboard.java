package threesixty.financial.base.client.dashboard.account;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tilefield.AbstractTileField;
import org.eclipse.scout.rt.client.ui.tile.AbstractTileGrid;
import org.eclipse.scout.rt.platform.text.TEXTS;

import threesixty.financial.base.client.dashboard.IDashboardTile;
import threesixty.financial.base.client.dashboard.account.AccountDashboardForm.MainBox.TileField;

public class AccountDashboard extends AbstractPageWithNodes {

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Dashboard");
    }

    public class AccountForm extends AbstractForm {
        
        @Override
        protected void initFormInternal() {
            super.initFormInternal();
            
            AccountDashboardTile tile = new AccountDashboardTile("Accounts");
            tile.setLabel("Test");
        
            
            getTileField().getTileGrid().addTile(tile);
       
        }
        
        public TileField getTileField() {
            return getFieldByClass(TileField.class);
        }
        
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
    }
    
    
}
