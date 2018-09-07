package com.mmlogs.service;

import com.mmlogs.bean.ChannelUserBean;
import com.mmlogs.bean.MobileConnectionBean;
import com.mmlogs.bean.PcConnectionBean;
import com.mmlogs.enums.ClientTypeEnums;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Class ConnectionManageService
 *
 * @author Gene.yang
 * @date 2018/07/04
 */
public class ConnectionManageService {
    private final Logger logger = LoggerFactory.getLogger(ConnectionManageService.class);

    // 最大连接数
    private static final int MAX_CONNECTION = 10000;


    /**
     * PC连接map
     */
    private final ConcurrentHashMap<Integer, PcConnectionBean> pcConnections = new ConcurrentHashMap<>();

    /**
     * 移动端连接map
     */
    private final ConcurrentHashMap<Integer, MobileConnectionBean> mobileConnections = new ConcurrentHashMap<>();

    /**
     * PC添加连接
     * @param uid 用户Uid
     * @param connectionBean 连接对象
     */
    public void add(int uid, PcConnectionBean connectionBean) {
        if (0 == uid) {
            return;
        }

        pcConnections.put(uid, connectionBean);
    }

    /**
     * 移动端添加连接
     * @param uid 用户uid
     * @param connectionBean 连接对象
     */
    public void add(int uid, MobileConnectionBean connectionBean) {
        if (0 == uid) {
            return;
        }

        mobileConnections.put(uid, connectionBean);
    }

    /**
     * 根据Uid获取用户移动端连接对象
     * @param uid 用户uid
     * @return 连接对象
     */
    public MobileConnectionBean getMobileConnectionByUid(int uid) {
        if (mobileConnections.containsKey(uid)) {
            return mobileConnections.get(uid);
        }

        return null;
    }

    /**
     * 根据UId获取用户PC端连接对象
     * @param uid 用户UID
     * @return 连接对象
     */
    public PcConnectionBean getPcConnectionByUid(int uid) {
        if (pcConnections.containsKey(uid)) {
            return pcConnections.get(uid);
        }

        return null;
    }

    /**
     * 判断用户是否存在移动端连接对象
     * @param uid 用户Uid
     * @return 连接对象
     */
    public boolean hasMobileConnection(int uid) {

        return mobileConnections.containsKey(uid);
    }

    /**
     * 判断用户是否存在PC端连接对象
     * @param uid 用户uid
     * @return 连接对象
     */
    public boolean hasPcConnection(int uid) {

        return pcConnections.containsKey(uid);
    }

    /**
     * 获取所有移动端连接对象数组
     * @return 连接对象数组
     */
    public ConcurrentHashMap<Integer, MobileConnectionBean> getMobileAll() {

        return mobileConnections;
    }

    /**
     * 获取所有PC连接对象数组
     * @return 连接对象数组
     */
    public ConcurrentHashMap<Integer, PcConnectionBean> getPcAll() {

        return pcConnections;
    }


    /**
     * 判断连接数是否达到最大，该方法同时判断PC和移动端总和
     * @return true 已满， false 未满
     */
    public boolean isMaxConnection() {
        int mobileCount = mobileConnections.size();
        int pcCount = pcConnections.size();

        if ((mobileCount + pcCount) >= MAX_CONNECTION) {
            return true;
        }

        return false;
    }

    /**
     * 获取移动端连接数
     * @return 总数
     */
    public int getMobileConnectionCount() {

        return mobileConnections.size();
    }

    /**
     * 获取PC端连接数
     * @return 总数
     */
    public int getPcConnectionCount() {

        return pcConnections.size();
    }











    /**
     * 销毁连接
     * @param context 连接对象
     * @param msg 关闭原因
     */
    public void destroy(ChannelHandlerContext context, String msg) {
        Channel channel = context.channel();

        logger.info("服务端关闭连接：" + msg);
        // 判断该连接是否绑定用户数据
        ChannelUserBean userBean = channel.attr(AbstractHandlerService.USER).get();
        if (null == userBean) {
            channel.close();
            return;
        }

        if (ClientTypeEnums.mobile.getType() == userBean.getClientType()) {
            // 移除移动连接
            mobileConnections.remove(userBean.getUid());
        } else if (ClientTypeEnums.pc.getType() == userBean.getClientType()) {
            // 移除pc连接
            pcConnections.remove(userBean.getUid());
        }

        channel.close();
    }


















    /**
     * 获取实例对象
     * @return 实例对象
     */
    public static ConnectionManageService getInstance() {
        return ConnectionManageServiceHolder.INSTANCE;
    }
    private ConnectionManageService() {}
    private static class ConnectionManageServiceHolder {
        private static final ConnectionManageService INSTANCE = new ConnectionManageService();
    }
}
