package com.mmlogs.tcp;

import com.alibaba.fastjson.JSONObject;
import com.mmlogs.enums.ClientTypeEnums;

import java.util.UUID;

/**
 * Class ClientTest1
 *
 * @author Gene.yang
 * @date 2018/07/04
 */
public class ClientTest1 {

    public static void main(String[] args) {

        new ImClient().connServer(ClientTest1.class);
    }

    // 握手连接
    public static void handShake() {
        JSONObject data = new JSONObject();

        data.put("clientType", ClientTypeEnums.pc.getType());
        data.put("uid", 1);
        data.put("cmd", 1);
        data.put("token", UUID.randomUUID().toString());

        ImClient.FUTURE.channel().writeAndFlush(data.toJSONString());
    }

    // 发送内容
    public static void sendMsg() {

    }
}