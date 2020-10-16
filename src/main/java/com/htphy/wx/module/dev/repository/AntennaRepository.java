package com.htphy.wx.module.dev.repository;

import com.htphy.wx.common.mvc.BaseRepository;
import com.htphy.wx.module.dev.model.Antenna;
import com.htphy.wx.net.netty.dev.AntennaMessage;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * 操作天线数据 repository
 *
 * @author hth
 */
@Mapper
public interface AntennaRepository extends BaseRepository<Antenna> {


    @Insert({"replace into antenna (`lowAngle_`, `directionAngle_`, `sign_`, `differ_`, `sum_`, `locking_`, `datatime_`, `terminalid_`) ",
            "values (#{m.lowAngle},#{m.directionAngle},#{m.signstr},#{m.differ},#{m.sum},#{m.locking},#{m.date},#{m.terminalid})"})
    void save(@Param("m") AntennaMessage model);

    /**
     * 根据id查询天线状态数据
     * @param id
     * @return
     */
    @Override
    @Select("select * from antenna where id_ = #{id}")
    @Results(id = "antennaMap",value = {
            @Result(column = "id_",property = "id",id = true),
            @Result(column = "lowAngle_",property = "lowAngle"),
            @Result(column = "directionAngle_",property = "directionAngle"),
            @Result(column = "sign_",property = "sign"),
            @Result(column = "differ_",property = "differ"),
            @Result(column = "sum_",property = "sum"),
            @Result(column = "locking_",property = "locking"),
            @Result(column = "datatime_",property = "datatime"),
            @Result(column = "terminalid_",property = "terminalid")
    })
    Optional<Antenna> getById(Long id);

    /**
     * 查询所有天线状态数据
     * @return
     */
    @Override
    @Select("select * from antenna")
    @ResultMap(value = "antennaMap")
    List<Antenna> all();

    /**
     * 查询某个终端的所有天线状态数据
     * @param id
     * @return
     */
    @Select("select * from antenna where terminalid_ = #{id}")
    @ResultMap(value = "antennaMap")
    List<Antenna> getByTerminalId(Long id);

}
