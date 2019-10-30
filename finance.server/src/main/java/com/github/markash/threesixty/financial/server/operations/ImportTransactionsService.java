package com.github.markash.threesixty.financial.server.operations;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.ProcessingException;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.server.jdbc.ISqlService;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.simpleflatmapper.csv.CsvMapper;
import org.simpleflatmapper.csv.CsvMapperFactory;
import org.simpleflatmapper.csv.CsvParser;

import com.github.markash.threesixty.financial.server.sql.SQLs;
import com.github.markash.threesixty.financial.shared.database.DatabaseException;
import com.github.markash.threesixty.financial.shared.operations.Allocation;
import com.github.markash.threesixty.financial.shared.operations.IImportTransactionsService;
import com.github.markash.threesixty.financial.shared.operations.ImportResult;
import com.github.markash.threesixty.financial.shared.operations.ImportTransactionLine;
import com.github.markash.threesixty.financial.shared.operations.ImportTransactionsException;
import com.github.markash.threesixty.financial.shared.operations.ImportTransactionsTablePageData;
import com.microsoft.sqlserver.jdbc.SQLServerDataTable;
import com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement;

public class ImportTransactionsService implements IImportTransactionsService {

    /**
     * Imports the transactions binary resource
     * 
     * @param resource The binary resource containing the transaction data
     * @return The import result
     */
    public ImportResult importFile(
            final BinaryResource resource) throws ImportTransactionsException {
        
        try {
            List<ImportTransactionLine> rows = getRows(resource.getContentAsString());

            importTransactionHistory(rows);

            updateFullTextIndex();
            
            deleteDuplicateTransactionHistory();
            
            return new ImportResult();
        } catch (Exception e) {
            throw new ImportTransactionsException("Unable to import transactions from file", e);
        }
    }

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
                          
            SQLServerPreparedStatement pStmt = (SQLServerPreparedStatement) connection.prepareStatement(SQLs.TransactionHistory.IMPORT);
            pStmt.setStructured(1, "dbo.TransactionHistoryType", transactions);  
            pStmt.execute();
            
            connection.commit();
            
        } catch (Exception e) {
            throw new DatabaseException("Database call to dbo.prTransactionHistoryImport failed", e);
        }
    }
    
    public void updateFullTextIndex() throws DatabaseException {
    
        try {
          
            BEANS.get(ISqlService.class)
                .getConnection()
                .prepareStatement(SQLs.TransactionHistory.FULL_TEXT_UPDATE)
                .execute();
            
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
            
            BEANS.get(ISqlService.class)
                .getConnection()
                .prepareStatement(SQLs.TransactionHistory.DELETE_DUPLICATES)
                .execute();
            
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
            
            BEANS.get(ISqlService.class)
                .getConnection()
                .prepareStatement(SQLs.TransactionHistory.AUTO_ALLOCATE)
                .execute();
            
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
            
            SQLServerPreparedStatement pStmt = (SQLServerPreparedStatement) connection.prepareStatement(SQLs.TransactionHistory.MANUAL_ALLOCATE);
            pStmt.setLong(1, transactionId);
            pStmt.setStructured(2, "dbo.AllocationType", allocationTable);  
            pStmt.execute();
                        
            connection.commit();
            
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
    
    protected List<ImportTransactionLine> getRows(final String content) throws IOException {
        CsvMapper<ImportTransactionLine> mapper = CsvMapperFactory.newInstance().defaultDateFormat("yyyyMMdd")
                .newBuilder(ImportTransactionLine.class).addMapping("Date").addMapping("Description")
                .addMapping("Amount").addMapping("Balance").mapper();

        List<ImportTransactionLine> rows = new ArrayList<>();

        CsvParser.skip(1).mapWith(mapper).forEach(content, rows::add);

        return rows;
    }

    

    @Override
    public ImportTransactionsTablePageData getImportTransactionsTableData(
            final SearchFilter filter) {
        
        ImportTransactionsTablePageData pageData = new ImportTransactionsTablePageData();
        
        SQL.selectInto(SQLs.TransactionHistory.SELECT_INTO_PAGE_DATA, new NVPair("page", pageData));
                
        return pageData;
    }
}
