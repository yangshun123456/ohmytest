package com.dao;

import com.entity.Dog;
import org.springframework.stereotype.Repository;

/**
 * @author : yangshun
 * @date : 2020/1/8 9:39
 */
@Repository
public class DogDao extends AbstractDao<Dog> {
    public Class<Dog> getTheClass() {
        return Dog.class;
    }
}
