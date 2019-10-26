package threesixty.financial.event.client.events;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithNodes;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.text.TEXTS;

public class EventNodePage extends AbstractPageWithNodes {

    @Override
    protected String getConfiguredTitle() {
        // TODO [mpash] verify translation
        return TEXTS.get("EventNodePage");
    }

    @Override
    protected void execCreateChildPages(List<IPage<?>> pageList) {
        // TODO [mpash] Auto-generated method stub.
        super.execCreateChildPages(pageList);
    }
}
