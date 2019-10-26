CREATE TYPE [dbo].[TransactionHistoryType] AS TABLE (
    [Date]        DATE             NOT NULL,
    [Description] NVARCHAR (256)   NOT NULL,
    [Amount]      DECIMAL (18, 10) NOT NULL,
    [Balance]     DECIMAL (18, 10) NOT NULL);

