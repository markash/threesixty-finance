package com.github.markash.threesixty.financial.shared.operations;

import java.util.List;

import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import threesixty.financial.base.shared.exception.DatabaseException;

/**
 * 
 * @author Mark P Ashworth (mp.ashworth@gmail.com)
 *
 */
@Bean
@TunnelToServer
public interface IImportTransactionsRepository extends IService {

    /**
     * Import the transaction history lines into the database
     * @param rows The transaction lines
     * @throws DatabaseException When the transaction history type object errors
     */
    void importTransactionHistory(
            final List<ImportTransactionLine> rows) throws DatabaseException;
    
    /**
     * Delete the transaction history that has already been imported / allocated in the ledger (i.e. duplicates).
     * @throws DatabaseException If the duplicates could not be removed
     */
    void deleteDuplicateTransactionHistory() throws DatabaseException;
    
    /**
     * Auto allocate the transaction history to the ledger based upon the description mapping to account
     * @throws DatabaseException If the auto allocation could not be performed
     */
    void autoAllocateTransactionHistory() throws DatabaseException;
    
    /**
     * Manual allocate the transaction history to the ledger
     * @param transactionId The transaction id within the transaction history
     * @param allocation A single allocation
     * @throws SQLServerException SQL Server exception
     * @throws DatabaseException If the manual allocation could not be performed
     */
    void manualAllocateTransactionHistory(
            final long transactionId,
            final Allocation allocation) throws DatabaseException;
    
    /**
     * Manual allocate the transaction history to the ledger
     * @param transactionId The transaction id within the transaction history
     * @param allocations The list of allocations
     * @throws DatabaseException If the manual allocation could not be performed
     */
    void manualAllocateTransactionHistory(
            final long transactionId,
            final List<Allocation> allocations) throws DatabaseException;
    
    /**
     * Delete the complete transaction history
     */
    void deleteAllTransactionHistory();
    
    /**
     * Updates the transaction history full text index so that the search stored procedure can locate
     * text within the description field for the automatic allocation process.
     * @throws DatabaseException If the full text index failed
     */
    void updateFullTextIndex() throws DatabaseException;
}
