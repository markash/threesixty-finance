package threesixty.financial.base.shared.account;

import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.lookup.ILookupService;

/**
 * Account Lookup Service
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 */
@TunnelToServer
public interface IAccountLookupService extends ILookupService<Long> {

    
}
