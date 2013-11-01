#!/bin/sh

# config
#TOMCAT_HOME=/opt/apache-tomcat-7.0.42
#MAVEN_HOME=/opt/apache-maven-3.1.0
#ECLIPSE_WORKSPACE=/home/elias/workspace-eclipse
#PROJECT_NAME=PhotoAlbum

# mejor se lo paso por argumentos para que sea portable
TOMCAT_HOME="$1"
MAVEN_HOME="$2"
ECLIPSE_WORKSPACE="$3"
PROJECT_NAME="$4"

# commands
TOMCAT_START=$TOMCAT_HOME/bin/startup.sh
TOMCAT_STOP=$TOMCAT_HOME/bin/shutdown.sh
TOMCAT_CLEAN="rm -Rv $TOMCAT_HOME/webapps/$PROJECT_NAME $TOMCAT_HOME/work/Catalina/localhost/$PROJECT_NAME"
CD_PROJECT="cd $ECLIPSE_WORKSPACE/$PROJECT_NAME"
MAVEN_INSTALL="$MAVEN_HOME/bin/mvn install -Dmaven.test.skip=true"

# (1) stop tomcat
echo
echo "--> Stoping tomcat..."
echo "--> [exec] $TOMCAT_STOP"
echo
$TOMCAT_STOP

# (2) clean webapp folder
sleep 2
echo
echo "--> Cleaning $PROJECT_NAME (session, cache, etc) from tomcat..."
echo "--> [exec] $TOMCAT_CLEAN"
echo
$TOMCAT_CLEAN

# (3) rebuild project
echo
echo "--> Rebuilding $PROJECT_NAME..."
echo "--> [exec] $CD_PROJECT"
$CD_PROJECT
echo "--> [exec] $MAVEN_INSTALL"
echo
$MAVEN_INSTALL
if [ "$?" != "0" ]; then
	exit
fi

# (4) start tomcat
echo
echo "--> Starting tomcat..."
echo "--> [exec] $TOMCAT_START"
echo
$TOMCAT_START
if [ "$?" != "0" ]; then
	exit
fi
echo
echo "--> Done."

## esto último (el 5) lo comento porque es mas cómodo mantener el tail corriendo
## siempre en terminal o eclipse

# (5) show tomcat logs
#sleep 2
#TOMCAT_PID=$(netstat -ntpl 2>/dev/null | grep :8080 | egrep -o -E '[0-9]+/\w+' | egrep -o -E '[0-9]+')
#TOMCAT_TAIL="tail -n +1 -f --pid=$TOMCAT_PID $TOMCAT_HOME/logs/catalina.out"
#echo
#echo "--> Show tomcat logs..."
#echo "--> [exec] $TOMCAT_TAIL"
#echo
#$TOMCAT_TAIL

