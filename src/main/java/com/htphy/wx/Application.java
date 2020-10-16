package com.htphy.wx;

import com.htphy.wx.net.netty.dev.UdpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
    UdpServer.start();
  }
}
