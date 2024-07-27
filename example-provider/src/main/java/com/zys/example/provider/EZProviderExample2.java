package com.zys.example.provider;

import com.zys.example.common.service.UserService;
import com.zys.example.provider.service.UserServiceImpl;
import com.zys.ezrpc.RpcApplication;
import com.zys.ezrpc.config.RegistryConfig;
import com.zys.ezrpc.config.RpcConfig;
import com.zys.ezrpc.model.ServiceMetaInfo;
import com.zys.ezrpc.register.LocalRegistry;
import com.zys.ezrpc.register.Registry;
import com.zys.ezrpc.register.RegistryFactory;
import com.zys.ezrpc.server.tcp.VertxTcpServer;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.example.provider
 * @Author: ZYS
 * @CreateTime: 2024-07-20  17:47
 */
public class EZProviderExample2 {
    public static void main(String[] args) throws Exception {
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        // 框架初始化
        RpcApplication.init();

        String userServiceName = UserService.class.getName();

        RpcConfig rpcConfig = RpcApplication.getRpcConfig();

        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());

        ServiceMetaInfo serviceMetaInfo = ServiceMetaInfo.builder()
                .serviceName(userServiceName)
                .serviceHost(rpcConfig.getServerHost())
                .servicePort(rpcConfig.getServerPort())
                .serviceVersion(rpcConfig.getVersion())
                .build();

        registry.register(serviceMetaInfo);

        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
