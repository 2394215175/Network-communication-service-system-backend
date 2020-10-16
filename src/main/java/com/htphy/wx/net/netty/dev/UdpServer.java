package com.htphy.wx.net.netty.dev;

import com.google.common.collect.Maps;

import com.htphy.wx.net.netty.websocket.WebSocketServer;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.htphy.wx.net.netty.core.NettyUdpFactory;
import com.htphy.wx.net.netty.core.handle.DatagramPacketHandler;

/**
 * 示例UDP服务端
 *
 * @author lw
 */
@Slf4j
public class UdpServer {
    //各个客户端未发心跳的次数
    private static Map<Integer, Integer> clients = Maps.newConcurrentMap();
    //各个客户端的远程地址
    private static Map<Integer, String> clientsRemoteAddress = Maps.newConcurrentMap();
    private static Channel serverChannel;
    //位置信息
    private static Map<Integer, double[]> position = Maps.newConcurrentMap();

    public static void start() {
        //定时器，每3秒各个客户端未发心跳包次数加1,由收到心跳包清0
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("scheduled-pool-%d").daemon(true).build());
        scheduledExecutorService.scheduleAtFixedRate(new MyScheduled(), 1, 3, TimeUnit.SECONDS);
        serverChannel = NettyUdpFactory.startServer(9090, new DatagramPacketHandler(), new MsgDecoder());
        log.debug("UDP服务器启动成功,端口为9090。");
    }

    static class MyScheduled implements Runnable {
        //判断客户端是否断开连接
        @Override
        public void run() {
            if (clients != null) {
                clients.forEach((key, value) -> {
                    if (value > 2) {
                        log.debug("客户端client：" + key + "断开连接");
                        //断开则移除
                        if (position.get(key)!=null){
                            position.remove(key);

                        }
                        if (clients.get(key)!=null){
                            clients.remove(key);
                            WebSocketServer.sendMessageAll("{\"terminalid\":"+key+",\"online\":false,\"type\":3}");
                        }
                        clientsRemoteAddress.remove(key);
                    } else {
                        log.debug("客户端client：" + key + "还在连接,远程地址为：" + clientsRemoteAddress.get(key));
                    }
                });
                //所有客户端加未发心跳次数1
                clients.replaceAll((k, v) -> v + 1);
            }
        }
    }

    public static Map<Integer, Integer> getClients() {
        return clients;
    }

    public static Map<Integer, String> getClientsRemoteAddress() {
        return clientsRemoteAddress;
    }
    public static Map<Integer, double[]> getPosition() {
        return position;
    }

    public static void stopClient(int terminalid) {
        String address = clientsRemoteAddress.get(terminalid);
        if (address != null) {
            String tmp[] = address.split("/|:");
            InetSocketAddress sender = new InetSocketAddress(tmp[1], Integer.parseInt(tmp[2]));
            String senddata = "close";
            DatagramPacket data = new DatagramPacket(Unpooled.copiedBuffer(senddata.getBytes()), sender);
            //向客户端返回ACK数据
            serverChannel.writeAndFlush(data);
            log.debug("正在关闭客户端" + terminalid);
        }
    }
}
