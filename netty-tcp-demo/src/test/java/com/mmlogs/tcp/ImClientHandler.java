package com.mmlogs.tcp;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class ImClientHandler
 *
 * @author Gene.yang
 * @date 2018/07/04
 */
public class ImClientHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(ImClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext context) {
        context.flush();
    }


    @Override
    public void channelRead(ChannelHandlerContext context, Object msg) {
        JSONObject data = (JSONObject) msg;

        int cmd = data.getIntValue("cmd");

        System.out.println("接收到消息：" + data.toString());
    }


    /** 超时5分钟直接关闭连接 */
    @Override
    public void userEventTriggered(ChannelHandlerContext context, Object evt) {
        Channel channel = context.channel();
        try {
            if (evt instanceof IdleStateEvent) {
                IdleState state = ((IdleStateEvent) evt).state();
                if (state.equals(IdleState.ALL_IDLE)) {

                    // 发送心跳包
                    JSONObject data = new JSONObject();
                    data.put("cmd", 2);

                    channel.writeAndFlush(data.toJSONString());
                }
            } else {
                super.userEventTriggered(context, evt);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("tcp空闲超时错误，" + e.getMessage());
        }
    }
}
