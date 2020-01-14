package com.dao;

import com.entity.Animal;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : yangshun
 * @date : 2020/1/8 9:30
 */
@Repository
public abstract class AbstractDao<T extends Animal> {

    public abstract Class<T> getTheClass();

    public String getClassName() {
        return getTheClass().getName();
    }

    public String findOne() {
        return getClassName() + "just";
    }

    public void letSing(T t) {
        t.sing();
    }

    public List<T> findAll(List list1) {
        List<T> list = (List<T>) list1;
        return list;
    }

    public T get(Object object){
        T t= (T) object;
        return t;
    }
}
