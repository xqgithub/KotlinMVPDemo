package com.example.baselibrary.designpatterns.singleton;

/**
 * 单例模式---静态内部类
 */
public class StaticInnerClass {

    private StaticInnerClass() {
    }

    public static StaticInnerClass getInstance() {
        return StaticInnerClassHolder.staticinnerclass;
    }

    //静态内部类
    private static class StaticInnerClassHolder {
        private static final StaticInnerClass staticinnerclass = new StaticInnerClass();
    }

}
