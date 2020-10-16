package com.htphy.wx.net.netty.dev;

import com.google.common.collect.Maps;
import com.htphy.wx.common.util.netutils.AntennaSaveUtils;
import com.htphy.wx.common.util.netutils.WeatherSaveUtils;
import com.htphy.wx.net.netty.websocket.WebSocketServer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Slf4j
public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Map<Integer, double[]> position=UdpServer.getPosition();
        int terminalid = byteBuf.readInt();
        short identification =byteBuf.readShort();
        byte srcaddress=byteBuf.readByte();
        byte desaddress=byteBuf.readByte();
        byte type = byteBuf.readByte();
        byte[] rewords = new byte[2];
        rewords[0] = byteBuf.readByte();
        rewords[1] = byteBuf.readByte();
        log.debug("终端ID为："+terminalid+" 数据包标识：" +identification+" 信源地址：" + srcaddress+" 信宿地址：" +desaddress+" 数据类别：" + type+" 保留字" + rewords[0] + rewords[1]);
        //天气数据
        if (type == 0) {
            int len = byteBuf.readInt();
            byte[] bytes = new byte[len];
            byteBuf.readBytes(bytes);
            WeatherMessage msg = new WeatherMessage();
            msg.setTerminalid(terminalid);
            Date dNow = new Date( );
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            msg.setDate(ft.format(dNow));
            msg.fromToBytes(bytes);
            log.debug("接收时间为："+msg.getDate()+"正文长度为：" + len+" 正文数据："+msg.toJson());
            //保存数据库
            WeatherSaveUtils.saveWeatherMessage(msg);
            //推送数据
            WebSocketServer.sendMessageAll(msg.toJson());
        }
        //天线数据
        if (type == 1) {
            int len = byteBuf.readInt();
            byte[] bytes = new byte[len];
            byteBuf.readBytes(bytes);
            AntennaMessage msg = new AntennaMessage();
            msg.setTerminalid(terminalid);
            //将字节放入成员变量
            msg.fromToBytes(bytes);
            Date dNow = new Date( );
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            msg.setDate(ft.format(dNow));
            log.debug("接收时间为："+msg.getDate()+"正文长度为：" + len+" 正文数据："+msg.toJson());
            //保存数据库
            AntennaSaveUtils.saveAntennaMessage(msg);
            //推送数据
            WebSocketServer.sendMessageAll(msg.toJson());
        }
        //位置数据
        if (type == 2) {
            int len = byteBuf.readInt();
            byte[] bytes = new byte[len];
            byteBuf.readBytes(bytes);
            PositionMessage msg = new PositionMessage();
            msg.setTerminalid(terminalid);
            //将字节放入成员变量
            msg.fromToBytes(bytes);
            double[] tmp=new double[2];
            tmp[0]= msg.getLng();
            tmp[1]=msg.getLat();
            if (position.get(terminalid) == null) {
                position.put(terminalid, tmp);
            }else {
                position.replace(terminalid,tmp);
            }
            Date dNow = new Date( );
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            log.debug("接收时间为："+ft.format(dNow)+"经度为：" + msg.getLng()+" 纬度："+msg.getLat());
        }
        //list.add(str);给下一个handler
    }
}