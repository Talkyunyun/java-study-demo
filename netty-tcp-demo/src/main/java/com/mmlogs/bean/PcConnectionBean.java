package com.mmlogs.bean;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

/**
 * Class PcConnectionBean
 * PC连接对象
 * @author Gene.yang
 * @date 2018/07/04
 */
@Data
public class PcConnectionBean {

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
