package com.example.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.text.DecimalFormat;

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

    /**
     * 00004
     * 屏幕适配最小宽度 生成
     */
    public void smallWidth() {
        // 基础数据以360DP
        double original_width = 360;
        double target_width = 720;
        int width_num = 360;

        DecimalFormat df = new DecimalFormat("#.0");
        String result = "";
        for (int i = 1; i <= width_num; i++) {
            String aString = "<dimen name='dimen_" + i + "x'" + ">" + df.format(target_width
                    * i / original_width) + "dp</dimen>";
            System.out.println(aString);
        }
    }

    /**
     * 00005
     * 获得手机屏幕信息
     */
    public void getPhoneScreenInfo(Context context) {
        LogUtils.i("MyApp",
                "手机屏幕宽度(像素): " + ScreenUtils.getScreenWidth(),
                "手机屏幕高度(像素): " + ScreenUtils.getScreenHeight(),
                "手机屏幕密度: " + ScreenUtils.getScreenDensity(context),
                "手机屏幕densityDpi: " + ScreenUtils.getScreendensityDpi(context),
                "手机屏幕宽度(dp): " + ScreenUtils.getScreenWidthDP(context),
                "手机屏幕高度(dp): " + ScreenUtils.getScreenHeightDP(context),
                "手机cpu_abi: " + DeviceUtils.getSupportedabis());
    }

}
