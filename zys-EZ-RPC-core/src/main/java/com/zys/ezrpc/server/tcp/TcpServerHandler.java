package com.zys.ezrpc.server.tcp;

import com.zys.ezrpc.model.RpcRequest;
import com.zys.ezrpc.model.RpcResponse;
import com.zys.ezrpc.protocol.ProtocolMessage;
import com.zys.ezrpc.protocol.ProtocolMessageDecoder;
import com.zys.ezrpc.protocol.ProtocolMessageEncoder;
import com.zys.ezrpc.protocol.ProtocolMessageTypeEnum;
import com.zys.ezrpc.register.LocalRegistry;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.lang.reflect.Method;


/**
 * TCP 请求处理器
 *
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.server.tcp
 * @Author: ZYS
 * @CreateTime: 2024-07-23  21:54
 */
public class TcpServerHandler implements Handler<NetSocket> {
    @Override
    public void handle(NetSocket socket) {
        TcpBufferHandlerWrapper tcpBufferHandlerWrapper = new TcpBufferHandlerWrapper(buffer -> {
            ProtocolMessage<RpcRequest> protocolMessage = null;
            try {
                protocolMessage = (ProtocolMessage<RpcRequest>) ProtocolMessageDecoder.decode(buffer);
            } catch (Exception e) {
                throw new RuntimeException("消息协议码解码失败");
            }

            RpcRequest rpcRequest = protocolMessage.getBody();

            // 构造响应结果对象
            RpcResponse rpcResponse = new RpcResponse();

            try {
                //获取被调用的服务实现类，并通过反射进行调用
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(implClass.newInstance(), rpcRequest.getParameters());
                // 封装返回结果
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("success");
            } catch (Exception e) {
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }

            // 发送响应，编码
            ProtocolMessage.Header header = protocolMessage.getHeader();
            header.setType((byte) ProtocolMessageTypeEnum.RESPONSE.getKey());
            ProtocolMessage<RpcResponse> responseProtocolMessage = new ProtocolMessage<>(header, rpcResponse);
            try {
                Buffer encode = ProtocolMessageEncoder.encode(responseProtocolMessage);
                socket.write(encode);
            } catch (IOException e) {
                throw new RuntimeException("协议消息编码错误");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        socket.handler(tcpBufferHandlerWrapper);
    }
}
