package com.htphy.wx.module.dev.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Author : zfk
 * Data : 8:53
 * 天线状态
 */
@Setter
@Getter
public class Antenna {
    @ApiModelProperty(value = "id")
    private int id;

    @ApiModelProperty(value = "俯仰角度")
    private short lowAngle;

    @ApiModelProperty(value = "方位角度")
    private short directionAngle;

    @ApiModelProperty(value = "卫星标识")
    private String sign;
    //差路极化 1：左旋，2：右旋
    @ApiModelProperty(value = "差路极化")
    private byte differ;
    //和路极化 1：左旋，2：右旋
    @ApiModelProperty(value = "和路极化")
    private byte sum;
    //载波锁定 1：锁定，2：失锁
    @ApiModelProperty(value = "和路极化")
    private byte locking;

    @ApiModelProperty(value = "创建时间")
    private String datatime;

    @ApiModelProperty(value = "关联终端")
    private int terminalid;

    @ApiModelProperty(value = "时间线对象")
    private TimeLine timeLine;
}
