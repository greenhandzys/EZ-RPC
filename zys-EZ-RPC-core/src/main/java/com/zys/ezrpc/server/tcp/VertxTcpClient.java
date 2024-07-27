package com.zys.ezrpc.server.tcp;

import cn.hutool.core.util.IdUtil;
import com.zys.ezrpc.RpcApplication;
import com.zys.ezrpc.model.RpcRequest;
import com.zys.ezrpc.model.RpcResponse;
import com.zys.ezrpc.model.ServiceMetaInfo;
import com.zys.ezrpc.protocol.*;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Vertx TCP 请求客户端
 *
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.server.tcp
 * @Author: ZYS
 * @CreateTime: 2024-07-22  21:01
 */
public class VertxTcpClient {

    /**
     * 发送请求
     *
     * @param rpcRequest      RPC 请求
     * @param serviceMetaInfo RPC 服务元数据
     * @return RPC 响应
     */
    public static RpcResponse doRequest(RpcRequest rpcRequest, ServiceMetaInfo serviceMetaInfo) throws InterruptedException, ExecutionException {
        // 发送 TCP 请求
        Vertx vertx = Vertx.vertx();
        NetClient netClient = vertx.createNetClient();
        CompletableFuture<RpcResponse> responseFuture = new CompletableFuture<>();
        netClient.connect(serviceMetaInfo.getServicePort(), serviceMetaInfo.getServiceHost(),
                result -> {
                    if (!result.succeeded()) {
                        System.err.println("Failed to connect to TCP server");
                        return;
                    }
                    NetSocket socket = result.result();
                    // 发送数据
                    // 构造消息
                    ProtocolMessage<RpcRequest> protocolMessage = new ProtocolMessage<>();
                    ProtocolMessage.Header header = new ProtocolMessage.Header();
                    header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
                    header.setVersion(ProtocolConstant.PROTOCOL_VERSION);
                    header.setSerializer((byte) Objects.requireNonNull(ProtocolMessageSerializerEnum.getEnumByValue(RpcApplication.getRpcConfig().getSerialize())).getKey());
                    header.setType((byte) ProtocolMessageTypeEnum.REQUEST.getKey());
                    // 生成全局请求 ID
                    header.setRequestId(IdUtil.getSnowflakeNextId());
                    protocolMessage.setHeader(header);
                    protocolMessage.setBody(rpcRequest);

                    // 编码请求
                    try {
                        Buffer encodeBuffer = ProtocolMessageEncoder.encode(protocolMessage);
                        socket.write(encodeBuffer);
                    } catch (IOException e) {
                        throw new RuntimeException("协议消息编码错误");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

//                    // 接收响应
                    TcpBufferHandlerWrapper bufferHandlerWrapper = new TcpBufferHandlerWrapper(
                            buffer -> {
                                try {
                                    ProtocolMessage<RpcResponse> rpcResponseProtocolMessage =
                                            (ProtocolMessage<RpcResponse>) ProtocolMessageDecoder.decode(buffer);
                                    responseFuture.complete(rpcResponseProtocolMessage.getBody());
                                } catch (IOException e) {
                                    throw new RuntimeException("协议消息解码错误");
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    );
                    socket.handler(bufferHandlerWrapper);

//                    socket.handler(buffer -> {
//                        try {
//                            ProtocolMessage<RpcResponse> rpcResponseProtocolMessage =
//                                    (ProtocolMessage<RpcResponse>) ProtocolMessageDecoder.decode(buffer);
//                            responseFuture.complete(rpcResponseProtocolMessage.getBody());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            throw new RuntimeException("协议消息解码错误");
//                        }
//                    });

                });

        RpcResponse rpcResponse = responseFuture.get();
        //关闭连接
        netClient.close();
        return rpcResponse;
    }


}
