#!/bin/bash

# 设置基本路径
cd `dirname $0`
GG_BASE_DIR="$(cd ..; pwd)"


# 设置运行路径
GG_BIN_DIR=$GG_BASE_DIR/bin
GG_LIB_DIR=$GG_BASE_DIR/lib
GG_CONF_DIR=$GG_BASE_DIR/conf
GG_LOG_DIR=$GG_BASE_DIR/logs


# 运行主类
MAIN_CLASS=com.mmlogs.Application

# 获取lib下所有jar包路径
GG_JARS=`ls $GG_LIB_DIR | grep .jar | awk '{print "'$GG_LIB_DIR'/"$0}' | tr "\n" ":"`


GG_RUN_JAVA_OPTION=" -Dfile.encoding=UTF-8 -DLogPath=$GG_LOG_DIR"

case $1 in
start)
    echo "start run"
    nohup java $GG_RUN_JAVA_OPTION -classpath $GG_JARS $MAIN_CLASS
    ;;
stop)
    echo "stop run"
    ;;
restart)
    echo "restart run"
    ;;
status)
    echo "status run"
    ;;
*)

    echo "Usage: $0 {start|stop|restart|status}" >&2

esac