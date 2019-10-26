IF (EXISTS(SELECT * FROM sys.procedures WHERE name = 'prBudgetItemProcess'))
	DROP PROCEDURE dbo.prBudgetItemProcess
GO

CREATE PROCEDURE dbo.prBudgetItemProcess
(
		@BudgetItem			BudgetItemType	READONLY
) AS
BEGIN
	--***********************************************************************************
	-- Synopsis: Processes the inserts, updates and deletions to the budget items
	--			 then summarizes these changes so as to roll up the values to the parent
	--			 lines.

	--DECLARE
	--		@vBudgetItem		AS BudgetItemType
	--
	--INSERT INTO @vBudgetItem VALUES
	--(
	--		8
	--	,	1
	--	,	100
	--	,	'Test'
	--	,	'UPDATE'
	--)
	--
	--EXEC dbo.prBudgetItemProcess @vBudgetItem
	--***********************************************************************************

	/* Process the inserts and updates */
	IF (EXISTS(SELECT * FROM @BudgetItem WHERE Process IN ('UPDATE', 'INSERT')))
		BEGIN
			MERGE dbo.BudgetItem AS TRG
	
			USING
			(
				SELECT
						BudgetId
					,	AccountId
					,	Amount
					,	Comment

				FROM @BudgetItem
				
				WHERE
						Process IN ('UPDATE', 'INSERT')
			) AS SRC

			ON 
			(
					[SRC].BudgetId = [TRG].BudgetId
				AND [SRC].AccountId = [TRG].AccountId
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
				,	GETDATE()
			)

			WHEN MATCHED THEN

			UPDATE --TRG
			SET
					[TRG].Amount			= [SRC].Amount
				,	[TRG].Comment			= [SRC].Comment
				,	[TRG].ProcessedDateTime	= GETDATE()
			;
		END

	/* Process the deletions */
	IF (EXISTS(SELECT * FROM @BudgetItem WHERE Process IN ('DELETE')))
		BEGIN	
			DELETE [TRG]
			FROM dbo.BudgetItem		[TRG]

			INNER JOIN @BudgetItem	[SRC]
					ON	[SRC].BudgetId	=	[TRG].BudgetId
					AND	[SRC].AccountId	=	[TRG].AccountId

		END

	/* Summarize the budget items to roll-up the amount values to parent items */
	EXEC dbo.prBudgetItemSummarize @BudgetItem
END

/*
DECLARE
		@vBudgetItem		AS BudgetItemType
	
INSERT INTO @vBudgetItem VALUES
(
		8
	,	100
	,	12500
	,	'Test'
	,	'UPDATE'
)
	
EXEC dbo.prBudgetItemProcess @vBudgetItem

SELECT * FROM vwBudgetItem

GO

DECLARE
		@vBudgetItem		AS BudgetItemType

INSERT INTO @vBudgetItem VALUES
(
		8
	,	1
	,	500
	,	'Test'
	,	'DELETE'
)
	
EXEC dbo.prBudgetItemProcess @vBudgetItem
GO

DECLARE
		@vBudgetItem		AS BudgetItemType

INSERT INTO @vBudgetItem VALUES
(
		8
	,	1
	,	200
	,	'Test'
	,	'INSERT'
)
	
EXEC dbo.prBudgetItemProcess @vBudgetItem
GO

SELECT * FROM vwBudgetItem
*/