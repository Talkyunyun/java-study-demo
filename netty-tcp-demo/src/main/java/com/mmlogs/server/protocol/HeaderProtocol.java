package com.mmlogs.server.protocol;

import lombok.Data;

/**
 *
 * TCP消息自定义头部
 * @author : Gene Yang <talkyunyn@126.com>
 * @date : 2018/09/07
 * @link : https://github.com/Talkyunyun
 */
@Data
public class HeaderProtocol {

    /**
     * 协议版本
     */
    private int version;

    /**
     * 消息内容长度
     */
    private int contentLength;

    /**
     * 操作类型
     */
    private int cmd;
}
