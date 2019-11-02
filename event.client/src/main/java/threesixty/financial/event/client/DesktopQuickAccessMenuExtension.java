package threesixty.financial.event.client;

import java.util.Set;

import org.eclipse.scout.rt.client.extension.ui.action.menu.AbstractMenuExtension;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;

import threesixty.financial.base.client.Desktop.QuickAccessMenu;

/**
 * 
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 */
public class DesktopQuickAccessMenuExtension extends AbstractMenuExtension<QuickAccessMenu> {

    public DesktopQuickAccessMenuExtension(
            final QuickAccessMenu owner) {

        super(owner);
    }

    @Order(30)
    public class NewMonthEndMenu extends AbstractMenu {

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.<IMenuType>hashSet();
        }

        @Override
        protected String getConfiguredText() {
            return TEXTS.get("NewMonthEnd");
        }
    }
    
    @Order(40)
    public class NewPaymentDateMenu extends AbstractMenu {

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.<IMenuType>hashSet();
        }

        @Override
        protected String getConfiguredText() {
            return TEXTS.get("NewPaymentDate");
        }
    }
}
