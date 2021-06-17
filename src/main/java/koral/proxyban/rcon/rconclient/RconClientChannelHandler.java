package koral.proxyban.rcon.rconclient;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import koral.proxyban.rcon.packets.PacketType;
import koral.proxyban.rcon.packets.RconPacket;

public class RconClientChannelHandler extends SimpleChannelInboundHandler<RconPacket>{

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,RconPacket msg) throws Exception{

         switch(msg.getType()){
             case COMMAND:
              if(msg.getMessage().isEmpty()) {
                  System.out.println("auth succesfull");
                  RconClient.requestId = msg.getRequestId() + 1;
                  RconClient.channel.writeAndFlush(new RconPacket(RconClient.requestId, PacketType.COMMAND, "proxyunban koralik"));
              }

            }
    }
}
