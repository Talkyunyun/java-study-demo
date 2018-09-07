package com.mmlogs.tcp;

import com.mmlogs.server.TcpDataDecoder;
import com.mmlogs.server.TcpDataEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * Class ImClient
 *
 * @author Gene.yang
 * @date 2018/07/04
 */
public class ImClient {
    private static String HOST = "127.0.0.1";
    private static int PORT = 8080;

    public static ChannelFuture FUTURE;

    public void connServer(Class<?> clazz) {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            // 心跳检测
                            pipeline.addLast("idleStateHandler", new IdleStateHandler(3, 3, 3, TimeUnit.SECONDS));

                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            pipeline.addLast("dataDecoder", new TcpDataDecoder());
                            pipeline.addLast("dataLength", new LengthFieldPrepender(4));
                            pipeline.addLast("dataEncoder", new TcpDataEncoder());
                            pipeline.addLast("dataHandler", new ImClientHandler());
                        }
                    });

            FUTURE = bootstrap.connect(HOST, PORT).sync();
            if (FUTURE.isSuccess()) {
                Method method = clazz.getMethod("handShake");
                method.invoke(clazz, null);
            }

            FUTURE.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
