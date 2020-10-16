package com.htphy.wx.module.dev.controller;

import com.htphy.wx.common.mvc.BaseController;
import com.htphy.wx.common.mvc.ResponseVO;
import com.htphy.wx.module.dev.model.User;

import com.htphy.wx.module.dev.service.UserService;
import com.htphy.wx.common.util.Md5.Md5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.htphy.wx.common.util.Md5.Md5Util.string2MD5;


/**
 * @author dengwenning
 * @version 1.0
 * @description TODO 登录逻辑
 * @date 2020/09/28 10:40
 */

@RestController
@Api(value = "userLogin")
public class LoginController extends BaseController<User, UserService> {

    public LoginController(UserService service) {
        super(service);
    }

    /**
     * @param user 前端表单数据，为username和password封装的josn对象
     * @return 返回对应的token
     * @Description 登录功能
     * @Param
     **/
    @ApiOperation(value = "login")
    @PostMapping(value = "/login")
    public ResponseVO<Map> login(@RequestBody User user) {

        String username = user.getUsername();
        User userResult = service.getByName(username);


        if (userResult != null) {
            if (userResult.getPassword().equals(user.getPassword())) {
                //登录成功，返回token
                Map<String, Object> map = new HashMap<>();
                map.put("token", username);
                map.put("permission", userResult.getPermission());
                map.put("id",userResult.getId());

                ResponseVO responseVO = new ResponseVO(map);

                System.out.println(responseVO.getCode());
                return responseVO;
            } else {
                //登录失败，密码错误，返回错误信息
                ResponseVO responseVO = new ResponseVO(HttpStatus.NOT_ACCEPTABLE, "登录失败，密码错误");
                System.out.println(responseVO.getCode());
                return responseVO;
            }
        } else {
            //登录失败，无用户，返回错误信息
            ResponseVO responseVO = new ResponseVO(HttpStatus.NOT_ACCEPTABLE, "登录失败，无用户");
            System.out.println(responseVO.getCode());
            return responseVO;
        }
    }

    /**
     * @return 返回token信息
     * @Description 返回成功
     * @Param
     **/
    @GetMapping("/info")
    public ResponseVO<Map> info() {

        Map<String, Object> map = new HashMap<>();
        map.put("name", "editor");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        ResponseVO responseVO = new ResponseVO(HttpStatus.OK, "成功", map);
        System.out.println(responseVO.getCode());
        return responseVO;
    }

    /**
     * @return 200成功码
     * @Description 登出功能，返回成功码，token删除在客户端
     * @Param
     **/
    @PostMapping("/logout")
    public ResponseVO<Map> logout() {
        ResponseVO responseVO = new ResponseVO(HttpStatus.OK, "成功");
        System.out.println(responseVO.getCode());
        return responseVO;
    }
}
