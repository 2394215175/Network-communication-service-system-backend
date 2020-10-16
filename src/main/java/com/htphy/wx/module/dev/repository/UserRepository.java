package com.htphy.wx.module.dev.repository;

import com.htphy.wx.common.mvc.BaseRepository;
import com.htphy.wx.module.dev.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRepository extends BaseRepository<User> {


    @Select({"select * from user where id_ = #{id}"})
    @Results(id = "userMap", value = {
            @Result(id = true, column = "id_", property = "id"),
            @Result(column = "username_", property = "username"),
            @Result(column = "password_", property = "password"),
            @Result(column = "telephone_", property = "telephone"),
            @Result(column = "address_", property = "address"),
            @Result(column = "permission_", property = "permission")
    })
    User getById(int id);

    /**
     * @param username 用户名
     * @return 用户实体类
     * @Description 根据用户名查询实体类
     * @Date 2020/09/28 13:27
     */
    @Select("select * from user where username_ = #{username}")
    @ResultMap(value = {"userMap"})
    User getByName(String username);



    @Select("select * from user where username_ like CONCAT('%',#{username},'%') ")
    @ResultMap(value = {"userMap"})
    List<User> getByLikeName(String username);


    @Override
    @Insert({"insert into user values (null,#{user.username},#{user.password}," +
            "#{user.telephone},#{user.address},1)"})
    @ResultMap(value = {"userMap"})
    void save(@Param("user") User user);


    @Override
    @Update({"update user set username_ = #{user.username}, " +
            "password_ = #{user.password},telephone_ = #{user.telephone}, " +
            "address_ = #{user.address} where id_ = #{user.id}"})
    @ResultMap(value = {"userMap"})
    boolean update(@Param("user") User user);


    @Override
    @Delete({"delete from user where id_ = #{user.id}"})
    @ResultMap(value = {"userMap"})
    boolean delete(@Param("user") User user);


    @Override
    @Select({"select * from user"})
    @ResultMap(value = {"userMap"})
    List<User> all();
}
