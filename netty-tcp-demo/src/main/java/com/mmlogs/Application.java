package com.mmlogs;


import com.mmlogs.server.TcpServer;

/**
 * Class Application
 *
 * @author Gene.yang
 * @date 2018/06/30
 */
public class Application {


    public static void main(String[] args) {


        // 启动tcp服务
        new TcpServer().start();
    }
}
