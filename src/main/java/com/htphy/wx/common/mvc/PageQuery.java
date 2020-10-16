package com.htphy.wx.common.mvc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 查询对象基类
 * @author lw
 */
@Getter
@Setter
public class PageQuery {

    @ApiModelProperty(notes = "每页显示记录数，默认10，最小值1",example = "10")
    private long pageSize = 10;

    @ApiModelProperty(notes = "当前页号，默认1",example = "1")
    private long pageNum = 1;

    @JsonIgnore
    public long getOffset(){
        return (pageNum - 1) * pageSize;
    }
}
