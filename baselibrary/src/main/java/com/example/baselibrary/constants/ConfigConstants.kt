package com.example.baselibrary.constants

/**
 * 全局常量(不可变的)配置说明
 */
object ConfigConstants {
    //全局url地址
    const val BASE_URL = "http://fy.iciba.com/"

    //错误日志名称
    const val ERROR_JOURNAL = "AKotlinMvpDemo"

    //应用启动，权限判断返回码
    const val PERMISSIONS_INIT_REQUEST_CODE = 1000
    //权限授权
    const val PERMISSIONS_GRANTED = 1001
    //权限拒绝
    const val PERMISSIONS_DENIED = 1002
    //权限参数
    const val EXTRA_PERMISSIONS = "me.chunyu.clwang.permission.extra_permission"
    //权限方案
    const val PACKAGE_URL_SCHEME = "package:"

    //okhttp 写超时
    const val OKHTTP_WRITE_TIME_OUT = 5L
    //okhttp 连接超时
    const val OKHTTP_CONNECT_TIME_OUT = 5L
    //okhttp 读取超时
    const val OKHTTP_READ_TIME_OUT = 5L

    //sp文件名
    const val SP_TEST_USER = "sp_test_user"


}