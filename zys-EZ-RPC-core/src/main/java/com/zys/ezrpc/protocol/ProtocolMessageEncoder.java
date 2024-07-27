package com.zys.ezrpc.protocol;


import com.zys.ezrpc.serializer.Serializer;
import com.zys.ezrpc.serializer.SerializerFactory;
import io.vertx.core.buffer.Buffer;

/**
 * @BelongsProject: EZ-RPC
 * @BelongsPackage: com.zys.ezrpc.protocol
 * @Author: ZYS
 * @CreateTime: 2024-07-23  21:32
 */
public class ProtocolMessageEncoder {
    public static Buffer encode(ProtocolMessage<?> protocolMessage) throws Exception {
        if (protocolMessage == null || protocolMessage.getHeader() == null) {
            return Buffer.buffer();
        }

        ProtocolMessage.Header header = protocolMessage.getHeader();

        //依次向缓冲区写入数据(magic,version,serializer,type,status,requestId,bodyLength)
        Buffer buffer = Buffer.buffer();
        buffer.appendByte(header.getMagic());
        buffer.appendByte(header.getVersion());
        buffer.appendByte(header.getSerializer());
        buffer.appendByte(header.getType());
        buffer.appendByte(header.getStatus());
        buffer.appendLong(header.getRequestId());

        //获取序列化器
        ProtocolMessageSerializerEnum serializerEnum = ProtocolMessageSerializerEnum.getEnumByKey(header.getSerializer());
        if (serializerEnum == null) {
            throw new RuntimeException("不存在序列化器:" + header.getSerializer() + "！");
        }
        Serializer serializer = SerializerFactory.getInstance(serializerEnum.getValue());

        byte[] bodyBytes = serializer.serialize(protocolMessage.getBody());
        buffer.appendInt(bodyBytes.length);
        buffer.appendBytes(bodyBytes);

        return buffer;
    }
}
