-- =============================================
-- Author:		Mark Ashworth
-- Create date: 2019-03-24
-- Description:	Calculate the account month-end summary
-- =============================================
PRINT 'Creating view `VIEW vwAccountMonthEndSummary`...';
GO
CREATE VIEW vwAccountMonthEndSummary AS
SELECT 
		AccountId
	,	AccountName
	,	SubAccountId
	,	SubAccountName
	,	MonthEnd
	,	SUM(Amount)		AS Amount
	
FROM dbo.vwLedger

GROUP BY
		AccountId
	,	AccountName
	,	SubAccountId
	,	SubAccountName
	,	MonthEnd
