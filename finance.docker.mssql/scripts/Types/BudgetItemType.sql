IF (NOT EXISTS(SELECT * FROM sys.types WHERE name = 'BudgetItemType'))
	BEGIN
		CREATE TYPE BudgetItemType AS TABLE 
		(
				BudgetId					INT				NULL
			,	AccountId					INT				NOT NULL
			,	Amount						DECIMAL(18, 10)	NOT NULL
			,	Comment						NVARCHAR(1024)	NULL
			,	Process						NVARCHAR(32)	NOT NULL CHECK(Process IN ('UPDATE', 'INSERT', 'DELETE'))
		)
	END