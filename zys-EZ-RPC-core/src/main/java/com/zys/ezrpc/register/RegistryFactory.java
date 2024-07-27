package com.zys.ezrpc.register;

import com.zys.ezrpc.spi.SpiLoader;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.register
 * @Author: ZYS
 * @CreateTime: 2024-07-20  16:00
 */
public class RegistryFactory {

    static {
        SpiLoader.load(Registry.class);
    }

    private static final Registry DEFAULT_REGISTRY = new ZookeeperRegistry();

    public static Registry getInstance(String registryType) {
        if (registryType == null || registryType.isEmpty()) {
            return DEFAULT_REGISTRY;
        }
        return SpiLoader.getInstance(Registry.class, registryType);
    }

}
