package com.github.markash.threesixty.financial.shared.accounts;

import java.util.List;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

@TunnelToServer
public interface IAccountsService extends IService {

	AccountsTablePageData getAccountsTableData(
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
