package com.example.baselibrary.mvp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import com.example.baselibrary.R;
import com.example.baselibrary.application.MyApplication;
import com.example.baselibrary.utils.StringUtils;

/**
 * Created by Administrator on 2019/7/5.
 * 自定义进度条，网络请求loading
 */

public class CustomProgressDialog extends Dialog {

    private static CustomProgressDialog customProgressDialog = null;

    private ImageView ivLoadingAnimation;

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public static CustomProgressDialog createDialog(Context context, boolean cancelableflag) {
        customProgressDialog = new CustomProgressDialog(context,
                R.style.CustomDialog);
        customProgressDialog.setContentView(R.layout.dialog_customprogress);
        customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        customProgressDialog.setCancelable(cancelableflag);//false  dialog弹出后会点击屏幕或物理返回键，dialog不消失
        customProgressDialog.setCanceledOnTouchOutside(false);//弹出后会点击屏幕，dialog不消失；点击物理返回键dialog消失
        return customProgressDialog;
    }

    private AnimationDrawable circleAnimation;

    public void onWindowFocusChanged(boolean hasFocus) {
        if (customProgressDialog == null) {
            return;
        }
        ivLoadingAnimation = customProgressDialog.findViewById(R.id.iv_loading_animation);
        if (hasFocus) {
            if (StringUtils.isBlank(circleAnimation)) {
                ivLoadingAnimation.setImageDrawable(ContextCompat.getDrawable(MyApplication.myapplication.getApplicationContext(), R.drawable.progress_circle_animation));

                circleAnimation = (AnimationDrawable) ivLoadingAnimation.getDrawable();
                circleAnimation.start();
            } else {
                if (!circleAnimation.isRunning()) {
                    circleAnimation.start();
                }
            }
        } else {
            if (!StringUtils.isBlank(circleAnimation) && circleAnimation.isRunning()) {
                circleAnimation.stop();
            }
        }

    }

    /**
     * 设置message
     *
     * @param strMessage
     * @return
     */
    public CustomProgressDialog setMessage(String strMessage) {
//        TextView tvMsg = (TextView) customProgressDialog
//                .findViewById(R.id.id_tv_loadingmsg);
//
//        if (tvMsg != null) {
//            tvMsg.setText(strMessage);
//        }

        return customProgressDialog;
    }

    @Override
    public void dismiss() {
        super.dismiss();
//        mLoadingView.reset();
    }

}
