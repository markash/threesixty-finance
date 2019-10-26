-- =============================================
-- Author:		Mark Ashworth
-- Create date: 2019-03-24
-- Description:	Calculate the month-end for date
-- =============================================
CREATE FUNCTION fnMonthEndFor 
(	
		@vDate DATE
)
RETURNS TABLE 
AS
RETURN 
(
	SELECT TOP 1
			[CAL].CalendarId
		,	[EVT].EventId
		,	[EVT].EventName
		,	[CAL].CalendarDate
		,	[CAL].CalendarEFD
		,	[CAL].CalendarETD
		,	[EVT].EventEFD
		,	[EVT].EventETD

	FROM dbo.fnPaymentDateFor(@vDate) [PAY]

		INNER JOIN dbo.Calendar [CAL]
			ON [CAL].CalendarId = [PAY].MonthEndId

		INNER JOIN dbo.[Event] [EVT]
			ON [EVT].EventId = [CAL].EventId
				AND (GETDATE() >= [EVT].EventEFD)
				AND (GETDATE() <  [EVT].EventETD OR [EVT].EventETD IS NULL)
)
