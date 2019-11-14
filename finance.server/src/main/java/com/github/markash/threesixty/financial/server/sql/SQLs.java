package com.github.markash.threesixty.financial.server.sql;

public class SQLs {

    private SQLs() {}
    
    public static final String LINE_FEED    = System.lineSeparator();
    public static final String SELECT       = "SELECT";
    public static final String FROM         = LINE_FEED + "FROM";
    public static final String WHERE        = LINE_FEED + "WHERE";
    public static final String INTO        = LINE_FEED + "INTO";
    
    public static class Account {
        
        private Account() {} 
        
        public static final String LOOKUP =
              SELECT
            + "         ACC.AccountId   "
            + "     ,   ACC.AccountName "
            + FROM
            + "     Account     AS ACC"            
            + " LEFT OUTER JOIN     Account     AS PRT"
            + "     ON  PRT.AccountId = ACC.ParentAccountId"
            + WHERE
            + "         PRT.AccountId > 0 "                                     // Only select sub-accounts
            + "<key>    AND ACC.AccountId = :key</key> "                        // <1>
            + "<text>   AND UPPER(ACC.AccountName) LIKE UPPER(:text) </text> "  // <2>
            + "<all></all>";
        
        public static final String PAGE_SELECT = 
                SELECT
              + "       AccountId"
              + "   ,   AccountName"
              + "   ,   ParentAccountId"
              + "   ,   ParentAccountName"
              + FROM
              + "   vwAccount"
              ;

        public static final String PAGE_DATA_SELECT_INTO = 
                INTO
              + "       :{page.accountId}"
              + "   ,   :{page.accountName}"
              + "   ,   :{page.accountParentId}"
              + "   ,   :{page.accountParentName}"
              ;  
          
        public static final String SELECT_INTO_PAGE_DATA = PAGE_SELECT + PAGE_DATA_SELECT_INTO;
          
        public static final String PARAM_PAGE = "page";
          
        public static class ValueObject {
            
            private ValueObject() {}
            
            public static final String VALUE_OBJECT_SELECT =
                        SELECT
                      + "       AccountId"
                      + "   ,   AccountName"
                      + "   ,   ParentAccountId"
                      + "   ,   Sign"
                      + FROM
                      + "   vwAccount "
                      ;
              
            public static final String SINGLE = 
                      "   WHERE"
                      + "         AccountId   = :{accountId}";
              
            public static final String INTO_OBJECT = 
                      INTO
                      + "       :{valueObject.id}"
                      + "     ,   :{valueObject.name}"
                      + "     ,   :{valueObject.parentId}"
                      + "     ,   :{valueObject.sign}";
              
            public static final String SELECT_SINGLE_INTO_VALUE_OBJECT = VALUE_OBJECT_SELECT + SINGLE + INTO_OBJECT;
              
            public static final String SELECT_ALL_INTO_VALUE_OBJECT = VALUE_OBJECT_SELECT + INTO_OBJECT;
        }
          
        public static final String PROC_SUB_ACCOUNT_NEW = "{call dbo.prSubAccountNew(:accountParentId, :accountName)}";
          
        public static final String PROC_SUB_ACCOUNT_NEW_INTP_PAGE_DATA = PROC_SUB_ACCOUNT_NEW + PAGE_DATA_SELECT_INTO;
    }
    
    public static class TransactionHistory {
        
        private TransactionHistory() {}
        
        public static final String IMPORT = "{call dbo.prTransactionHistoryImport(?)}";
        
        public static final String DELETE_DUPLICATES = "{call dbo.prTransactionHistoryDeleteDuplicates()}";
        
        public static final String AUTO_ALLOCATE = "{call dbo.prTransactionHistoryAutoAllocate()}";
        
        public static final String MANUAL_ALLOCATE = "{call dbo.prTransactionHistoryManualAllocate (?, ?)}";
        
        public static final String FULL_TEXT_UPDATE = "ALTER FULLTEXT INDEX ON TransactionHistory START FULL POPULATION";
        
        public static final String DELETE = "DELETE FROM TransactionHistory";
        
        public static final String PAGE_SELECT = 
                  SELECT
                + "         TransactionId "
                + "     ,   Date "
                + "     ,   Description "
                + "     ,   Amount "
                + "     ,   Balance "
                + FROM
                + "         TransactionHistory ";

        public static final String PAGE_DATA_SELECT_INTO = 
                  INTO
                + "         :{page.transactionId}, "
                + "         :{page.date}, "
                + "         :{page.description}, "
                + "         :{page.amount}, "
                + "         :{page.balance}";  
            
        public static final String SELECT_INTO_PAGE_DATA = PAGE_SELECT + PAGE_DATA_SELECT_INTO;
    }
    
    public static class Budget {
        
        private Budget() {}
        
        public static final String PARAM_PAGE = "page";
        
        public static final String PARAM_BUDGET_ID = "budgetId";
        
        public static final String PAGE_SELECT =
                SELECT
                + "         BudgetId"
                + "     ,   MonthEndId"
                + "     ,   MonthEndDate"
                + "     ,   ProcessedDateTime"
                + FROM
                + "         vwBudget";
        
        
        public static final String INTO_OBJECT = 
                INTO
                + "         :{page.budgetId}"
                + "     ,   :{page.monthEndId}"
                + "     ,   :{page.monthEndDate}"
                + "     ,   :{page.processedDateTime}";
        
        public static final String SELECT_INTO_PAGE = PAGE_SELECT + INTO_OBJECT;
        
        public static final String MATERIALIZE_NEW_BUDGET = "{call dbo.prBudgetMaterializeNew(:budgetId) }";
    }
    
    public static class BudgetItem {
        
        private BudgetItem() {}
        
        public static final String PARAM_PAGE = "page";
        
        public static final String PAGE_SELECT =
                SELECT
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
                + FROM
                + "         vwBudgetItem";
        
        public static final String SINGLE_BY_BUDGET_ID =
                WHERE
                + "         BudgetId    =   :budgetId";
        
        public static final String INTO_OBJECT = 
                INTO
                + "         :{page.budgetId}"
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
        
        public static final String SELECT_SINGLE_BY_BUDGET_ID_INTO_PAGE = PAGE_SELECT + SINGLE_BY_BUDGET_ID + INTO_OBJECT;
        
        public static class ValueObject {
            
            private ValueObject() {}
            
            public static final String VALUE_OBJECT_SELECT =
                    SELECT
                    + "         BudgetId "
                    + "     ,   AccountId "
                    + "     ,   BudgetAmount"
                    + "     ,   LedgerAmount"
                    + "     ,   DifferenceAmount"
                    + "     ,   Comment"
                    + FROM
                    + "         vwBudgetItem"
                    ;
            
            public static final String SINGLE = 
                    WHERE
                    + "         BudgetId    = :{budgetId}"
                    + "     AND AccountId   = :{accountId}";
            
            public static final String INTO_OBJECT = 
                    INTO
                    + "         :{valueObject.budgetId}"
                    + "     ,   :{valueObject.accountId}"
                    + "     ,   :{valueObject.budgetAmount}"
                    + "     ,   :{valueObject.ledgerAmount}"
                    + "     ,   :{valueObject.differenceAmount}"
                    + "     ,   :{valueObject.comment}";
            
            public static final String SELECT_SINGLE_INTO_VALUE_OBJECT = VALUE_OBJECT_SELECT + SINGLE + INTO_OBJECT;
        }
        
        public static final String BUDGET_ITEM_PROCESS = "{call dbo.prBudgetItemProcess (?)}";
    }
}
