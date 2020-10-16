package com.htphy.wx.net.netty.core;

import com.google.common.collect.Maps;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Map;

/**
 * Netty UDP连接类
 *
 * @author lw
 */
@Slf4j
public class NettyUdpFactory {

    private static final Map<Integer, Channel> serverChannel = Maps.newConcurrentMap();

    /**
     * 开启UDP客户端连接
     *
     * @param remoteAddress 远程地址
     * @param handlers      处理器
     * @return 启动信道
     */
    public static Channel startClient(InetSocketAddress remoteAddress, ChannelHandler... handlers) {
        Channel channel = null;
        //启动器
        Bootstrap bootstrap = new Bootstrap();
        //配置启动器
        bootstrap.group(NettyConstant.workerGroup)
                .remoteAddress(remoteAddress)
                .channel(NioDatagramChannel.class)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel nioDatagramChannel) throws Exception {
                        ChannelPipeline pipeline = nioDatagramChannel.pipeline();
                        pipeline.addLast(handlers);
                    }
                });
        try {
            channel = bootstrap.connect().sync().channel();

            //监听channel的close
            channel.closeFuture().addListener((ChannelFutureListener) future -> {
                //TODO 关闭channel的回调
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return channel;

    }

    /**
     * 开启本地UDP服务
     *
     * @param localPort 本地端口
     * @param handlers  处理器
     * @return 启动信道
     */
    public static Channel startServer(int localPort, ChannelHandler... handlers) {
        Channel channel = null;
        if (serverChannel.get(localPort) != null) {
            channel = serverChannel.get(localPort);
            if (channel.isOpen()) {
                return channel;
            } else {
                serverChannel.remove(channel);
            }
        }

        //启动器
        Bootstrap bootstrap = new Bootstrap();
        //配置启动器
        bootstrap.group(NettyConstant.workerServerGroup)
                .channel(NioDatagramChannel.class)
                .handler(new ChannelInitializer<NioDatagramChannel>() {

                    @Override
                    protected void initChannel(NioDatagramChannel nioDatagramChannel) throws Exception {
                        ChannelPipeline pipeline = nioDatagramChannel.pipeline();
                        pipeline.addLast(handlers);
                    }
                });
        try {
            channel = bootstrap.bind(localPort).sync().channel();
            //监听channel的close
            Channel finalChannel = channel;
            channel.closeFuture().addListener((ChannelFutureListener) future -> {
                //TODO 关闭channel回调
                serverChannel.remove(finalChannel);
            });
            serverChannel.put(localPort, channel);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return channel;
    }

}
