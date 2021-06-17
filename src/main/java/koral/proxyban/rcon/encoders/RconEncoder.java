package koral.proxyban.rcon.encoders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import koral.proxyban.rcon.packets.RconPacket;

import java.nio.charset.StandardCharsets;

public class RconEncoder extends MessageToByteEncoder<RconPacket>{
    @Override
    protected void encode(ChannelHandlerContext ctx,RconPacket packet,ByteBuf out) throws Exception{
                out.writeIntLE(2 * 4 + packet.getMessage().getBytes(StandardCharsets.UTF_8).length + 2);
                out.writeIntLE(packet.getRequestId());
                out.writeIntLE(packet.getType().getValue());
                out.writeBytes(packet.getMessage().getBytes(StandardCharsets.ISO_8859_1));
                out.writeByte(0);
                out.writeByte(0);
    }
}
