package com.htphy.wx.module.dev.repository;

import com.htphy.wx.common.mvc.BaseRepository;
import com.htphy.wx.module.dev.model.Weather;
import com.htphy.wx.net.netty.dev.WeatherMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * 添加天气数据 repository
 *
 * @author hth
 */
@Mapper
public interface WeatherRepository extends BaseRepository<Weather> {


    @Insert({"replace into weather (`temperature_`, `humidity_`, `illumination_`, `pressure_`, `velocity_`, `direction_`, `rainfall_`,`datatime_`,`terminalid_`) ",
            "values (#{m.temperature},#{m.humidity},#{m.illumination},#{m.pressure},#{m.velocity},#{m.direction},#{m.rainfall},#{m.date},#{m.terminalid})"})
    void save(@Param("m") WeatherMessage model);


    @Override
    @Select("select * from weather where id_ = #{id}")
    @Results(id = "weatherMap",value = {
            @Result(column = "id_",property = "id",id = true),
            @Result(column = "temperature_",property = "temperature"),
            @Result(column = "humidity_",property = "humidity"),
            @Result(column = "illumination_",property = "illumination"),
            @Result(column = "pressure_",property = "pressure"),
            @Result(column = "velocity_",property = "velocity"),
            @Result(column = "direction_",property = "direction"),
            @Result(column = "rainfall_",property = "rainfall"),
            @Result(column = "datatime_",property = "datatime"),
            @Result(column = "terminalid_",property = "terminalid")
    })
    Optional<Weather> getById(Long id);

    @Override
    @Select("select * from weather")
    @ResultMap(value = "weatherMap")
    List<Weather> all();

    @Select("select * from weather where terminalid_ = #{terminalid}")
    @ResultMap(value = "weatherMap")
    List<Weather> getByTerminalId(@Param("terminalid") Long terminalid);

}