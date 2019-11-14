package threesixty.financial.base.shared.account;

import java.util.List;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import com.github.markash.threesixty.financial.shared.accounts.AccountFormData;
import com.github.markash.threesixty.financial.shared.accounts.AccountTablePageData;

@TunnelToServer
public interface IAccountsService extends IService {

	AccountTablePageData getAccountsTableData(
	        final SearchFilter filter);
	
	List<Account> retrieveAccounts();
	
	Account retrieveAccount(
	        final Long accountId);
	
	AccountFormData prepareCreate(
	        final AccountFormData formData);

    AccountFormData create(
            final AccountFormData formData);

    AccountFormData load(
            final AccountFormData formData);

    AccountFormData store(
            final AccountFormData formData);
}
