package com.mmlogs.enums;

/**
 * Class ClientTypeEnums
 * 客户端类型
 * @author Gene.yang
 * @date 2018/07/05
 */
public enum ClientTypeEnums {
    //

    mobile(1, "移动端"),
    pc(2, "PC");

    private int type;
    private String desc;


    ClientTypeEnums(int type, String desc){
        this.type = type;
        this.desc = desc;
    }

    public int getType(){
        return this.type;
    }

    public String getDesc(){
        return this.desc;
    }
}
