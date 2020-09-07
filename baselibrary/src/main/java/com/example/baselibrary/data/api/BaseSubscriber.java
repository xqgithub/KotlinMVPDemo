package com.example.baselibrary.data.api;

import android.app.Activity;
import android.content.Context;
import com.example.baselibrary.R;
import com.example.baselibrary.application.MyApplication;
import com.example.baselibrary.mvp.dialog.CustomProgressDialog;
import com.example.baselibrary.utils.LogUtils;
import com.example.baselibrary.utils.NetworkUtils;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 订阅基类
 * 带有loading框的
 * 在home键后台，接口能可以调用
 */
public abstract class BaseSubscriber<T> implements Observer<T> {

    public abstract void onSuccess(T t);

    public abstract void onFailure(String message, int error_code);


    private Context mContext;
    //是否显示弹框标识
    private boolean showdialogflag;
    //是否可以点击取消标识
    private boolean cancelableflag;

    public Disposable disposable;


    /**
     * @param mContext       Activity 上下文
     * @param showdialogflag 是否显示loading框
     * @param cancelableflag 物理返回键 是否消失
     */
    public BaseSubscriber(Context mContext,
                          boolean showdialogflag,
                          boolean cancelableflag) {
        this.mContext = mContext;
        this.showdialogflag = showdialogflag;
        this.cancelableflag = cancelableflag;
    }

    /**
     * 事件还未发送之前被调用，可以用于做一些准备工作，例如数据的清零或重置
     */
//    @Override
//    public void onStart() {
//        super.onStart();
//        if (!NetworkUtils.isConnected()) {
//            //1.没有网络取消订阅
//            unsubscribe();
//            //2.发送错误信息
//            onFailure(MyApplication.myapplication.getApplicationContext().getString(R.string.error_nonetwork), 800);
//        } else {
//            try {
//                progressdialog(mContext, "",
//                        showdialogflag,
//                        cancelableflag,
//                        true);
//            } catch (Exception e) {
//                LogUtils.e(e.getMessage());
//            }
//        }
//    }
    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;

        if (!NetworkUtils.isConnected()) {
            disposable.dispose();
            onFailure(MyApplication.myapplication.getApplicationContext().getString(R.string.error_nonetwork), 800);
        } else {
            try {
                progressdialog(mContext, "",
                        showdialogflag,
                        cancelableflag,
                        true);
            } catch (Exception e) {
                LogUtils.e(e.getMessage());
            }
        }
    }

    @Override
    public void onNext(T t) {
        ApiResponse2 br = (ApiResponse2) t;
        if (br == null) {
            onFailure(MyApplication.myapplication.getApplicationContext().getString(R.string.prompt_requestfailed), ExceptionHandle.INTERNAL_SERVER_ERROR);
            return;
        }
        int error_code = 0;
        if (br.getStatus() == 1) {
            onSuccess(t);
        } else {
            error_code = -1;
            onFailure("解析错误", error_code);
        }
    }

    /**
     * 事件队列完成
     */
    @Override
    public void onComplete() {
        try {
            progressdialog(mContext, "",
                    showdialogflag,
                    cancelableflag,
                    false);
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        } finally {
            disposable.dispose();
        }
    }

    /**
     * 事件队列异常
     */
    @Override
    public void onError(Throwable e) {
        try {
            LogUtils.e(e.getMessage());
            //1.发送错误信息
            onFailure(MyApplication.myapplication.getApplicationContext().getString(R.string.prompt_busynetwork) + ExceptionHandle.handleException(e).code, 777);
//            2. 启动轮询弹框
//            StaticStateUtils.detectDomainWhetherNormal(App.getApplication());
            //3.取消掉loading框
            progressdialog(mContext, "",
                    showdialogflag,
                    cancelableflag,
                    false);
        } catch (Exception e1) {
            LogUtils.e(e1.getMessage());
        } finally {
            disposable.dispose();
        }
    }


    /**
     * 开始加载进度条
     *
     * @param context        上下文
     * @param msg            进度条内容
     * @param showdialog     是否创建dialog标识
     * @param cancelableflag dialog弹出后会点击屏幕或物理返回键是否有效果
     * @param showflag       adapter.show或者dialog.diss
     */
    private CustomProgressDialog cProgressDialog;

    private void progressdialog(Context context, String msg, boolean showdialog,
                                boolean cancelableflag, boolean showflag) {
        if (showdialog) {
            if (!((Activity) context).isFinishing()) {
                if (showflag) {
                    if (cProgressDialog == null) {
                        cProgressDialog = CustomProgressDialog
                                .createDialog(context, cancelableflag);
                        cProgressDialog.setMessage(msg);
                    }
                    cProgressDialog.show();
                } else {//停止加载
                    if (cProgressDialog != null && cProgressDialog.isShowing()) {
                        cProgressDialog.dismiss();
                        cProgressDialog = null;
                    }
                }
            } else {//停止加载
                if (cProgressDialog != null && cProgressDialog.isShowing()) {
                    cProgressDialog.dismiss();
                    cProgressDialog = null;
                }
            }
        }
    }


}

