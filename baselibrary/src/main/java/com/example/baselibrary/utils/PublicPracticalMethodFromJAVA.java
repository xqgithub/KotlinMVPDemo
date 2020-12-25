package com.example.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorRes;

import com.example.baselibrary.constants.ConfigConstants;

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
        double target_width = 480;
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
        LogUtils.i(ConfigConstants.TAG_ALL,
                "手机屏幕宽度(像素): " + ScreenUtils.getScreenWidth(),
                "手机屏幕高度(像素): " + ScreenUtils.getScreenHeight(),
                "手机屏幕密度: " + ScreenUtils.getScreenDensity(context),
                "手机屏幕densityDpi: " + ScreenUtils.getScreendensityDpi(context),
                "手机屏幕宽度(dp): " + ScreenUtils.getScreenWidthDP(context),
                "手机屏幕高度(dp): " + ScreenUtils.getScreenHeightDP(context),
                "手机cpu_abi: " + DeviceUtils.getSupportedabis());
    }

    /**
     * 00006
     * 2进制、10进制、16进制之间的转换
     */
    //2进制转10进制
    public int twoBinaryToTenHex(String parameter) {
        if (!StringUtils.isBlank(parameter)) {
            int result = Integer.parseInt(parameter, 2);
            return result;
        } else {
            LogUtils.e(ConfigConstants.TAG_ALL, "参数为空，请查看");
            return -1;
        }
    }

    //10进制转2进制
    public String tenHexToTwoBinary(int parameter) {
        if (!StringUtils.isBlank(parameter)) {
            String result = Integer.toBinaryString(parameter);
            return result;
        } else {
            LogUtils.e(ConfigConstants.TAG_ALL, "参数为空，请查看");
            return "";
        }
    }

    //10进制转16进制
    public String tenHexToHexadecimal(int parameter) {
        if (!StringUtils.isBlank(parameter)) {
            String result = Integer.toHexString(parameter);
            return result;
        } else {
            LogUtils.e(ConfigConstants.TAG_ALL, "参数为空，请查看");
            return "";
        }
    }

    //16进制转10进制
    public int hexadecimalToTenHex(String parameter) {
        if (!StringUtils.isBlank(parameter)) {
            int result = Integer.parseInt(parameter, 16);
            return result;
        } else {
            LogUtils.e(ConfigConstants.TAG_ALL, "参数为空，请查看");
            return -1;
        }
    }

    //2进制转16进制
    public String twoBinaryToHexadecimal(String parameter) {
        if (!StringUtils.isBlank(parameter)) {
            int ten = Integer.parseInt(parameter, 2);
            String result = Integer.toHexString(ten);
            return result;
        } else {
            LogUtils.e(ConfigConstants.TAG_ALL, "参数为空，请查看");
            return "";
        }
    }

    //16进制转2进制
    public String hexadecimalTotwoBinary(String parameter) {
        if (!StringUtils.isBlank(parameter)) {
            int ten = Integer.parseInt(parameter, 16);
            String result = Integer.toBinaryString(ten);
            return result;
        } else {
            LogUtils.e(ConfigConstants.TAG_ALL, "参数为空，请查看");
            return "";
        }
    }

    /**
     * 00007
     * 状态栏透明方法
     * - SYSTEM_UI_FLAG_LOW_PROFILE   设置状态栏和导航栏中的图标变小，变模糊或者弱化其效果。这个标志一般用于游戏，电子书，视频，或者不需要去分散用户注意力的应用软件。
     * - SYSTEM_UI_FLAG_HIDE_NAVIGATION  隐藏导航栏，点击屏幕任意区域，导航栏将重新出现，并且不会自动消失
     * - SYSTEM_UI_FLAG_FULLSCREEN       隐藏状态栏，点击屏幕区域不会出现，需要从状态栏位置下拉才会出现
     * - SYSTEM_UI_FLAG_LAYOUT_STABLE    稳定布局，主要是在全屏和非全屏切换时，布局不要有大的变化。一般和View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN、View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION搭配使用。同时，android:fitsSystemWindows要设置为true
     * - SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  将布局内容拓展到导航栏的后面
     * - SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN     将布局内容拓展到状态的后面
     * - SYSTEM_UI_FLAG_IMMERSIVE    使状态栏和导航栏真正的进入沉浸模式,即全屏模式，如果没有设置这个标志，设置全屏时，我们点击屏幕的任意位置，就会恢复为正常模式
     * - SYSTEM_UI_FLAG_IMMERSIVE_STICKY  它的效果跟View.SYSTEM_UI_FLAG_IMMERSIVE一样。但是，它在全屏模式下，用户上下拉状态栏或者导航栏时，这些系统栏只是以半透明的状态显示出来，并且在一定时间后会自动消息
     *
     * @param useThemestatusBarColor 是否使用特殊的标题栏背景颜色，android5.0以上可以设置状态栏背景色，如果不使用则使用透明色值
     * @param useStatusBarColor      是否使用状态栏文字和图标为暗色，如果状态栏采用了白色系，则需要使状态栏和图标为暗色，android6.0以上可以设置
     */
    public void transparentStatusBar(Activity activity,
                                     boolean useThemestatusBarColor,
                                     boolean useStatusBarColor,
                                     @ColorRes int color) {
//        //适配Android 4.4 +的方法
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = activity.getWindow();
//            // Translucent status bar
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // Translucent navigation bar
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//        //适配 Android 5.0+ 的方法
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = activity.getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
//        }

        Window window = activity.getWindow();
        //5.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            View decorView = window.getDecorView();
            int options = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(options);
            if (useThemestatusBarColor) {
                window.setStatusBarColor(activity.getResources().getColor(color));
            } else {
                window.setStatusBarColor(Color.TRANSPARENT);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = window.getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                    | localLayoutParams.flags);
        }

        //android6.0以后可以对状态栏文字颜色和图标进行修改
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (useStatusBarColor) {//暗色
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {//浅色
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }
}
