-- =============================================
-- Author:		Mark Ashworth
-- Create date: 24 March 2019
-- Description:	Looks up the account from the description
-- =============================================
CREATE FUNCTION dbo.fnAccountLookupByDescription 
(	
		@pDescription NVARCHAR(256)
)
RETURNS TABLE
AS
RETURN
(
	SELECT
			[A].AccountId
		,	[A].AccountName

	FROM STRING_SPLIT(REPLACE(REPLACE(@pDescription, '-', ' '), ':', ' '), ' ')
		INNER JOIN dbo.Term [T]
			ON UPPER([T].TermText) = UPPER(value)

		INNER JOIN dbo.AccountMapping [AM]
			ON [AM].TermId = [T].TermId

		INNER JOIN dbo.Account [A]
			ON [A].AccountId = [AM].AccountId
)
