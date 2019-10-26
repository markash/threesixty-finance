ALTER PROCEDURE prTransactionHistoryDeleteDuplicates
AS
BEGIN
	SET NOCOUNT ON;

    DELETE [HIS]
	FROM [dbo].[TransactionHistory] [HIS]
	
		INNER JOIN dbo.[Ledger] [LGR]
			ON [LGR].[Date] = [HIS].[Date]
				AND [LGR].[Description] = [HIS].[Description]
				AND [LGR].[Amount] = [HIS].[Amount]
				AND [LGR].[Balance] = [HIS].[Balance]
END
