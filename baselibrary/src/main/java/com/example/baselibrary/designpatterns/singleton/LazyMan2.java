package com.example.baselibrary.designpatterns.singleton;

/**
 * 单例模式---懒汉式(线程安全)
 */
public class LazyMan2 {

    private LazyMan2() {

    }

    private static LazyMan2 lazyman2 = null;

    public static synchronized LazyMan2 getInstance() {
        if (lazyman2 == null) {
            lazyman2 = new LazyMan2();
        }
        return lazyman2;
    }
}
