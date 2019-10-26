PRINT 'Creating/updating procedure prTransactionHistoryAutoAllocate...'

IF (EXISTS(SELECT * FROM sys.procedures WHERE name = 'prTransactionHistoryAutoAllocate'))
	BEGIN	
		DROP PROCEDURE dbo.prTransactionHistoryAutoAllocate
	END

GO

CREATE PROCEDURE dbo.prTransactionHistoryAutoAllocate
AS
BEGIN
	-- =============================================
	-- Author:		Mark Ashworth
	-- Create date: 24 March 2019
	-- Description:	Auto allocates the transaction history item 
	--			    to ledger item based upon the free text match
	--			    of the comment to the account mapping to text term
	--				defined in the AccountMapping and Term tables.
	-- =============================================
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	DECLARE @vTermId	INT
		,	@vTermText	NVARCHAR(256)
		,	@vAccountId	INT
	;
	DECLARE @vTerm		TABLE
	(
			TermId		INT
		,	TermText	NVARCHAR(256)
	);

	INSERT INTO @vTerm (TermId, TermText)
	SELECT 
			TermId
		,	TermText
	FROM dbo.Term;

	DECLARE db_cursor CURSOR FOR 
	SELECT
			TermId
		,	TermText
	FROM @vTerm

	OPEN db_cursor  
	FETCH NEXT FROM db_cursor INTO @vTermId, @vTermText 

	WHILE @@FETCH_STATUS = 0  
	BEGIN

		SELECT TOP 1
				@vAccountId = [AM].AccountId
		FROM dbo.AccountMapping [AM]
		WHERE
				[AM].TermId = @vTermId


		DELETE FROM dbo.TransactionHistory
		OUTPUT
				DELETED.[Date]
			,	DELETED.[Description]
			,	DELETED.[Amount]
			,	DELETED.[Balance]
			,	@vAccountId

		INTO dbo.[Ledger]
		(
				[Date]
			,	[Description]
			,	[Amount]
			,	[Balance]
			,	[AccountId]
		)
		WHERE 
			CONTAINS([Description], @vTermText)

		DELETE FROM @vTerm
		WHERE
			TermId = @vTermId

		FETCH NEXT FROM db_cursor INTO @vTermId, @vTermText  
	END 

	CLOSE db_cursor  
	DEALLOCATE db_cursor 

	/* Allocate a month-end to the ledger item/s */
	EXEC dbo.prLedgerAssignMonthEnd
END
GO

PRINT 'Updating procedure prTransactionHistoryAutoAllocate completed.'