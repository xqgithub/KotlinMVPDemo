package com.example.baselibrary.mvp.toolsclass

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.SerializationService
import com.example.baselibrary.constants.RouterTag
import com.google.gson.Gson
import java.lang.reflect.Type

/**
 * 用withObject 传值的时候 需要先写一个 JsonServiceImpl类 继承 SerializationService
 */
@Route(path = RouterTag.SerializationService)
class JsonServiceImpl : SerializationService {

    var gson: Gson? = null


    override fun <T : Any?> json2Object(input: String?, clazz: Class<T>?): T {
        return gson!!.fromJson(input, clazz)
    }

    override fun init(context: Context?) {
        gson = Gson()
    }

    override fun object2Json(instance: Any?): String {
        return gson!!.toJson(instance)
    }

    override fun <T : Any?> parseObject(input: String?, clazz: Type?): T {
        return gson!!.fromJson(input, clazz)
    }

}