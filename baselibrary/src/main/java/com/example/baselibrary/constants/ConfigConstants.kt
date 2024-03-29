package com.example.baselibrary.constants

import com.example.baselibrary.application.MyApplication
import com.example.baselibrary.utils.SDCardUtils
import java.io.File

/**
 * 全局常量(不可变的)配置说明
 */
object ConfigConstants {
    //全局url地址
    const val BASE_URL = "http://fy.iciba.com/"

    //错误日志名称
    const val ERROR_JOURNAL = "AKotlinMvpDemo"

    //全局的TAG
    const val GLOBAL_TAG = "=-="

    /** 应用启动，权限判断返回码 **/
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

    /** sp文件名 **/
    const val SP_TEST_USER = "sp_test_user"

    /** 全局数据库名称 **/
    const val DB_SQL_NAME = "kotlinmvpdemo"

    /** 存储相关常量 **/
    //Byte与Byte的倍数
    const val BYTE = 1

    //KB与Byte的倍数
    const val KB = 1024

    //MB与Byte的倍数
    const val MB = 1024 * KB

    //GB与Byte的倍数
    const val GB = 1024 * MB

    /** 时间相关常量 **/
    //毫秒与毫秒的倍数
    const val MSEC = 1

    //秒与毫秒的倍数
    const val SEC = 1000 * MSEC

    //分与毫秒的倍数
    const val MIN = 60 * SEC

    //时与毫秒的倍数
    const val HOUR = 60 * MIN

    //天与毫秒的倍数
    const val DAY = 24 * HOUR

    /** gilde配置数据 **/
    //gild缓存大小
    const val GILDE_DISKCACHESIZEBYTES = 1024 * 1024 * 100 // 100 MB

    //gilde缓存路径
    val GILDE_DISKCACHEDIR =
        SDCardUtils.getExternalFilesDir(
            MyApplication.myapplication.applicationContext,
            null
        ).absolutePath + File.separator + "AGlideImage"

    /** 版本差异配置 **/
    //1.AndroidManifest中meta-data的package_id的key
    const val PACKAGE_ID = "package_id"

    //2.AndroidManifest中meta-data的app_version的key
    const val APP_VERSION = "app_version"

    //1.测试版本
    const val ceshi = "package_10001"

    //2.生产版本
    const val shengchan = "package_10002"


    /** 广播action字段 **/
    const val notifacatio_close = "notifacatio_close"

    /** TAG名称 **/
    const val TAG_ALL = "=-= KotlinMVPDemo =-="


    /** 录屏功能 **/
    //录屏的名字
    const val screenrecord_name = "myfirstscreenrecord"

    //视频保存的目录
    const val screenrecord_dir = "AScreenRecord"

    //视频的文件名
    const val screenrecord_filename = "haha.mp4"


    /** UI设计标准 **/
    //pad的设计图宽
    const val PAD_WIDTH = 1024

    //pad的设计图高
    const val PAD_HEIGHT = 768

    //phone的设计图宽
    const val PHONE_WIDTH = 360

    //phone的设计图高
    const val PHONE_HEIGHT = 640

    /** TestPictureSelectorActivity **/
    //可以继续添加图片
    const val TYPE_CAMERA = -10000

    //不能继续添加图片
    const val TYPE_PICTURE = -9999


    /** PermissionsActivity 权限判断后，执行方法 **/
    //启动页需要申请的权限
    const val PERMISSIONS_GRANTED_STARTUPACTIVITY = -99999

    //日历管理权限
    const val PERMISSIONS_GRANTED_CALENDAR_MANAGEMENT = -99998


}
