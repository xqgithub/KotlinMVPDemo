package com.example.baselibrary.designpatterns.singleton;

/**
 * 单例模式---懒汉式(线程不安全)
 */
public class LazyMan {

    private LazyMan() {

    }

    private static LazyMan lazyman = null;

    public static LazyMan getInstance() {
        if (lazyman == null) {
            //在第一次调用getInstance()时才实例化，实现懒加载,所以叫懒汉式
            lazyman = new LazyMan();
        }
        return lazyman;
    }
}
