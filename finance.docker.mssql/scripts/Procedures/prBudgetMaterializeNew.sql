
IF (EXISTS(SELECT * FROM sys.procedures WHERE name = 'prBudgetMaterializeNew'))
	DROP PROCEDURE dbo.prBudgetMaterializeNew
GO

CREATE PROCEDURE dbo.prBudgetMaterializeNew
(
		@BudgetId					INT
) AS
BEGIN

	;WITH vAccounts AS
	(
		SELECT
				[ACC].AccountId
			,	[ACC].AccountName
			,	[ACC].ParentAccountId
			,	[PRT].AccountName		[ParentAccountName]
		
		FROM dbo.Account				[ACC]
	
		LEFT OUTER JOIN dbo.Account		[PRT]
				ON	[PRT].AccountId	=	[ACC].ParentAccountId
	),
	vBudgetItems AS
	(
		SELECT 
				[B].BudgetId	[BudgetId]
			,	[A].AccountId	[AccountId]
			,	0				[Amount]
			,	NULL			[Comment]
			,	GETDATE()		[ProcessedDateTime]
		FROM vAccounts			[A]

		CROSS JOIN vwBudget		[B]

		WHERE
			[B].BudgetId = @BudgetId
	)
	MERGE dbo.BudgetItem AS TRG
	
	USING
	(
		SELECT * 
		FROM vBudgetItems
	) AS SRC

	ON 
	(
			[SRC].BudgetId		=	[TRG].BudgetId
		AND	[SRC].AccountId		=	[TRG].AccountId
	)

	WHEN NOT MATCHED THEN

	INSERT
	(
			BudgetId
		,	AccountId
		,	Amount
		,	Comment
		,	ProcessedDateTime
	)
	VALUES
	(
			BudgetId
		,	AccountId
		,	Amount
		,	Comment
		,	ProcessedDateTime
	)
	;

END