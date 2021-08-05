package com.example.kotlinmvpdemo.utils;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.example.kotlinmvpdemo.R;
import com.luck.picture.lib.style.PictureCropParameterStyle;
import com.luck.picture.lib.style.PictureParameterStyle;

/**
 * Date:2021/7/28
 * Time:14:58
 * author:joker
 */
public class PictureStyleUtils {

    private volatile static PictureStyleUtils mInstance;

    public PictureCropParameterStyle mCropParameterStyle;

    /**
     * 单例模式
     */
    public static PictureStyleUtils getInstance() {
        if (mInstance == null) {
            synchronized (PictureStyleUtils.class) {
                if (mInstance == null) {
                    mInstance = new PictureStyleUtils();
                }
            }
        }
        return mInstance;
    }

    public PictureParameterStyle getNumStyle(Context mContext) {
        // 相册主题
        PictureParameterStyle mPictureParameterStyle = new PictureParameterStyle();
        // 是否改变状态栏字体颜色(黑白切换)
        mPictureParameterStyle.isChangeStatusBarFontColor = false;
        // 是否开启右下角已完成(0/9)风格
        mPictureParameterStyle.isOpenCompletedNumStyle = false;
        // 是否开启类似QQ相册带数字选择风格
        mPictureParameterStyle.isOpenCheckNumStyle = true;
        // 相册状态栏背景色
        mPictureParameterStyle.pictureStatusBarColor = Color.parseColor("#7D7DFF");
        // 相册列表标题栏背景色
        mPictureParameterStyle.pictureTitleBarBackgroundColor = Color.parseColor("#7D7DFF");
        // 相册列表标题栏右侧上拉箭头
        mPictureParameterStyle.pictureTitleUpResId = R.drawable.picture_icon_arrow_up;
        // 相册列表标题栏右侧下拉箭头
        mPictureParameterStyle.pictureTitleDownResId = R.drawable.picture_icon_arrow_down;
        // 相册文件夹列表选中圆点
        mPictureParameterStyle.pictureFolderCheckedDotStyle = R.drawable.picture_orange_oval;
        // 相册返回箭头
        mPictureParameterStyle.pictureLeftBackIcon = R.drawable.picture_icon_back;
        // 标题栏字体颜色
        mPictureParameterStyle.pictureTitleTextColor = ContextCompat.getColor(mContext, R.color.app_color_white);
        // 相册右侧取消按钮字体颜色  废弃 改用.pictureRightDefaultTextColor和.pictureRightDefaultTextColor
        mPictureParameterStyle.pictureCancelTextColor = ContextCompat.getColor(mContext, R.color.app_color_white);
        // 选择相册目录背景样式
        mPictureParameterStyle.pictureAlbumStyle = R.drawable.picture_new_item_select_bg;
        // 相册列表勾选图片样式
        mPictureParameterStyle.pictureCheckedStyle = R.drawable.picture_checkbox_num_selector;
        // 相册列表底部背景色
        mPictureParameterStyle.pictureBottomBgColor = ContextCompat.getColor(mContext, R.color.picture_color_fa);
        // 已选数量圆点背景样式
        mPictureParameterStyle.pictureCheckNumBgStyle = R.drawable.picture_num_oval_blue;
        // 相册列表底下预览文字色值(预览按钮可点击时的色值)
        mPictureParameterStyle.picturePreviewTextColor = ContextCompat.getColor(mContext, R.color.picture_color_blue);
        // 相册列表底下不可预览文字色值(预览按钮不可点击时的色值)
        mPictureParameterStyle.pictureUnPreviewTextColor = ContextCompat.getColor(mContext, R.color.app_color_blue);
        // 相册列表已完成色值(已完成 可点击色值)
        mPictureParameterStyle.pictureCompleteTextColor = ContextCompat.getColor(mContext, R.color.app_color_blue);
        // 相册列表未完成色值(请选择 不可点击色值)
        mPictureParameterStyle.pictureUnCompleteTextColor = ContextCompat.getColor(mContext, R.color.app_color_blue);
        // 预览界面底部背景色
        mPictureParameterStyle.picturePreviewBottomBgColor = ContextCompat.getColor(mContext, R.color.picture_color_fa);
        // 原图按钮勾选样式  需设置.isOriginalImageControl(true); 才有效
        mPictureParameterStyle.pictureOriginalControlStyle = R.drawable.picture_original_blue_checkbox;
        // 原图文字颜色 需设置.isOriginalImageControl(true); 才有效
        mPictureParameterStyle.pictureOriginalFontColor = ContextCompat.getColor(mContext, R.color.app_color_blue);
        // 外部预览界面删除按钮样式
        mPictureParameterStyle.pictureExternalPreviewDeleteStyle = R.drawable.picture_icon_delete;
        // 外部预览界面是否显示删除按钮
        mPictureParameterStyle.pictureExternalPreviewGonePreviewDelete = true;
//        // 自定义相册右侧文本内容设置
//        mPictureParameterStyle.pictureRightDefaultText = "";
//        // 自定义相册未完成文本内容
//        mPictureParameterStyle.pictureUnCompleteText = "";
//        // 自定义相册完成文本内容
//        mPictureParameterStyle.pictureCompleteText = "";
//        // 自定义相册列表不可预览文字
//        mPictureParameterStyle.pictureUnPreviewText = "";
//        // 自定义相册列表预览文字
//        mPictureParameterStyle.picturePreviewText = "";

//        // 自定义相册标题字体大小
//        mPictureParameterStyle.pictureTitleTextSize = 18;
//        // 自定义相册右侧文字大小
//        mPictureParameterStyle.pictureRightTextSize = 14;
//        // 自定义相册预览文字大小
//        mPictureParameterStyle.picturePreviewTextSize = 14;
//        // 自定义相册完成文字大小
//        mPictureParameterStyle.pictureCompleteTextSize = 14;
//        // 自定义原图文字大小
//        mPictureParameterStyle.pictureOriginalTextSize = 14;

        // 裁剪主题
        mCropParameterStyle = new PictureCropParameterStyle(
                ContextCompat.getColor(mContext, R.color.app_color_blue),
                ContextCompat.getColor(mContext, R.color.app_color_blue),
                ContextCompat.getColor(mContext, R.color.app_color_white),
                mPictureParameterStyle.isChangeStatusBarFontColor);
        return mPictureParameterStyle;
    }

}
