PRINT 'Creating view `vwBudget`...';

IF EXISTS(SELECT * FROM sys.views WHERE name = 'vwBudget')
	BEGIN
		DROP VIEW dbo.vwBudget
	END
GO


CREATE VIEW vwBudget
AS
	WITH vLedger AS
	(
		SELECT 
				MonthEndId
			,	AccountId
			,	SUM(Amount)			[LedgerAmount]
		FROM dbo.vwLedger

		GROUP BY
				MonthEndId
			,	AccountId

	)
	SELECT
			[BGT].BudgetId
		,	[BGT].MonthEndId
		,	[MTH].MonthEndDate
		,	[BGT].ProcessedDateTime
		
	FROM dbo.Budget					[BGT]
	
	INNER JOIN dbo.vwMonthEndDate	[MTH]
			ON	[MTH].MonthEndId	=	[BGT].MonthEndId
		
	LEFT OUTER JOIN vLedger			[LGR]
			ON	[LGR].AccountId		=	[BGT].
GO