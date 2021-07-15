package com.example.testlibrary

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA

/**
 * Date:2021/6/10
 * Time:10:17
 * author:joker
 * 图片选择工具 PictureSelector  测试类
 */
@Route(path = RouterTag.TestPictureSelectorActivity)
class TestPictureSelectorActivity : BaseActivity() {

    override fun setupComponent(myAppComponet: MyAppComponet) {
    }

    override fun onBeforeSetContentLayout() {
        PublicPracticalMethodFromJAVA.getInstance().transparentStatusBar(
            this,
            false, true,
            R.color.full_red
        )
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_testpictureselector
    }
}