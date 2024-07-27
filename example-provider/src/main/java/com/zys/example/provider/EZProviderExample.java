package com.zys.example.provider;

import com.zys.example.common.service.UserService;
import com.zys.example.provider.service.UserServiceImpl;
import com.zys.ezrpc.RpcApplication;
import com.zys.ezrpc.config.RpcConfig;
import com.zys.ezrpc.register.LocalRegistry;
import com.zys.ezrpc.server.VertxHttpServer;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.example.provider
 * @Author: ZYS
 * @CreateTime: 2024-07-18  09:51
 */
public class EZProviderExample {
    public static void main(String[] args) {
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        VertxHttpServer vertxHttpServer = new VertxHttpServer();
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        vertxHttpServer.doStart(rpcConfig.getServerPort());
    }
}
