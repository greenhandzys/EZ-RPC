package com.zys.ezrpc.server.tcp;

import com.zys.ezrpc.server.HttpServer;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;

import java.util.Arrays;

/**
 * Vertx TCP 服务器
 *
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.server.tcp
 * @Author: ZYS
 * @CreateTime: 2024-07-22  20:53
 */
public class VertxTcpServer implements HttpServer {

    private byte[] handleRequest(byte[] request) {
        return Buffer.buffer("Hello Client!" + Arrays.toString(request)).getBytes();
    }

    @Override
    public void doStart(int port) {
        Vertx vertx = Vertx.vertx();

        NetServer server = vertx.createNetServer();

        server.connectHandler(new TcpServerHandler());

        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("TCP Server start success!");
            } else {
                System.out.println("TCP Server start fail!Case:" + result.cause().getMessage());
            }
        });

    }

//    public static void main(String[] args) {
//        new VertxTcpServer().doStart(8888);
//    }
}
