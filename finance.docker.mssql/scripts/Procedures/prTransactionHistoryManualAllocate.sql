PRINT 'Creating/updating procedure prTransactionHistoryManualAllocate...'

IF (EXISTS(SELECT * FROM sys.procedures WHERE name = 'prTransactionHistoryManualAllocate'))
	BEGIN	
		DROP PROCEDURE dbo.prTransactionHistoryManualAllocate
	END

GO

CREATE PROCEDURE dbo.prTransactionHistoryManualAllocate
		@TransactionId	INT
	,	@Allocation		AllocationType READONLY
AS
BEGIN
	-- =============================================
	-- Author:		Mark Ashworth
	-- Create date: 24 March 2019
	-- Description:	Looks up the account from the description
	--
	-- Version	  : 29 July 2019
	--            : Added the comment to the allocation
	--
	-- Version    : 17 Aug 2019
	--            : Changed to allow for multiple split allocations
	-- =============================================
	SET NOCOUNT ON;

	DECLARE @TransactionHistory		TABLE
	(
			[TransactionId]			INT
		,	[Date]					DATE
		,	[Description]			NVARCHAR(256)
		,	[Amount]				DECIMAL(18, 10)
		,	[Balance]				DECIMAL(18, 10)
	);

	DELETE dbo.TransactionHistory
	OUTPUT
			[DELETED].[TransactionId]
		,	[DELETED].[Date]
		,	[DELETED].[Description]
		,	[DELETED].[Amount]
		,	[DELETED].[Balance]
	INTO @TransactionHistory
	(
			[TransactionId]
		,	[Date]
		,	[Description]
		,	[Amount]
		,	[Balance]
	)
	WHERE 
			TransactionId = @TransactionId

	INSERT INTO dbo.Ledger
	(
			[Date]
		,	[Description]
		,	[Amount]
		,	[Balance]
		,	[AccountId]
		,	[Comment]
	)
	SELECT
			[T].[Date]
		,	[T].[Description]
		,	[A].[Amount]
		,	[T].[Balance]
		,	[A].[AccountId]
		,	[A].[Comment]
	FROM @TransactionHistory	[T]
	CROSS JOIN @Allocation		[A]

	/* Allocate a month-end to the ledger item/s */
	EXEC dbo.prLedgerAssignMonthEnd
END
GO

PRINT 'Updating procedure prTransactionHistoryManualAllocate completed.'