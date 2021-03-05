package com.example.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.baselibrary.R;
import com.example.baselibrary.constants.ConfigConstants;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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
        double target_width = 360;
        int width_num = 360;

        DecimalFormat df = new DecimalFormat("#0.0");
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

    /**
     * 00008
     * 设置状态栏的高度
     *
     * @param mActivity
     * @param v_statusBar_top
     */
    public void setBarHeight(Activity mActivity, TextView v_statusBar_top) {
        int BarHeight = BarUtils.getStatusBarHeight(mActivity);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) v_statusBar_top.getLayoutParams();
        layoutParams.height = BarHeight;
        v_statusBar_top.setLayoutParams(layoutParams);
    }

    /**
     * 00009
     * 判断底部导航栏是否显示
     */
    private boolean isNavigationBarShow(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(activity).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }
    }


    /**
     * 00009
     * 设置底部导航栏高度
     */
    public void setNavigationBarHeight(Activity mActivity, TextView v_statusBar_bottom) {
        if (!isNavigationBarShow(mActivity)) {
            int height = mActivity.getResources().getDimensionPixelSize(R.dimen.dimen_20x);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) v_statusBar_bottom.getLayoutParams();
            layoutParams.height = height;
            v_statusBar_bottom.setLayoutParams(layoutParams);
        } else {
            Resources resources = mActivity.getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            int height = resources.getDimensionPixelSize(resourceId);//导航栏的高度
            int height1 = mActivity.getResources().getDimensionPixelSize(R.dimen.dimen_10x);
//            LogUtils.i("Navi height----->:" + height);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) v_statusBar_bottom.getLayoutParams();
            layoutParams.height = (height + height1);
            v_statusBar_bottom.setLayoutParams(layoutParams);
        }
    }


    /**
     * 00010
     * 示例 Build.VERSION_CODES.LOLLIPOP
     *
     * @param sdkVersion 需要对比的sdk版本号
     */
    public boolean isHigherSpecifySDKVersion(int sdkVersion) {
        return Build.VERSION.SDK_INT >= sdkVersion;
    }

    /**
     * 00010
     * 示例 Build.VERSION_CODES.LOLLIPOP
     *
     * @param sdkVersion 需要对比的sdk版本号
     */
    public boolean isLowerSpecifySDKVersion(int sdkVersion) {
        return Build.VERSION.SDK_INT < sdkVersion;
    }


    /**
     * 00011
     * 图片压缩---质量压缩
     * 图片的大小是没有变的，因为质量压缩不会减少图片的像素，它是在保持像素的前提下改变图片的位深及透明度等，来达到压缩图片的目的，这也是为什么该方法叫质量压缩方法
     * 图片的长，宽，像素都不变，那么bitmap所占内存大小是不会变
     * 看到bytes.length是随着quality变小而变小的。这样适合去传递二进制的图片数据，比如微信分享图片，要传入二进制数据过去，限制32kb之内
     * png格式，quality就没有作用了，bytes.length不会变化，因为png图片是无损的，不能进行压缩
     */
    public Bitmap qualityCompress(Context context, int DrawableId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), DrawableId);
        LogUtils.i("压缩前图片的大小" + (bitmap.getByteCount() / 1024 / 1024)
                + "M宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());
        for (int i = 10; i < 100; i = i + 10) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, i, baos);
            byte[] bytes = baos.toByteArray();
            ByteArrayInputStream isBm = new ByteArrayInputStream(bytes);// 把压缩后的数据baos存放到ByteArrayInputStream中
            bitmap = BitmapFactory.decodeStream(isBm, null, null);
            LogUtils.i("压缩后图片的大小" + (bitmap.getByteCount() / 1024 / 1024)
                    + "M 宽度为" + bitmap.getWidth() + " 高度为" + bitmap.getHeight()
                    + " bytes.length=" + (bytes.length / 1024) + "KB"
                    + " quality=" + i);
        }
        return bitmap;
    }

    /**
     * 00011
     * 图片压缩---采样率压缩
     * 置inSampleSize的值(int类型)后，假如设为2，则宽和高都为原来的1/2，宽高都减少了，自然内存也降低了
     * 代码没用过options.inJustDecodeBounds = true; 因为我是固定来取样的数据，为什么这个压缩方法叫采样率压缩，是因为配合inJustDecodeBounds，先获取图片的宽、高【这个过程就是取样】，然后通过获取的宽高，动态的设置inSampleSize的值
     * inJustDecodeBounds设置为true的时候，BitmapFactory通过decodeResource或者decodeFile解码图片时，将会返回空(null)的Bitmap对象，这样可以避免Bitmap的内存分配，但是它可以返回Bitmap的宽度、高度以及MimeType
     */
    public Bitmap samplingRateCompress(Context context, int DrawableId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream is = context.getResources().openRawResource(DrawableId);
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;
//        LogUtils.i("压缩前图片的大小" + (bitmap.getByteCount() / 1024 / 1024)
//                + "M宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());
        options.inSampleSize = 1;
        options.inJustDecodeBounds = false;
        Bitmap bitmap1 = BitmapFactory.decodeStream(context.getResources().openRawResource(DrawableId), null, options);

        LogUtils.i("压缩后图片的大小" + (bitmap1.getByteCount() / 1024 / 1024)
                + "M 宽度为" + bitmap1.getWidth() + " 高度为" + bitmap1.getHeight());
        return bitmap1;
    }


    /**
     * 00011
     * 图片压缩---缩放压缩
     * bitmap的长度和宽度分别缩小了一半，图片大小缩小了四分之一
     */
    public Bitmap martixCompress(Context context, int DrawableId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), DrawableId);
//        LogUtils.i("压缩前图片的大小" + (bitmap.getByteCount() / 1024 / 1024)
//                + "M宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());
        Matrix matrix = new Matrix();
        matrix.setScale(1f, 1f);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
//        LogUtils.i("压缩后图片的大小" + (bitmap.getByteCount() / 1024 / 1024)
//                + "M 宽度为" + bitmap.getWidth() + " 高度为" + bitmap.getHeight());
        return bitmap;
    }

    /**
     * 00011
     * 图片压缩---RGB_565法
     */
    public void RGB565(Context context, int DrawableId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), DrawableId);
        LogUtils.i("压缩前图片的大小" + (bitmap.getByteCount() / 1024 / 1024)
                + "M宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());
        InputStream is = context.getResources().openRawResource(DrawableId);
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmap = BitmapFactory.decodeStream(is, null, options2);
        LogUtils.i("压缩后图片的大小" + (bitmap.getByteCount() / 1024 / 1024)
                + "M 宽度为" + bitmap.getWidth() + " 高度为" + bitmap.getHeight());
    }

}
