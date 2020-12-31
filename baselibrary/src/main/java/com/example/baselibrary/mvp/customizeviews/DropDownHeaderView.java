package com.example.baselibrary.mvp.customizeviews;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.baselibrary.R;
import com.example.baselibrary.application.MyApplication;
import com.example.baselibrary.utils.StringUtils;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;
import com.scwang.smart.refresh.layout.simple.SimpleComponent;

/**
 * 自定义下拉刷新headerView
 */
public class DropDownHeaderView extends SimpleComponent implements RefreshHeader {

    private ImageView iv_loading_animation;
    private TextView tv_name;

    private AnimationDrawable circleAnimation;

    public static String REFRESH_HEADER_PULLING = "下拉可以刷新";//"下拉可以刷新";
    public static String REFRESH_HEADER_LOADING = "正在加载...";//"正在加载...";
    public static String REFRESH_HEADER_RELEASE = "释放立即刷新";
    public static String REFRESH_HEADER_REFRESHING = "正在刷新";
    public static String REFRESH_HEADER_FINISH = "刷新成功";//"刷新完成";
    public static String REFRESH_HEADER_FAILED = "刷新失败";//"刷新失败";


    public DropDownHeaderView(Context context) {
        this(context, null);
    }


    public DropDownHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public DropDownHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.view_dropdown_headerview, this);
        tv_name = view.findViewById(R.id.tv_name);
        iv_loading_animation = view.findViewById(R.id.iv_loading_animation);
        iv_loading_animation.setImageDrawable(ContextCompat.getDrawable(MyApplication.myapplication.getApplicationContext(), R.drawable.progress_circle_animation));
    }


    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        if (success) {
            tv_name.setText(REFRESH_HEADER_FINISH);
        } else {
            tv_name.setText(REFRESH_HEADER_FAILED);
        }
        return super.onFinish(refreshLayout, success);
    }


    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                tv_name.setText(REFRESH_HEADER_PULLING);
                break;
            case Refreshing:
            case RefreshReleased:
                tv_name.setText(REFRESH_HEADER_REFRESHING);
                break;
            case ReleaseToRefresh:
                tv_name.setText(REFRESH_HEADER_RELEASE);
                break;
            case ReleaseToTwoLevel:
                break;
            case Loading:
                tv_name.setText(REFRESH_HEADER_LOADING);
                break;
        }
    }


    /**
     * 动画
     *
     * @param animationflag 动画标识 true 启动动画 flase 停止动画
     */
    private void dropDownAnimation(boolean animationflag) {
        if (animationflag) {
            if (StringUtils.isBlank(circleAnimation)) {
                circleAnimation = (AnimationDrawable) iv_loading_animation.getDrawable();
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
}
