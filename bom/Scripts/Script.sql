SELECT * FROM Finance.dbo.TransactionHistoryLog as t

 SELECT   
		ACC.AccountId
     ,	ACC.AccountName
     ,	PRT.*
FROM                Account     AS ACC            
LEFT OUTER JOIN     Account     AS PRT
     ON  PRT.AccountId = ACC.ParentAccountId
WHERE
    1 = 1
    AND PRT.AccountId > 0