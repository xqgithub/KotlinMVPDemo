package com.example.baselibrary.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.baselibrary.constants.ConfigConstants;

import java.util.Locale;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.onAdaptListener;
import me.jessyan.autosize.utils.AutoSizeLog;

/**
 * Date:2021/6/9
 * Time:17:04
 * author:joker
 * 屏幕适配工具类 基于AutoSize
 */
public class ScreenTools {

    private volatile static ScreenTools mInstance;

    /**
     * 单例模式
     */
    public static ScreenTools getInstance() {
        if (mInstance == null) {
            synchronized (ScreenTools.class) {
                if (mInstance == null) {
                    mInstance = new ScreenTools();
                }
            }
        }
        return mInstance;
    }


    /**
     * 初始化 AutoSize，在自定义的Application中初始化
     *
     * @param application
     * @param mContext
     */
    public void initAutoSize(Application application, Context mContext) {
        //当 App 中出现多进程, 并且您需要适配所有的进程, 就需要在 App 初始化时调用 initCompatMultiProcess()
//        AutoSize.initCompatMultiProcess(mContext);

        //如果在某些特殊情况下出现 InitProvider 未能正常实例化, 导致 AndroidAutoSize 未能完成初始化
        //可以主动调用 AutoSize.checkAndInit(this) 方法, 完成 AndroidAutoSize 的初始化后即可正常使用
        //如何控制 AndroidAutoSize 的初始化，让 AndroidAutoSize 在某些设备上不自动启动？https://github.com/JessYanCoding/AndroidAutoSize/issues/249
        AutoSize.checkAndInit(application);

        AutoSizeConfig.getInstance()
                //是否让框架支持自定义 Fragment 的适配参数, 由于这个需求是比较少见的, 所以须要使用者手动开启
                //如果没有这个需求建议不开启
                .setCustomFragment(false)

                //是否屏蔽系统字体大小对 AndroidAutoSize 的影响, 如果为 true, App 内的字体的大小将不会跟随系统设置中字体大小的改变
                //如果为 false, 则会跟随系统设置中字体大小的改变, 默认为 false
                .setExcludeFontScale(true)

                //区别于系统字体大小的放大比例, AndroidAutoSize 允许 APP 内部可以独立于系统字体大小之外，独自拥有全局调节 APP 字体大小的能力
                //当然, 在 APP 内您必须使用 sp 来作为字体的单位, 否则此功能无效, 不设置或将此值设为 0 则取消此功能
//                .setPrivateFontScale(0.8f)

                //屏幕适配监听器
                .setOnAdaptListener(new onAdaptListener() {
                    @Override
                    public void onAdaptBefore(Object target, Activity activity) {
                        //使用以下代码, 可以解决横竖屏切换时的屏幕适配问题
                        //使用以下代码, 可支持 Android 的分屏或缩放模式, 但前提是在分屏或缩放模式下当用户改变您 App 的窗口大小时
                        //系统会重绘当前的页面, 经测试在某些机型, 某些情况下系统不会重绘当前页面, ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application!!!
//                        AutoSizeConfig.getInstance().setScreenWidth(ScreenUtils.getScreenSize(activity)[0]);
//                        AutoSizeConfig.getInstance().setScreenHeight(ScreenUtils.getScreenSize(activity)[1]);
                        AutoSizeLog.d(String.format(Locale.ENGLISH, "%s onAdaptBefore!", target.getClass().getName()));
                    }

                    @Override
                    public void onAdaptAfter(Object target, Activity activity) {
                        AutoSizeLog.d(String.format(Locale.ENGLISH, "%s onAdaptAfter!", target.getClass().getName()));
                    }
                })

                //是否打印 AutoSize 的内部日志, 默认为 true, 如果您不想 AutoSize 打印日志, 则请设置为 false
                .setLog(true)
                //是否使用设备的实际尺寸做适配, 默认为 false, 如果设置为 false, 在以屏幕高度为基准进行适配时
                //AutoSize 会将屏幕总高度减去状态栏高度来做适配
                //设置为 true 则使用设备的实际屏幕高度, 不会减去状态栏高度
                //在全面屏或刘海屏幕设备中, 获取到的屏幕高度可能不包含状态栏高度, 所以在全面屏设备中不需要减去状态栏高度，所以可以 setUseDeviceSize(true)
                .setUseDeviceSize(true)
                //是否全局按照宽度进行等比例适配, 默认为 true, 如果设置为 false, AutoSize 会全局按照高度进行适配
                .setBaseOnWidth(true);


        //设置屏幕适配逻辑策略类, 一般不用设置, 使用框架默认的就好
//                .setAutoAdaptStrategy(new AutoAdaptStrategy())

        //初始化以全局宽度适配
        if (isPad(mContext)) {
            AutoSizeConfig.getInstance().setDesignWidthInDp(ConfigConstants.PAD_WIDTH);
        } else {
            AutoSizeConfig.getInstance().setDesignWidthInDp(ConfigConstants.PHONE_WIDTH);
        }
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
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
    public void setLandscape(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }


    /**
     * 设置屏幕为竖屏
     *
     * @param activity activity
     */
    public void setPortrait(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    /**
     * 判断是否横屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isLandscape(Context mContext) {
        return mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断是否竖屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public boolean isPortrait(Context mContext) {
        return mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * dp 转换成 px
     *
     * @param mContext
     * @param dpValue
     * @param isBaseOnWidth 是否以宽度适配
     */
    public int dp2px(Context mContext, float dpValue, boolean isBaseOnWidth) {
//        float scale = mContext.getResources().getDisplayMetrics().density;
//        float result = dpValue * scale + 0.5f;
        float scale;
        if (isBaseOnWidth) {
            if (isLandscape(mContext)) {
                scale = AutoSizeConfig.getInstance().getScreenWidth() * 1.0f / ConfigConstants.PAD_WIDTH;
            } else {
                scale = AutoSizeConfig.getInstance().getScreenWidth() * 1.0f / ConfigConstants.PHONE_WIDTH;
            }
        } else {
            if (isLandscape(mContext)) {
                scale = AutoSizeConfig.getInstance().getScreenHeight() * 1.0f / ConfigConstants.PAD_HEIGHT;
            } else {
                scale = AutoSizeConfig.getInstance().getScreenHeight() * 1.0f / ConfigConstants.PHONE_HEIGHT;
            }
        }
        int result = (int) (dpValue * scale + 0.5f);
        return result;
    }

    /**
     * px 转换成 dp
     *
     * @param mContext
     * @param pxValue
     * @param isBaseOnWidth 是否以宽度适配
     */
    public int px2dp(Context mContext, float pxValue, boolean isBaseOnWidth) {
        float scale;
        if (isBaseOnWidth) {
            if (isLandscape(mContext)) {
                scale = AutoSizeConfig.getInstance().getScreenWidth() * 1.0f / ConfigConstants.PAD_WIDTH;
            } else {
                scale = AutoSizeConfig.getInstance().getScreenWidth() * 1.0f / ConfigConstants.PHONE_WIDTH;
            }
        } else {
            if (isLandscape(mContext)) {
                scale = AutoSizeConfig.getInstance().getScreenHeight() * 1.0f / ConfigConstants.PAD_HEIGHT;
            } else {
                scale = AutoSizeConfig.getInstance().getScreenHeight() * 1.0f / ConfigConstants.PHONE_HEIGHT;
            }
        }
        int result = (int) (pxValue / scale + 0.5f);
        return result;
    }


    /**
     * 获得设备真实的信息
     * 实际显示区域 指定包含系统装饰的内容的显示部分。 即便如此，如果窗口管理器使用（adb shell wm size）模拟较小的显示器，则实际显示区域可能小于显示器的物理尺寸。 使用以下方法查询实际显示区域：getRealSize(Point)、getRealMetrics(DisplayMetrics)。
     */
    public void getEquipmentInformation(Context mContext) {
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
    public void getDisplayAreaInformation(Context mContext) {
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
