IF (NOT EXISTS(SELECT * FROM sys.tables WHERE name = 'Ledger'))
	BEGIN
		PRINT 'Creating table `Ledger`...'
		
		CREATE TABLE [dbo].[Ledger] (
		    [LedgerId]    INT              IDENTITY (1, 1) NOT NULL,
		    [Date]        DATETIME2 (7)    NOT NULL,
		    [Description] NVARCHAR (256)   NOT NULL,
		    [Amount]      DECIMAL (18, 10) NOT NULL,
		    [Balance]     DECIMAL (18, 10) NOT NULL,
		    [AccountId]   INT              NULL,
		    [MonthEndId]  INT              NULL,
		    [Comment]     NVARCHAR (1024)  NULL,
		    CONSTRAINT [PK_Transaction] PRIMARY KEY CLUSTERED ([LedgerId] ASC)
		);
	END
ELSE
	BEGIN
		PRINT 'Existing table `Ledger` configured, continuing...'
	END
