IF (NOT EXISTS(SELECT * FROM sys.tables WHERE name = 'Account'))
	BEGIN
		PRINT 'Creating table `Account`...'

		CREATE TABLE dbo.Account
		(
				AccountId					INT					NOT NULL
			,	AccountName					NVARCHAR (256)		NOT NULL
		    ,	ParentAccountId				INT					NULL
		    ,	[Sign]						CHAR(1)				NULL
		    
		    ,	CONSTRAINT PK_Account PRIMARY KEY CLUSTERED 
			    (
			    		AccountId ASC
			   	)
		)
		
		CREATE UNIQUE NONCLUSTERED INDEX IX_Account
			    ON dbo.Account
				(
						AccountId ASC
				)
		
	END
ELSE
	BEGIN
		PRINT 'Existing table `Account` configured, continuing...'
	END
	
	
PRINT 'Merging account heirarchy...'	
DECLARE @vAccount				TABLE
(
		AccountId				INT
	,	AccountName				NVARCHAR(256)
	,	ParentAccountId			INT
	,	[Sign]					CHAR(1)
)
	
INSERT INTO @vAccount VALUES
 (-1000,	'Holding',						NULL,  '+')
,(-500,		'Income',						-1000, '+')
,(-400,		'Expense',						-1000, '-')
,(1, 		'Salary',						-500, NULL)
,(2, 		'Banking',						-400, NULL)
,(3, 		'Telecommunication',			-400, NULL)
,(4, 		'Insurance',					-400, NULL)
,(5, 		'Loan Repayment',				-400, NULL)
,(6, 		'Recreation',					-400, NULL)
,(7, 		'Travel',						-400, NULL)
,(8, 		'Home',							-400, NULL)
,(9, 		'Medical',						-400, NULL)
,(10, 		'Donation',						-400, NULL)
,(11, 		'School',						-400, NULL)
,(12, 		'Maintenance',					-400, NULL)
,(13, 		'Investment',					-500, NULL)
,(14, 		'Legal',						-400, NULL)
,(15,		'Revenue Service',				-500, NULL)
,(100, 		'Salary',						1, NULL)
,(101, 		'Cash',							1, NULL)
,(200, 		'Fee',							2, NULL)
,(201, 		'Credit Card',					2, NULL)
,(300, 		'Webonline',					3, NULL)
,(301, 		'MTN',							3, NULL)
,(302, 		'Vox',							3, NULL)
,(303, 		'Telkom',						3, NULL)
,(304, 		'MWeb',							3, NULL)
,(400, 		'Funeral Insurance',			4, NULL)
,(401, 		'Vehicle Insurance',			4, NULL)
,(402, 		'Education Insurance',			4, NULL)
,(403, 		'Life Insurance',				4, NULL)
,(500, 		'Home Loan',					5, NULL)
,(501, 		'Vehicle Loan',					5, NULL)
,(600, 		'Eating Out',					6, NULL)
,(601, 		'Movies',						6, NULL)
,(602, 		'Anniversary',					6, NULL)
,(603, 		'Books',						6, NULL)
,(604, 		'Accommodation',				6, NULL)
,(605, 		'DStv',							6, NULL)
,(606, 		'Virgin Active',				6, NULL)
,(607, 		'ATKV',							6, NULL)
,(608, 		'Vacation - Honeymoon',			6, NULL)
,(609, 		'Vacation - Matric Vacation',	6, NULL)
,(610, 		'Gifts - Natasja',				6, NULL)
,(611, 		'Gifts - Dillon',				6, NULL)
,(612, 		'Gifts - Crystal',				6, NULL)
,(613, 		'Gifts - Mark',					6, NULL)
,(614, 		'Gifts - Family',				6, NULL)
,(615, 		'Vacation - Run The Berg',		6, NULL)
,(616, 		'Vacation - September',			6, NULL)
,(617, 		'Running',						6, NULL)
,(700, 		'Petrol',						7, NULL)
,(701, 		'Vehicle Repairs',				7, NULL)
,(703, 		'Gautrain',						7, NULL)
,(800, 		'Groceries',					8, NULL)
,(801, 		'Water & Lights',				8, NULL)
,(802, 		'Pets',							8, NULL)
,(803, 		'Boarding',						8, NULL)
,(804, 		'Home Access Control',			8, NULL)
,(805, 		'Home Repairs',					8, NULL)
,(806, 		'Toiletries',					8, NULL)
,(807, 		'Stationery',					8, NULL)
,(808, 		'Haircut',						8, NULL)
,(809, 		'Home Renovation - Wall',		8, NULL)
,(810, 		'Home Renovation - Paving',		8, NULL)
,(811, 		'Home Renovation - Garage',		8, NULL)
,(812, 		'Gardener',						8, NULL)
,(813, 		'Room & Boarding',				8, NULL)
,(900, 		'Discovery Vitality',			9, NULL)
,(901, 		'Discovery Medical Aid',		9, NULL)
,(902,		'Dental',						9, NULL)
,(1000, 	'Offering',						10, NULL)
,(1001, 	'Meals on Wheels',				10, NULL)
,(1002, 	'Doctors Without Borders',		10, NULL)
,(1003, 	'Natasja',						10, NULL)
,(1004,		'Tithe',						10, NULL)
,(1100, 	'School Fees',					11, NULL)
,(1101, 	'Driving Lessons',				11, NULL)
,(1102, 	'Matric Dress',					11, NULL)
,(1200, 	'Maintenance - Crystal',		12, NULL)
,(1300, 	'Investment',					13, NULL)
,(1301, 	'Investment Return',			13, NULL)
,(1400, 	'Divorce Legal Advice',			14, NULL)
,(1501,		'Revenue Service Payment',		15,	NULL)
,(1502,		'Revenue Service Refund',		15,	NULL)


MERGE Account AS TRG

USING 
	(
		SELECT *
		FROM @vAccount
		
	) AS SRC
	
ON ([TRG].AccountId = [SRC].AccountId)

WHEN NOT MATCHED THEN
	INSERT
	(
			AccountId
		,	AccountName
		,	ParentAccountId
	)
	VALUES
	(
			[SRC].AccountId
		,	[SRC].AccountName
		,	[SRC].ParentAccountId
	);
	
/*
SELECT 
	CONCAT(',(', AccountId, ', ''', AccountName, ''',', ParentAccountId, ')')
FROM dbo.Account
*/