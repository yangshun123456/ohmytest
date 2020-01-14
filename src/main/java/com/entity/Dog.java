package com.entity;

import lombok.Data;

/**
 * @author : yangshun
 * @date : 2020/1/8 9:37
 */
@Data
public class Dog extends Animal {

    private String color;
    public void sing() {
        System.out.println("WangWang");
    }

    @Override
    public String toString() {
        return "Dog{" +
                "color='" + color + '\'' +
                "name='" + getName() + '\'' +
                "name='" + getLeg() + '\'' +
                '}';
    }
}
