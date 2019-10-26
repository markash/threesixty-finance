
IF (EXISTS(SELECT * FROM sys.procedures WHERE name = 'prBudgetSummarize'))
	DROP PROCEDURE dbo.prBudgetSummarize
GO

CREATE PROCEDURE dbo.prBudgetSummarize
(
		@BudgetId					INT
) AS
BEGIN
	/*
	DECLARE
			@BudgetId					INT
	
	SET @BudgetId = 8
	
	EXEC dbo.prBudgetSummarize @BudgetId
	*/

	DECLARE	@vLevel					TABLE
	(
			[Id]					INT	IDENTITY(1, 1)
		,	[Minimum]				INT
		,	[Maximum]				INT
		,	[Name]					NVARCHAR(64)
	);

	INSERT INTO @vLevel
	(
			[Minimum]
		,	[Maximum]
		,	[Name]
	)
	VALUES
			(-9999,		-1000,	'ROOT')
		,	(-999,		-1,		'SECTION')
		,	(0,			99,		'CATEGORY')
		,	(100,		9900,	'ACCOUNT')


	WHILE (EXISTS(SELECT * FROM @vLevel))
		BEGIN

			DECLARE
					@vId			INT
				,	@vMinimum		INT
				,	@vMaximum		INT

			/* Get the level to summarize */
			SELECT TOP 1
					@vId		=	[Id]
				,	@vMinimum	=	[Minimum]
				,	@vMaximum	=	[Maximum]
			FROM @vLevel

			ORDER BY
					[Id] DESC

			/* Summarize */
			MERGE dbo.BudgetItem AS TRG

			USING
			(
				SELECT
						BudgetId			[BudgetId]
					,	ParentAccountId		[AccountId]
					,	SUM(BudgetAmount)	[Amount]

				FROM dbo.vwBudgetItem

				WHERE
						BudgetId	=	@BudgetId
					AND	ParentAccountId IS NOT NULL
					AND	AccountId	>=	@vMinimum
					AND AccountId	<=	@vMaximum

				GROUP BY
						BudgetId
					,	ParentAccountId
			) AS SRC

			ON 
			(
					[SRC].BudgetId	=	[TRG].BudgetId
				AND	[SRC].AccountId	=	[TRG].AccountId
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
				,	NULL
				,	GETDATE()
			)

			WHEN MATCHED THEN

			UPDATE
			SET
					[TRG].Amount			= [SRC].Amount
				,	[TRG].Comment			= NULL
				,	[TRG].ProcessedDateTime	= GETDATE()
			;

			/* Remove the level */
			DELETE
			FROM @vLevel
			WHERE
					[Id]	=	@vId
		END
END
