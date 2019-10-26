FROM mcr.microsoft.com/mssql/server:2017-CU8-ubuntu
LABEL Author="Mark Ashworth"

ENV MSSQL_SA_PASSWORD=${SA_PASSWORD}
ENV ACCEPT_EULA=Y

# Install Full Text Search
RUN (apt-get update \
   && apt-get install --yes curl apt-transport-https \
   && curl --fail --silent --show-error --location https://packages.microsoft.com/keys/microsoft.asc | gpg --dearmor | tee /usr/share/keyrings/microsoft-archive-keyring.gpg > /dev/null \
   && curl --fail --silent --show-error --location https://packages.microsoft.com/config/ubuntu/16.04/mssql-server-2017.list | sed "s@arch=amd64@arch=amd64 signed-by=/usr/share/keyrings/microsoft-archive-keyring.gpg@" | tee /etc/apt/sources.list.d/mssql-server.list > /dev/null \
   && apt-get update \
   && apt-get install --yes mssql-server-fts)