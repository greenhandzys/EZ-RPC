package com.zys.ezrpc;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.zys.ezrpc.config.RegistryConfig;
import com.zys.ezrpc.config.RpcConfig;
import com.zys.ezrpc.constant.RpcConstant;
import com.zys.ezrpc.register.Registry;
import com.zys.ezrpc.register.RegistryFactory;
import com.zys.ezrpc.serializer.SerializerKeys;
import com.zys.ezrpc.utils.ConfigUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc
 * @Author: ZYS
 * @CreateTime: 2024-07-18  16:07
 */
@Slf4j
public class RpcApplication {

    private static volatile RpcConfig rpcConfig;

    private static volatile RegistryConfig rpcRegistryConfig;
//
//    public static void init(RpcConfig newRpcConfig) {
//        // 加载配置文件
//        rpcConfig = newRpcConfig;
//        rpcRegistryConfig = ConfigUtils.loadConfig(RegistryConfig.class, RpcConstant.DEFAULT_REGISTRY_CONFIG_PREFIX);
//        if (rpcRegistryConfig == null) {
//            rpcConfig.setRegistryConfig(new RegistryConfig());
//        }
//        rpcConfig.setRegistryConfig(rpcRegistryConfig);
//        log.info("RpcApplication init success: {}", rpcConfig.toString());
//        //注册中心初始化
//        Registry registry = RegistryFactory.getInstance(rpcRegistryConfig.getRegistry());
//        registry.init(rpcRegistryConfig);
//        log.info("Registry init success: {}", rpcRegistryConfig);
//        Runtime.getRuntime().addShutdownHook(new Thread(registry::destroy));
//    }
//
//    public static void init() {
//        RpcConfig newRpcConfig;
//        try {
//            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_RPC_CONFIG_PREFIX);
//        } catch (Exception e) {
//            log.error("RpcApplication init error: {}", e.getMessage());
//            log.info("RpcApplication default init");
//            newRpcConfig = new RpcConfig();
//        }
//        init(newRpcConfig);
//    }
//
//    /**
//     * 带环境的初始化（后缀）
//     *
//     * @param environment 环境后缀
//     */
//    public static void init(String environment) {
//        RpcConfig newRpcConfig;
//        try {
//            if (StrUtil.isEmpty(environment)) {
//                init();
//            }
//            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_RPC_CONFIG_PREFIX, environment);
//            rpcRegistryConfig = ConfigUtils.loadConfig(RegistryConfig.class, RpcConstant.DEFAULT_REGISTRY_CONFIG_PREFIX, environment);
//            if (rpcRegistryConfig != null) {
//                newRpcConfig.setRegistryConfig(rpcRegistryConfig);
//            }
//        } catch (Exception e) {
//            log.error("RpcApplication init error: {}", e.getMessage());
//            log.info("RpcApplication default init");
//            newRpcConfig = new RpcConfig();
//        }
//        init(newRpcConfig);
//    }
//
//    public static RpcConfig getRpcConfig() {
//        if (rpcConfig == null) {
//            synchronized (RpcApplication.class) {
//                if (rpcConfig == null) {
//                    init();
//                }
//            }
//        }
//        return rpcConfig;
//    }

    /**
     * 框架初始化，支持传入自定义配置
     *
     * @param newRpcConfig
     */
    public static void init(RpcConfig newRpcConfig) {
        rpcConfig = newRpcConfig;
        log.info("rpc init, config = {}", newRpcConfig.toString());
        rpcRegistryConfig = ConfigUtils.loadConfig(RegistryConfig.class, RpcConstant.DEFAULT_REGISTRY_CONFIG_PREFIX);
        if (rpcRegistryConfig == null) {
            rpcConfig.setRegistryConfig(new RegistryConfig());
        }
        rpcConfig.setRegistryConfig(rpcRegistryConfig);
        log.info("RpcApplication init success: {}", rpcConfig.toString());
        // 注册中心初始化
        Registry registry = RegistryFactory.getInstance(rpcRegistryConfig.getRegistry());
        registry.init(rpcRegistryConfig);
        log.info("registry init, config = {}", rpcRegistryConfig);

        // 创建并注册 Shutdown Hook，JVM 退出时执行操作
        Runtime.getRuntime().addShutdownHook(new Thread(registry::destroy));
    }

    /**
     * 初始化
     */
    public static void init() {
        RpcConfig newRpcConfig;
        try {
            newRpcConfig = ConfigUtils.loadConfig(RpcConfig.class, RpcConstant.DEFAULT_RPC_CONFIG_PREFIX);
        } catch (Exception e) {
            // 配置加载失败，使用默认值
            newRpcConfig = new RpcConfig();
        }
        init(newRpcConfig);
    }


    /**
     * 获取配置
     *
     * @return
     */
    public static RpcConfig getRpcConfig() {
        if (rpcConfig == null) {
            synchronized (RpcApplication.class) {
                if (rpcConfig == null) {
                    init();
                }
            }
        }
        return rpcConfig;
    }
}
