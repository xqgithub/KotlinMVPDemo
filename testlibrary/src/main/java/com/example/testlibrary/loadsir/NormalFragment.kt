package com.example.testlibrary.loadsir

import android.view.View
import android.widget.Toast
import com.example.baselibrary.base.BaseLoadSirFragment
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.mvp.view.loadsir.ErrorCallback
import com.example.baselibrary.mvp.view.loadsir.LoadingCallback
import com.example.baselibrary.mvp.view.loadsir.PostUtil
import com.example.testlibrary.R
import kotlinx.android.synthetic.main.fragment_normal.*


/**
 * Date:2022/7/1
 * Time:9:58
 * author:dimple
 */
class NormalFragment : BaseLoadSirFragment() {

    override fun setupComponent(myAppComponet: MyAppComponet) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_normal
    }

    override fun lazyLoad() {
        PostUtil.postCallbackDelayed(mBaseLoadService, ErrorCallback::class.java)
    }

    override fun onNetReload(view: View) {
        tv_result_a.text = "Oh, Yes."
        Toast.makeText(context, "reload in Fragment A", Toast.LENGTH_SHORT).show()
        mBaseLoadService.showCallback(LoadingCallback::class.java)
        PostUtil.postSuccessDelayed(mBaseLoadService, 3000)
    }

    override fun loadNet() {
    }
}