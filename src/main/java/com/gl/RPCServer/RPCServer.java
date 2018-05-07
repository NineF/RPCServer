package com.gl.RPCServer;

import com.gl.handler.ServiceChannelHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class RPCServer {
	public static void main(String[] args) {

		new RPCServer().run();

	}

	public void run() {

		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		System.out.println("start.......");

		try {

			ServerBootstrap b = new ServerBootstrap();

			b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<Channel>() {

						@Override
						protected void initChannel(Channel arg0) throws Exception {
							arg0.pipeline().addLast("framer",
									new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
							arg0.pipeline().addLast("decoder", new StringDecoder());
							arg0.pipeline().addLast("encoder", new StringEncoder());
							arg0.pipeline().addLast("handler", new ServiceChannelHandler());
						}
					});

			ChannelFuture f = b.bind(8081).sync();
			System.out.println("服务端启动成功...");
			f.channel().closeFuture().sync();

		} catch (Exception e) {
		} finally {

			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();

		}

	}

}
