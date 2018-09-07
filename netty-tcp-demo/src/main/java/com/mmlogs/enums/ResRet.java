package com.mmlogs.enums;


/**
 * Class ResRet
 *
 * @author Gene.yang
 * @date 2018/06/24
 */
public enum ResRet {
    // 定义基本操作描述
    success(0, "Successful operation"),
    signError(10000, "Signature error"),
    invalidToken(10001, "Invalid token"),
    parameterError(10002, "Missing parameter: "),
    invalidLogin(10003, "Login Invalid"),
    invalidCmd(10004, "Invalid Command"),
    invalidRequest(10005, "Invalid Request"),
    connectionFull(10006, "Connection is full"),
    connectionExist(10007, "Connection already exist"),
    handsShakeError(10008, "Hands shake error"),
    otherDevicesLogin(10009, "You're logged in on other devices"),
    fail(10010, "Operation failed");

    private int ret;
    private String msg;


    ResRet(int ret, String msg){
        this.ret = ret;
        this.msg = msg;
    }

    public int getRet(){
        return this.ret;
    }

    public String getMsg(){
        return this.msg;
    }
}
