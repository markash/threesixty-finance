# ThreeSixty Finance
[![Build Status](https://travis-ci.org/markash/threesixty-finance.svg?branch=master)](https://travis-ci.org/markash/threesixty-finance)

[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=threesixty-finance&metric=alert_status)](https://sonarcloud.io/dashboard?id=threesixty-finance)
 
## Tomcat Deployment 
### How to create developer certificates
Use the mkcert application which is a simple tool for making locally-trusted development certificates and it requires no configuration.

Download available at https://github.com/FiloSottile/mkcert/releases

```
mkcert-v1.4.0-windows-amd64.exe -install
```

**Note** that on Windows to run the command line as Administrator when executing the <b>mkcert-v1.4.0-windows-amd64.exe -install</b> command.

```
mkcert-v1.4.0-windows-amd64.exe localhost 127.0.0.1 ::1
```

Rename the two generated pem files as 
1. localhost.pem
2. localhost-key.pem

Copy the files to the Tomcat conf directory.

Configure SSL within the Tomcat server.xml file by uncommenting the section that refers to using a pem file to hold the key and certificates. Remove the attribute that refers to the certificate chain file because it was not configured in this case. 