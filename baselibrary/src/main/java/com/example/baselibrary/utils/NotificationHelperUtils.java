package com.example.baselibrary.utils;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.widget.RemoteViews;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import com.example.baselibrary.R;
import com.example.baselibrary.constants.ConfigConstants;
import com.example.baselibrary.mvp.boradcastreceiver.NotificationBrodcaseReceiver;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * 通知栏工具
 */
public class NotificationHelperUtils implements NotificationBrodcaseReceiver.ICallBack {

    //通道渠道id
    public static final String PRIMARY_CHANNEL = "default";
    //通道渠道id
    public static final String SECONDARY_CHANNEL = "second";

    private static String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    private volatile static NotificationHelperUtils utils;

    //通知管理类
    private NotificationManager mManager;

    public NotificationHelperUtils() {
    }

    /**
     * 初始化唯一对象
     *
     * @return
     */
    public static NotificationHelperUtils getInstance() {
        if (utils == null) {
            synchronized (NotificationHelperUtils.class) {
                if (utils == null) {
                    utils = new NotificationHelperUtils();
                }
            }
        }
        return utils;
    }


    /**
     * NotificationManager 实例化
     *
     * @return
     */
    public NotificationManager getNotificationManager(Context context) {
        if (mManager == null) {
            synchronized (NotificationHelperUtils.class) {
                if (mManager == null) {
                    mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                }
            }
        }
        return mManager;
    }


    /**
     * 判断通知栏权限是否打开
     */
    //表示versionCode=19 也就是4.4的系统以及以上的系统生效。4.4以下系统默认全部打开状态。
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public Boolean isNotifacationEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (mManager != null) {
                return mManager.areNotificationsEnabled();
            }
        }
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo info = context.getApplicationInfo();
        String pag = context.getApplicationContext().getPackageName();
        int uid = info.uid;
        Class appOpsClass = null;
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method meth = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
            Field field = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (int) field.get(Integer.class);
            return ((int) meth.invoke(mAppOps, value, uid, pag) == AppOpsManager.MODE_ALLOWED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 打开权限界面
     */
    public void openPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, activity.getPackageName());
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, activity.getApplicationInfo().uid);
            activity.startActivity(intent);
        } else {
            PublicPracticalMethodFromJAVA.getInstance().toPermissionSetting(activity);
        }
    }


    /**
     * 发送普通通知
     */
    public void sendNotification(Context context, Class<?> cla, int id,
                                 String contenttitile, String contenttext) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL);
        builder.setContentTitle(contenttitile);//标题
        builder.setContentText(contenttext);//内容
        builder.setSmallIcon(R.mipmap.app_logo_shandian);//小图标
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.app_logo_jingyong));//大图标，未设置时使用小图标代替
        builder.setSubText("艾斯");//次要内容
        builder.setContentInfo("萨博");//附加内容
        builder.setWhen(System.currentTimeMillis());//设置事件发生的时间
        builder.setShowWhen(true);// 设置是否显示时间
        builder.setUsesChronometer(false);//设置是否显示时钟表示时间
//        builder.setChronometerCountDown(false);//设置时钟是否为倒计时
        /**
         * Notification.DEFAULT_SOUND	添加默认声音提醒
         * Notification.DEFAULT_VIBRATE	添加默认震动提醒
         * Notification.DEFAULT_LIGHTS	添加默认呼吸灯提醒
         * Notification.DEFAULT_ALL	同时添加以上三种默认提醒
         */
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);//设置通知选项

        builder.setOnlyAlertOnce(true);//设置只提醒一次
        builder.setOngoing(false);//设置这是否是正在进行的通知。 用户无法取消正在进行的通知

        //点击通知后，状态栏是否自动删除通知。取消通知时，将广播通过setDeleteIntent（PendingIntent）设置的PendingIntent
        //需要同时设置了 setContentIntent() 才有效
        builder.setAutoCancel(true);

        Intent intent = new Intent(context, cla);//点击通知的时候启动Activity的意图
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("key", "haha");//传递字段
        intent.putExtra("fromPush", "true");//传递字段
        int pushid = (int) System.currentTimeMillis();
        /**
         * PendingIntent.FLAG_ONE_SHOT 相同的PendingIntent只能使用一次，且遇到相同的PendingIntent时不会去更新PendingIntent中封装的Intent的extra部分的内容
         * PendingIntent.FLAG_NO_CREATE 如果要创建的PendingIntent尚未存在，则不创建新的PendingIntent，直接返回null
         * PendingIntent.FLAG_CANCEL_CURRENT 如果要创建的PendingIntent已经存在了，那么在创建新的PendingIntent之前，原先已经存在的PendingIntent中的intent将不能使用
         * PendingIntent.FLAG_UPDATE_CURRENT 如果要创建的PendingIntent已经存在了，那么在保留原先PendingIntent的同时，将原先PendingIntent封装的Intent中的extra部分替换为现在新创建的PendingIntent的intent中extra的内容
         */
        PendingIntent pendingIntent = PendingIntent.getActivity(context, pushid, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);//提供单击通知时发送的PendingIntent

        /**
         * Notification.PRIORITY_MAX	重要而紧急的通知，通知用户这个事件是时间上紧迫的或者需要立即处理的。
         * Notification.PRIORITY_HIGH	高优先级用于重要的通信内容，例如短消息或者聊天，这些都是对用户来说比较有兴趣的
         * Notification.PRIORITY_DEFAULT	默认优先级用于没有特殊优先级分类的通知
         * Notification.PRIORITY_LOW	低优先级可以通知用户但又不是很紧急的事件。只显示状态栏图标
         * Notification.PRIORITY_MIN 用于后台消息 (例如天气或者位置信息)。只有用户下拉通知抽屉才能看到内容
         */
        builder.setPriority(Notification.PRIORITY_HIGH);//优先级


        // 添加自定义声音提醒
