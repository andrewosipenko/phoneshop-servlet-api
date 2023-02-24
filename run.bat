set MAVEN_OPTS=-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000
rem mvn -Dmaven.test.skip=true tomcat7:run-war
call mvn clean package
call java -classpath target\phoneshop-servlet-api\WEB-INF\lib\*;target\classes com.es.phoneshop.PhoneshopMain