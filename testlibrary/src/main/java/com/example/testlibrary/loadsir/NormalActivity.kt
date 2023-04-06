package com.example.testlibrary.loadsir

import android.os.Bundle
import android.os.SystemClock
import android.widget.TextView
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.mvp.view.loadsir.EmptyCallback
import com.example.baselibrary.mvp.view.loadsir.ErrorCallback
import com.example.baselibrary.mvp.view.loadsir.LoadingCallback
import com.example.baselibrary.mvp.view.loadsir.PostUtil
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.baselibrary.utils.loadServiceInit
import com.example.baselibrary.utils.showEmpty
import com.example.baselibrary.utils.showError
import com.example.testlibrary.R
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import kotlinx.android.synthetic.main.activity_normal.*

/**
 * Date:2022/6/30
 * Time:15:41
 * author:dimple
 */
class NormalActivity : BaseActivity() {

    var loadService: LoadService<Any>? = null

    override fun setupComponent(myAppComponet: MyAppComponet) {
    }

    override fun onBeforeSetContentLayout() {
        PublicPracticalMethodFromJAVA.getInstance()
            .transparentStatusBar(
                this,
                true, true,
                R.color.appwhite
            )
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_normal
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadService = LoadSir.getDefault().register(this) { view ->
            loadService?.showCallback(LoadingCallback::class.java)
            PostUtil.postSuccessDelayed(loadService, 3000)
        }.setCallBack(EmptyCallback::class.java) { context, view ->
            val mTvEmpty: TextView = view.findViewById(R.id.tv_empty)
            mTvEmpty.text = "fine, no data. You must fill it!"
        }

        PostUtil.postCallbackDelayed(loadService, EmptyCallback::class.java, 100)
    }
}