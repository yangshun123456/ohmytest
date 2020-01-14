package com.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : yangshun
 * @date : 2020/1/8 9:33
 */
@Setter
@Getter
public abstract class Animal {
    private Integer leg;
    private String name;

    public abstract void sing();
}
