package com.mmlogs.service;


import com.mmlogs.bean.MobileConnectionBean;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Class ConnectionManageServiceTest
 *
 * @author Gene.yang
 * @date 2018/07/04
 */
public class ConnectionManageServiceTest {

    private static ConnectionManageService connectionManageService = ConnectionManageService.getInstance();


    public static void main(String[] args) {
        closeChannel();
    }


    // 服务端主动关闭连接
    public static void closeChannel() {
        ConcurrentHashMap<Integer, MobileConnectionBean> connectionBeans = connectionManageService.getMobileAll();

        System.out.println(connectionBeans);
    }
}
