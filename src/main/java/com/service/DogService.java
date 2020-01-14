package com.service;

import com.dao.AbstractDao;
import com.dao.DogDao;
import com.entity.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : yangshun
 * @date : 2020/1/8 10:43
 */
@Service
public class DogService extends AbstractService<Dog> {
    @Autowired
    DogDao dogDao;

    public AbstractDao<Dog> getAbstractDao() {
        return dogDao;
    }

}
