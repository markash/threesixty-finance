IF (NOT EXISTS(SELECT * FROM sys.columns WHERE name = 'Sign' AND object_id = OBJECT_ID('Account')))
	BEGIN
		/* Add the [Sign] column */
		ALTER TABLE dbo.Account
		ADD [Sign] CHAR(1) NULL
		
		
		DECLARE @vAccount				TABLE
		(
				AccountId				INT
			,	AccountName				NVARCHAR(256)
			,	ParentAccountId			INT
			,	[Sign]					CHAR(1)
		)
			
		/* Configure the [Sign] column for the top-level accounts */
		INSERT INTO @vAccount VALUES
		 (-1000,	'Holding',						NULL,  '+')
		,(-500,		'Income',						-1000, '+')
		,(-400,		'Expense',						-1000, '-')
				
		MERGE dbo.Account AS TRG
		
		USING
		(
			SELECT *
			FROM @vAccount
		) AS SRC
		
		ON
		(
				[SRC].AccountId	=	[TRG].AccountId
		)
		
		WHEN MATCHED THEN
		
		UPDATE
		SET
				[TRG].[Sign]	= 	[SRC].[Sign]
				
		;
		
	END