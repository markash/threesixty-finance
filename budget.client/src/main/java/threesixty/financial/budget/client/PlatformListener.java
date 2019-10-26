package threesixty.financial.budget.client;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.IPlatform.State;
import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.shared.extension.IExtensionRegistry;

public class PlatformListener implements IPlatformListener{

    @Override
    public void stateChanged(
            final PlatformEvent event) {
        
      if (event.getState() == State.BeanManagerValid) {
        registerExtensions();
      }
    }

    private void registerExtensions() {
      IExtensionRegistry extensionRegistry = BEANS.get(IExtensionRegistry.class);

      // Register UI extensions
      extensionRegistry.register(DesktopQuickAccessMenuExtension.class);
//      extensionRegistry.register(PersonFormTabExtension.class);
//      extensionRegistry.register(PersonTablePageExtension.class);
//      extensionRegistry.register(EventOutlineExtension.class);
//      extensionRegistry.register(EventPageExtension.class);

      // Register DTO extensions
//      extensionRegistry.register(PersonFormTabExtensionData.class);
//      extensionRegistry.register(PersonTablePageDataExtension.class);
    }
}
