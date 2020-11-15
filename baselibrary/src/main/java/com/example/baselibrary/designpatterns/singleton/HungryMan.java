package com.example.baselibrary.designpatterns.singleton;

/**
 * 单例模式---饿汉式
 */
public class HungryMan {

    private static final HungryMan hungryman = new HungryMan();

    private HungryMan() {

    }

    public static HungryMan getInstance() {
        return hungryman;
    }
}
