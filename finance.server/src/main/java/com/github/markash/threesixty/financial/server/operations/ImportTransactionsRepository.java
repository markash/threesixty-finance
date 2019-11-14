package com.github.markash.threesixty.financial.server.operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.server.jdbc.ISqlService;
import org.eclipse.scout.rt.server.jdbc.SQL;

import com.github.markash.threesixty.financial.server.sql.SQLs;
import com.github.markash.threesixty.financial.shared.operations.Allocation;
import com.github.markash.threesixty.financial.shared.operations.IImportTransactionsRepository;
import com.github.markash.threesixty.financial.shared.operations.ImportTransactionLine;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;

import threesixty.financial.base.shared.exception.DatabaseException;

public class ImportTransactionsRepository implements IImportTransactionsRepository {

    public void importTransactionHistory(
            List<ImportTransactionLine> rows) throws DatabaseException {
        
        try {
            
            Connection connection = BEANS.get(ISqlService.class).getConnection();
            
            // Create an in-memory data table.  
            SQLServerDataTable transactions = new SQLServerDataTable();
              
            // Define metadata for the data table.  
            transactions.addColumnMetadata(ImportTransactionLine.COL_DATE,         java.sql.Types.DATE);
            transactions.addColumnMetadata(ImportTransactionLine.COL_DESCRIPTION,  java.sql.Types.NVARCHAR);
            transactions.addColumnMetadata(ImportTransactionLine.COL_AMOUNT,       java.sql.Types.DECIMAL);
            transactions.addColumnMetadata(ImportTransactionLine.COL_BALANCE,      java.sql.Types.DECIMAL);
            
            for(ImportTransactionLine row : rows) {
                transactions.addRow(
                        row.getDate().format(DateTimeFormatter.ISO_DATE), 
                        row.getDescription(), 
                        row.getAmount(), 
                        row.getBalance());   
            }
                          
            try (SQLServerPreparedStatement pStmt = (SQLServerPreparedStatement) connection.prepareStatement(SQLs.TransactionHistory.IMPORT)) {
                pStmt.setStructured(1, "dbo.TransactionHistoryType", transactions);  
                pStmt.execute();
                
                connection.commit();
            }
        } catch (Exception e) {
            throw new DatabaseException("Database call to dbo.prTransactionHistoryImport failed", e);
        }
    }
    
    public void updateFullTextIndex() throws DatabaseException {
    
        try {
          
            Connection connection = BEANS.get(ISqlService.class).getConnection();
            
            try (PreparedStatement pStmt = connection.prepareStatement(SQLs.TransactionHistory.FULL_TEXT_UPDATE)) {
             
                pStmt.execute();
            }
        } catch (Exception e) {
            throw new DatabaseException("Database call to update transaction history full index failed", e);
        }
    }
    
    /**
     * Delete the transaction history that has already been imported / allocated in the ledger (i.e. duplicates).
     * @throws DatabaseException If the duplicates could not be removed
     */
    public void deleteDuplicateTransactionHistory() throws DatabaseException {
        
        try {
            Connection connection = BEANS.get(ISqlService.class).getConnection();
            
            try (PreparedStatement pStmt = connection.prepareStatement(SQLs.TransactionHistory.DELETE_DUPLICATES)) {
                
                pStmt.execute();
            }
        } catch (Exception e) {
            throw new DatabaseException("Database call to delete the duplicate transaction history items failed.", e);
        }
    }
    
    /**
     * Auto allocate the transaction history to the ledger based upon the description mapping to account
     * @throws DatabaseException If the auto allocation could not be performed
     */
    public void autoAllocateTransactionHistory() throws DatabaseException {
        
        try {
            Connection connection = BEANS.get(ISqlService.class).getConnection();
            
            try (PreparedStatement pStmt = connection.prepareStatement(SQLs.TransactionHistory.AUTO_ALLOCATE)) {
                
                pStmt.execute();
            }
        } catch (Exception e) {
            throw new DatabaseException("Database call to auto allocate the transaction history.", e);
        }
    }
    
    /**
     * Manual allocate the transaction history to the ledger
     * @param transactionId The transaction id within the transaction history
     * @param allocation A single allocation
     * @throws ProcessingException If the manual allocation could not be performed
     */
    public void manualAllocateTransactionHistory(
            final long transactionId,
            final Allocation allocation) throws DatabaseException {
        
        manualAllocateTransactionHistory(transactionId, Arrays.asList(allocation));
    }
    
    /**
     * Manual allocate the transaction history to the ledger
     * @param transactionId The transaction id within the transaction history
     * @param accountId The account id of the account to allocate to
     * @param comment The comment for the allocation
     * @throws ProcessingException If the manual allocation could not be performed
     */
    public void manualAllocateTransactionHistory(
            final long transactionId,
            final List<Allocation> allocations) throws DatabaseException {

        Connection connection = null;
        try {
            connection = BEANS.get(ISqlService.class).getConnection();
            
            // Create an in-memory data table.  
            SQLServerDataTable allocationTable = new SQLServerDataTable();
              
            // Define metadata for the data table.  
            allocationTable.addColumnMetadata(Allocation.COL_ACCOUNT_ID,           java.sql.Types.INTEGER);
            allocationTable.addColumnMetadata(Allocation.COL_AMOUNT,               java.sql.Types.DECIMAL);
            allocationTable.addColumnMetadata(Allocation.COL_COMMENT,              java.sql.Types.NVARCHAR);
            
            for(Allocation allocation : allocations) {
                allocationTable.addRow(
                        allocation.getAccountId(), 
                        allocation.getAmount(), 
                        allocation.getComment());   
            }
            
            try (SQLServerPreparedStatement pStmt = (SQLServerPreparedStatement) connection.prepareStatement(SQLs.TransactionHistory.MANUAL_ALLOCATE)) {
                
                pStmt.setLong(1, transactionId);
                pStmt.setStructured(2, "dbo.AllocationType", allocationTable);  
                pStmt.execute();
                            
                connection.commit();
            }
            
        } catch (SQLException e) {
            
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (Exception w) {
                    //NOSONAR
                }
            }
            
            throw new DatabaseException("Unable to allocate transaction history", e);
        }
    }
    
    /**
     * Delete the complete transaction history
     */
    public void deleteAllTransactionHistory() {
        
        SQL.update(SQLs.TransactionHistory.DELETE);
    }
}
