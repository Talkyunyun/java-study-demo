package com.mmlogs.handler;


import com.alibaba.fastjson.JSONObject;
import com.mmlogs.bean.ChannelUserBean;
import com.mmlogs.bean.MobileConnectionBean;
import com.mmlogs.bean.PcConnectionBean;
import com.mmlogs.enums.ClientTypeEnums;
import com.mmlogs.enums.CmdEnums;
import com.mmlogs.enums.ResRet;
import com.mmlogs.service.AbstractHandlerService;
import com.mmlogs.service.ConnectionManageService;
import com.mmlogs.util.ResUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;


/**
 * Class HandShakeHandler
 * 握手处理
 * @author Gene.yang
 * @date 2018/06/24
 */
public class HandShakeHandler {
    private static final Logger logger = LoggerFactory.getLogger(HandShakeHandler.class);

    private static final ConnectionManageService connectionManageService = ConnectionManageService.getInstance();

    /**
     * 当前连接对象
     */
    private static ChannelHandlerContext context;

    /**
     * 请求数据对象
     */
    private static JSONObject reqData;

    /**
     * 连接建立处理，根据token找到用户信息，并为该连接生成一个唯一sid返回给客户端
     * @param ctx
     * @return
     */
    public static void handle(JSONObject data, ChannelHandlerContext ctx, int cmd) {
        context = ctx;
        reqData = data;

        Channel channel = ctx.channel();
        try {
            String token = data.getString("token");
            int uid = data.getIntValue("uid");
            int clientType = data.getIntValue("clientType"); // 客户端类型

            ConnectionManageService connectionManageService = ConnectionManageService.getInstance();
            // 判断连接是否达到上限
            if (connectionManageService.isMaxConnection()) {
                channel.writeAndFlush(ResUtil.error(cmd, ResRet.connectionFull.getMsg(), ResRet.connectionFull.getRet()));
                channel.close();
                return;
            }

            addConnection(uid, clientType);
        } catch (Exception e) {
            e.printStackTrace();
            handFail();
        }
    }


    /**
     * 添加连接并通知用户
     * @param uid
     * @param clientType
     */
    private static void addConnection(int uid, int clientType) {
        String sid = UUID.randomUUID().toString();

        if (clientType == ClientTypeEnums.mobile.getType()) {
            MobileConnectionBean connectionBeanOther = connectionManageService.getMobileConnectionByUid(uid);
            if (null != connectionBeanOther) {
                // 下线mobile其他设备
                Channel channelOther = connectionBeanOther.getContext().channel();
                channelOther.writeAndFlush(ResUtil.notify(ResRet.otherDevicesLogin.getRet(), ResRet.otherDevicesLogin.getMsg()));

                // 清空socket用户数据，防止销毁连接，把新连接销毁掉
                Attribute<ChannelUserBean> attribute = channelOther.attr(AbstractHandlerService.USER);
                attribute.set(null);

                connectionManageService.destroy(connectionBeanOther.getContext(), "mobile下线其他设备");
                logger.info("移动端端下线其他设备");
            }

            // 移动端连接
            MobileConnectionBean connectionBean = new MobileConnectionBean();
            connectionBean.setUid(uid);
            connectionBean.setContext(context);
            connectionBean.setSid(sid);

            connectionManageService.add(uid, connectionBean);
        } else if (clientType == ClientTypeEnums.pc.getType()) {
            PcConnectionBean connectionBeanOther = connectionManageService.getPcConnectionByUid(uid);
            if (null != connectionBeanOther) {
                // 下线PC其他设备
                Channel channelOther = connectionBeanOther.getContext().channel();
                channelOther.writeAndFlush(ResUtil.notify(ResRet.otherDevicesLogin.getRet(), ResRet.otherDevicesLogin.getMsg()));

                // 清空socket用户数据，防止销毁连接，把新连接销毁掉
                Attribute<ChannelUserBean> attribute = channelOther.attr(AbstractHandlerService.USER);
                attribute.set(null);

                connectionManageService.destroy(connectionBeanOther.getContext(), "PC下线其他设备");
                logger.info("PC端下线其他设备");
            }

            // pc连接
            PcConnectionBean connectionBean = new PcConnectionBean();
            connectionBean.setUid(uid);
            connectionBean.setContext(context);
            connectionBean.setSid(sid);

            System.out.println("开始添加：2");
            connectionManageService.add(uid, connectionBean);
        } else {
            // 非法客户端类型，握手失败
            handFail();
            return;
        }

        // 绑定用户到socket对象
        bindChannelAttr(uid, sid, clientType);
    }

    /**
     * 绑定用户信息到socket对象上
     * @param uid
     * @param sid
     * @param clientType
     */
    private static void bindChannelAttr(int uid, String sid, int clientType) {
        Channel channel = context.channel();
        // 绑定用户到socket对象
        Attribute<ChannelUserBean> attribute = channel.attr(AbstractHandlerService.USER);
        ChannelUserBean userBean = new ChannelUserBean();
        userBean.setUid(uid);
        userBean.setClientType(clientType);
        userBean.setSid(sid);
        userBean.setConnectionTime(System.currentTimeMillis());
        attribute.set(userBean);

        // TODO 测试用户数据
        JSONObject userInfo = new JSONObject();
        userInfo.put("uid", uid);
        userInfo.put("name", "gene_" + uid);
        userInfo.put("age", "age_" + uid);
        userInfo.put("clientType", clientType);

        // 返回用户信息
        reqData.put("user", userInfo);
        reqData.put("sid", sid);

        channel.writeAndFlush(ResUtil.success(CmdEnums.HAND_SHAKE.getCmd(), reqData));
    }


    /**
     * 握手失败，响应客户端
     */
    private static void handFail() {

        context.channel().writeAndFlush(ResUtil.error(CmdEnums.HAND_SHAKE.getCmd(), ResRet.handsShakeError.getMsg(), ResRet.handsShakeError.getRet()));
        context.channel().close();
    }
}
