package koral.proxyban.rcon.rconserver;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import koral.proxyban.ProxyBan;
import koral.proxyban.rcon.packets.PacketType;
import koral.proxyban.rcon.packets.RconPacket;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.HashSet;

public class RconServerChannelHandler extends SimpleChannelInboundHandler<RconPacket>{

    public static Channel channel;
    public static HashSet<Channel> authenticated = new HashSet<>();

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause) throws Exception{
        if(authenticated.contains(ctx.channel())) authenticated.remove(ctx.channel());
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception{
        System.out.println(ctx.channel().remoteAddress().toString() + " connected to RCON");
        channel = ctx.channel();

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,RconPacket msg) throws Exception{
        if(msg.getType() == PacketType.HANDSHAKE) {
                if ( msg.getMessage().equals(RconServer.password) ) {
                    ctx.writeAndFlush(new RconPacket(msg.getRequestId(),PacketType.COMMAND,""));
                    authenticated.add(ctx.channel());
                } else {
                    ctx.writeAndFlush(new RconPacket(-1,PacketType.COMMAND,""));
                    ctx.close();
                }

        }
        if(msg.getType() == PacketType.COMMAND){
            if(authenticated.contains(ctx.channel())) {
                ProxyBan.getProxyBan().getProxy().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(),msg.getMessage());
                ProxyBan.getProxyBan().getProxy().getConsole().sendMessage(new TextComponent("RCON used command: " + msg.getMessage()));
            }
            else
                ctx.close();
        }
    }
}
