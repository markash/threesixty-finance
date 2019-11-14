package threesixty.financial.base.client.common.menu;

import java.util.Set;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TreeMenuType;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import threesixty.financial.base.shared.Icons.LineAwesome;

/**
 * Abstract New Menu Item
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 */
public class AbstractNewMenu extends AbstractMenu {
    @Override
    protected String getConfiguredText() {
        return TEXTS.get("New");
    }

    @Override
    protected String getConfiguredIconId() {
        return LineAwesome.PLUS_CIRCLE;
    }
    
    @Override
    protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TableMenuType.SingleSelection, TreeMenuType.SingleSelection);
    }
}