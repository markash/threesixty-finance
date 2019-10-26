IF (NOT EXISTS(SELECT * FROM sys.tables WHERE name = 'AccountMapping'))
	BEGIN
		PRINT 'Creating table `AccountMapping`...'
		
		CREATE TABLE [dbo].[AccountMapping] (
		    [AccountMappingId]  INT      IDENTITY (1, 1) NOT NULL,
		    [AccountId]         INT      NOT NULL,
		    [TermId]            INT      NOT NULL,
		    [AccountMappingEFD] DATETIME NULL,
		    [AccountMappingETD] DATETIME NULL,
		    CONSTRAINT [PK_AccountMapping] PRIMARY KEY NONCLUSTERED ([AccountMappingId] ASC)
		)
	
		CREATE UNIQUE CLUSTERED INDEX [IX_AccountMapping]
		    ON [dbo].[AccountMapping]([AccountId] ASC, [TermId] ASC);

	END
ELSE
	BEGIN
		PRINT 'Existing table `AccountMapping` configured, continuing...'
	END	