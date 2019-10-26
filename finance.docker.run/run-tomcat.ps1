#-v C:/SDE/server/docker/scout-tomcat/logs:/logs
docker container run -p 443:8443 -p 80:8080 --name scout_tomcat --link scout_mssql  -it mpashworth/scout-tomcat 