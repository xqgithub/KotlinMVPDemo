package com.example.baselibrary.mvp.toolsclass

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.StringUtils

/**
 * ARouter 拦截器 类
 */
class ARouterInterceptor {

    /**
     * 测试拦截器1
     * @param priority 数值越高，优先级越低
     */
    @Interceptor(priority = 1)
    class Test1Interceptor : IInterceptor {
        /**
         * 拦截方法
         * 可以在调到页面之前做一些处理
         */
        override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
            //要拦截的地址
            val path = postcard!!.path
            if (StringUtils.compared(RouterTag.TestProductFlavorsActivity, path)) {
                LogUtils.i(ConfigConstants.TAG_ALL, "拦截器 ${Test1Interceptor::class.java.name} 进行了拦截")
            }

            //传值，这里的key如果跟ARouter.getInstance().build中的一样会导致外面的值被替换掉
            postcard.withString("interceptor_key1", "haha")
            //继续执行
            callback!!.onContinue(postcard)
            //有问题需要处理
//            callback!!.onInterrupt(Exception("拦截有问题，需要停止"))
        }

        /**
         * 拦截器初始化，只初始化一次，在ARouter 初始化的时候 一起初始化
         */
        override fun init(context: Context?) {
            LogUtils.i(ConfigConstants.TAG_ALL, "拦截器 ${Test1Interceptor::class.java.name} 初始化")
        }
    }

    /**
     * 测试拦截器2
     */
    @Interceptor(priority = 10)
    class Test2Interceptor : IInterceptor {
        override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
            //要拦截的地址
            val path = postcard!!.path
            if (StringUtils.compared(RouterTag.TestProductFlavorsActivity, path)) {
                LogUtils.i(ConfigConstants.TAG_ALL, "拦截器 ${Test2Interceptor::class.java.name} 进行了拦截")
            }
            //继续执行
            callback!!.onContinue(postcard)
        }

        override fun init(context: Context?) {
            LogUtils.i(ConfigConstants.TAG_ALL, "拦截器 ${Test2Interceptor::class.java.name} 初始化")
        }
    }


}