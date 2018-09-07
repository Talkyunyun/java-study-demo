package com.mmlogs.enums;

/**
 * Class CmdEnums
 *
 * @author Gene.yang
 * @date 2018/07/06
 */
public enum CmdEnums {
    // 消息类型定义

    HAND_SHAKE(1, "握手连接"),
    HEART_BEAT(2, "心跳检测"),
    MESSAGE_UP(3, "消息上行"),
    MESSAGE_DOWN(4, "消息下行");

    private int cmd;

    private String desc;

    CmdEnums(int cmd, String desc) {
        this.cmd = cmd;
        this.desc = desc;
    }

    public int getCmd() {
        return cmd;
    }

    public String getDesc() {
        return desc;
    }
}
