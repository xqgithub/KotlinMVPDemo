package com.example.baselibrary.utils.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Date:2021/9/6
 * Time:14:32
 * author:joker
 * Activity 管理
 */
public class MyActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks, ActivityState {

    private List<Activity> activityList = new ArrayList<>();
    private List<Activity> resumeActivity = new ArrayList<>();


    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        activityList.add(0, activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        if (!resumeActivity.contains(activity)) {
            resumeActivity.add(activity);
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        resumeActivity.remove(activity);
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        activityList.remove(activity);
//        LogUtils.i(ConfigConstants.TAG_ALL, "currentActivity =-= " + getActivityName(MyApplication.myApplication.lifecycleCallbacks.current().toString()));
//        for (int i = 0; i < TKBaseApplication.myApplication.lifecycleCallbacks.count(); i++) {
//            List<Activity> activities = TKBaseApplication.myApplication.lifecycleCallbacks.getActivitys();
//            LogUtils.i(ConfigConstants.TAG_ALL, "activities =-= " + getActivityName(activities.get(i).toString()));
//        }
    }

    @Override
    public Activity current() {
        return activityList.size() > 0 ? activityList.get(0) : null;
    }

    @Override
    public int count() {
        return activityList.size();
    }

    @Override
    public boolean isFront() {
        return resumeActivity.size() > 0;
    }

    @Override
    public List<Activity> getActivitys() {
        return activityList;
    }


    public String getActivityName(String name) {
        String result = "";
        result = name.substring(name.lastIndexOf(".") + 1);
        return result;
    }
}
