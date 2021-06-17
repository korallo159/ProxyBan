package koral.proxyban.rcon.rconclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import koral.proxyban.rcon.decoders.RconDecoder;
import koral.proxyban.rcon.encoders.RconEncoder;
import koral.proxyban.rcon.packets.PacketType;
import koral.proxyban.rcon.packets.RconPacket;

import java.util.concurrent.CompletableFuture;

public class RconClient{

    public static Channel channel;
    public static int requestId;
    public static CompletableFuture<Boolean> isAuthenticated;

    public RconClient(String host,int port, String password) throws InterruptedException{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)//
                    .handler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        public void initChannel(SocketChannel ch){
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new RconEncoder(), new RconDecoder(), new RconClientChannelHandler());


                        }
                    });

            ChannelFuture f = b.connect(host,port).sync();
            f.addListener(( ChannelFutureListener ) channelFuture -> {
                if ( channelFuture.isSuccess() ) {

                    channel = f.sync().channel();
                    channel.writeAndFlush(new RconPacket(0, PacketType.HANDSHAKE, password));

                }

            });
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    }



}

