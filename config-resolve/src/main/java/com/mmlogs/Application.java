package com.mmlogs;


import com.mmlogs.config.Config;

/**
 * Class Application
 *
 * @author Gene.yang
 * @date 2018/06/30
 */
public class Application {

    public static void main(String[] args) {
        Config config = Config.getInstance();

        System.out.println("value: " + config.getRedisImMsgDownQueueKey());


    }
}