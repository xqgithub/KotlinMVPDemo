package com.example.baselibrary.utils;

import android.app.Activity;
import android.content.Intent;

/**
 * 公共实用类(java方法)
 */
public class PublicPracticalMethodFromJAVA {

    private static volatile PublicPracticalMethodFromJAVA mInstance;

    /**
     * 单例模式
     */
    public static PublicPracticalMethodFromJAVA getInstance() {
        if (mInstance == null) {
            synchronized (PublicPracticalMethodFromJAVA.class) {
                if (mInstance == null) {
                    mInstance = new PublicPracticalMethodFromJAVA();
                }
            }
        }
        return mInstance;
    }

    /**
     * 00001
     * 1.解决app点击桌面图标每次重新启动
     * 2.当是web页面跳转我们app的时候，如果本应用已经在后台，那么直接唤起；如果没有则重新启动
     *
     * @param activity
     */
    public void restartApp(Activity activity) {
        if (!activity.isTaskRoot()) {
            Intent intent = activity.getIntent();
            if (intent != null) {
                String scheme = intent.getScheme();
                if (!StringUtils.isBlank(scheme) && scheme.equals("nuts")) {
                    activity.finish();
                }
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) &&
                        Intent.ACTION_MAIN.equals(action)) {
                    activity.finish();
                }
            }
        }
    }


}
