package com.htphy.wx.common.mvc;

/**
 * Controller 基类
 *
 * @author lw
 */
public abstract class BaseController<T,S extends BaseService> {

    protected final S service;

    public BaseController(S service) {
        this.service = service;
    }



}
