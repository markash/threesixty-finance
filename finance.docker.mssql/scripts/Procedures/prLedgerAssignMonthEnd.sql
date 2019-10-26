PRINT 'Creating/updating procedure prLedgerAssignMonthEnd...'

IF (EXISTS(SELECT * FROM sys.procedures WHERE name = 'prLedgerAssignMonthEnd'))
	BEGIN
		DROP PROCEDURE dbo.prLedgerAssignMonthEnd
	END

GO

CREATE PROCEDURE dbo.prLedgerAssignMonthEnd
AS
BEGIN
	-- =============================================
	-- Author:		Mark Ashworth
	-- Create date: 08 September 2019
	-- Description:	Allocates a month end to the ledger item
	--
	-- =============================================
	SET NOCOUNT ON;

	UPDATE [TGT]
	SET 
			[TGT].MonthEndId	=	[MTE].CalendarId
	FROM dbo.[Ledger] [TGT]
	
	CROSS APPLY dbo.fnMonthEndFor([Date]) [MTE]
	
	WHERE
			[TGT].MonthEndId IS NULL

	SET NOCOUNT OFF;
END
GO

PRINT 'Updating procedure prLedgerAssignMonthEnd completed.'