package com.ohh.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 自定义的 Handler
 *
 * @author Gary
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("ctx = " + ctx);
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端的消息是：" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端的地址是：" + ctx.channel().remoteAddress());
        System.out.println(ctx.executor() == ctx.channel().eventLoop());
        ctx.executor().submit(() -> {
            System.out.println("处理一些业务");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("业务处理完了");
            ctx.writeAndFlush(Unpooled.copiedBuffer("Good Bye，客户端", CharsetUtil.UTF_8));
        });
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello，客户端", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
