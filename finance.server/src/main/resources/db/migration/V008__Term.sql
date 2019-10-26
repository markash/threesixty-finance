IF (NOT EXISTS(SELECT * FROM sys.tables WHERE name = 'Term'))
	BEGIN
		PRINT 'Creating table `Term`...'
		
		CREATE TABLE [dbo].[Term] (
		    [TermId]   INT            IDENTITY (1, 1) NOT NULL,
		    [TermText] NVARCHAR (256) NOT NULL,
		    CONSTRAINT [PK_Term] PRIMARY KEY CLUSTERED ([TermId] ASC)
		)
	END
ELSE
	BEGIN
		PRINT 'Existing table `Term` configured, continuing...'
	END	
