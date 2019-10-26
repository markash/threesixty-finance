IF (NOT EXISTS(SELECT * FROM sys.table_types WHERE name = 'AllocationType'))
	BEGIN
		PRINT 'Creating table type `AllocationType`...'

		CREATE TYPE dbo.AllocationType AS TABLE (
				AccountId		INT					NOT NULL
			,	Amount			DECIMAL(18, 10)		NOT NULL
			,	Comment			NVARCHAR(1024)  	NULL
		)

		PRINT 'Created table type `AllocationType`'
	END
ELSE
	BEGIN
		PRINT 'Existing table type `AllocationType` configured, continuing'
	END