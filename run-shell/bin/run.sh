#!/bin/bash
##
## java项目运行脚本
## @Author: Gene.yang
##
## 使用案例：
## ./run.sh start|stop|restart|status
##
## 注意：
##     1、唯一需要修改的是项目运行主类。
##     2、如果想对jvm自定义参数配置，可修改G_RUN_JAVA_OPTION参数
##



# 运行主类
MAIN_CLASS=com.mmlogs.runshell.RunShellApplication

# 设置基本路径
cd `dirname $0`
G_BASE_DIR="$(cd ..; pwd)"
G_NAME=$G_BASE_DIR

# 设置项目路径信息
G_BIN_DIR=$G_BASE_DIR/bin
G_LIB_DIR=$G_BASE_DIR/lib
G_CONF_DIR=$G_BASE_DIR/conf
G_LOG_DIR=$G_BASE_DIR/logs

# 获取lib下所有jar包路径
G_JARS=`ls $G_LIB_DIR | grep .jar | awk '{print "'$G_LIB_DIR'/"$0}' | tr "\n" ":"`

# java运行option配置
G_RUN_JAVA_OPTION=" -Dfile.encoding=UTF-8 -DLogPath=$G_LOG_DIR"

# 启动
function start() {
    pid=$(getPid)
    if [ "$pid" != "" ]; then
        echo -e "The service is already running. is pid: $pid"
        return
    fi

    if [ ! -d $G_LOG_DIR ]; then
        mkdir $G_LOG_DIR
    fi

    echo -e "Service is starting up."

    ## 运行项目
    nohup java $G_RUN_JAVA_OPTION -classpath $G_CONF_DIR:$G_JARS $MAIN_CLASS >$G_LOG_DIR/nohup 2>&1 &

    sleep 5
    echo -e "Service startup successful."
}


# 停止
function stop() {
    pid=$(getPid)
    if [ "$pid" != "" ]; then
        echo -e "Process PID is: $pid"
        echo -e "The service is stopping."
        kill -9 "$pid"
        echo -e "Service stopped successfully."
    else
        echo -e "The service does not run or does not exist."
    fi
}

# 查看状态
function status() {
    pid=$(getPid)
    if [ "$pid" != "" ]; then
        echo -e "The service is running, is pid: $pid"
    else
        echo -e "Service stops or does not exist."
    fi
}

# 获取运行pid
function getPid() {
    pid=`ps -ef | grep -w "$G_NAME" | grep -v "grep" | awk '{printf $2}'`

    echo $pid
}


# 使用案例
function usage() {
    echo -e "Usage: $0 {start|stop|restart|status}"
}


case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        stop
        start
        ;;
    status)
        status
        ;;
    *)
        usage
        ;;
esac
exit 0