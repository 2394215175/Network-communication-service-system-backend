package com.htphy.wx.module.dev.repository;

import com.htphy.wx.common.mvc.BaseRepository;
import com.htphy.wx.module.dev.model.Terminal;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Author : zfk
 * Data : 14:34
 * 终端管理：DAO层
 */
@Mapper
public interface TerminalRepository extends BaseRepository<Terminal> {


    /**
     * 根据终端id找到终端对象
     * @param id
     * @return
     */
    @Select("select * from terminal where id_ = #{id}")
    @Results(id="terminalMap", value={
            @Result(column = "id_",property = "id",id = true),
            @Result(column="name_", property="name"),
            @Result(column="ip_", property="ip"),
            @Result(column = "port_",property = "port")
    })
    Terminal getById(int id);

    /**
     * 根据终端name模糊查找
     * @param name
     * @return
     */
    @Select("select * from terminal where name_ like CONCAT('%',#{name},'%')")
    @ResultMap(value = "terminalMap")
    List<Terminal> getByName(String name);


    /**
     * 修改终端信息
     * @param terminal 传入的修改后的信息封装成Terminal对象
     * @return 修改成功true,失败false
     */
    @Update({"<script>",
            "update terminal",
            "  <set>",
            "    <if test='name != null'>name_=#{name},</if>",
            "    <if test='ip != null'>ip_=#{ip},</if>",
            "    <if test='port != null'>port_=#{port}</if>",
            "  </set>",
            "where id_=#{id}",
            "</script>"})
    @ResultMap(value = "terminalMap")
    @Override
    boolean update(Terminal terminal);

    /**
     * 删除终端信息
     * @param id 终端唯一标识id
     * @return 删除成功true,失败false
     */
    @Delete("delete from terminal where id_ = #{id}")
    @ResultMap(value = "terminalMap")
    boolean delete(int id);

    /**
     * 增加终端
     * @param terminal
     */
    @Insert("insert into terminal values(null,#{name},#{ip},#{port})")
    @ResultMap(value = "terminalMap")
    @Override
    void save(Terminal terminal);


    /**
     * 显示所有的终端信息
     * @return
     */
    @Select("select * from terminal")
    @ResultMap(value = "terminalMap")
    @Override
    List<Terminal> all();


    /**
     * 分页查询用户
     * @return
     */
    @Select("select * from terminal")
    @ResultMap(value = "terminalMap")
    List<Terminal> selectPage();



}
