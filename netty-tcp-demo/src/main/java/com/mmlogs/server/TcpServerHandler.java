package com.mmlogs.server;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mmlogs.bean.ChannelUserBean;
import com.mmlogs.enums.CmdEnums;
import com.mmlogs.enums.ResRet;
import com.mmlogs.handler.HandShakeHandler;
import com.mmlogs.handler.HeartBeatHandler;
import com.mmlogs.handler.MessageHandle;
import com.mmlogs.service.AbstractHandlerService;
import com.mmlogs.service.ConnectionManageService;
import com.mmlogs.util.ResUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Class TcpServerHandler
 *
 * @author Gene.yang
 * @date 2018/06/30
 */
public class TcpServerHandler extends ChannelInboundHandlerAdapter {
    private final Logger logger = LoggerFactory.getLogger(TcpServerHandler.class);

    /**
     * 连接服务管理
     */
    private final ConnectionManageService connectionManageService = ConnectionManageService.getInstance();


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        logger.info("新连接加入：" + channel.id());
//
        JSONObject data = new JSONObject();
        data.put("name", "gene");
        data.put("age", 23);

        JSONArray arr = new JSONArray();
        arr.add("aaa");
        arr.add("bb");
        arr.add("cc");
        arr.add("sss");

        data.put("children", arr);

        System.out.println("data" + data.toString());
        channel.writeAndFlush(data.toJSONString());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        connectionManageService.destroy(ctx, "客户端断开连接");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        JSONObject data = (JSONObject) msg;
        Channel channel = ctx.channel();


        // 操作类型
        int cmd = data.getIntValue("cmd");
        if (CmdEnums.HAND_SHAKE.getCmd() == cmd) {
            HandShakeHandler.handle(data, ctx, cmd);
        } else if (CmdEnums.HEART_BEAT.getCmd() == cmd) {
            HeartBeatHandler.handle(data, ctx, cmd);
        } else if (CmdEnums.MESSAGE_UP.getCmd() == cmd) {
            MessageHandle.handle(data, ctx, cmd);
        } else {
            channel.writeAndFlush(ResUtil.error(cmd, ResRet.invalidCmd.getMsg(), ResRet.invalidCmd.getRet()));
            channel.close();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().flush();
    }


    /**
     * 心跳检测
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        Channel channel = ctx.channel();

        logger.info("看时间地方康师傅就是地方开始发动机的时刻");
        try {
            if (!channel.hasAttr(AbstractHandlerService.USER)) {
                throw new Exception("该连接没有绑定，关闭");
            }

            ChannelUserBean userBean = channel.attr(AbstractHandlerService.USER).get();
            if (null == userBean) {
                throw new Exception("不存在该连接");
            }

            if (evt instanceof IdleStateEvent) {
                IdleState state = ((IdleStateEvent) evt).state();
                if (state.equals(IdleState.ALL_IDLE)) {// 超时直接关闭连接

                    connectionManageService.destroy(ctx, "超时主动关闭连接");
                } else if (state.equals(IdleState.READER_IDLE)) {// 发送心跳包

                }
            } else {
                super.userEventTriggered(ctx, evt);
            }
        } catch (Exception e) {
            channel.close();

            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Channel channel = ctx.channel();

        cause.printStackTrace();

        try {
            connectionManageService.destroy(ctx, "通道异常，关闭连接");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("通道异常，关闭连接");
            channel.close();
        }
    }
}
