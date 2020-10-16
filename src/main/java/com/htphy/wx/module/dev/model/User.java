package com.htphy.wx.module.dev.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * @author dengwenning
 * @version 1.0
 * @description TODO 用户实体类
 * @date 2020/09/28 10:59
 */

@Getter
@Setter
public class User {

    @ApiModelProperty(value = "ID", example = "1")
    private int id;

    @ApiModelProperty(value = "username")
    private String username;

    @ApiModelProperty(value = "password")
    private String password;

    @ApiModelProperty(value = "permission")
    private int permission;

    @ApiModelProperty(value = "telephone")
    private String telephone;

    @ApiModelProperty(value = "adress")
    private String address;

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(int id, String username, String password, String telephone, String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.telephone = telephone;
        this.address = address;
    }

    public User(String username, String password, String telephone, String address) {
        this.username = username;
        this.password = password;
        this.telephone = telephone;
        this.address = address;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", permission=" + permission +
                '}';
    }
}

