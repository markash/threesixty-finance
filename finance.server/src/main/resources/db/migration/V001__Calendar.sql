IF (NOT EXISTS(SELECT * FROM sys.tables WHERE name = 'Calendar'))
	BEGIN
		CREATE TABLE Calendar
		(
				CalendarId					INT      	NOT NULL	IDENTITY (1, 1)
			,	EventId						INT      	NOT NULL
			,	CalendarDate				DATE     	NOT NULL
			,	MonthEndId					INT      	NULL
			,	CalendarEFD					DATETIME 	NULL
			,	CalendarETD					DATETIME 	NULL
			    
			,	CONSTRAINT PK_Calendar PRIMARY KEY CLUSTERED
				(
						CalendarId ASC
				)
			
			,	CONSTRAINT FK_Calendar_Event FOREIGN KEY
				(
						EventId
				) 
				REFERENCES [dbo].[Event] 
				(
						EventId
				)
		)		
	END
ELSE
	BEGIN
		PRINT 'Existing table `Calendar` configured, continuing...'
	END
	
PRINT 'Inserting dates into `Calendar`...'

DECLARE @vCalendar			TABLE
(
		PaymentDate			DATE
	,	MonthEndDate		DATE
	,	CalendarEFD			DATETIME
	,	CalendarETD			DATETIME
)

DECLARE
		@vMonthEndEventId	INT
	,	@vPaymentEventId	INT

INSERT INTO @vCalendar VALUES
	('2019-08-23',	'2019-08-31',	'2018-12-31',	NULL)

SELECT @vMonthEndEventId = EventId FROM Event WHERE EventName = 'Month-end Date'

SELECT @vPaymentEventId = EventId FROM Event WHERE EventName = 'Payment Date'


PRINT 'Inserting month-end dates into `Calendar`...'

MERGE Calendar AS TRG

USING 
	(
		SELECT
				@vMonthEndEventId	AS EventId
			,	MonthEndDate
			,	CalendarEFD
			,	CalendarETD
		FROM @vCalendar
	) AS SRC

ON (TRG.CalendarDate = SRC.MonthEndDate AND TRG.EventId = SRC.EventId)

WHEN NOT MATCHED THEN
	INSERT
	(
			EventId
		,	CalendarDate
		,	MonthEndId
		,	CalendarEFD
		,	CalendarETD
	)
	VALUES
	(
			SRC.EventId
		,	SRC.MonthEndDate
		,	NULL
		,	SRC.CalendarEFD
		,	SRC.CalendarETD
	);
	
	
PRINT 'Inserting payment dates into `Calendar`...'

MERGE Calendar AS TRG

USING 
	(
		SELECT
				@vPaymentEventId	AS EventId
			,	[T].PaymentDate
			,	[S].CalendarId		AS MonthEndId			
			,	[T].CalendarEFD
			,	[T].CalendarETD
		FROM @vCalendar		[T]
		
		INNER JOIN Calendar	[S]
				ON	[S].CalendarDate	=	[T].MonthEndDate
				AND	[S].EventId			=	@vMonthEndEventId
				AND	(GETDATE() 			>=	[S].CalendarEFD)
				AND (GETDATE()			<	[S].CalendarETD OR [S].CalendarETD IS NULL)
		
	) AS SRC

ON ([TRG].CalendarDate = [SRC].PaymentDate AND [TRG].EventId = [SRC].EventId)

WHEN NOT MATCHED THEN
	INSERT
	(
			EventId
		,	CalendarDate
		,	MonthEndId
		,	CalendarEFD
		,	CalendarETD
	)
	VALUES
	(
			[SRC].EventId
		,	[SRC].PaymentDate
		,	[SRC].MonthEndId
		,	[SRC].CalendarEFD
		,	[SRC].CalendarETD
	);
	


	