IF (NOT EXISTS(SELECT * FROM sys.tables WHERE name = 'TransactionHistoryLog'))
	BEGIN
		PRINT 'Creating table `TransactionHistoryLog`...'
		
		CREATE TABLE [dbo].[TransactionHistoryLog] (
		    [LogId]     INT           IDENTITY (1, 1) NOT NULL,
		    [Date]      DATETIME      NOT NULL,
		    [StartDate] DATE          NOT NULL,
		    [EndDate]   DATE          NOT NULL,
		    [Records]   INT           NOT NULL,
		    [Action]    NVARCHAR (64) NOT NULL,
		    CONSTRAINT [PK_TransactionHistoryLog] PRIMARY KEY CLUSTERED ([LogId] ASC)
		);
	END
ELSE
	BEGIN
		PRINT 'Existing table `TransactionHistoryLog` configured, continuing...'
	END	