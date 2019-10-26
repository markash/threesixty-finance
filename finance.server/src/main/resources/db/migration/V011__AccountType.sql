
IF (NOT EXISTS(SELECT * FROM sys.table_types WHERE name = 'AccountType')) 
	BEGIN
		PRINT 'Creating table type `AccountType`...'

		CREATE TYPE dbo.AccountType AS TABLE 
		(
				AccountId		INT
			,	AccountName		NVARCHAR(256)
			,	ParentAccountId	INT
	
			PRIMARY KEY (AccountId)
		)
	END
ELSE
	BEGIN
		PRINT 'Existing table type `AccountType` configured, continuing...'
	END