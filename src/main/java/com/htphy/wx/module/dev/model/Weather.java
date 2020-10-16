package com.htphy.wx.module.dev.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

/**
 * Author : zfk
 * Data : 8:55
 * 天气数据
 */
@Setter
@Getter
public class Weather {

    @ApiModelProperty(value = "id")
    private int id;

    @ApiModelProperty(value = "温度")
    private short temperature;

    @ApiModelProperty(value = "湿度")
    private short humidity;

    @ApiModelProperty(value = "光照")
    private int illumination;

    @ApiModelProperty(value = "气压")
    private byte pressure;

    @ApiModelProperty(value = "风速")
    private short velocity;
    //风向 1:东，2:西，3:南,4:北
    @ApiModelProperty(value = "风向")
    private byte direction;

    @ApiModelProperty(value = "雨量")
    private short rainfall;

    @ApiModelProperty(value = "创建时间")
    private String datatime;
    
    @ApiModelProperty(value = "关联终端")
    private int terminalid;

    @ApiModelProperty(value = "时间线对象")
    private TimeLine timeLine;
}
