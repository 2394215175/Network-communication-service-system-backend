package com.htphy.wx.module.dev.controller;

import com.htphy.wx.common.mvc.BaseController;
import com.htphy.wx.common.mvc.ResponseVO;
import com.htphy.wx.common.util.page.PageRequest;
import com.htphy.wx.common.util.page.PageResult;
import com.htphy.wx.module.dev.model.Antenna;
import com.htphy.wx.module.dev.model.TimeLine;
import com.htphy.wx.module.dev.service.AntennaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author : zfk
 * Date : 8:27
 * 操作天线状态数据
 */
@RestController
@RequestMapping("/antenna")
@Api(value = "Antenna")
public class AntennaController extends BaseController<Antenna, AntennaService> {


    public ResponseVO<List<Antenna>> getByIdAndTime(@RequestParam("id") Long id,
                                                     @RequestParam("type") int type) {
        //获取当前时间的月日
        Calendar now = Calendar.getInstance();
        int nowmouth = now.get(Calendar.MONTH);
        int nowday = now.get(Calendar.DAY_OF_MONTH);

        //设置时间线对象
        TimeLine timeLine = new TimeLine();

        //根据查询所有天线状态数据
        List<Antenna> antennas = service.getByTerminalId(id);
        List<Antenna> list = new ArrayList<>();

        for (Antenna antenna : antennas) {
            String datatime = antenna.getDatatime();
            //按空格分割获取年月日和时间
            String[] strings = datatime.split(" ");
            //按-分割获取年、月、日
            String[] splitdata = strings[0].split("-");
            //把月、日转成int类型
            int mouth = Integer.parseInt(splitdata[1]);
            int day = Integer.parseInt(splitdata[2]);

            if (type == 1) {
                //按日返回
                if (nowday <= day + 1) {
                    timeLine.setDate(strings[0]);
                    timeLine.setTime(strings[1]);
                    antenna.setTimeLine(timeLine);
                    list.add(antenna);

                }
            } else if (type == 2) {
                //按周返回
                if (nowday <= day + 7) {
                    timeLine.setDate(strings[0]);
                    timeLine.setTime(strings[1]);
                    antenna.setTimeLine(timeLine);
                    list.add(antenna);
                }
            } else {
                //按月返回
                if (nowmouth <= mouth + 1 && nowday > day) {
                    timeLine.setDate(strings[0]);
                    timeLine.setTime(strings[1]);
                    antenna.setTimeLine(timeLine);
                    list.add(antenna);
                }
            }
        }

        return new ResponseVO<>(list);
    }


    /**
     * 根据id查询天线状态数据
     *
     * @param id
     * @return
     */
    @GetMapping("/getById")
    @ApiOperation(value = "根据id查询天线状态数据")
    public ResponseVO<Antenna> getById(@RequestParam("id") Long id) {

        Optional<Antenna> optionalAntenna = service.getById(id);
        Antenna antenna = optionalAntenna.get();

        String datatime = antenna.getDatatime();
        String[] strings = datatime.split(" ");
        //设置时间线对象
        TimeLine timeLine = new TimeLine();
        timeLine.setDate(strings[0]);
        timeLine.setTime(strings[1]);

        antenna.setTimeLine(timeLine);

        return new ResponseVO<>(antenna);
    }

    /**
     * 查询所有天线状态数据
     *
     * @return
     */
    @GetMapping("/all")
    @ApiOperation(value = "查询所有天线状态数据")
    public ResponseVO<List<Antenna>> all() {

        List<Antenna> all = service.all();

        //往antenna对象封装timeline
        for (Antenna a : all) {
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


    @GetMapping("/getByTerminalId")
    @ApiOperation(value = "获得某个终端的所有天线状态数据")
    public ResponseVO<List<Antenna>> getByTerminalId(@RequestParam("terminalId") Long terminalId) {
        List<Antenna> list = service.getByTerminalId(terminalId);
        //往antenna对象封装timeline
        for (Antenna a : list) {
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
    @ApiOperation(value = "获得分页终端信息", notes = "按照分页对象设置，进行分页查找")
    @PostMapping(value = "/findPage")
    public ResponseVO<PageResult> findPage(@RequestBody PageRequest pageQuery) {

        PageResult page = service.findPage(pageQuery);
        List<Antenna> all = (List<Antenna>) page.getContent();
        for (Antenna a : all) {
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

    public AntennaController(AntennaService service) {
        super(service);
    }
}
