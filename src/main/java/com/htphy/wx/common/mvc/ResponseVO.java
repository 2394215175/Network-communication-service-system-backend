package com.htphy.wx.common.mvc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * 通用返回对象
 *
 * @author lw
 */
@Getter
@Setter
public class ResponseVO<T> {
    @JsonIgnore
    private HttpStatus status = HttpStatus.OK;
    private int code;
    private String msg = "success";
    private T data;

    public ResponseVO(T data) {
        this.data = data;
    }

    public ResponseVO(HttpStatus status, T data) {
        this.status = status;
        this.data = data;
    }

    public ResponseVO(HttpStatus status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getCode(){
        if(this.status != null){
            return this.status.value();
        }
        return 0;
    }
}
