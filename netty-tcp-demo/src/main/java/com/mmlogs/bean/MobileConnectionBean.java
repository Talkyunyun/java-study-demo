package com.mmlogs.bean;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

/**
 * Class MobileConnectionBean
 * 移动端连接对象
 * @author Gene.yang
 * @date 2018/07/04
 */
@Data
public class MobileConnectionBean {

    /**
     * 用户uid
     */
    private int uid;

    /**
     * sessionId
     */
    private String sid;

    /**
     * 连接对象
     */
    private ChannelHandlerContext context;

}
