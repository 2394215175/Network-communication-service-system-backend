package com.htphy.wx.net.netty.core.handle;

import com.google.common.collect.Maps;
import com.htphy.wx.net.netty.dev.BytesTransform;
import com.htphy.wx.net.netty.dev.UdpServer;
import com.htphy.wx.net.netty.websocket.WebSocketServer;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.Map;

/**
 * UDP数据包数据输入处理器
 * 此处返回ack数据，并且记录每个client的远程地址，以及处理心跳包
 * <p>提取DatagramPacket中Bytebuf内容
 *
 * @author lw
 */
@Slf4j
public class DatagramPacketHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
        if (msg instanceof DatagramPacket) {
            DatagramPacket packet = (DatagramPacket) msg;
            byte[] bytes1 = new byte[21];
            packet.content().getBytes(0, bytes1);
            //心跳包不返还
            if (bytes1[8] == 0 || bytes1[8] == 1) {
                byte[] bytes = new byte[21];
                //1为成功，2为失败
                bytes[0] = 1;
                //错误原因,不超过20个字节
                String reason = "";
                if (bytes[0] == 1) {
                    reason = "success";
                } else {
                    reason = "fail";
                }
                //组装数据
                System.arraycopy(reason.getBytes(), 0, bytes, 1, reason.getBytes().length);
                DatagramPacket data = new DatagramPacket(Unpooled.copiedBuffer(bytes), packet.sender());
                //向客户端返回ACK数据
                ctx.writeAndFlush(data);
            } else {
                //心跳包处理
                Map<Integer, String> clientsRemoteAddress = UdpServer.getClientsRemoteAddress();
                //记录client远程地址
                byte[] bytesId = new byte[4];
                bytesId[0] = bytes1[0];
                bytesId[1] = bytes1[1];
                bytesId[2] = bytes1[2];
                bytesId[3] = bytes1[3];
                int terminalid = BytesTransform.bytes2int(bytesId);
                if (clientsRemoteAddress.get(terminalid) == null) {
                    clientsRemoteAddress.put(terminalid, packet.sender().toString());
                } else {
                    clientsRemoteAddress.replace(terminalid, packet.sender().toString());
                }

                Map<Integer, Integer> clients = UdpServer.getClients();
                //心跳包，收到心跳包次数清零。
                log.debug("收到client" + terminalid + "心跳");
                if (clients.get(terminalid) == null) {
                    clients.put(terminalid, 0);
                    WebSocketServer.sendMessageAll("{\"terminalid\":"+terminalid+",\"online\":true,\"type\":3}");
                }
                if (clients.get(terminalid) != 0) {
                    clients.replace(terminalid, 0);
                }
            }
            //提取 Bytebuf 转发到下一处理器
            ctx.fireChannelRead(packet.content());

        }
    }

}
