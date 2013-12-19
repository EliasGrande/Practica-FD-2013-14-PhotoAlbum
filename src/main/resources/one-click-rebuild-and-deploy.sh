#!/bin/sh

# config
#TOMCAT_HOME_WEB=/opt/apache-tomcat-7.0.42
#TOMCAT_HOME_REST=/opt/apache-tomcat-7.0.42-2
#H2_JAR=/opt/apache-tomcat-7.0.42/lib/h2-1.3.173.jar
#MAVEN_HOME=/opt/apache-maven-3.1.0
#ECLIPSE_WORKSPACE=/home/elias/workspace-eclipse
#PROJECT_NAME=PhotoAlbum

# mejor se lo paso por argumentos para que sea portable
TOMCAT_WEB_HOME="$1"
TOMCAT_REST_HOME="$2"
H2_JAR="$3"
MAVEN_HOME="$4"
ECLIPSE_WORKSPACE="$5"
PROJECT_NAME="$6"
H2_PORT="9092"
TOMCAT_WEB_PORT="8080"
TOMCAT_REST_PORT="8081"

echo "TOMCAT_WEB_HOME $TOMCAT_WEB_HOME"
echo "TOMCAT_REST_HOME $TOMCAT_REST_HOME"
echo "H2_JAR $H2_JAR"
echo "MAVEN_HOME $MAVEN_HOME"
echo "ECLIPSE_WORKSPACE $ECLIPSE_WORKSPACE"
echo "PROJECT_NAME $PROJECT_NAME"
echo "H2_PORT $H2_PORT"
echo "TOMCAT_WEB_PORT $TOMCAT_WEB_PORT"
echo "TOMCAT_REST_PORT $TOMCAT_REST_PORT"

# xterm geometry
GEOMETRY_H2="-geometry 200x20+0+30"
GEOMETRY_TOMCAT_WEB="-geometry 200x20+0+340"
GEOMETRY_TOMCAT_REST="-geometry 200x20+0+650"
# commands
TOMCAT_WEB_START=$TOMCAT_WEB_HOME/bin/startup.sh
TOMCAT_WEB_STOP=$TOMCAT_WEB_HOME/bin/shutdown.sh
TOMCAT_REST_START=$TOMCAT_REST_HOME/bin/startup.sh
TOMCAT_REST_STOP=$TOMCAT_REST_HOME/bin/shutdown.sh
TOMCAT_WEB_CLEAN="rm -Rv $TOMCAT_WEB_HOME/webapps/$PROJECT_NAME $TOMCAT_WEB_HOME/work/Catalina/localhost/$PROJECT_NAME"
TOMCAT_REST_CLEAN="rm -Rv $TOMCAT_REST_HOME/webapps/$PROJECT_NAME $TOMCAT_REST_HOME/work/Catalina/localhost/$PROJECT_NAME"
CD_PROJECT="cd $ECLIPSE_WORKSPACE/$PROJECT_NAME"
MAVEN_INSTALL="$MAVEN_HOME/bin/mvn install -Dmaven.test.skip=true"
H2_START="xterm $GEOMETRY_H2 -T H2-Server -e java -cp $H2_JAR org.h2.tools.Server -tcp -trace -tcpPort $H2_PORT"
H2_STOP="java -cp $H2_JAR org.h2.tools.Server -tcpShutdown tcp://localhost:$H2_PORT"

# stop servers
echo
echo "--> Stoping tomcat (web)..."
echo "--> [exec] $TOMCAT_WEB_STOP"
echo
$TOMCAT_WEB_STOP
echo
echo "--> Stoping tomcat (rest)..."
echo "--> [exec] $TOMCAT_REST_STOP"
echo
$TOMCAT_REST_STOP
echo
echo "--> Stoping H2 server..."
echo "--> [exec] $H2_STOP"
echo
$H2_STOP

# clean webapp folder
sleep 2
echo
echo "--> Cleaning $PROJECT_NAME (web) from tomcat..."
echo "--> [exec] $TOMCAT_WEB_CLEAN"
echo
$TOMCAT_WEB_CLEAN
echo
echo "--> Cleaning $PROJECT_NAME (rest) from tomcat..."
echo "--> [exec] $TOMCAT_REST_CLEAN"
echo
$TOMCAT_REST_CLEAN

# rebuild project
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

# start h2 server
echo
echo "--> Starting H2 server..."
echo "--> [exec] $H2_START"
$($H2_START) &
h2pid=$!
sleep 20

TOMCAT_WEB_TAIL="xterm $GEOMETRY_TOMCAT_WEB -T Web-Server-Tail -e tail -n 0 -f --pid=$h2pid $TOMCAT_WEB_HOME/logs/catalina.out"
TOMCAT_REST_TAIL="xterm $GEOMETRY_TOMCAT_REST -T Rest-Server-Tail -e tail -n 0 -f --pid=$h2pid $TOMCAT_REST_HOME/logs/catalina.out"

# show tomcat logs
echo
echo "--> Showing tomcat logs (web)..."
echo "--> [exec] $TOMCAT_WEB_TAIL"
$($TOMCAT_WEB_TAIL) &
echo
echo "--> Showing tomcat logs (rest)..."
echo "--> [exec] $TOMCAT_REST_TAIL"
$($TOMCAT_REST_TAIL) &

# start tomcat
echo
echo "--> Starting tomcat (web)..."
echo "--> [exec] $TOMCAT_WEB_START"
echo
$TOMCAT_WEB_START
if [ "$?" != "0" ]; then
	exit
fi
echo
echo "--> Starting tomcat (rest)..."
echo "--> [exec] $TOMCAT_REST_START"
echo
$TOMCAT_REST_START
if [ "$?" != "0" ]; then
	exit
fi
# stop servers
echo
echo "--> Press ENTER to stop servers. Waiting..."
read input
echo "--> Stoping tomcat (web)..."
echo "--> [exec] $TOMCAT_WEB_STOP"
echo
$TOMCAT_WEB_STOP
echo
echo "--> Stoping tomcat (rest)..."
echo "--> [exec] $TOMCAT_REST_STOP"
echo
$TOMCAT_REST_STOP
echo
echo "--> Stoping H2 server..."
echo "--> [exec] $H2_STOP"
echo
$H2_STOP
echo
echo "--> Done."

