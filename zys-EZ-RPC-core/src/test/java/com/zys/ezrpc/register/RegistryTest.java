package com.zys.ezrpc.register;

import com.zys.ezrpc.config.RegistryConfig;
import com.zys.ezrpc.model.ServiceMetaInfo;
import org.junit.Before;
import org.junit.Test;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.register
 * @Author: ZYS
 * @CreateTime: 2024-07-20  16:37
 */
public class RegistryTest {
    private final Registry registry = new ZookeeperRegistry();
    @Before
    public void init(){
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("http://132.232.176.241:2181");
        registry.init(registryConfig);
    }

    @Test
    public void register() throws Exception {
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo().builder()
                .serviceName("HelloService")
                .serviceVersion("1.0.0")
                .serviceHost("localhost")
                .servicePort(8080)
                .build();
        registry.register(serviceMetaInfo);

        serviceMetaInfo = new ServiceMetaInfo().builder()
                .serviceName("HelloService")
                .serviceVersion("1.0.0")
                .serviceHost("localhost")
                .servicePort(1001)
                .build();
        registry.register(serviceMetaInfo);

        serviceMetaInfo = new ServiceMetaInfo().builder()
                .serviceName("HelloService")
                .serviceVersion("2.0.0")
                .serviceHost("localhost")
                .servicePort(1000)
                .build();
        registry.register(serviceMetaInfo);
    }

}
