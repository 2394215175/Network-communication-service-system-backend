package com.htphy.wx.module.dev.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.htphy.wx.common.mvc.BaseService;
import com.htphy.wx.common.util.page.PageRequest;
import com.htphy.wx.common.util.page.PageResult;
import com.htphy.wx.common.util.page.PageUtils;
import com.htphy.wx.module.dev.model.Weather;
import com.htphy.wx.module.dev.repository.WeatherRepository;
import com.htphy.wx.net.netty.dev.WeatherMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeatherService extends BaseService<Weather, WeatherRepository> {

    /**
     * net层使用 - 存储天气数据
     * @param model
     */
    public void save(WeatherMessage model){
        repository.save(model);
    }


    @Override
    public Optional<Weather> getById(Long id) {
        return super.getById(id);
    }

    @Override
    public List<Weather> all() {
        return super.all();
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
    private PageInfo<Weather> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Weather> menus = repository.all();
        return new PageInfo<Weather>(menus);
    }


    public List<Weather> getByTerminalId(Long id){
        return repository.getByTerminalId(id);
    }

    public WeatherService(WeatherRepository repository) {
        super(repository);
    }
}
