package com.example.baselibrary.mvp.boradcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.baselibrary.constants.ConfigConstants;
import com.example.baselibrary.utils.StringUtils;

/**
 * 通知栏广播监听
 */
public class NotificationBrodcaseReceiver extends BroadcastReceiver {

    public static ICallBack miCallBack;

    public static void setiCallBack(ICallBack iCallBack) {
        miCallBack = iCallBack;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (!StringUtils.isBlank(action)) {
            if (ConfigConstants.notifacatio_close.equals(action)) {
                miCallBack.callBack(intent.getIntExtra("id", 0));
            }
        }
    }

    /**
     * 回调接口
     */
    public interface ICallBack {
        void callBack(int id);
    }
}
