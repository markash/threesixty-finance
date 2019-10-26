package threesixty.financial.event.client;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktopExtension;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.platform.Order;

import threesixty.financial.event.client.events.EventOutline;

public class DesktopExtension extends AbstractDesktopExtension {

    @Override
    protected List<Class<? extends IOutline>> getConfiguredOutlines() {
        List<Class<? extends IOutline>> outlines = new ArrayList<>();
        outlines.add(EventOutline.class);
        return outlines;
    }
    
    @Order(2000)
    public class EventOutlineViewButton extends AbstractOutlineViewButton {

      public EventOutlineViewButton() {
        super(getCoreDesktop(), EventOutline.class);
      }

      @Override
      protected DisplayStyle getConfiguredDisplayStyle() {
        return DisplayStyle.MENU;
      }

      @Override
      protected String getConfiguredKeyStroke() {
        return "ctrl-shift-e";
      }
    }
}
