IF (NOT EXISTS(SELECT * FROM sys.tables WHERE name = 'Budget'))
	BEGIN
		PRINT 'Creating table `Budget`...'
		CREATE TABLE dbo.Budget
		(
				BudgetId				INT			NOT NULL IDENTITY(1, 1)
			,	MonthEndId				INT			NOT NULL
			,	ProcessedDateTime		DATETIME	DEFAULT(GETDATE())
			
			,	CONSTRAINT PK_Budget PRIMARY KEY CLUSTERED 
			    (
			    		BudgetId ASC
			   	)
		   	
		   	,	CONSTRAINT FK_BudgetMonthEnd FOREIGN KEY
		   		(
		   				MonthEndId
		   		)
		   		REFERENCES dbo.Calendar (
		   				CalendarId
		   		)
		   		
		)
		
		CREATE UNIQUE NONCLUSTERED INDEX IX_BudgetMonthEnd
			ON dbo.Budget
		(
				MonthEndId ASC
		)
	END
GO

PRINT 'Creating budgets for month end calendar dates...'
MERGE dbo.Budget AS TRG
USING 
	(
		SELECT
				MonthEndId
		FROM dbo.vwMonthEndDate
	) AS SRC 

ON	(TRG.MonthEndId = SRC.MonthEndId)

WHEN NOT MATCHED THEN
	INSERT
	(
			MonthEndId
	) 
	VALUES
	(
			SRC.MonthEndId
	);
	
GO


	
	