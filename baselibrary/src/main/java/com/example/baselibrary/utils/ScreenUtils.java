package com.example.baselibrary.utils;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

import com.example.baselibrary.application.MyApplication;
import com.example.baselibrary.constants.ConfigConstants;

/**
 * desc  : 屏幕相关工具类
 */
public class ScreenUtils {

    private ScreenUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取屏幕的宽度（单位：px）
     *
     * @return 屏幕宽px
     */
    public static int getScreenWidth() {
        WindowManager windowManager = (WindowManager) MyApplication.myapplication.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(dm);// 给白纸设置宽高
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度（单位：px）
     *
     * @return 屏幕高px
     */
    public static int getScreenHeight() {
        WindowManager windowManager = (WindowManager) MyApplication.myapplication.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(dm);// 给白纸设置宽高
        return dm.heightPixels;
    }

    /**
     * 设置屏幕为横屏
     * <p>还有一种就是在Activity中加属性android:screenOrientation="landscape"</p>
     * <p>不设置Activity的android:configChanges时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次</p>
     * <p>设置Activity的android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次</p>
     * <p>设置Activity的android:configChanges="orientation|keyboardHidden|screenSize"（4.0以上必须带最后一个参数）时
     * 切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法</p>
     *
     * @param activity activity
     */
    public static void setLandscape(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 设置屏幕为竖屏
     *
     * @param activity activity
     */
    public static void setPortrait(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 判断是否横屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isLandscape() {
        return MyApplication.myapplication.getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断是否竖屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isPortrait() {
        return MyApplication.myapplication.getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 获取屏幕旋转角度
     *
     * @param activity activity
     * @return 屏幕旋转角度
     */
    public static int getScreenRotation(Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            default:
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity activity
     * @return Bitmap
     */
    public static Bitmap captureWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret = Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        view.destroyDrawingCache();
        return ret;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity activity
     * @return Bitmap
     */
    public static Bitmap captureWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int statusBarHeight = BarUtils.getStatusBarHeight(activity);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret = Bitmap.createBitmap(bmp, 0, statusBarHeight, dm.widthPixels, dm.heightPixels - statusBarHeight);
        view.destroyDrawingCache();
        return ret;
    }

    /**
     * 判断是否锁屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isScreenLock() {
        KeyguardManager km = (KeyguardManager) MyApplication.myapplication.getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);
        return km.inKeyguardRestrictedInputMode();
    }

    /**
     * 设置进入休眠时长
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     *
     * @param duration 时长
     */
    public static void setSleepDuration(int duration) {
        Settings.System.putInt(MyApplication.myapplication.getApplicationContext().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, duration);
    }

    /**
     * 获取进入休眠时长
     *
     * @return 进入休眠时长，报错返回-123
     */
    public static int getSleepDuration() {
        try {
            return Settings.System.getInt(MyApplication.myapplication.getApplicationContext().getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -123;
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        // System.out.println("dip2pxscale----->" + scale);
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 得到设备的密度
     */
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 得到设备的densityDpi
     */
    public static float getScreendensityDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }


    /**
     * 获取屏幕的宽度（单位：dp）
     *
     * @return 屏幕宽dp
     */
    public static int getScreenWidthDP(Context context) {
        int WidthDP = (int) (getScreenWidth() / getScreenDensity(context));
        return WidthDP;
    }

    /**
     * 获取屏幕的高度（单位：dp）
     *
     * @return 屏幕高dp
     */
    public static int getScreenHeightDP(Context context) {
        int HeightDP = (int) (getScreenHeight() / getScreenDensity(context));
        return HeightDP;
    }

    /**
     * 获得设备真实的信息
     * 实际显示区域 指定包含系统装饰的内容的显示部分。 即便如此，如果窗口管理器使用（adb shell wm size）模拟较小的显示器，则实际显示区域可能小于显示器的物理尺寸。 使用以下方法查询实际显示区域：getRealSize(Point)、getRealMetrics(DisplayMetrics)。
     */
    public static void getEquipmentInformation(Context mContext) {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(outMetrics);
        int widthPixel = outMetrics.widthPixels;
        int heightPixel = outMetrics.heightPixels;
        // 屏幕密度表示为每英寸点数。
        int densityDpi = outMetrics.densityDpi;//屏幕密度dpi（120 / 160 / 240）
        // 显示器的逻辑密度。
        float density = outMetrics.density;//屏幕密度（0.75 / 1.0 / 1.5）
        // 显示屏上显示的字体缩放系数。
        float scaledDensity = outMetrics.scaledDensity;

        int screenWidth = (int) (widthPixel / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (heightPixel / density);// 屏幕高度(dp)

        LogUtils.i(ConfigConstants.TAG_ALL,
                "手机设备屏幕宽度(px) =-= " + widthPixel,
                "手机设备屏幕高度(px) =-= " + heightPixel,
                "手机屏幕密度 density =-= " + density,
                "手机屏幕密度 densityDpi =-= " + densityDpi,
                "手机屏幕宽度(dp) =-= " + screenWidth,
                "手机屏幕高度(dp) =-= " + screenHeight,
                "字体缩放系数 =-= " + scaledDensity,
                "手机的cpu型号 =-= " + DeviceUtils.getSupportedabis()
        );
    }

    /**
     * 获得设备的显示区域的信息
     * <p>
     * 应用程序显示区域 指定可能包含应用程序窗口的显示部分，不包括系统装饰。 应用程序显示区域可以小于实际显示区域，因为系统减去诸如状态栏之类的装饰元素所需的空间。 使用以下方法查询应用程序显示区域：getSize(Point)、getRectSize(Rect)和getMetrics(DisplayMetrics)。
     */
    public static void getDisplayAreaInformation(Context mContext) {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int widthPixel = outMetrics.widthPixels;
        int heightPixel = outMetrics.heightPixels;
        // 屏幕密度表示为每英寸点数。
        int densityDpi = outMetrics.densityDpi;//屏幕密度dpi（120 / 160 / 240）
        // 显示器的逻辑密度。
        float density = outMetrics.density;//屏幕密度（0.75 / 1.0 / 1.5）
        // 显示屏上显示的字体缩放系数。
        float scaledDensity = outMetrics.scaledDensity;

        int screenWidth = (int) (widthPixel / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (heightPixel / density);// 屏幕高度(dp)

        LogUtils.i(ConfigConstants.TAG_ALL,
                "手机显示屏幕宽度(px) =-= " + widthPixel,
                "手机显示屏幕高度(px) =-= " + heightPixel,
                "手机屏幕密度 density =-= " + density,
                "手机屏幕密度 densityDpi =-= " + densityDpi,
                "手机屏幕宽度(dp) =-= " + screenWidth,
                "手机屏幕高度(dp) =-= " + screenHeight,
                "字体缩放系数 =-= " + scaledDensity,
                "手机的cpu型号 =-= " + DeviceUtils.getSupportedabis()
        );
    }


}