package threesixty.financial.base.client.dashboard;

import org.eclipse.scout.rt.client.ui.tile.AbstractHtmlTile;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.annotations.ConfigProperty;

public abstract class AbstractDashboardTile extends AbstractHtmlTile implements IDashboardTile {

    private String group;
    
    @Override
    protected void initConfig() {
        super.initConfig();
        
        setLabel(getConfiguredLabel());
    }
    
    @Order(10)
    @ConfigProperty(ConfigProperty.TEXT)
    protected String getConfiguredLabel() {
        
        return null;
    }
    
    @Override
    public String getLabel() {
        
        return (String) propertySupport.getProperty(PROP_LABEL);
    }
    
    @Override
    public void setLabel(
            final String label) {
        
        propertySupport.setProperty(PROP_LABEL, label);
    }
    
    @Override
    public String getGroup() {

        return this.group;
    }
    
    @Override
    public void setGroup(
            final String group) {
        
        this.group = group;
    }
}
