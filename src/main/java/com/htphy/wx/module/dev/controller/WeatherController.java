package com.htphy.wx.module.dev.controller;

import com.htphy.wx.common.mvc.BaseController;
import com.htphy.wx.common.mvc.ResponseVO;
import com.htphy.wx.common.util.page.PageRequest;
import com.htphy.wx.common.util.page.PageResult;
import com.htphy.wx.module.dev.model.TimeLine;
import com.htphy.wx.module.dev.model.Weather;
import com.htphy.wx.module.dev.service.WeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

/**
 * Author : zfk
 * Data : 10:26
 * 天气数据控制层
 */
@RestController
@RequestMapping("/weather")
@Api(value = "Weather")
public class WeatherController extends BaseController<Weather, WeatherService> {

    /**
     * 根据Id查找天气数据
     * @param id
     * @return 天气数据
     */
    @GetMapping("/getById")
    @ApiOperation("根据天气Id查找天气数据")
    public ResponseVO<Weather> getById(@RequestParam("id") Long id){

        Optional<Weather> optionalWeather = service.getById(id);
        Weather weather = optionalWeather.get();

        //解析weather的datetime属性
        String datatime = weather.getDatatime();
        String[] strings = datatime.split(" ");

        //往weather封装timeLine对象
        TimeLine timeLine = new TimeLine();
        timeLine.setDate(strings[0]);
        timeLine.setTime(strings[1]);

        weather.setTimeLine(timeLine);

        return new ResponseVO<>(weather);

    }

    /**
     * 获得所有天气数据
     * @return list列表
     */
    @GetMapping("/all")
    @ApiOperation("获得所有天气数据")
    public ResponseVO<List<Weather>> all(){
        List<Weather> all = service.all();

        for(Weather a : all){
            //解析weather的datetime属性
            String datatime = a.getDatatime();
            String[] strings = datatime.split(" ");
            //设置时间线对象
            TimeLine timeLine = new TimeLine();

            timeLine.setDate(strings[0]);
            timeLine.setTime(strings[1]);

            a.setTimeLine(timeLine);
        }

        return new ResponseVO<>(all);
    }

    /**
     * 按照分页对象设置，进行分页查找
     * {
     *     "pageNum" : 1,
     *     "pageSize" : 1
     * }
     * @param pageQuery
     * @return
     */
    @ApiOperation(value="获得分页终端信息", notes="按照分页对象设置，进行分页查找")
    @PostMapping(value="/findPage")
    public ResponseVO<PageResult> findPage(@RequestBody PageRequest pageQuery) {

        PageResult page = service.findPage(pageQuery);
        List<Weather> all = (List<Weather>) page.getContent();
        for(Weather a : all){
            String datatime = a.getDatatime();
            String[] strings = datatime.split(" ");
            //设置时间线对象
            TimeLine timeLine = new TimeLine();

            timeLine.setDate(strings[0]);
            timeLine.setTime(strings[1]);

            a.setTimeLine(timeLine);
        }
        return new ResponseVO<>(page);
    }

    @GetMapping("/getByTerminalId")
    @ApiOperation("获得某个终端的所有天气数据")
    public ResponseVO<List<Weather>> getByTerminalId(@RequestParam("id") Long id){

        List<Weather> list = service.getByTerminalId(id);
        for(Weather a : list){
            String datatime = a.getDatatime();
            String[] strings = datatime.split(" ");
            //设置时间线对象
            TimeLine timeLine = new TimeLine();

            timeLine.setDate(strings[0]);
            timeLine.setTime(strings[1]);

            a.setTimeLine(timeLine);
        }
        return new ResponseVO<>(list);
    }

    public WeatherController(WeatherService service) {
        super(service);
    }
}
