PRINT 'Creating view `vwLedger`...';

IF (EXISTS(SELECT * FROM sys.views WHERE name = 'vwLedger'))
	BEGIN
		DROP VIEW dbo.vwLedger;
	END

GO

CREATE VIEW dbo.vwLedger
AS
	-- =============================================
	-- Author:			Mark Ashworth
	-- Create date:		2019-03-24
	-- Description:		Ledger view
	-- =============================================
SELECT
		LGR.LedgerId
	,	CAST(LGR.Date AS DATE)		AS Date
	,	LGR.MonthEndId
	,	CAL.CalendarDate			AS MonthEndDate
	,	LGR.Description
	,	LGR.Amount
	,	LGR.Balance
	,	ACC.AccountId
	,	ACC.AccountName
	,	SUB.AccountId				AS SubAccountId
	,	SUB.AccountName				AS SubAccountName

FROM dbo.Ledger AS LGR 

INNER JOIN dbo.Account AS SUB 
      ON SUB.AccountId = LGR.AccountId

INNER JOIN dbo.Account AS ACC 
      ON ACC.AccountId = SUB.ParentAccountId

INNER JOIN dbo.Calendar AS CAL 
      ON CAL.CalendarId = LGR.MonthEndId

GO
PRINT 'Creating view `vwLedger` completed';
