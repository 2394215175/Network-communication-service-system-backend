package com.htphy.wx.module.dev.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Author : zfk
 * Date : 10:52
 */
@Getter
@Setter
public class TimeLine {

    @ApiModelProperty(value = "yyyy-MM-dd",example = "2020-10-10")
    private String date;

    @ApiModelProperty(value = "HH:mm:ss",example = "11:31:33")
    private String time;
}
