package com.example.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * 公共实用类(java方法2)
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


    /**
     * 00002
     * 根据metaDataKey获取metaDataValue
     *
     * @param ctx
     * @param metaDataKey
     * @return
     */
    public String getMetaDataValue(Context ctx, String metaDataKey) {
        if (ctx == null) {
            return null;
        }
        String channelName = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(),
                        PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = applicationInfo.metaData.getString(metaDataKey);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }


    /**
     * 00003
     * 跳转到权限设置
     *
     * @param activity
     */
    public void toPermissionSetting(Activity activity) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            toSystemConfig(activity);
        } else {
            try {
                toApplicationInfo(activity);
            } catch (Exception e) {
                e.printStackTrace();
                toSystemConfig(activity);
            }
        }
    }

    /**
     * 00003
     * 应用信息界面
     *
     * @param activity
     */
    public void toApplicationInfo(Activity activity) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivity(localIntent);
    }


    /**
     * 00003
     * 系统设置界面
     *
     * @param activity
     */
    public void toSystemConfig(Activity activity) {
        try {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
