package com.htphy.wx.module.dev.controller;

import com.htphy.wx.common.mvc.BaseController;
import com.htphy.wx.common.mvc.ResponseVO;
import com.htphy.wx.module.dev.model.Terminal;
import com.htphy.wx.module.dev.service.TerminalService;
import com.htphy.wx.common.util.page.PageRequest;
import com.htphy.wx.common.util.page.PageResult;
import com.htphy.wx.net.netty.dev.UdpServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Author : zfk
 * Data : 13:38
 * 终端管理
 */
@RestController
@RequestMapping("/terminal")
@Api(value = "Terminal")
public class TerminalController extends BaseController<Terminal,TerminalService> {

    //存放上线的终端id和终端ip+port
    private Map<Integer, String> map = UdpServer.getClientsRemoteAddress();

    @ApiOperation(value="停止终端", notes="")
    @GetMapping("/stop")
    @CrossOrigin
    public ResponseVO<Boolean> stop(@RequestParam("terminalid") Integer terminalid) {
        UdpServer.stopClient(terminalid);
        return new ResponseVO<>(true);
    }

    /**
     * 修改终端信息
     * @param terminal
     * @return
     */
    @ApiOperation(value="修改终端信息", notes="")
    @GetMapping("/update")
    @CrossOrigin
    public ResponseVO<Boolean> update(Terminal terminal) {

        boolean flag = service.update(terminal);

        return new ResponseVO<>(flag);
    }
    /**
     * 新增终端
     * @param terminal
     * @return
     */
    @ApiOperation(value="新增终端", notes="根据terminal对象创建用户")
    @GetMapping("/save")
    @CrossOrigin
    public ResponseVO<Boolean> save(Terminal terminal){
        Terminal flag = service.save(terminal);

        if (terminal.getId() == flag.getId()){
            return new ResponseVO<>(true);
        }
        else {
            return new ResponseVO<>(false);
        }
    }

    /**
     * 通过id获得终端信息
     * @param id
     * @return
     */
    @ApiOperation(value="查找终端", notes="通过id获得终端信息")
    @GetMapping("/getById")
    @CrossOrigin
    public ResponseVO<Terminal> getById(@RequestParam("id") int id){

        Terminal terminal = service.getById(id);

        return new ResponseVO<>(terminal);
    }

    /**
     * 根据name模糊查询，并分页展示
     * @param selectName
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value="查找终端", notes="通过name获得终端信息")
    @GetMapping("/getByName")
    @CrossOrigin
    public ResponseVO<PageResult> getByName(@RequestParam("selectName") String selectName,
                                            @RequestParam("pageNum") int pageNum,
                                            @RequestParam("pageSize") int pageSize){
        PageRequest pageRequest = new PageRequest(pageNum,pageSize);

        PageResult pageResult = service.getByName(selectName, pageRequest);

        List<Terminal> content = (List<Terminal>) pageResult.getContent();
        //将终端状态封装入终端对象
        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry<Integer, String> next = iterator.next();
            int id = next.getKey();

            for (Terminal terminal : content){

                if (terminal.getId() == id) {
                    String terminalVal = next.getValue();

                    String[] split = terminalVal.split("/|:");
                    String ip =  split[1];
                    String port = split[2];

                    if (terminalVal == null) {
                        terminal.setStatus(false);
                    } else {
                        terminal.setStatus(true);
                        terminal.setIp(ip);
                        terminal.setPort(Integer.parseInt(port));
                    }
                }

            }
        }

        return new ResponseVO<>(pageResult);
    }


    /**
     * 通过id删除终端
     * @param id
     * @return
     */
    @ApiOperation(value="删除终端", notes="通过id删除终端")
    @GetMapping("/delete")
    @CrossOrigin
    public ResponseVO<Boolean> delete(@RequestParam("id") int id){

        boolean flag = service.delete(id);

        return new ResponseVO<>(flag);
    }

    /**
     * 获得所有终端信息
     * @return
     */
    @ApiOperation(value="获得所有终端信息", notes="获得所有终端信息")
    @GetMapping("/all")
    @CrossOrigin
    public ResponseVO<List<Terminal>> all(){

        List<Terminal> all = service.all();
        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
        //将终端状态封装入终端对象
        while (iterator.hasNext()){
            Map.Entry<Integer, String> next = iterator.next();
            int id = next.getKey();

            for (Terminal terminal : all){
                if (terminal.getId() == id){
                    if (next.getValue() == null){
                        terminal.setStatus(false);
                    }
                    else {
                        terminal.setStatus(true);
                    }

                }
            }
        }
        //将对应的经纬度地址封装入终端对象
        Map<Integer, double[]> position = UdpServer.getPosition();
        Iterator<Map.Entry<Integer, double[]>> positionIter = position.entrySet().iterator();
        while (positionIter.hasNext()){
            Map.Entry<Integer, double[]> next = positionIter.next();

            Integer terminalId = next.getKey();

            for (Terminal terminal : all){
                if (terminal.getId() == terminalId){
                    if(next.getValue() != null){
                        terminal.setPosition(next.getValue());
                    }
                }
            }
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
    @CrossOrigin
    public ResponseVO<PageResult> findPage(@RequestBody PageRequest pageQuery) {
        PageResult page = service.findPage(pageQuery);
        List<Terminal> content = (List<Terminal>) page.getContent();

        //将终端状态封装入终端对象
        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry<Integer, String> next = iterator.next();
            int id = next.getKey();

            for (Terminal terminal : content){

                if (terminal.getId() == id) {
                    String terminalVal = next.getValue();

                    String[] split = terminalVal.split("/|:");
                    String ip =  split[1];
                    String port = split[2];

                    if (terminalVal == null) {
                        terminal.setStatus(false);
                    } else {
                        terminal.setStatus(true);
                        terminal.setIp(ip);
                        terminal.setPort(Integer.parseInt(port));
                    }
                }

            }
        }
        return new ResponseVO<>(page);
    }

    @GetMapping("/showOnlineTerminal")
    @ApiOperation(value = "展示所有上线的终端")
    public ResponseVO<List<Terminal>> showOnlineTerminal(){
        //将对应的经纬度地址封装入终端对象
        Map<Integer, double[]> position = UdpServer.getPosition();


        List<Terminal> all = service.all();

        List<Terminal> list = new ArrayList<>();
        for (Terminal terminal : all){
            int id = terminal.getId();
            //存储上线的终端
            if (position.get(id) != null){
                terminal.setPosition(position.get(id));
                list.add(terminal);
            }

        }


        return new ResponseVO<>(list);
    }


    public TerminalController(TerminalService service) {

        super(service);
    }
}
