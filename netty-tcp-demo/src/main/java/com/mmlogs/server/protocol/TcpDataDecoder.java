package com.mmlogs.server.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author : Gene Yang <talkyunyn@126.com>
 * @date : 2018/09/07
 * @link : https://github.com/Talkyunyun
 */
public class TcpDataDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        // 获取协议版本
        int version = in.readInt();

        // 获取消息长度
        int contentLength = in.readInt();

        // 获取操作类型
        int cmd = in.readInt();


        // 组装协议头
        HeaderProtocol headerProtocol = new HeaderProtocol();
        headerProtocol.setCmd(cmd);
        headerProtocol.setContentLength(contentLength);
        headerProtocol.setVersion(version);

        // 读取消息内容
        byte[] content = in.readBytes(in.readableBytes()).array();

        MessageProtocol messageProtocol = new MessageProtocol();
        messageProtocol.setHeaderProtocol(headerProtocol);
        messageProtocol.setContent(new String(content));

        out.add(messageProtocol);


    }
}
