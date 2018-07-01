package com.mmlogs.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Class TcpServer
 *
 * @author Gene.yang
 * @date 2018/06/30
 */
public class TcpServer extends Thread {

    @Override
    public void run() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(256);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);

            bootstrap.channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();

                    pipeline.addLast("idleStateHandler", new IdleStateHandler(1, 2, 3, TimeUnit.SECONDS));
                    pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                    pipeline.addLast("decoder", new TcpDataDecoder());
                    pipeline.addLast("fileLength", new LengthFieldPrepender(4));
                    pipeline.addLast("encoder", new TcpDataEncoder());

                    pipeline.addLast("handler", new TcpServerHandler());
                }
            });

            // 绑定端口
            ChannelFuture future = bootstrap.bind(8080).sync();
            if (future.isSuccess()) {
                System.out.println("服务启动成功 port: " + 8080);
            }

            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("服务器启动失败" + e.getMessage());
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
