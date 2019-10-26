PRINT 'Creating view `vwAccount`...';

IF EXISTS(SELECT * FROM sys.views WHERE name = 'vwAccount')
	BEGIN
		DROP VIEW dbo.vwAccount
	END
GO


CREATE VIEW vwAccount
AS
	SELECT
			[ACC].AccountId
		,	[ACC].AccountName
		,	[ACC].ParentAccountId
		,	[PRT].AccountName			[ParentAccountName]
		,	COALESCE(
				[ACC].[Sign]
			, 	[PRT].[Sign]
			,	[GRP].[Sign])			[Sign] 
	FROM dbo.Account					[ACC]
	
	LEFT OUTER JOIN dbo.Account			[PRT]
			ON	[PRT].AccountId		=	[ACC].ParentAccountId
			
	LEFT OUTER JOIN dbo.Account			[GRP]
			ON	[GRP].AccountId		=	[PRT].ParentAccountId
GO