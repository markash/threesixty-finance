CREATE PROCEDURE prTransactionHistoryImport
(
		@transactionHistoryLines dbo.TransactionHistoryType READONLY 
)
AS
BEGIN
	SET NOCOUNT ON;

	/* Remove the existing transaction lines */
	DELETE FROM [dbo].TransactionHistory;

	/* Import the transaction lines */
	INSERT INTO [dbo].TransactionHistory
	(
			[Date]
		,	[Description]
		,	[Amount]
		,	[Balance]
	)
	SELECT
			[Date]
		,	[Description]
		,	[Amount]
		,	[Balance]
	FROM @transactionHistoryLines;
	
	/* Log the import */
	INSERT INTO [dbo].TransactionHistoryLog
	(
			[Date]
		,	[StartDate]
		,	[EndDate]
		,	[Records]
		,	[Action]
	)
	SELECT
			GETDATE()
		,	MIN([Date])
		,	MAX([Date])
		,	COUNT(*)
		,	'IMPORT'
	FROM [dbo].TransactionHistory

END
