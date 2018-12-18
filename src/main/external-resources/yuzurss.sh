#!/bin/sh

SERVICE_NAME=yuzurss
SERVICE_RAM=32M
SERVICE_MAIN=fr.lrgn.yuzurss.YuzuRssApplication

if screen -ls $SERVICE_NAME | grep -q $SERVICE_NAME
then

  echo "The service $SERVICE_NAME is already started."

else

  HEAP="-Xms$SERVICE_RAM -Xmx$SERVICE_RAM"
  ERROR="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../var/log/$SERVICE_NAME-$(date +%Y%m%d-%H%M%S).hprof -XX:ErrorFile=../var/log/$SERVICE_NAME-$(date +%Y%m%d-%H%M%S)-error.log"

  GC="-XX:+UseG1GC -XX:MaxGCPauseMillis=50 -XX:GCPauseIntervalMillis=500 -XX:+DisableExplicitGC"
  GC_LOGS="-Xlog:gc*=trace:file=../var/log/$SERVICE_NAME-$(date +%Y%m%d-%H%M%S)-gc.log:tags,time,uptime,level"

  CLASSPATH="-cp ../cfg:../lib/*"

  screen -dmS $SERVICE_NAME java -server $HEAP $ERROR $GC $GC_LOGS $CLASSPATH $SERVICE_MAIN

  echo "The service $SERVICE_NAME has been started."

fi