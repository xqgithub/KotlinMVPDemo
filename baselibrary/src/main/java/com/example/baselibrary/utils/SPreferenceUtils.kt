package com.example.baselibrary.utils

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by beijixiong on 2018/9/21.
 * SharePreference 工具类
 * filename 保存的文件名
 * elementname 保存的字段名
 * value     保存的类型
 */
class SPreferenceUtils<T>(val context: Context, val filename: String, val elementname: String, val value: T) :
    ReadWriteProperty<Any?, T> {

    val prefs by lazy {
        context.getSharedPreferences(filename, Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(elementname, value)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(elementname, value)
    }

    /**
     * 保存数据
     * 1.apply没有返回值而commit返回boolean表明修改是否提交成功
     * 2.apply是将修改数据原子提交到内存, 而后异步真正提交到硬件磁盘, 而commit是同步的提交到硬件磁盘
     * 3.apply方法不会提示任何失败的提示,commit会有提示返回结果
     */
    private fun <U> putPreference(name: String, value: U) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }.apply()
    }

    private fun <U> putPreference2(name: String, value: U): Boolean = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }.commit()
    }

    /**
     * 查询数据
     */
    private fun <U> findPreference(name: String, value: U): U = with(prefs) {
        val res: Any? = when (value) {
            is Long -> getLong(name, value)
            is String -> getString(name, value)
            is Int -> getInt(name, value)
            is Boolean -> getBoolean(name, value)
            is Float -> getFloat(name, value)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }
        res as U
    }


}