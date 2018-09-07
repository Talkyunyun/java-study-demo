package com.mmlogs.server.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author : Gene Yang <talkyunyn@126.com>
 * @date : 2018/09/07
 * @link : https://github.com/Talkyunyun
 */
public class TcpDataEncoder extends MessageToByteEncoder<MessageProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {

        // 将message转换成二进制数据
        HeaderProtocol headerProtocol = msg.getHeaderProtocol();


        // 写入header信息
        out.writeInt(headerProtocol.getVersion());
        out.writeInt(msg.getContent().length());
        out.writeInt(headerProtocol.getCmd());

        // 写入消息主体信息
        out.writeBytes(msg.getContent().getBytes());
    }
}
