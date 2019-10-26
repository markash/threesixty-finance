package threesixty.financial.base.client.dashboard;

import org.eclipse.scout.rt.client.ui.tile.AbstractTileAccordionGroupManager;
import org.eclipse.scout.rt.client.ui.tile.GroupTemplate;

public class DashboardTileGroupManager extends AbstractTileAccordionGroupManager<IDashboardTile> {

    public static final Object ID = DashboardTileGroupManager.class;
    
    private String iconId;
    
    @Override
    public Object getId() {

        return ID;
    }
    
    @Override
    public Object getGroupIdByTile(
            final IDashboardTile tile) {
        
        return tile.getGroup();
    }

    public String getIconId() {
        
        return this.iconId;
    }

    public void setIconId(
            final String iconId) {
        
        this.iconId = iconId;
    }

    @Override
    public GroupTemplate createGroupForTile(
            final IDashboardTile tile) {
        
        return new GroupTemplate(tile.getGroup(), tile.getGroup())
                .withIconId(getIconId());
    }
}
