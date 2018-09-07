package com.mmlogs.server.protocol;

import lombok.Data;

/**
 *
 * TCP自定义协议传输
 * @author : Gene Yang <talkyunyn@126.com>
 * @date : 2018/09/07
 * @link : https://github.com/Talkyunyun
 */
@Data
public class MessageProtocol {

    /**
     * TCP协议头
     */
    private HeaderProtocol headerProtocol;

    /**
     * TCP协议主体
     */
    private String content;
}
