package com.example.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Date:2021/6/7
 * Time:11:01
 * author:joker
 * 键盘控制器
 */
public class KeyBoardUtil {

    private volatile static KeyBoardUtil mInstance;

    /**
     * 单例模式
     */
    public static KeyBoardUtil getInstance() {
        if (mInstance == null) {
            synchronized (KeyBoardUtil.class) {
                if (mInstance == null) {
                    mInstance = new KeyBoardUtil();
                }
            }
        }
        return mInstance;
    }


    /**
     * 显示键盘
     *
     * @param context
     * @param et
     */
    public void showKeyBoard(Context context, View et) {
        if (StringUtils.isBlank(context) || StringUtils.isBlank(et)) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏键盘
     *
     * @param context
     * @param et
     */
    public void hideKeyBoard(Context context, View et) {
        if (StringUtils.isBlank(context) || StringUtils.isBlank(et)) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    /**
     * 隐藏软键盘
     */
    public void hideInputMethod(Context mContext) {
        if (!(mContext instanceof Activity)) {
            return;
        }
        View token = ((Activity) mContext).getCurrentFocus();
        if (token == null) {
            return;
        }
        getInputMethodManager(mContext).hideSoftInputFromWindow(token.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 显示软键盘
     *
     * @param mContext
     */
    public void showInputMethod(Context mContext) {
        if (!(mContext instanceof Activity)) {
            return;
        }
        View token = ((Activity) mContext).getCurrentFocus();
        if (token == null) {
            return;
        }
        getInputMethodManager(mContext).showSoftInput(token, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public InputMethodManager getInputMethodManager(Context mContext) {
        return (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    }
}
