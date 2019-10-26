package com.github.markash.threesixty.financial.database.transactions;

import com.github.markash.threesixty.financial.shared.operations.ITransactionHistoryRepository;

public class TransactionHistoryRepository implements ITransactionHistoryRepository {

    
    
    
//    private ISqlService getSQLService() {
//        
//        return BEANS.get(ISqlService.class);
//        

//        List<TransactionHistory> transactions = getImportTransactionRepository().getTransactionHistory();
//
//        ImportTransactionsTableRowData[] data = 
//                transactions
//                .stream()
//                .map(transaction -> {
//                    ImportTransactionsTableRowData row = new ImportTransactionsTableRowData();
//                    row.setTransactionId(transaction.getId());
//                    row.setDate(transaction.getDate());
//                    row.setDescription(transaction.getDescription());
//                    row.setAmount(transaction.getAmount());
//                    row.setBalance(transaction.getBalance());
//                    return row;
//                })
//                .collect(Collectors.toList())
//                .toArray(new ImportTransactionsTableRowData[transactions.size()]);
//        
//        
//        pageData.setRows(data);
//
//    }
//    
    
}
