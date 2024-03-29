package threesixty.financial.base.client.operations;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

import threesixty.financial.base.shared.Icons;

/**
 * 
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 *
 */
@Order(3000)
public class OperationsOutline extends AbstractOutline {

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    super.execCreateChildPages(pageList);
    pageList.add(new ImportTransactionsTablePage());
  }
  
	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Operations");
	}

	@Override
	protected String getConfiguredIconId() {
		return Icons.LineAwesome.FOLDER;
	}
}
