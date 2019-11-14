package threesixty.financial.base.client.search;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractSearchOutline;
import org.eclipse.scout.rt.platform.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import threesixty.financial.base.shared.Icons;

/**
 * 
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 *
 */
@Order(2000)
public class SearchOutline extends AbstractSearchOutline {

	private static final Logger LOG = LoggerFactory.getLogger(SearchOutline.class);

	@Override
	protected void execSearch(
	        final String query) {
		LOG.info("Search started");
		// TODO [mpash]: Implement search
	}

	@Override
	protected String getConfiguredIconId() {
		return Icons.LineAwesome.SEARCH;
	}
}
