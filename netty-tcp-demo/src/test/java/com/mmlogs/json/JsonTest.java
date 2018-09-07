package com.mmlogs.json;

import com.alibaba.fastjson.JSONObject;

/**
 * Class JsonTest
 *
 * @author Gene.yang
 * @date 2018/07/05
 */
public class JsonTest {


    public static void main(String[] args) {

        jsonTest();
    }



    private static void jsonTest() {
        JSONObject data = new JSONObject();

        data.put("age", 23);

        System.out.println("ddd: " + data.getIntValue("age"));
    }
}
