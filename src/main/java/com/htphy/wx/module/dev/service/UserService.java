package com.htphy.wx.module.dev.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.htphy.wx.common.mvc.BaseService;
import com.htphy.wx.module.dev.model.User;
import com.htphy.wx.module.dev.repository.UserRepository;
import com.htphy.wx.common.util.page.PageRequest;
import com.htphy.wx.common.util.page.PageResult;
import com.htphy.wx.common.util.page.PageUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dengwenning
 * @version 1.0
 * @description TODO 用户的Service层
 * @date 2020/09/28 15:48
 */

@Service
public class UserService extends BaseService<User, UserRepository> {

    public UserService(UserRepository repository) {
        super(repository);
    }

    //通过用户名查询，用于登录
    public User getByName(String username) {
        return repository.getByName(username);
    }

    //模糊查询
    public List<User> getByLikeName(String username) {
        return repository.getByLikeName(username);
    }

    //插入方法
    @Override
    public User save(User user) {
        return super.save(user);
    }

    //修改方法
    @Override
    public boolean update(User user) {
        return repository.update(user);
    }

    //删除方法
    @Override
    public boolean delete(User user) {
        return repository.delete(user);
    }

    //查询方法
    public User getById(int id) {
        return repository.getById(id);
    }

    //查询所有
    @Override
    public List<User> all() {
        return repository.all();
    }

    /**
     * 分页查询
     * 这里统一封装了分页请求和结果，避免直接引入具体框架的分页对象, 如MyBatis或JPA的分页对象
     * 从而避免因为替换ORM框架而导致服务层、控制层的分页接口也需要变动的情况，替换ORM框架也不会
     * 影响服务层以上的分页接口，起到了解耦的作用
     *
     * @param pageRequest 自定义，统一分页查询请求
     * @return PageResult 自定义，统一分页查询结果
     */
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest));
    }

    /**
     * 调用分页插件完成分页
     * @param pageRequest
     * @return
     */
    private PageInfo<User> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<User> menus = repository.all();
        return new PageInfo<User>(menus);
    }

}
