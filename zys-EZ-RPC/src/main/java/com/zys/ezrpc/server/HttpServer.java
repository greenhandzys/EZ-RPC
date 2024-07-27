package com.zys.ezrpc.server;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.server
 * @Author: ZYS
 * @CreateTime: 2024-07-18  10:00
 */
public interface HttpServer {
    /**
     * 服务器启动
     * @param port 端口
     */
    void doStart(int port);
}
