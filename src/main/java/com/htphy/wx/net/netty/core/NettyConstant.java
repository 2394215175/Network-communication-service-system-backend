package com.htphy.wx.net.netty.core;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Netty 相关常量
 * @author lw
 */
public class NettyConstant {

    public static EventLoopGroup bossServerGroup = new NioEventLoopGroup(1);
    public static EventLoopGroup workerServerGroup = new NioEventLoopGroup();
    public static EventLoopGroup workerGroup = new NioEventLoopGroup();

}
