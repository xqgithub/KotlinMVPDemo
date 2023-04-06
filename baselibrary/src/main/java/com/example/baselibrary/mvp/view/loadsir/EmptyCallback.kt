package com.example.baselibrary.mvp.view.loadsir

import com.example.baselibrary.R
import com.kingja.loadsir.callback.Callback

/**
 * Date:2022/6/30
 * Time:16:00
 * author:dimple
 */
class EmptyCallback : Callback() {
    override fun onCreateView(): Int {
        return R.layout.layout_empty
    }
}