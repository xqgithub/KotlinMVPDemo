package com.example.baselibrary.designpatterns.singleton;

/**
 * 单例模式---双重检查锁定（DCL）
 */
public class DoubleCheckLock {

    //volatile 能够防止代码的重排序，保证得到的对象是初始化过
    private volatile static DoubleCheckLock doublechecklock;

    private DoubleCheckLock() {

    }

    public static DoubleCheckLock getInstance() {
        if (doublechecklock == null) {//第一次检查，避免不必要的同步
            synchronized (DoubleCheckLock.class) {//synchronized对DoubleCheckLock加全局锁，保证每次只要一个线程创建实例
                if (doublechecklock == null) {//第二次检查，为null时才创建实例
                    doublechecklock = new DoubleCheckLock();
                }
            }
        }
        return doublechecklock;
    }


}
