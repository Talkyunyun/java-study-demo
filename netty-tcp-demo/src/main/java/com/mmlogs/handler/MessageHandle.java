package com.mmlogs.handler;


import com.alibaba.fastjson.JSONObject;
import com.mmlogs.bean.ChannelUserBean;
import com.mmlogs.enums.ResRet;
import com.mmlogs.service.AbstractHandlerService;
import com.mmlogs.util.ResUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Class MessageHandle
 *
 * @author Gene.yang
 * @date 2018/06/24
 */
public class MessageHandle {
    private static final Logger logger = LoggerFactory.getLogger(MessageHandle.class);


    public static void handle(JSONObject data, ChannelHandlerContext context, int cmd) {
        Channel channel = context.channel();

        try {
            ChannelUserBean userBean = channel.attr(AbstractHandlerService.USER).get();
            if (null == userBean) {
                channel.writeAndFlush(ResUtil.error(cmd, ResRet.invalidRequest.getMsg(), ResRet.invalidRequest.getRet()));
                channel.close();
                return ;
            }

            int from = data.getIntValue("from");
            if (from == 0) {
                channel.writeAndFlush(ResUtil.error(cmd, ResRet.parameterError.getMsg() + "from", ResRet.parameterError.getRet()));
                return ;
            }

            data.put("msg", "我是服务端发送过来的");
            data.put("from", "233");

            channel.writeAndFlush(ResUtil.success(cmd, data));
        } catch (Exception e) {
            e.printStackTrace();

            channel.writeAndFlush(ResUtil.error(cmd));

            logger.error("消息上行失败. msg: " + e.getMessage());
        }
    }
}
