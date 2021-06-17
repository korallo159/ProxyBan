package koral.proxyban.rcon.rconserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import koral.proxyban.ProxyBan;
import koral.proxyban.rcon.decoders.RconDecoder;
import koral.proxyban.rcon.encoders.RconEncoder;

public class RconServer{
    public static String password;
    public RconServer(int port, String password){
        this.password = password;
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)//
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new RconEncoder(), new RconDecoder(), new RconServerChannelHandler());
                        }
                    });


            ChannelFuture f = b.bind(port).sync();
            System.out.println("Bungee rcon server exposed on port + " + ProxyBan.config.getInt("rconport") + " successfully.");


            f.channel().closeFuture().sync();
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
