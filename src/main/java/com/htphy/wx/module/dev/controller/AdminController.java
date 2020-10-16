package com.htphy.wx.module.dev.controller;

import com.htphy.wx.common.mvc.BaseController;
import com.htphy.wx.common.mvc.ResponseVO;
import com.htphy.wx.module.dev.model.User;
import com.htphy.wx.module.dev.service.UserService;
import com.htphy.wx.common.util.page.PageRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.htphy.wx.common.util.Md5.Md5Util.string2MD5;


/**
 * @author dengwenning
 * @version 1.0
 * @description TODO 用户的增删改查Controller层
 * @date 2020/09/28 15:43
 */

@RestController
@RequestMapping("/admin")
@Api(value = "admin")
public class AdminController<list> extends BaseController<User, UserService> {

    public AdminController(UserService service) {
        super(service);
    }

    /**
     * @param password  用户密码
     * @param telephone 用户电话
     * @param address   用户住址
     * @return true添加成功  false添加失败
     * @Description 添加用户
     * @Param name 用户名
     **/
    @ApiOperation(value = "save", notes = "")
    @PostMapping(value = "/save")
    public void save(@RequestParam("username") String username,
                     @RequestParam("password") String password,
                     @RequestParam("telephone") String telephone,
                     @RequestParam("address") String address
    ) {
        String passwordMD5 = string2MD5(password);
        User saveResult = service.save(new User(username, passwordMD5, telephone, address));
    }

    /**
     * @param id 用户id
     * @return true删除成功 false删除失败
     * @Description 根据用户id 删除用户
     **/
    @ApiOperation(value = "delete", notes = "")
    @DeleteMapping(value = "/delete")
    public boolean delete(@RequestParam("id") int id) {
        User deleteuser = new User(id);
        return service.delete(deleteuser);
    }


    /**
     * @return boolean true修改成功 false修改失败
     * @Description 修改用户信息，不能修改权限
     **/
    @ApiOperation(value = "update", notes = "")
    @PostMapping(value = "/update")
    public boolean update(@RequestBody User user) {
        String passwordMD5 = string2MD5(user.getPassword());

        User updateuser = new User(user.getId(), user.getUsername(), passwordMD5, user.getTelephone(), user.getAddress());
        return service.update(updateuser);
    }

    /**
     * @param id 用户id
     * @return 返回用户实体类
     * @Description 根据用户id查询用户
     **/
    @ApiOperation(value = "getById", notes = "")
    @GetMapping(value = "/getById")
    public User getById(@RequestParam("id") int id) {
        return service.getById(id);
    }

    /**
     * @return 返回user的list集合
     * @Description 查询所有
     **/
    @ApiOperation(value = "getAll", notes = "")
    @GetMapping(value = "/getAll")
    public List<User> getAll() {
        List<User> all = service.all();
        return all;
    }

    /**
     * @return 返回user的list集合
     * @Description 根据name模糊查询
     **/
    @ApiOperation(value = "getByLikeName", notes = "")
    @GetMapping(value = "/getByLikeName")
    public ResponseVO<List<User>> getName(@RequestParam("username") String username) {
        List<User> users = service.getByLikeName(username);

        return new ResponseVO<>(users);
    }

    /**
     * 按照分页对象设置，进行分页查找
     * {
     * "pageNum" : 1,
     * "pageSize" : 1
     * }
     *
     * @param pageQuery
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "按照分页对象设置，进行分页查找")
    @PostMapping(value = "/findPage")
    public Object findPage(@RequestBody PageRequest pageQuery) {
        return service.findPage(pageQuery);
    }

}
