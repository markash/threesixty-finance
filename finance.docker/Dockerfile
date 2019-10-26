# we are extending everything from tomcat:8.0 image ...
FROM tomcat:8.5-jre8
LABEL Author="Mark Ashworth"

RUN mkdir /opt/ssl
RUN openssl req -new -newkey rsa:4096 -days 3650 -nodes -x509 -subj \
    "/C=ZA/ST=GAUTENG/L=JOHANNESBURG/O=ThreeSixty/CN=threesixty.github.com" -keyout \
    /opt/ssl/ssl.key -out /opt/ssl/ssl.crt

COPY ./server.xml           /usr/local/tomcat/conf/ 
COPY ./tomcat-users.xml     /usr/local/tomcat/conf/

COPY ./finance.server.war   /usr/local/tomcat/webapps/
COPY ./finance.war          /usr/local/tomcat/webapps/