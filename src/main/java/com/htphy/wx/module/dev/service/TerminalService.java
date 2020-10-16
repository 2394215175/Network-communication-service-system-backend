package com.htphy.wx.module.dev.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.htphy.wx.common.mvc.BaseService;
import com.htphy.wx.module.dev.model.Terminal;
import com.htphy.wx.module.dev.repository.TerminalRepository;
import com.htphy.wx.common.util.page.PageRequest;
import com.htphy.wx.common.util.page.PageResult;
import com.htphy.wx.common.util.page.PageUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author : zfk
 * Data : 14:34
 */
@Service
public class TerminalService extends BaseService<Terminal, TerminalRepository>{

    /**
     * 增加终端
     * @param terminal 要增加的终端
     * @return
     */
    @Override
    public Terminal save(Terminal terminal) {
        return super.save(terminal);
    }

    /**
     * 修改终端信息
     * @param terminal 修改后的终端对象
     * @return
     */
    @Override
    public boolean update(Terminal terminal) {
        return repository.update(terminal);
    }

    /**
     * 删除终端信息
     * @param id 终端唯一标识id
     * @return
     */
    public boolean delete(int id) {
        return repository.delete(id);
    }

    /**
     * 通过id获得终端信息
     * @param id 终端唯一标识id
     * @return
     */
    public Terminal getById(int id) {
        return repository.getById(id);
    }

    /**
     * 通过name模糊查询
     * @param name
     * @return
     */
    public PageResult getByName(String name,PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Terminal> list = repository.getByName(name);
        PageResult pageResult = PageUtils.getPageResult(pageRequest, new PageInfo<Terminal>(list));

        return pageResult;
    }

    /**
     * 显示所有终端信息
     * @return
     */
    @Override
    public List<Terminal> all() {
        return super.all();
    }


    /**
     * 分页查询
     * 这里统一封装了分页请求和结果，避免直接引入具体框架的分页对象, 如MyBatis或JPA的分页对象
     * 从而避免因为替换ORM框架而导致服务层、控制层的分页接口也需要变动的情况，替换ORM框架也不会
     * 影响服务层以上的分页接口，起到了解耦的作用
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
    private PageInfo<Terminal> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Terminal> menus = repository.selectPage();
        return new PageInfo<Terminal>(menus);
    }

    public TerminalService(TerminalRepository repository) {
        super(repository);
    }
}
