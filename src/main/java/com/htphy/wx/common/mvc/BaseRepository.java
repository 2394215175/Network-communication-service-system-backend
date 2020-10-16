package com.htphy.wx.common.mvc;

import java.util.List;
import java.util.Optional;

/**
 * Repository 基类
 *
 *
 * @author lw*/
public interface BaseRepository<T> {

    void save(T t);

    boolean exists(Long id);

    boolean update(T t);

    boolean delete(T t);

    Optional<T> getById(Long id);

    List<T> all();

}
