package com.htphy.wx.common.util.netutils;
import com.htphy.wx.module.dev.service.AntennaService;
import com.htphy.wx.net.netty.dev.AntennaMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public class AntennaSaveUtils {
    @Autowired
    private AntennaService Service;

    public static AntennaSaveUtils AntennaSaveUtils;

    @PostConstruct
    public void init() {
        AntennaSaveUtils = this;
        AntennaSaveUtils.Service=this.Service;
    }
    //utils工具类中使用service和mapper接口的方法例子，用"testUtils.xxx.方法" 就可以了
    public static void saveAntennaMessage(AntennaMessage model){
        AntennaSaveUtils.Service.save(model);
    }
}
