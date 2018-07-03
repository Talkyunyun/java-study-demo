package com.mmlogs.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class PropertyUtil
 * 项目配置文件读取工具
 * @author Gene.yang
 * @date 2018/07/02
 */
public class PropertyUtil {
    private static Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

    /**
     * 总配置文件
     */
    private static Properties properties;

    /**
     * 当前环境配置文件
     */
    private static Properties propertiesEnv;

    static {
        loadProps();
    }

    /**
     * 加载配置文件
     */
    synchronized static private void loadProps() {
        properties = new Properties();
        propertiesEnv = new Properties();

        InputStream inEnv = null;
        InputStream in = null;
        try {
            // 获取主配置文件
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(in);

            // 存在环境配置文件
            String env = properties.getProperty("server.run.env");
            if (null != env) {
                inEnv = PropertyUtil.class.getClassLoader().getResourceAsStream("application-" + env + ".properties");
                propertiesEnv.load(inEnv);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("没有发现相应配置文件", e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("读取配置文件失败", e);
        } finally {
            try {
                in.close();
                inEnv.close();
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("关闭文件流失败", e);
            }
        }
    }

    /**
     * 获取配置文件值
     * @param key 获取Key
     * @return 返回值
     */
    public static String getProperty(String key) {
        if (null == properties) {
            loadProps();
        }

        if (properties.containsKey(key)) {
            return properties.getProperty(key);
        }

        if (propertiesEnv != null) {
            return propertiesEnv.getProperty(key);
        }

        return null;
    }

    /**
     * 获取配置文件值，如果没有获取默认值
     * @param key 获取key
     * @param defaultValue 获取默认值
     * @return 返回值
     */
    public static String getProperty(String key, String defaultValue) {
        if (null == properties) {
            loadProps();
        }

        if (properties.containsKey(key)) {
            return properties.getProperty(key, defaultValue);
        }

        if (propertiesEnv != null) {
            return propertiesEnv.getProperty(key);
        }

        return null;
    }
}
