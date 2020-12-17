package com.example.baselibrary.mvp.toolsclass

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.PretreatmentService
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.utils.LogUtils

/**
 * ARouter 预处理
 * 先走预处理服务，再走IInterceptor拦截服务
 */
@Route(path = RouterTag.PretreatmentService)
class PretreatmentServiceImpl : PretreatmentService {
    override fun onPretreatment(context: Context?, postcard: Postcard?): Boolean {
        //是否需要导航
        var isPretreatmen = true
        return isPretreatmen
    }

    override fun init(context: Context?) {
        LogUtils.i(ConfigConstants.TAG_ALL, "PretreatmentService 初始化")
    }
}