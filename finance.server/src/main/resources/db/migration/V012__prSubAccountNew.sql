
IF (EXISTS(SELECT * FROM sys.procedures where name = 'prSubAccountNew'))
	BEGIN
		PRINT 'Dropping procedure `prSubAccountNew`, since it exists';

		DROP PROCEDURE dbo.prSubAccountNew
	END

GO
CREATE PROCEDURE dbo.prSubAccountNew
(
		@AccountId			INT
	,	@SubAccountName		NVARCHAR(256)
)
AS
BEGIN
	DECLARE
			@SubAccountId	INT
		,	@Account		AccountType;


	/* Get the next sub account id in the account range */
	SELECT 
			@SubAccountId = MAX(AccountId) + 1
	FROM dbo.Account
	WHERE
			AccountId >=	@AccountId * 100
		AND AccountId <		(@AccountId + 1) * 100


	INSERT INTO dbo.Account 
	(
			AccountId
		,	AccountName
		,	ParentAccountId
	)
	OUTPUT 
			INSERTED.AccountId
		,	INSERTED.AccountName
		,	INSERTED.ParentAccountId
	INTO @Account
	VALUES 
	(
			@SubAccountId
		,	@SubAccountName
		,	@AccountId
	)

	SELECT
			[A].AccountId
		,	[A].AccountName
		,	[A].ParentAccountId
		,	[A].ParentAccountName
		,	[A].[Sign]

	FROM vwAccount			[A]

	INNER JOIN Account		[O]
			ON	[O].AccountId = [A].AccountId

END
GO
PRINT 'Configured procedure `prAccountNew`'
