package com.github.markash.threesixty.financial.server.accounts;

import org.eclipse.scout.rt.server.jdbc.lookup.AbstractSqlLookupService;

import com.github.markash.threesixty.financial.server.sql.SQLs;
import com.github.markash.threesixty.financial.shared.accounts.IAccountLookupService;

/**
 * Account Lookup Service
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 */
public class AccountLookupService 
        extends AbstractSqlLookupService<Long> 
        implements IAccountLookupService {

    
    @Override
    protected String filterSqlByKey(
            final String sqlSelect) {
        
        return super.filterSqlByKey(sqlSelect);
    }

    @Override
    protected String getConfiguredSqlSelect() {
        
        return SQLs.Account.LOOKUP;
    }  
}
