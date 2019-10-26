FROM mcr.microsoft.com/mssql/server:2017-CU8-ubuntu
LABEL Author="Mark Ashworth"

RUN mkdir -p /opt/mssql-scripts
RUN mkdir -p /opt/mssql-scripts/Functions
RUN mkdir -p /opt/mssql-scripts/Procedures
RUN mkdir -p /opt/mssql-scripts/Tables
RUN mkdir -p /opt/mssql-scripts/Types
RUN mkdir -p /opt/mssql-scripts/Views
RUN mkdir -p /opt/mssql-packages

COPY scripts/Database.sql   /opt/mssql-scripts
COPY scripts/Functions/*.*  /opt/mssql-scripts/Functions/
COPY scripts/Procedures/*.* /opt/mssql-scripts/Procedures/
COPY scripts/Tables/*.*     /opt/mssql-scripts/Tables/
COPY scripts/Types/*.*      /opt/mssql-scripts/Types/
COPY scripts/Views/*.*      /opt/mssql-scripts/Views/
COPY packages/*.*           /opt/mssql-packages/

ENV MSSQL_SA_PASSWORD=${SA_PASSWORD}
ENV ACCEPT_EULA=Y

# Install Full Text Search
RUN (apt-get update \
   && apt-get install --yes curl apt-transport-https \
   && curl --fail --silent --show-error --location https://packages.microsoft.com/keys/microsoft.asc | gpg --dearmor | tee /usr/share/keyrings/microsoft-archive-keyring.gpg > /dev/null \
   && curl --fail --silent --show-error --location https://packages.microsoft.com/config/ubuntu/16.04/mssql-server-2017.list | sed "s@arch=amd64@arch=amd64 signed-by=/usr/share/keyrings/microsoft-archive-keyring.gpg@" | tee /etc/apt/sources.list.d/mssql-server.list > /dev/null \
   && apt-get update \
   && apt-get install --yes mssql-server-fts)

# Wait for the Server to start then execute the scripts
RUN ( /opt/mssql/bin/sqlservr --accept-eula & ) | grep -q "Service Broker manager has started" \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P ${MSSQL_SA_PASSWORD} -i /opt/mssql-scripts/Database.sql \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U ${DB_USER} -P ${DB_PASSWORD} -i /opt/mssql-scripts/Types/TransactionHistoryType.sql \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U ${DB_USER} -P ${DB_PASSWORD} -i /opt/mssql-scripts/Tables/Account.sql \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U ${DB_USER} -P ${DB_PASSWORD} -i /opt/mssql-scripts/Tables/AccountMapping.sql \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U ${DB_USER} -P ${DB_PASSWORD} -i /opt/mssql-scripts/Tables/Event.sql \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U ${DB_USER} -P ${DB_PASSWORD} -i /opt/mssql-scripts/Tables/Calendar.sql \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U ${DB_USER} -P ${DB_PASSWORD} -i /opt/mssql-scripts/Tables/Ledger.sql \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U ${DB_USER} -P ${DB_PASSWORD} -i /opt/mssql-scripts/Tables/Term.sql \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U ${DB_USER} -P ${DB_PASSWORD} -i /opt/mssql-scripts/Tables/TransactionHistory.sql \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U ${DB_USER} -P ${DB_PASSWORD} -i /opt/mssql-scripts/Tables/TransactionHistoryLog.sql \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U ${DB_USER} -P ${DB_PASSWORD} -i /opt/mssql-scripts/Views/vwLedger.sql \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U ${DB_USER} -P ${DB_PASSWORD} -i /opt/mssql-scripts/Views/vwAccountMonthEndSummary.sql \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U ${DB_USER} -P ${DB_PASSWORD} -i /opt/mssql-scripts/Functions/fnAccountLookupByDescription.sql \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U ${DB_USER} -P ${DB_PASSWORD} -i /opt/mssql-scripts/Functions/fnPaymentDateFor.sql \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U ${DB_USER} -P ${DB_PASSWORD} -i /opt/mssql-scripts/Functions/fnMonthEndFor.sql \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U ${DB_USER} -P ${DB_PASSWORD} -i /opt/mssql-scripts/Procedures/prTransactionHistoryDeleteDuplicates.sql \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U ${DB_USER} -P ${DB_PASSWORD} -i /opt/mssql-scripts/Procedures/prTransactionHistoryImport.sql \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U ${DB_USER} -P ${DB_PASSWORD} -i /opt/mssql-scripts/Procedures/prTransactionHistoryManualAllocate.sql \
    && /opt/mssql-tools/bin/sqlcmd -S localhost -U ${DB_USER} -P ${DB_PASSWORD} -i /opt/mssql-scripts/Procedures/prTransactionHistoryAutoAllocate.sql \
    && pkill sqlservr 