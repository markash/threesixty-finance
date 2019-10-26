
IF (EXISTS(SELECT * FROM sys.procedures WHERE name = 'prBudgetItemSummarize'))
	DROP PROCEDURE dbo.prBudgetItemSummarize
GO

CREATE PROCEDURE dbo.prBudgetItemSummarize
(
		@BudgetItem						BudgetItemType	READONLY
) AS
BEGIN
	--***********************************************************************************
	-- Synopsis: Summarizes the budgets for the budget items
	--           This procedure is called by the prBudgetItemProcess to summarize all
	--           the budgets that belong to the budget items
	--
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
	--EXEC dbo.prBudgetItemSummarize @vBudgetItem
	--
	--***********************************************************************************

	DECLARE	@vBudget				TABLE
	(
			[BudgetId]				INT
	);

	INSERT INTO @vBudget
	
	SELECT DISTINCT
			BudgetId

	FROM @BudgetItem

	WHILE (EXISTS(SELECT * FROM @vBudget))
		BEGIN

			DECLARE
					@vBudgetId		INT

			SELECT TOP 1
					@vBudgetId	=	BudgetId

			FROM @vBudget


			EXEC dbo.prBudgetSummarize @vBudgetId

			/* Remove the level */
			DELETE
			FROM @vBudget
			WHERE
					[BudgetId]	=	@vBudgetId
		END
END
