package com.mmlogs.bean;


import lombok.Data;


/**
 * Class ResBean
 * 响应客户端数据格式定义
 * @author Gene.yang
 * @date 2018/07/04
 */
@Data
public class ResBean<T> {

    /**
     * 操作类型，和前端
     */
    private int cmd;

    /**
     * 操作状态
     */
    private int ret;

    /**
     * 操作状态描述
     */
    private String msg;

    /**
     * 操作具体返回值
     */
    private T data;
}