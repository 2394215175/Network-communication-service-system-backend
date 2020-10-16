package com.htphy.wx.common.mvc;

import java.util.List;
import java.util.Optional;

/**
 * Service基类
 *
 * @author lw
 */
public abstract class BaseService<T,R extends BaseRepository<T>> {

    protected final R repository;

    public BaseService(R repository) {
        this.repository = repository;
    }

    public T save(T t) {
        repository.save(t);
        return t;
    }

    public boolean update(T t) {
        return this.repository.update(t);
    }

    public boolean delete(T t) {
        return this.repository.delete(t);
    }

    public Optional<T> getById(Long id) {
        return this.repository.getById(id);
    }

    public boolean exists(Long id) {
        return this.repository.exists(id);
    }

    public List<T> all() {
        return this.repository.all();
    }
}
