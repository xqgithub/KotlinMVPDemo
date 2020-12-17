package com.example.baselibrary.mvp.toolsclass

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.utils.LogUtils

/**
 * ARouter 全局降解策略
 * 优先级低于单独降解，如果使用了单独降解后 不会走该方法
 */
@Route(path = "/service/degradeservice")
class DegradeServiceImpl : DegradeService {

    override fun onLost(context: Context?, postcard: Postcard?) {
        LogUtils.i(ConfigConstants.TAG_ALL, "失败的地址：${postcard!!.path}")
    }

    override fun init(context: Context?) {
        LogUtils.i(ConfigConstants.TAG_ALL, "DegradeService 初始化")
    }
}