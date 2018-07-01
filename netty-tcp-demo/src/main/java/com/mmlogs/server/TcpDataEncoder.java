package com.mmlogs.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

/**
 * Class TcpDataEncoder
 *
 * @author Gene.yang
 * @date 2018/06/30
 */
public class TcpDataEncoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {

        out.writeBytes(msg.getBytes(CharsetUtil.UTF_8));
    }
}
