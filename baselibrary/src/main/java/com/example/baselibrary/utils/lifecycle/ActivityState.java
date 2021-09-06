package com.example.baselibrary.utils.lifecycle;

import android.app.Activity;

import java.util.List;

/**
 * Date:2021/6/24
 * Time:17:30
 * author:joker
 * Activity状态
 */
public interface ActivityState {

    /**
     * 得到当前Activity
     *
     * @return
     */
    Activity current();

    /**
     * 任务栈中Activity的总数
     *
     * @return
     */
    int count();

    /**
     * 判断应用是否处于前台，即是否可见
     *
     * @return
     */
    boolean isFront();


    /**
     * 得到栈内Activitys
     *
     * @return
     */
    List<Activity> getActivitys();

}
