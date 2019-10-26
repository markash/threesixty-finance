CREATE DATABASE Finance;
GO
USE Finance;
GO
PRINT 'Creating login `Finance`...'
CREATE LOGIN [scout_finance]
    WITH 
            PASSWORD = N'password1*'
        ,   SID = 0x10B78363EA1789499F3AB7C1FC55B171
        ,   DEFAULT_DATABASE = [Finance]
        ,   DEFAULT_LANGUAGE = [us_english]
        ,   CHECK_POLICY = OFF;
GO
PRINT 'Creating user `Finance`...'
CREATE USER [scout_finance] FOR LOGIN [scout_finance];
GO
PRINT 'Adding `Finance` to dbo_owner...'
ALTER ROLE [db_owner] ADD MEMBER [scout_finance];
GO
exec sp_addrolemember 'db_owner', 'scout_finance'
GO
PRINT 'Creating full text catalog `FinanceTextCatalog`...'
CREATE FULLTEXT CATALOG [FinanceTextCatalog]
    WITH ACCENT_SENSITIVITY = ON
    AUTHORIZATION [dbo];
GO
USE Finance;
GO