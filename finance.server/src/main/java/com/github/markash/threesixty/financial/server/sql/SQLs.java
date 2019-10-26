package com.github.markash.threesixty.financial.server.sql;

public interface SQLs {

    public interface Account {
        
        String LOOKUP =
              " SELECT   "
            + "         ACC.AccountId   "
            + "     ,   ACC.AccountName "
            
            + " FROM                Account     AS ACC"            
            + " LEFT OUTER JOIN     Account     AS PRT"
            + "     ON  PRT.AccountId = ACC.ParentAccountId"
            + " WHERE"
            + "     1 = 1 "
            + "<key>    AND ACC.AccountId = :key</key> " // <1>
            + "<text>   AND UPPER(ACC.AccountName) LIKE UPPER(:text) </text> " // <2>
            + "<all></all>";
        
        String PAGE_SELECT = 
                " SELECT"
              + "       AccountId"
              + "   ,   AccountName"
              + "   ,   ParentAccountId"
              + "   ,   ParentAccountName"
              + " FROM vwAccount "
              ;

          String PAGE_DATA_SELECT_INTO = 
                " INTO"
              + "       :{page.accountId}"
              + "   ,   :{page.accountName}"
              + "   ,   :{page.accountParentId}"
              + "   ,   :{page.accountParentName}"
              ;  
          
          String SELECT_INTO_PAGE_DATA = PAGE_SELECT + PAGE_DATA_SELECT_INTO;
          
          String PARAM_PAGE = "page";
          
          interface ValueObject {
              String VALUE_OBJECT_SELECT =
                        " SELECT"
                      + "       AccountId"
                      + "   ,   AccountName"
                      + "   ,   ParentAccountId"
                      + "   ,   Sign"
                      + " FROM vwAccount "
                      ;
              
              String SINGLE = 
                      "   WHERE"
                      + "         AccountId   = :{accountId}";
              
              String INTO = 
                      "   INTO    :{valueObject.id}"
                      + "     ,   :{valueObject.name}"
                      + "     ,   :{valueObject.parentId}"
                      + "     ,   :{valueObject.sign}";
              
              String SELECT_SINGLE_INTO_VALUE_OBJECT = VALUE_OBJECT_SELECT + SINGLE + INTO;
              
              String SELECT_ALL_INTO_VALUE_OBJECT = VALUE_OBJECT_SELECT + INTO;
          }
          
          String PROC_SUB_ACCOUNT_NEW = "{call dbo.prSubAccountNew(:accountParentId, :accountName)}";
          
          String PROC_SUB_ACCOUNT_NEW_INTP_PAGE_DATA = PROC_SUB_ACCOUNT_NEW + PAGE_DATA_SELECT_INTO;
    }
    
    public interface TransactionHistory {
        
        String IMPORT = "{call dbo.prTransactionHistoryImport(?)}";
        
        String DELETE_DUPLICATES = "{call dbo.prTransactionHistoryDeleteDuplicates()}";
        
        String AUTO_ALLOCATE = "{call dbo.prTransactionHistoryAutoAllocate()}";
        
        String MANUAL_ALLOCATE = "{call dbo.prTransactionHistoryManualAllocate (?, ?)}";
        
        //String SELECT = "SELECT * FROM TransactionHistory";
        
        String FULL_TEXT_UPDATE = "ALTER FULLTEXT INDEX ON TransactionHistory START FULL POPULATION";
        
        String DELETE = "DELETE FROM TransactionHistory";
        
        String PAGE_SELECT = 
                  "SELECT"
                + "         TransactionId "
                + "     ,   Date "
                + "     ,   Description "
                + "     ,   Amount "
                + "     ,   Balance "
                + "FROM     TransactionHistory ";

            String PAGE_DATA_SELECT_INTO = 
                  "INTO     :{page.transactionId}, "
                + "         :{page.date}, "
                + "         :{page.description}, "
                + "         :{page.amount}, "
                + "         :{page.balance}";  
            
            String SELECT_INTO_PAGE_DATA = PAGE_SELECT + PAGE_DATA_SELECT_INTO;
    }
    
    interface Budget {
        
        String PARAM_PAGE = "page";
        
        String PARAM_BUDGET_ID = "budgetId";
        
        String PAGE_SELECT =
                "SELECT "
                + "         BudgetId "
                + "     ,   MonthEndId "
                + "     ,   MonthEndDate "
                + "     ,   ProcessedDateTime"
                + " FROM"
                + "         vwBudget";
        
        
        String INTO = 
                "   INTO    :{page.budgetId}"
                + "     ,   :{page.monthEndId}"
                + "     ,   :{page.monthEndDate}"
                + "     ,   :{page.processedDateTime}";
        
        String SELECT_INTO_PAGE = PAGE_SELECT + INTO;
        
        String MATERIALIZE_NEW_BUDGET = "{call dbo.prBudgetMaterializeNew(:budgetId) }";
    }
    
    interface BudgetItem {
        
        String PARAM_PAGE = "page";
        
        String PAGE_SELECT =
                "SELECT "
                + "         BudgetId "
                + "     ,   MonthEndDate "
                + "     ,   AccountId "
                + "     ,   AccountName"
                + "     ,   ParentAccountId"
                + "     ,   ParentAccountName"
                + "     ,   BudgetAmount"
                + "     ,   LedgerAmount"
                + "     ,   DifferenceAmount"
                + "     ,   Comment"
                + "     ,   ProcessedDateTime"
                + " FROM"
                + "         vwBudgetItem";
        
        String SINGLE_BY_BUDGET_ID =
                " WHERE   "
                + "         BudgetId    =   :budgetId";
        
        String INTO = 
                "   INTO    :{page.budgetId}"
                + "     ,   :{page.monthEndDate}"
                + "     ,   :{page.accountCode}"
                + "     ,   :{page.accountName}"
                + "     ,   :{page.accountParentCode}"
                + "     ,   :{page.accountParentName}"
                + "     ,   :{page.budgetAmount}"
                + "     ,   :{page.ledgerAmount}"
                + "     ,   :{page.differenceAmount}"
                + "     ,   :{page.comment}"
                + "     ,   :{page.processedDateTime}";
        
        String SELECT_SINGLE_BY_BUDGET_ID_INTO_PAGE = PAGE_SELECT + SINGLE_BY_BUDGET_ID + INTO;
        
        interface ValueObject {
            String VALUE_OBJECT_SELECT =
                    "SELECT "
                    + "         BudgetId "
                    + "     ,   AccountId "
                    + "     ,   BudgetAmount"
                    + "     ,   LedgerAmount"
                    + "     ,   DifferenceAmount"
                    + "     ,   Comment"
                    + " FROM"
                    + "         vwBudgetItem"
                    ;
            
            String SINGLE = 
                    "   WHERE"
                    + "         BudgetId    = :{budgetId}"
                    + "     AND AccountId   = :{accountId}";
            
            String INTO = 
                    "   INTO    :{valueObject.budgetId}"
                    + "     ,   :{valueObject.accountId}"
                    + "     ,   :{valueObject.budgetAmount}"
                    + "     ,   :{valueObject.ledgerAmount}"
                    + "     ,   :{valueObject.differenceAmount}"
                    + "     ,   :{valueObject.comment}";
            
            String SELECT_SINGLE_INTO_VALUE_OBJECT = VALUE_OBJECT_SELECT + SINGLE + INTO;
        }
        
        String BUDGET_ITEM_PROCESS = "{call dbo.prBudgetItemProcess (?)}";
    }
}
