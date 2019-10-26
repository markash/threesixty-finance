-- =============================================
-- Author:		Mark Ashworth
-- Create date: 2019-03-24
-- Description:	Calculate the payment for date
-- =============================================
CREATE FUNCTION fnPaymentDateFor 
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
		,	[CAL].MonthEndId

	FROM dbo.Calendar [CAL]

		INNER JOIN dbo.[Event] [EVT]
			ON [EVT].EventId = [CAL].EventId
				AND (GETDATE() >= [EVT].EventEFD)
				AND (GETDATE() <  [EVT].EventETD OR [EVT].EventETD IS NULL)

	WHERE
			[CAL].EventId = 1
		AND (GETDATE() >= [CAL].CalendarEFD)
		AND (GETDATE() <  [CAL].CalendarETD OR [CAL].CalendarETD IS NULL)
		AND [CAL].CalendarDate <= @vDate

	ORDER BY 
			[CAL].CalendarDate DESC
)
