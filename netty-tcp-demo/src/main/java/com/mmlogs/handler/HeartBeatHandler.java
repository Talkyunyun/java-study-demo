package com.mmlogs.handler;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mmlogs.bean.ChannelUserBean;
import com.mmlogs.enums.ResRet;
import com.mmlogs.service.AbstractHandlerService;
import com.mmlogs.service.ConnectionManageService;
import com.mmlogs.util.ResUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class HeartBeatHandler
 * 心跳检测服务
 *
 * @author Gene.yang
 * @date 2018/06/24
 */
public class HeartBeatHandler {
    private static final Logger logger = LoggerFactory.getLogger(HeartBeatHandler.class);

    private static final ConnectionManageService connectionManageService = ConnectionManageService.getInstance();

    /**
     * 处理心跳消息
     *
     * @param data    客户端发送数据
     * @param context 连接上下文
     */
    public static void handle(JSONObject data, ChannelHandlerContext context, int cmd) {
        Channel channel = context.channel();

        try {
            if (!channel.hasAttr(AbstractHandlerService.USER)) {
                throw new Exception("该连接没有注册，关闭");
            }

            ChannelUserBean userBean = channel.attr(AbstractHandlerService.USER).get();
            // 不存在该用户连接，直接关闭当年连接
            if (null == userBean) {
                throw new Exception("不存在该用户连接信息");
            }

            logger.info("m：" + connectionManageService.getMobileConnectionCount() + " | p: " + connectionManageService.getPcConnectionCount());
            channel.writeAndFlush(ResUtil.success(cmd));
        } catch (Exception e) {
            channel.writeAndFlush(ResUtil.error(cmd, ResRet.handsShakeError.getMsg(), ResRet.handsShakeError.getRet()));
            channel.close();

            logger.error("心跳检测错误: req: " + data + " | msg: " + e.getMessage());
        }
    }
}
