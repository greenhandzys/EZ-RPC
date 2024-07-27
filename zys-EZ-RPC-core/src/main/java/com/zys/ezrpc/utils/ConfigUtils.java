package com.zys.ezrpc.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.utils
 * @Author: ZYS
 * @CreateTime: 2024-07-18  15:58
 */
public class ConfigUtils {


    /**
     * 加载配置对象
     *
     * @param clazz
     * @param prefix
     * @param <T>
     * @return
     */
    public static <T> T loadConfig(Class<T> clazz, String prefix) {
        return loadConfig(clazz, prefix, "");
    }

    /**
     * 加载配置对象带环境
     *
     * @param clazz
     * @param prefix
     * @param environment
     * @param <T>
     * @return
     */
    public static <T> T loadConfig(Class<T> clazz, String prefix, String environment) {
        StringBuilder configFileBuilder = new StringBuilder("application");

        if (StrUtil.isNotBlank(environment)) {
            configFileBuilder.append("-").append(environment);
        }

        configFileBuilder.append(".properties");

        Props props = new Props(configFileBuilder.toString());

        return props.toBean(clazz, prefix);
    }

}
