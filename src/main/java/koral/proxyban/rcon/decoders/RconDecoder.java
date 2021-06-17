package koral.proxyban.rcon.decoders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import koral.proxyban.rcon.packets.PacketType;
import koral.proxyban.rcon.packets.RconPacket;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class RconDecoder extends ByteToMessageDecoder{

    private Integer length = null;
    @Override
    protected void decode(ChannelHandlerContext ctx,ByteBuf in,List<Object> out) throws Exception{

        if(in.readableBytes() > 4){
           if(length == null) length = in.readIntLE();
           if(in.readableBytes() >= length) {
               int requestId = in.readIntLE();
               if(requestId == -1) {
                   System.out.println("Udalo sie polaczyc z serwerem, ale haslo sie nie zgadza");
                   ctx.close();
                   return;
               }
               int type = in.readIntLE();
               if(type == 0) return;
               byte[] bytes = new byte[in.readableBytes() - 2];
               in.readBytes(bytes);
               in.skipBytes(2);
               String message = new String(bytes,StandardCharsets.ISO_8859_1);
               out.add(new RconPacket(requestId,PacketType.fromInt(type),message));
               length = null;
           }
        }
    }
}
