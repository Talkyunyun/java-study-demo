package com.mmlogs;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.mmlogs.bean.ConfigBean;

import java.io.FileReader;

/**
 * Class StartApplication
 * java项目解析配置文件案例
 * @author Gene.yang
 * @date 2018/06/28
 */
public class Application {

    public static void main(String[] args) throws Exception {


        System.out.println("哈哈哈哈哈哈");

//        YamlReader reader = new YamlReader(new FileReader(Application.class.getClassLoader().getResource("config.yml").getPath()));
//        ConfigBean contact = reader.read(ConfigBean.class);
//        System.out.println(contact.toString());
    }
}
