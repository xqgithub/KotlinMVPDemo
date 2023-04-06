package com.example.baselibrary.mvp.view.loadsir

import com.example.baselibrary.R
import com.kingja.loadsir.callback.Callback

/**
 * Date:2022/6/30
 * Time:15:48
 * author:dimple
 */
class ErrorCallback : Callback() {

    override fun onCreateView(): Int {
        return R.layout.layout_error
    }
}