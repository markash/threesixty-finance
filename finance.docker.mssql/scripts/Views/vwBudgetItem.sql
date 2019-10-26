PRINT 'Creating view `vwBudgetItem`...';

IF EXISTS(SELECT * FROM sys.views WHERE name = 'vwBudgetItem')
	BEGIN
		DROP VIEW dbo.vwBudgetItem
	END
GO


CREATE VIEW vwBudgetItem
AS
	WITH vSubLedger AS
	(
		SELECT 
				MonthEndId
			,	SubAccountId
			,	SUM(Amount)					[LedgerAmount]
		FROM dbo.vwLedger

		GROUP BY
				MonthEndId
			,	SubAccountId

	), 
	vLedger AS
	(
		SELECT 
				MonthEndId
			,	AccountId
			,	SUM(Amount)					[LedgerAmount]
		FROM dbo.vwLedger

		GROUP BY
				MonthEndId
			,	AccountId

	),
	vHolding AS
	(
		SELECT
				[LGR].MonthEndId
			,	[ACC].ParentAccountId		[AccountId]
			,	SUM([LGR].LedgerAmount)		[LedgerAmount]
		FROM vLedger						[LGR]

		INNER JOIN dbo.Account				[ACC]
				ON	[ACC].AccountId	=		[LGR].AccountId

		GROUP BY
				[LGR].MonthEndId
			,	[ACC].ParentAccountId
	),
	vBudget AS
	(
		SELECT
				[BGI].BudgetId
			,	[BGT].MonthEndId
			,	[BGT].MonthEndDate
			,	[BGI].AccountId
			,	[ACC].AccountName
			,	[ACC].ParentAccountId
			,	[ACC].ParentAccountName
			,	[BGI].Amount
			,	[BGI].Comment
			,	[BGI].ProcessedDateTime
			,	[ACC].[Sign]

		FROM dbo.BudgetItem					[BGI]
	
		INNER JOIN dbo.vwBudget				[BGT]
				ON	[BGT].BudgetId		=	[BGI].BudgetId
			
		INNER JOIN dbo.vwAccount			[ACC]
				ON	[ACC].AccountId		=	[BGI].AccountId
	)
	SELECT
			[BGI].BudgetId
		,	[BGI].MonthEndDate
		,	[BGI].AccountId
		,	[BGI].AccountName
		,	[BGI].ParentAccountId
		,	[BGI].ParentAccountName
		,	[BGI].Amount					[BudgetAmount]
		,	COALESCE
			(
					[HLD].LedgerAmount
				,	[SGR].LedgerAmount
				,	[LGR].LedgerAmount
			)								[LedgerAmount]
		,
			CASE
				WHEN [BGI].[Sign] = '-'
				THEN
					ABS([BGI].Amount) -
					ABS
					(
						COALESCE(
								[HLD].LedgerAmount
							,	[SGR].LedgerAmount
							,	[LGR].LedgerAmount
						)
					)								
				WHEN [BGI].[Sign] = '+'
				THEN	
					ABS
					(
						COALESCE(
								[HLD].LedgerAmount
							,	[SGR].LedgerAmount
							,	[LGR].LedgerAmount
						)
					) -
					ABS([BGI].Amount)
			END								[DifferenceAmount]
		,	[BGI].Comment
		,	[BGI].ProcessedDateTime
		,	[BGI].[Sign]

	FROM vBudget							[BGI]

	LEFT OUTER JOIN vSubLedger				[SGR]
			ON	[SGR].SubAccountId	=		[BGI].AccountId
			AND	[SGR].MonthEndId	=		[BGI].MonthEndId

	LEFT OUTER JOIN vLedger					[LGR]
			ON	[LGR].AccountId		=		[BGI].AccountId
			AND	[LGR].MonthEndId	=		[BGI].MonthEndId

	LEFT OUTER JOIN vHolding				[HLD]
			ON	[HLD].AccountId		=		[BGI].AccountId
			AND	[HLD].MonthEndId	=		[BGI].MonthEndId
GO