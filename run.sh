#!/bin/bash
export MAVEN_OPTS=-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000
# mvn -Dmaven.test.skip=true tomcat7:run-war
mvn clean package
java -classpath target/phoneshop-servlet-api/WEB-INF/lib/*:target/classes com.es.phoneshop.PhoneshopMain --path /phoneshop --war target/phoneshop-servlet-api.war --port 8080