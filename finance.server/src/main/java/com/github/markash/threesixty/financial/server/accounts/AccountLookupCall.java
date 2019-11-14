package com.github.markash.threesixty.financial.server.accounts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

import com.github.markash.threesixty.financial.shared.accounts.AccountTablePageData.AccountTableRowData;

public class AccountLookupCall extends LocalLookupCall<Long> {
    
    private static final long serialVersionUID = 1L;
   
    @Override
    protected List<ILookupRow<Long>> execCreateLookupRows() {
        
      List<ILookupRow<Long>> rows = new ArrayList<>();
      rows.add(createLookupRow(1L, "Test", 2L, "Blah"));
      rows.add(createLookupRow(2L, "Blah", 3L, "Me"));
      
      return rows;
    }
   
    private ILookupRow<Long> createLookupRow(
            final Long accountId, 
            final String accountName, 
            final Long accountParentId, 
            final String accountParentName) {
      
      AccountTableRowData data = new AccountTableRowData();
      data.setAccountId(accountId);
      data.setAccountParentId(accountParentId);
      data.setAccountName(accountName);
      data.setAccountParentName(accountParentName);
      
      return new LookupRow<>(accountId, accountName);
    }
 }