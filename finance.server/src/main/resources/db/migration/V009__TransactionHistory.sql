IF (NOT EXISTS(SELECT * FROM sys.tables WHERE name = 'TransactionHistory'))
	BEGIN
		PRINT 'Creating table `TransactionHistory`...'
		
		CREATE TABLE [dbo].[TransactionHistory] (
		    [TransactionId] INT              IDENTITY (1, 1) NOT NULL,
		    [Date]          DATE             NOT NULL,
		    [Description]   NVARCHAR (256)   NOT NULL,
		    [Amount]        DECIMAL (18, 10) NOT NULL,
		    [Balance]       DECIMAL (18, 10) NOT NULL,
		    CONSTRAINT [PK_TransactionHistory] PRIMARY KEY CLUSTERED ([TransactionId] ASC)
		)
		
		PRINT 'Creating full text index on `TransactionHistory`...'
		CREATE FULLTEXT INDEX 
			ON TransactionHistory  
			(  
					[Description] Language 1033                  
			)  
		    KEY INDEX PK_TransactionHistory  
		    ON FinanceTextCatalog  
		    WITH 
		   			CHANGE_TRACKING OFF
		   		,	NO POPULATION
	END
ELSE
	BEGIN
		PRINT 'Existing table `TransactionHistory` configured, continuing...'
	END	
GO