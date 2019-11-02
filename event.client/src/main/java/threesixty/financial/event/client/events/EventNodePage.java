package threesixty.financial.event.client.events;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class EventNodePage extends AbstractPageWithNodes {

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Event");
    }

    @Override
    protected void execCreateChildPages(
            final List<IPage<?>> pageList) {

        super.execCreateChildPages(pageList);
    }
}
