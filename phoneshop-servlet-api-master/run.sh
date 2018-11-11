#!/bin/bash
export MAVEN_OPTS=-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000
mvn tomcat7:run