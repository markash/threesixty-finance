package threesixty.financial.event.client.events;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.text.TEXTS;

import com.github.markash.threesixty.financial.shared.Icons;

public class EventOutline extends AbstractOutline {

    @Override
    protected String getConfiguredTitle() {
      return TEXTS.get("Events");
    }

    @Override
    protected void execCreateChildPages(List<IPage<?>> pageList) {
      EventNodePage eventNodePage = new EventNodePage();
      pageList.add(eventNodePage);
    }

    @Override
    protected String getConfiguredIconId() {
      return Icons.LineAwesome.Calendar;
    }
}
