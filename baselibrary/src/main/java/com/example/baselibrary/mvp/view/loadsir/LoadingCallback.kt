package com.example.baselibrary.mvp.view.loadsir

import android.content.Context
import android.view.View
import com.example.baselibrary.R
import com.kingja.loadsir.callback.Callback

/**
 * Date:2022/6/30
 * Time:16:30
 * author:dimple
 */
class LoadingCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_loading
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }
}