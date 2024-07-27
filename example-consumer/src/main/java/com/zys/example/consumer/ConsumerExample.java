package com.zys.example.consumer;

import com.zys.ezrpc.RpcApplication;
import com.zys.ezrpc.config.RpcConfig;
import com.zys.ezrpc.utils.ConfigUtils;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.example.consumer
 * @Author: ZYS
 * @CreateTime: 2024-07-18  16:22
 */
public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc","consumer");
        System.out.println("rpc = " + rpc);

        RpcApplication.init(rpc);
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        System.out.println("rpcConfig = " + rpcConfig);

        RpcConfig rpcConfig1 = RpcApplication.getRpcConfig();
        System.out.println("rpcConfig1 = " + rpcConfig1);
    }
}
