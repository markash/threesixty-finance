package com.github.markash.threesixty.financial.shared.text;

import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.AbstractDynamicNlsTextProviderService;

/**
 * @author mpash
 */
@Order(-2000)
public class DefaultTextProviderService extends AbstractDynamicNlsTextProviderService {
	@Override
	public String getDynamicNlsBaseName() {
		return "org.eclipse.scout.apps.helloworld.shared.texts.Texts";
	}
}
