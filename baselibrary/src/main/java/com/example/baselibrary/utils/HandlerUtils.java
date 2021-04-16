package com.example.baselibrary.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Date:2021/4/16
 * Time:10:47
 * author:joker
 * Handler 工具类
 */
public class HandlerUtils {

//    public static volatile HandlerUtils handlerUtils;
//
//    public static HandlerUtils getInstance() {
//        if (handlerUtils == null) {
//            synchronized (HandlerUtils.class) {
//                if (handlerUtils == null) {
//                    handlerUtils = new HandlerUtils();
//                }
//            }
//        }
//        return handlerUtils;
//    }


    /**
     * Handler静态
     */
    public static class HandlerHolder extends Handler {

        private WeakReference<Context> mContext;
        private WeakReference<OnReceiveMessageListener> onReceiveMessageListener;

        /**
         * 使用必读：推荐在Activity或者Activity内部持有类中实现该接口，不要使用匿名类，可能会被GC
         *
         * @param context
         * @param onReceiveMessageListener
         */
        public HandlerHolder(Looper looper, Context context, OnReceiveMessageListener onReceiveMessageListener) {
            super(looper);
            this.mContext = new WeakReference<>(context);
            this.onReceiveMessageListener = new WeakReference<>(onReceiveMessageListener);
        }

        @Override
        public void handleMessage(Message msg) {
            if (StringUtils.isBlank(mContext.get())) {
                return;
            }
            if (!StringUtils.isBlank(onReceiveMessageListener) && !StringUtils.isBlank(onReceiveMessageListener.get())) {
                onReceiveMessageListener.get().handlerMessage(msg);
            }
        }
    }

    /**
     * 收到消息回调接口
     */
    public interface OnReceiveMessageListener {
        void handlerMessage(Message msg);
    }
}
