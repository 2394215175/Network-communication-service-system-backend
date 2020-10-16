package com.htphy.wx.net.netty.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.htphy.wx.common.util.Md5.Md5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

@ServerEndpoint("/server/{userId}/{sign}")
@Component
public class WebSocketServer {

    static Log log=LogFactory.get(WebSocketServer.class);

    /**静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。*/
    private static int onlineCount = 0;
    /**concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。*/
    private static ConcurrentHashMap<String,WebSocketServer> webSocketMap = new ConcurrentHashMap<>();
    private static List<WebSocketServer> allWebsocket=new ArrayList<>();
    /**与某个客户端的连接会话，需要通过它来给客户端发送数据*/
    private Session session;
    /**接收userId*/
    private String userId="";

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId,@PathParam("sign") String sign) throws IOException {
        this.session = session;
        this.userId=userId;
        //将userid转换为md5用于验证
        String md5Sign = Md5Util.string2MD5(userId);
        if (md5Sign.equals(sign)) {
//            if(webSocketMap.containsKey(userId)){
//                webSocketMap.remove(userId);
//                webSocketMap.put(userId,this);
//                //加入set中
//            }else{
//                webSocketMap.put(userId,this);
//                //加入set中
//                addOnlineCount();
//                //在线数加1
//            }

            if (!allWebsocket.contains(this)){
                allWebsocket.add(this);
            }
            log.info("用户连接:"+userId+",当前在线终端为:" + allWebsocket.size());
            try {
                sendMessage("连接成功");
            } catch (IOException e) {
                log.error("用户:"+userId+",网络异常!!!!!!");
            }
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
//        if(webSocketMap.containsKey(userId)){
//            webSocketMap.remove(userId);
//            //从set中删除
//            subOnlineCount();
//        }
        if (allWebsocket.contains(this)){
            allWebsocket.remove(this);
        }
        log.info("用户 "+userId+" 退出"+",当前在线用户为:" + allWebsocket.size());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("收到用户 "+userId+"消息为:"+message);
        if(message.equals("close")){
            //webSocketMap.get(userId).session.close();
            if (allWebsocket.contains(this)){
                allWebsocket.remove(this);
                session.close();
            }
            session.close();
        }
    }

    /**
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:"+this.userId+",原因:"+error.getMessage());
        error.printStackTrace();
    }
    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        //getBasicRemote()定时推送
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 主动推送给所有用户
     * @param message
     */
    public static void sendMessageAll(String message){
        allWebsocket.forEach((value)->{
            try {
                value.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 给指定用户发送自定义消息
     */
    public static void sendInfo(String message,@PathParam("userId") String userId) throws IOException {
        log.info("发送消息到:"+userId+"，报文:"+message);
        if(StringUtils.isNotBlank(userId)&&webSocketMap.containsKey(userId)){
            webSocketMap.get(userId).sendMessage(message);
        }else{
            log.error("用户"+userId+",不在线！");
        }
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}




