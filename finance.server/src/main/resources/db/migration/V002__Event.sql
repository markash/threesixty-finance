IF (NOT EXISTS(SELECT * FROM sys.tables WHERE name = 'Event'))
	BEGIN
		PRINT 'Creating table `Event`...'
		CREATE TABLE [dbo].[Event] (
		    [EventId]   INT            NOT NULL,
		    [EventName] NVARCHAR (256) NULL,
		    [EventEFD]  DATETIME       NULL,
		    [EventETD]  DATETIME       NULL,
		    CONSTRAINT [PK_Event] PRIMARY KEY CLUSTERED ([EventId] ASC)
		);
	END
ELSE
	BEGIN
		PRINT 'Existing table `Event` configured, continuing...'
	END
	
PRINT 'Merge the two types of events...'
DECLARE @vEvent			TABLE
(
		EventId			INT
	,	EventName		NVARCHAR(256)
	,	EventEFD		DATETIME
	,	EventETD		DATETIME
);

INSERT INTO @vEvent VALUES
	(1,	'Payment Date',		'2018-12-31',	NULL)
,	(2,	'Month-end Date',	'2018-12-31',	NULL)

MERGE dbo.Event AS TRG
USING
	(
		SELECT 
				EventId
			,	EventName
			,	EventEFD
			,	EventETD
		FROM @vEvent
	) AS SRC
	
ON (TRG.EventId = SRC.EventId)

WHEN NOT MATCHED THEN
	INSERT
	(
			EventId
		,	EventName
		,	EventEFD
		,	EventETD
	)
	VALUES
	(
			EventId
		,	EventName
		,	EventEFD
		,	EventETD
	);

GO
