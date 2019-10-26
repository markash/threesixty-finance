package threesixty.financial.base.client.settings;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;

import com.github.markash.threesixty.financial.shared.Icons;

/**
 * 
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 *
 */
@Order(3000)
public class SettingsOutline extends AbstractOutline {

	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("Settings");
	}

	@Override
	protected String getConfiguredIconId() {
		return Icons.LineAwesome.Gears;
	}
}
