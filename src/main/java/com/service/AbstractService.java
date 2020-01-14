package com.service;

import com.dao.AbstractDao;
import com.entity.Animal;

import java.util.List;

/**
 * @author : yangshun
 * @date : 2020/1/8 10:33
 */
public abstract class AbstractService<T extends Animal> {
    public abstract AbstractDao<T> getAbstractDao();

    public List<T> findAll(List list1){
        return getAbstractDao().findAll(list1);
    }

    public T get(Object object){
        return getAbstractDao().get(object);
    }
}
