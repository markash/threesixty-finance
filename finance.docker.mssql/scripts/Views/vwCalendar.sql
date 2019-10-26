PRINT 'Creating view `vwCalendar`...';

IF EXISTS(SELECT * FROM sys.views WHERE name = 'vwPaymentDate')
	BEGIN
		DROP VIEW dbo.vwPaymentDate
	END
GO

IF EXISTS(SELECT * FROM sys.views WHERE name = 'vwMonthEndDate')
	BEGIN
		DROP VIEW dbo.vwMonthEndDate
	END
GO

IF EXISTS(SELECT * FROM sys.views WHERE name = 'vwCalendar')
	BEGIN
		DROP VIEW dbo.vwCalendar
	END
GO

CREATE VIEW dbo.vwCalendar
AS
	-- =============================================
	-- Author:			Mark Ashworth
	-- Create date:		2019-08-24
	-- Description:		Calendar view
	-- =============================================
	SELECT
			[CAL].CalendarId
		,	[CAL].CalendarDate
		,	[EVT].EventId
		,	[EVT].EventName
		,	[CAL].MonthEndId
		,	[CAL].CalendarEFD
		,	[CAL].CalendarETD
		,	[EVT].EventEFD
		,	[EVT].EventETD
	
	FROM dbo.Calendar		[CAL]
	
	INNER JOIN dbo.Event	[EVT]
			ON	[EVT].EventId 	= 	[CAL].EventId
			AND	(GETDATE() 		>= 	[EVT].EventEFD)
			AND	(GETDATE() 		<  	[EVT].EventETD OR [EVT].EventETD IS NULL)
	WHERE
			(GETDATE() 			>=	[CAL].CalendarEFD)
		AND	(GETDATE() 			< 	[CAL].CalendarETD OR [CAL].CalendarETD IS NULL)
GO

CREATE VIEW dbo.vwPaymentDate
AS
	-- =============================================
	-- Author:			Mark Ashworth
	-- Create date:		2019-08-24
	-- Description:		Payment Date view
	-- =============================================
	SELECT
			[CAL].CalendarId			[PaymentDateId]
		,	[CAL].CalendarDate			[PaymentDate]
		,	[CAL].EventId				[EventId]
		,	[CAL].EventName				[EventName]
		,	[CAL].MonthEndId			[MonthEndId]
		,	[CAL].CalendarEFD
		,	[CAL].CalendarETD
		,	[CAL].EventEFD
		,	[CAL].EventETD
	
	FROM dbo.vwCalendar		[CAL]
	
	WHERE
			[CAL].EventId = 1			
GO

CREATE VIEW dbo.vwMonthEndDate
AS
	-- =============================================
	-- Author:			Mark Ashworth
	-- Create date:		2019-08-24
	-- Description:		Month End Date view
	-- =============================================
	SELECT
			[CAL].CalendarId			[MonthEndId]
		,	[CAL].CalendarDate			[MonthEndDate]
		,	[CAL].EventId				[EventId]
		,	[CAL].EventName				[EventName]
		,	[CAL].CalendarEFD
		,	[CAL].CalendarETD
		,	[CAL].EventEFD
		,	[CAL].EventETD
		
	FROM dbo.vwCalendar		[CAL]
	
	WHERE
			[CAL].EventId = 2			
GO