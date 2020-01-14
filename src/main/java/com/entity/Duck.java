package com.entity;

import lombok.Data;

/**
 * @author : yangshun
 * @date : 2020/1/8 9:36
 */
@Data
public class Duck extends Animal {
    private String yuMao;
    public void sing() {
        System.out.println("DarkDark");
    }
}
