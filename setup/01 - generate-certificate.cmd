"C:\Program Files\Java\jdk1.8.0_181\bin\keytool.exe" -genkey -keyalg RSA -dname CN=localhost -alias tomcat -keystore "C:\SDE\server\apache-tomcat-8.5.43\conf\localhost_rsa.jks" -keypass delphi18* -storepass delphi18*

"C:\Program Files\Java\jdk1.8.0_181\bin\keytool.exe" -importkeystore -srckeystore tomcat -destkeystore tomcat.jks -deststoretype pkcs12