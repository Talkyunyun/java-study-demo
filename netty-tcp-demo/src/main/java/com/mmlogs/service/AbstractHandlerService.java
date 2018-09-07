package com.mmlogs.service;

import com.mmlogs.bean.ChannelUserBean;
import io.netty.util.AttributeKey;

/**
 * Class AbstractHandlerService
 *
 * @author Gene.yang
 * @date 2018/07/04
 */
public class AbstractHandlerService {

    /**
     * 用户数据
     */
    public static final AttributeKey<ChannelUserBean> USER = AttributeKey.valueOf("user");
}
