package com.htphy.wx.module.dev.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.htphy.wx.common.mvc.BaseService;
import com.htphy.wx.common.util.page.PageRequest;
import com.htphy.wx.common.util.page.PageResult;
import com.htphy.wx.common.util.page.PageUtils;
import com.htphy.wx.module.dev.model.Antenna;
import com.htphy.wx.module.dev.repository.AntennaRepository;
import com.htphy.wx.net.netty.dev.AntennaMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * service层：天线状态数据
 */

@Service
public class AntennaService extends BaseService<Antenna, AntennaRepository> {

    /**
     * net层使用 - 存储天线状态数据
     * @param model
     */
    public void save(AntennaMessage model){
        repository.save(model);
    }

    @Override
    public Optional<Antenna> getById(Long id) {
        return repository.getById(id);
    }

    @Override
    public List<Antenna> all() {
        return super.all();
    }


    public List<Antenna> getByTerminalId(Long id){
        return repository.getByTerminalId(id);
    }

    /**
     * 分页查询
     * @param pageRequest
     * @return
     */
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest));
    }

    /**
     * 调用分页插件完成分页
     * @param pageRequest
     * @return
     */
    private PageInfo<Antenna> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Antenna> menus = repository.all();
        return new PageInfo<Antenna>(menus);
    }

    public AntennaService (AntennaRepository repository) {
        super(repository);
    }
}
