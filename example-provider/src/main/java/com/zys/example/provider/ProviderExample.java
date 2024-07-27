package com.zys.example.provider;

import com.zys.ezrpc.RpcApplication;
import com.zys.ezrpc.config.RegistryConfig;
import com.zys.ezrpc.config.RpcConfig;
import com.zys.ezrpc.utils.ConfigUtils;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.example.provider.service
 * @Author: ZYS
 * @CreateTime: 2024-07-18  16:33
 */
public class ProviderExample {
    public static void main(String[] args) {
        RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class, "rpc", "provider");

        RegistryConfig registryConfig = ConfigUtils.loadConfig(RegistryConfig.class, "reg", "provider");
        rpcConfig.setRegistryConfig(registryConfig);

        RpcApplication.init(rpcConfig);

        RpcConfig config = RpcApplication.getRpcConfig();

        System.out.println("config = " + config);
    }
}
