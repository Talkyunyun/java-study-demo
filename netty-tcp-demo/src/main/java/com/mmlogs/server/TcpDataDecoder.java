package com.mmlogs.server;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * Class TcpDataDecoder
 *
 * @author Gene.yang
 * @date 2018/06/30
 */
public class TcpDataDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] data = new byte[in.readableBytes()];
        in.readBytes(data);

        String dataStr = new String(data, CharsetUtil.UTF_8);
        out.add(JSONObject.parse(dataStr));
    }
}
