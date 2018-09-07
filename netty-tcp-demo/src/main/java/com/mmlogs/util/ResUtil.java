package com.mmlogs.util;


import com.alibaba.fastjson.JSONObject;
import com.mmlogs.bean.ResBean;
import com.mmlogs.enums.CmdEnums;
import com.mmlogs.enums.ResRet;


/**
 * Class ResUtil
 *
 * @author Gene.yang
 * @date 2018/06/30
 */
public class ResUtil {
    private static final ResBean RES_BEAN = new ResBean();


    /** 通知类 */
    public static String notify(Object data, int ret, String msg) {
        JSONObject pushData = new JSONObject();
        pushData.put("cmd", CmdEnums.MESSAGE_DOWN.getCmd());
        pushData.put("ret", ret);
        pushData.put("msg", msg);
        pushData.put("data", data);

        return pushData.toString();
    }
    public static String notify(int ret, String msg) {

        return notify("", ret, msg);
    }

    /** 成功 */
    public static String success(int cmd, Object data, String msg, int ret) {
        return setData(cmd, data, msg, ret);
    }
    public static String success(int cmd, Object data) {
        return success(cmd, data, ResRet.success.getMsg(), ResRet.success.getRet());
    }
    public static String success(int cmd) {
        return success(cmd, "");
    }


    /** 失败 */
    public static String error(int cmd, String msg, int ret, Object data) {
        return setData(cmd, data, msg, ret);
    }
    public static String error(int cmd, String msg, int ret) {
        return error(cmd, msg, ret, "");
    }
    public static String error(int cmd) {
        return error(cmd, ResRet.fail.getMsg(), ResRet.fail.getRet());
    }


    /**
     * 设置数据
     * @param cmd 操作类型
     * @param data data数据
     * @param msg 消息提示语
     * @param ret 操作状态码
     * @return
     */
    private static String setData(int cmd, Object data, String msg, int ret) {
        RES_BEAN.setCmd(cmd);
        RES_BEAN.setData(data);
        RES_BEAN.setMsg(msg);
        RES_BEAN.setRet(ret);

        return JSONObject.toJSONString(RES_BEAN);
    }
}
