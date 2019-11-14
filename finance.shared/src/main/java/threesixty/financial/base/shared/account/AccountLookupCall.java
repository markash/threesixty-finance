package threesixty.financial.base.shared.account;

import org.eclipse.scout.rt.shared.services.lookup.ILookupService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

/**
 * Account Lookup Call
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 */
public class AccountLookupCall extends LookupCall<Long> {
    private static final long serialVersionUID = 5768458272054863780L;

    @Override
    protected Class<? extends ILookupService<Long>> getConfiguredService() {

        return IAccountLookupService.class;
    }  
}