//        builder.setSound(Uri.parse("path/to/sound"));

        // 添加自定义震动提醒
        // 延迟200ms后震动300ms，再延迟400ms后震动500ms
//        long[] pattern = new long[]{200, 300, 400, 500};
//        builder.setVibrate(pattern);

        // 添加自定义呼吸灯提醒，自动添加FLAG_SHOW_LIGHTS
//        int argb = 0xffff0000;  // led灯光颜色
//        int onMs = 300;         // led亮灯持续时间
//        int offMs = 100;        // led熄灯持续时间
//        builder.setLights(argb, onMs, offMs);


        builder.setChannelId(PRIMARY_CHANNEL);

        /**
         * Notification.FLAG_SHOW_LIGHTS	是否使用呼吸灯提醒
         * Notification.FLAG_INSISTENT 持续提醒 (声音 / 振动) 直到用户响应(点击 / 取消)
         * Notification.FLAG_ONLY_ALERT_ONCE 提醒 (铃声 / 震动 / 滚动通知摘要) 只执行一次
         * Notification.FLAG_AUTO_CANCEL 用户点击通知后自动取消
         * Notification.FLAG_ONGOING_EVENT 正在进行中通知
         * Notification.FLAG_NO_CLEAR 用户无法取消
         * Notification.FLAG_FOREGROUND_SERVICE 表示正在运行的服务
         */
        Notification notification = builder.build();
//        notification.flags = Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_AUTO_CANCEL;

        //判断是否是8.0Android.O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chan1 = new NotificationChannel(PRIMARY_CHANNEL, "Test Channel", NotificationManager.IMPORTANCE_HIGH);
            chan1.enableLights(true);
            chan1.enableVibration(true);
            chan1.setDescription(PRIMARY_CHANNEL);
            chan1.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            getNotificationManager(context).createNotificationChannel(chan1);
        }
        getNotificationManager(context).notify(id, notification);
    }

    /**
     * 发送自定义通知
     */
    public void sendNotification2(final Context context, Class<?> cla, int id,
                                  String contenttitile, String contenttext) {
        //判断是否是8.0Android.O
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(PRIMARY_CHANNEL, "Test Channel", NotificationManager.IMPORTANCE_HIGH);
            getNotificationManager(context).createNotificationChannel(channel);
            //是否绕过请勿打扰模式
            channel.canBypassDnd();
            //闪光灯
            channel.enableLights(true);
            //锁屏显示通知
            channel.setLockscreenVisibility(Notification.VISIBILITY_SECRET);
            //闪关灯的灯光颜色
            channel.setLightColor(Color.RED);
            //桌面launcher的消息角标
            channel.canShowBadge();
            //是否允许震动
            channel.enableVibration(true);
            //获取系统通知响铃声音的配置
            channel.getAudioAttributes();
            //获取通知取到组
            channel.getGroup();
            //设置可绕过  请勿打扰模式
            channel.setBypassDnd(true);
            //设置震动模式
            channel.setVibrationPattern(new long[]{100, 100, 200});
            //是否会有灯光
            channel.shouldShowLights();
            channel.setDescription(PRIMARY_CHANNEL);
            getNotificationManager(context).createNotificationChannel(channel);
        }

        //通知栏广播回调初始化
        NotificationBrodcaseReceiver.setiCallBack(this);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL);
        builder.setContentTitle(contenttitile);//标题
        builder.setContentText(contenttext);//内容
        builder.setSmallIcon(R.mipmap.app_logo_shandian);//小图标
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);//设置通知选项
        builder.setOnlyAlertOnce(true);//设置只提醒一次
        builder.setOngoing(false);//设置这是否是正在进行的通知。 用户无法取消正在进行的通知
        builder.setPriority(Notification.PRIORITY_HIGH);//优先级
        builder.setChannelId(PRIMARY_CHANNEL);
        /**
         * RemoteViews 只支持
         * 1.FrameLayout  LinearLayout  RelativeLayout  GridLayout
         * 2.AnalogClock, button, Chronometer, ImageButton, ImageView ,ProgressBar ,TextView,  ViewFlipper ListView , GridView ,StackVie,AdapterViewFlipper,ViewStub
         */
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.view_notification_customize);
        remoteViews.setTextViewText(R.id.tv_title, contenttitile);
        remoteViews.setTextViewText(R.id.tv_subtitle, contenttext);
        builder.setCustomContentView(remoteViews);


        //关闭按钮点击事件
        Intent intent = new Intent();
        intent.setAction(ConfigConstants.notifacatio_close);
        intent.putExtra("id", id);
        PendingIntent pi_close = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_close, pi_close);


        Notification notification = builder.build();
//        notification.flags = Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_AUTO_CANCEL;

        getNotificationManager(context).notify(id, notification);
    }

    /**
     * 广播回调方法
     *
     * @param id
     */
    @Override
    public void callBack(int id) {
        //清除广播
        mManager.cancel(id);
    }

//    /**
//     * 通知栏点击事件动态广播注册
//     */
//    public void registerNotificationBrodcaseRecever(Activity activity,
//                                                    NotificationBrodcaseReceiver receiver, String action) {
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(action);
//        activity.registerReceiver(receiver, filter);
//    }
//
//    /**
//     * 通知栏点击事件动态广播注销
//     */
//    public void unRegisterNotificationBrodcaseRecever(Activity activity) {
//        activity.unregisterReceiver(receiver);
//    }
}
