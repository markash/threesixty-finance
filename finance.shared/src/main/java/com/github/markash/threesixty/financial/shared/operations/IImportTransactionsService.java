package com.github.markash.threesixty.financial.shared.operations;

import java.util.List;

import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;

import com.github.markash.threesixty.financial.shared.database.DatabaseException;

/**
 * 
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 *
 */
@Bean
@TunnelToServer
public interface IImportTransactionsService extends IService {

    /**
     * Import the resource (comma-separated file) into the transaction history
     * @param rows The transaction lines to import
     * @return The import result
     * @throws ImportTransactionsException If importing the transactions failed
     */
    ImportResult importFile(
            final List<ImportTransactionLine> rows) throws ImportTransactionsException;

    /**
     * Return the imported transaction hitory data
     * @param filter The filter for the transaction history
     * @return Page table data containing the transaction history
     */
    ImportTransactionsTablePageData getImportTransactionsTableData(
            final SearchFilter filter);
    
    /**
     * Auto allocate the transaction history to the ledger based upon the description mapping to account
     * @throws DatabaseException If the auto allocation could not be performed
     */
    void autoAllocateTransactionHistory() throws DatabaseException;
    
    /**
     * Delete the complete transaction history
     */
    void deleteAllTransactionHistory();
    
    /**
     * Manual allocate the transaction history to the ledger
     * @param transactionId The transaction id within the transaction history
     * @param allocations The list of allocations
     * @throws DatabaseException If the manual allocation could not be performed
     */
    void manualAllocateTransactionHistory(
            final long transactionId,
            final List<Allocation> allocations) throws DatabaseException;
}
