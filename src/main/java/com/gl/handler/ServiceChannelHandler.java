package com.gl.handler;

import java.net.InetAddress;
import java.util.Date;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServiceChannelHandler extends SimpleChannelInboundHandler<String> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		
		System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
		ctx.writeAndFlush("客户端" + InetAddress.getLocalHost().getHostName() + "成功与服务端建立连接！  \n");
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		// 收到消息直接打印输出
		System.out.println("服务端接受的消息 : " + msg);
		if ("quit".equals(msg)) {// 服务端断开的条件
			ctx.close();
		}
		Date date = new Date();
		// 返回客户端消息
		ctx.writeAndFlush(date + "\n");
	}

	

}
