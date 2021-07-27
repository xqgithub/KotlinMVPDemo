package com.example.baselibrary.utils;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.view.View;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * Date:2021/7/14
 * Time:17:53
 * author:joker
 * 防止重复点击类
 */
public class RxViewUtils {

    private volatile static RxViewUtils mInstance;

    /**
     * 单例模式
     */
    public static RxViewUtils getInstance() {
        if (mInstance == null) {
            synchronized (RxViewUtils.class) {
                if (mInstance == null) {
                    mInstance = new RxViewUtils();
                }
            }
        }
        return mInstance;
    }

    @SuppressLint("CheckResult")
    public void setOnClickListeners(final Action1<View> action, long time, @NonNull View... target) {
        for (View view : target) {
            onClick(view).throttleFirst(time, TimeUnit.MILLISECONDS).subscribe(new Consumer<View>() {
                @Override
                public void accept(View view) throws Exception {
                    action.onRxViewClick(view);
                }
            });
        }
    }


    /**
     * 监听onclick事件防抖动
     *
     * @param view
     * @return
     */
    @CheckResult
    @NonNull
    private Observable<View> onClick(@NonNull View view) {
        return Observable.create(new ViewClickOnSubscribe(view));
    }

    /**
     * onclick事件防抖动
     * 返回view
     */
    private class ViewClickOnSubscribe implements ObservableOnSubscribe<View> {
        private View view;

        public ViewClickOnSubscribe(View view) {
            this.view = view;
        }

        @Override
        public void subscribe(@io.reactivex.annotations.NonNull final ObservableEmitter<View> e) throws Exception {

            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!e.isDisposed()) {
                        e.onNext(view);
                    }
                }
            };
            view.setOnClickListener(listener);
        }
    }


    /**
     * 点击事件转发接口
     *
     * @param <T> the first argument type
     */
    public interface Action1<T> {
        void onRxViewClick(T t);
    }


    public class Preconditions {
        public void checkArgument(boolean assertion, String message) {
            if (!assertion) {
                throw new IllegalArgumentException(message);
            }
        }

        public <T> T checkNotNull(T value, String message) {
            if (value == null) {
                throw new NullPointerException(message);
            }
            return value;
        }

        public void checkUiThread() {
            if (Looper.getMainLooper() != Looper.myLooper()) {
                throw new IllegalStateException(
                        "Must be called from the main thread. Was: " + Thread.currentThread());
            }
        }

        private Preconditions() {
            throw new AssertionError("No instances.");
        }
    }
}
