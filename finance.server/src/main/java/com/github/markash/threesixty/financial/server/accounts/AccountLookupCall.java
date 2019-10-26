package com.github.markash.threesixty.financial.server.accounts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

import com.github.markash.threesixty.financial.shared.accounts.AccountsTablePageData.AccountsTableRowData;

public class AccountLookupCall extends LocalLookupCall<Long> {
    
    private static final long serialVersionUID = 1L;
   
    @Override
    protected List<ILookupRow<Long>> execCreateLookupRows() throws ProcessingException {
      List<ILookupRow<Long>> rows = new ArrayList<ILookupRow<Long>>();
      rows.add(createLookupRow(1L, "Test", 2L, "Blah"));
      rows.add(createLookupRow(2L, "Blah", 3L, "Me"));
      
      return rows;
    }
   
    private ILookupRow<Long> createLookupRow(
            final Long accountId, 
            final String accountName, 
            final Long accountParentId, 
            final String accountParentName) {
      
      AccountsTableRowData data = new AccountsTableRowData();
      data.setAccountId(accountId);
      data.setAccountParentId(accountParentId);
      data.setAccountName(accountName);
      data.setAccountParentName(accountParentName);
      
      LookupRow<Long> row = new LookupRow<Long>(accountId, accountName);
      
      
      //row.set (data);
      
      return row;
    }
 }