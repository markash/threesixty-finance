
SET SERVER=DESKTOP

sqlcmd.exe -S %SERVER% -U %DB_USER% -P %DB_PASSWORD% -d Finance -i ./Tables/Event.sql
sqlcmd.exe -S %SERVER% -U %DB_USER% -P %DB_PASSWORD% -d Finance -i ./Tables/Calendar.sql

sqlcmd.exe -S %SERVER% -U %DB_USER% -P %DB_PASSWORD% -d Finance -i ./Tables/Account.sql

sqlcmd.exe -S %SERVER% -U %DB_USER% -P %DB_PASSWORD% -d Finance -i ./Tables/Budget.sql
sqlcmd.exe -S %SERVER% -U %DB_USER% -P %DB_PASSWORD% -d Finance -i ./Tables/BudgetItem.sql

sqlcmd.exe -S %SERVER% -U %DB_USER% -P %DB_PASSWORD% -d Finance -i ./Views/vwAccount.sql
sqlcmd.exe -S %SERVER% -U %DB_USER% -P %DB_PASSWORD% -d Finance -i ./Views/vwCalendar.sql
sqlcmd.exe -S %SERVER% -U %DB_USER% -P %DB_PASSWORD% -d Finance -i ./Views/vwBudget.sql
sqlcmd.exe -S %SERVER% -U %DB_USER% -P %DB_PASSWORD% -d Finance -i ./Views/vwBudgetItem.sql