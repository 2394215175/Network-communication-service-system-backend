package com.htphy.wx.net.netty.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.ConnectException;
import java.net.SocketAddress;

/**
 * Netty TCP 连接类
 *
 * @author lw
 */
@Slf4j
public class NettyTcpFactory {

    /**
     * 开启TCP客户端连接
     *
     * @param remoteAddress 远程地址
     * @param handlers      处理器
     * @return 启动信道
     */
    public static Channel startClient(SocketAddress remoteAddress, ChannelHandler... handlers) {
        Channel channel = null;

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(NettyConstant.workerGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(remoteAddress)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(handlers);
                    }
                });

        try {
            channel = bootstrap.connect().sync().channel();
            //监听channel的close
            channel.closeFuture().addListener((ChannelFutureListener) future -> {
                //TODO 关闭channel的回调
            });

        } catch (Exception e) {
            if (e instanceof ConnectException) {
                log.warn("连接失败: {}", e.getMessage());
            } else {
                e.printStackTrace();
            }
        }

        return channel;
    }

    /**
     * 开启本地TCP服务
     *
     * @param localPort 本地端口
     * @param handlers  处理器
     * @return 启动信道
     */
    public static Channel startServer(int localPort, ChannelHandler... handlers) {
        Channel channel = null;

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(NettyConstant.bossServerGroup, NettyConstant.workerServerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(handlers);
                    }
                });
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        // 是否启用心跳保活机机制
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

        try {
            channel = bootstrap.bind(localPort).sync().channel();
            //监听channel的close
            channel.closeFuture().addListener((ChannelFutureListener) future -> {
                //关闭channel回调
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return channel;
    }


}
