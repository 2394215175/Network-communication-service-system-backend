package com.htphy.wx.common.util.netutils;

import com.htphy.wx.module.dev.service.WeatherService;
import com.htphy.wx.net.netty.dev.WeatherMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class WeatherSaveUtils {
    @Autowired
    private WeatherService Service;

    public static WeatherSaveUtils WeatherSaveUtils;

    @PostConstruct
    public void init() {
        WeatherSaveUtils = this;
        WeatherSaveUtils.Service=this.Service;
    }
    //utils工具类中使用service和mapper接口的方法例子，用"testUtils.xxx.方法" 就可以了
    public static void saveWeatherMessage(WeatherMessage model){
        WeatherSaveUtils.Service.save(model);
    }
}
