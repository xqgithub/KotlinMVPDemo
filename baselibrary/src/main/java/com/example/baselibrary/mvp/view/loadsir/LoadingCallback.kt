package com.example.baselibrary.mvp.view.loadsir

import android.content.Context
import android.view.View
import com.example.baselibrary.R
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.utils.LogUtils
import com.kingja.loadsir.callback.Callback

/**
 * Date:2022/6/30
 * Time:16:30
 * author:dimple
 */
class LoadingCallback() : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_loading
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }

    /**
     * 将Callback添加到当前视图时的回调，View为当前Callback的布局View
     */
    override fun onAttach(context: Context?, view: View?) {
//        super.onAttach(context, view)
//        LogUtils.i(ConfigConstants.TAG_ALL, "=-= onAttach")
    }

    /**
     * 将Callback从当前视图删除时的回调，View为当前Callback的布局View
     */
    override fun onDetach() {
//        super.onDetach()
//        LogUtils.i(ConfigConstants.TAG_ALL, "=-= onDetach")
    }

    /**
     * 是否在显示Callback视图的时候显示原始图(SuccessView)，返回true显示，false隐藏
     */
    override fun getSuccessVisible(): Boolean {
        return false
    }
}