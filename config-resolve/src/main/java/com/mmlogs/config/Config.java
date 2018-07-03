package com.mmlogs.config;

import com.mmlogs.target.Value;
import com.mmlogs.util.PropertyUtil;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * Class Config
 *
 * @author Gene.yang
 * @date 2018/07/02
 */
@Getter
public class Config {
    private final Logger logger = LoggerFactory.getLogger(Config.class);

    @Value("server.run.env")
    private String env;
    @Value("app.name")
    private String appName;
    @Value("tcp.server.port")
    private int tcpServerPort;




    @Value("redis.keys.imMsgDownQueueKey")
    private String redisImMsgDownQueueKey;
    @Value("redis.keys.imMsgUpQueueKey")
    private String redisImMsgUpQueueKey;
    @Value("redis.keys.imOnlineNumber")
    private String redisImOnlineNumber;













    /**
     * 获取实例对象
     * @return config实例
     */
    public static Config getInstance() {
        return ConfigHolder.INSTANCE;
    }

    /**
     * 初始化操作
     * 根据注解读取配置文件并对相应属性进行赋值
     */
    private Config() {
        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            // 没有使用value注解的属性，直接跳过
            if (!field.isAnnotationPresent(Value.class)) {
                continue;
            }

            try {
                // 获取注解值
                String configField = field.getAnnotation(Value.class).value();
                String configValue = PropertyUtil.getProperty(configField);
                if (configValue == null) {
                    continue;
                }

                if (field.getType().equals(String.class)) {
                    field.set(this, configValue);
                } else if (field.getType() == Integer.class || field.getType() == int.class) {
                    field.set(this, Integer.parseInt(configValue));
                } else if (field.getType() == Long.class || field.getType() == long.class) {
                    field.set(this, Long.parseLong(configValue));
                } else if (field.getType() == Boolean.class || field.getType() == boolean.class) {
                    field.set(this, Boolean.parseBoolean(configValue));
                } else if (field.getType() == Double.class || field.getType() == double.class) {
                    field.set(this, Double.parseDouble(configValue));
                } else if (field.getType() == Float.class || field.getType() == float.class) {
                    field.set(this, Float.parseFloat(configValue));
                } else {
                    logger.warn("不支持该字段类型赋值 field: " + field);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("配置文件赋值异常", e);
            }
        }
    }
    private static class ConfigHolder {
        private static final Config INSTANCE = new Config();
    }
}
