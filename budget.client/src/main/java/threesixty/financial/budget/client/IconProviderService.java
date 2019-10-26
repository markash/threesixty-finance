package threesixty.financial.budget.client;

import java.net.URL;

import org.eclipse.scout.rt.client.services.common.icon.AbstractIconProviderService;
import org.eclipse.scout.rt.platform.Order;

@Order(2000)
public class IconProviderService extends AbstractIconProviderService {

  @Override
  protected URL findResource(
          final String relativePath) {
    
      return ResourceBase.class.getResource("icons/" + relativePath);
  }
}