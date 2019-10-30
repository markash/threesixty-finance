package com.github.markash.threesixty.financial.server.accounts;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.rt.shared.services.common.security.ACCESS;

import com.github.markash.threesixty.financial.server.sql.SQLs;
import com.github.markash.threesixty.financial.shared.accounts.Account;
import com.github.markash.threesixty.financial.shared.accounts.AccountFormData;
import com.github.markash.threesixty.financial.shared.accounts.AccountsTablePageData;
import com.github.markash.threesixty.financial.shared.accounts.AccountsTablePageData.AccountsTableRowData;
import com.github.markash.threesixty.financial.shared.accounts.CreateAccountPermission;
import com.github.markash.threesixty.financial.shared.accounts.IAccountsService;
import com.github.markash.threesixty.financial.shared.accounts.ReadAccountPermission;
import com.github.markash.threesixty.financial.shared.accounts.UpdateAccountPermission;

public class AccountsService implements IAccountsService {

	
	@Override
	public AccountsTablePageData getAccountsTableData(SearchFilter filter) {
		
		AccountsTablePageData pageData = new AccountsTablePageData();
				
		SQL.selectInto(
		        SQLs.Account.SELECT_INTO_PAGE_DATA, 
		        new NVPair(SQLs.Account.PARAM_PAGE, pageData));

		return pageData;
	}

	/**
	 * Retrieve all the accounts
	 * @return List of accounts
	 */
    @Override
    public List<Account> retrieveAccounts() {

        BeanArrayHolder<Account> valueObject = new BeanArrayHolder<>(Account.class);
        
        SQL.selectInto(
                SQLs.Account.ValueObject.SELECT_ALL_INTO_VALUE_OBJECT, 
                new NVPair("valueObject", valueObject));
        
        return Arrays.asList(valueObject.getBeans());
    }

    /**
     * Retrieve an account by id
     * @param accountId The account id
     * @return The account or error if not found
     * @throws ProcessingException If there account by id could not be found
     */
    @Override
    public Account retrieveAccount(
            final Long accountId) {

        BeanArrayHolder<Account> valueObject = new BeanArrayHolder<>(Account.class);
        
        SQL.selectInto(
                SQLs.Account.ValueObject.SELECT_SINGLE_INTO_VALUE_OBJECT, 
                new NVPair("accountId", accountId),
                new NVPair("valueObject", valueObject));
        
        if (valueObject.getBeans().length > 0) {
            
            return valueObject.getBeans()[0];
        }
        
        throw new ProcessingException("Account accountId=" + accountId + " not found.");
    }
    
    @Override
    public AccountFormData prepareCreate(AccountFormData formData) {
        if (!ACCESS.check(new CreateAccountPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
        // TODO [mpash] add business logic here.
        return formData;
    }

    @Override
    public AccountFormData create(
            final AccountFormData formData) {
        
        if (!ACCESS.check(new CreateAccountPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
        
        AccountsTablePageData pageData = new AccountsTablePageData();
        
        SQL.select(
                SQLs.Account.PROC_SUB_ACCOUNT_NEW_INTP_PAGE_DATA, 
                new NVPair("accountParentId", formData.getParentAccount().getValue()),
                new NVPair("accountName", formData.getAccountName().getValue()),
                new NVPair("page", pageData)
                );

        if (pageData.getRowCount() > 0) {
        
            AccountsTableRowData row = pageData.rowAt(0);
            formData.getAccountId().setValue(row.getAccountId());
        }
        
        return formData;
    }

    @Override
    public AccountFormData load(AccountFormData formData) {
        if (!ACCESS.check(new ReadAccountPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }

        return formData;
    }

    @Override
    public AccountFormData store(
            final AccountFormData formData) {
        
        if (!ACCESS.check(new UpdateAccountPermission())) {
            throw new VetoException(TEXTS.get("AuthorizationFailed"));
        }
        
        AccountsTablePageData pageData = new AccountsTablePageData();
        
        SQL.select(
                SQLs.Account.PROC_SUB_ACCOUNT_NEW_INTP_PAGE_DATA, 
                new NVPair("accountParentId", formData.getParentAccount().getValue()),
                new NVPair("accountName", formData.getAccountName().getValue()),
                new NVPair("pageData", pageData)
                );

        if (pageData.getRowCount() > 0) {
        
            AccountsTableRowData row = pageData.rowAt(0);
            formData.getAccountId().setValue(row.getAccountId());
        }
        
        return formData;
    }
}
