package com.htphy.wx.module.dev.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * Author : zfk
 * Data : 11:42
 * 实体类：终端类
 */
@Setter
@Getter
public class Terminal {


    @ApiModelProperty(value = "id", example = "1")
    private int id;

    @ApiModelProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "status")
    private boolean status;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "port")
    private int port;

    @ApiModelProperty(value = "position")
    private double[] position;
}
