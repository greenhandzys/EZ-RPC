package com.zys.ezrpc.server;

import io.vertx.core.Vertx;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.server
 * @Author: ZYS
 * @CreateTime: 2024-07-18  10:01
 */
public class VertxHttpServer implements HttpServer{
    @Override
    public void doStart(int port) {
        Vertx vertx = Vertx.vertx();

        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        server.requestHandler(new HttpServerHandler());

        server.listen(port,result -> {
            if(result.succeeded()){
                System.out.println("server start success");
                System.out.println("server port:"+result.result().actualPort());
            }else{
                System.out.println("server start fail");
                System.out.println("fail:"+result.cause());
            }
        });

    }
}
