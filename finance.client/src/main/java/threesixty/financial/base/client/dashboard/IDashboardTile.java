package threesixty.financial.base.client.dashboard;

import org.eclipse.scout.rt.client.ui.tile.IHtmlTile;

/**
 * 
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 */
public interface IDashboardTile extends IHtmlTile {

    String PROP_LABEL = "label";
    
    String getLabel();
    String getGroup();
    
    void setLabel(String label);
    void setGroup(String group);
}
