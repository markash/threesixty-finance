package com.github.markash.threesixty.financial.server.operations;

import java.util.List;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import com.github.markash.threesixty.financial.server.sql.SQLs;
import com.github.markash.threesixty.financial.shared.operations.Allocation;
import com.github.markash.threesixty.financial.shared.operations.IImportTransactionsRepository;
import com.github.markash.threesixty.financial.shared.operations.IImportTransactionsService;
import com.github.markash.threesixty.financial.shared.operations.ImportResult;
import com.github.markash.threesixty.financial.shared.operations.ImportTransactionLine;
import com.github.markash.threesixty.financial.shared.operations.ImportTransactionsException;
import com.github.markash.threesixty.financial.shared.operations.ImportTransactionsTablePageData;

import threesixty.financial.base.shared.exception.DatabaseException;

public class ImportTransactionsService implements IImportTransactionsService {

    /**
     * Imports the transactions binary resource
     * 
     * @param rows The transaction lines to import
     * @return The import result
     */
    public ImportResult importFile(
            final List<ImportTransactionLine> rows) throws ImportTransactionsException {
        
        try {
            
            BEANS.get(IImportTransactionsRepository.class).importTransactionHistory(rows);

            BEANS.get(IImportTransactionsRepository.class).updateFullTextIndex();
            
            BEANS.get(IImportTransactionsRepository.class).deleteDuplicateTransactionHistory();
            
            return new ImportResult();
        } catch (Exception e) {
            throw new ImportTransactionsException("Unable to import transactions from file", e);
        }
    }

    /**
     * Auto allocate the transaction history to the ledger based upon the description mapping to account
     * @throws DatabaseException If the auto allocation could not be performed
     */
    public void autoAllocateTransactionHistory() throws DatabaseException {
        
        BEANS.get(IImportTransactionsRepository.class).autoAllocateTransactionHistory();
    }
    
    /**
     * Delete the complete transaction history
     */
    public void deleteAllTransactionHistory() {
        
        BEANS.get(IImportTransactionsRepository.class).deleteAllTransactionHistory();
    }

    /**
     * Manual allocate the transaction history to the ledger
     * @param transactionId The transaction id within the transaction history
     * @param allocations The list of allocations
     * @throws DatabaseException If the manual allocation could not be performed
     */
    public void manualAllocateTransactionHistory(
            final long transactionId,
            final List<Allocation> allocations) throws DatabaseException {
        
        BEANS.get(IImportTransactionsRepository.class).manualAllocateTransactionHistory(transactionId, allocations);
    }
    
    @Override
    public ImportTransactionsTablePageData getImportTransactionsTableData(
            final SearchFilter filter) {
        
        ImportTransactionsTablePageData pageData = new ImportTransactionsTablePageData();
        
        SQL.selectInto(SQLs.TransactionHistory.SELECT_INTO_PAGE_DATA, new NVPair("page", pageData));
                
        return pageData;
    }
}
