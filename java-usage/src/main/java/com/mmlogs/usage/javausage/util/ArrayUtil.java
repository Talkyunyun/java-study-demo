package com.mmlogs.usage.javausage.util;


import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;


/**
 * Class ArrayUtil
 *
 * @author Gene.yang
 * @date 2018/06/24
 */
public class ArrayUtil {

    /**
     * 去重
     * @param data
     * @return
     */
    public static List<Integer> unique(Integer[] data) {
        List<Integer> list = new ArrayList<>();

        for (int i=0, len = data.length; i <len; i++) {
            if(!list.contains(data[i])) {
                list.add(data[i]);
            }
        }

        return list;
    }

    /**
     * 去重
     * @param data
     * @return
     */
    public static List<String> unique(String[] data) {
        List<String> list = new ArrayList<>();

        for (int i=0, len = data.length; i < len; i++) {
            if(!list.contains(data[i])) {
                list.add(data[i]);
            }
        }

        return list;
    }


    /**
     * 去重
     * @param data
     * @return
     */
    public static List<Integer> unique(JSONArray data) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0, len = data.size(); i < len; i++) {
            if (!list.contains(data.getIntValue(i))) {
                list.add(data.getIntValue(i));
            }
        }

        return list;
    }
}
