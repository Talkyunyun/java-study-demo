package com.mmlogs.bean;

import lombok.Data;

/**
 * Class ChannelUserBean
 *
 * @author Gene.yang
 * @date 2018/07/06
 */
@Data
public class ChannelUserBean {

    /**
     * 链接sessionID
     */
    private String sid;

    /**
     * 用户Uid
     */
    private int uid;

    /**
     * 客户端类型
     */
    private int clientType;

    /**
     * 链接时间
     */
    private long connectionTime;
}
