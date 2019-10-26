
IF (NOT EXISTS(SELECT * FROM sys.tables WHERE name = 'BudgetItem'))
	BEGIN
		PRINT 'Creating table `BudgetItem`...'
		
		CREATE TABLE dbo.BudgetItem
		(
				BudgetId					INT				NOT NULL
			,	AccountId					INT				NOT NULL
			,	Amount						DECIMAL(18, 10)	NOT NULL
			,	Comment						NVARCHAR(1024)	NULL
			,	ProcessedDateTime			DATETIME		DEFAULT(GETDATE())
			
			,	CONSTRAINT PK_BudgetItem PRIMARY KEY CLUSTERED
				(
						BudgetId
					,	AccountId
				)
				
			,	CONSTRAINT FK_BudgetItemBudget FOREIGN KEY
				(
						BudgetId
				)
				REFERENCES dbo.Budget
				(
						BudgetId
				)
				
			,	CONSTRAINT FK_BudgetItemAccount FOREIGN KEY
				(
						AccountId
				)
				REFERENCES dbo.Account
				(
						AccountId
				)
		)
		
	END