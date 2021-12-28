package com.example.kotlinmvpdemo.mvp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.FileUtils
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.*
import com.example.kotlinmvpdemo.R
import com.example.kotlinmvpdemo.di.componets.DaggerNDKPractiseComponet
import com.example.kotlinmvpdemo.di.modules.NDKPractiseModule
import com.example.kotlinmvpdemo.mvp.views.NDKPractiseView
import com.example.kotlinmvpdemo.ndk.nativelib
import example.com.testkotlin.haha.utils.showShortToastSafe
import kotlinx.android.synthetic.main.activity_ndk_practise.*

/**
 * NDK 测试
 */
@Route(path = RouterTag.NDKPractiseActivity)
class NDKPractiseActivity : BaseActivity(), NDKPractiseView {

    private lateinit var nativelib: nativelib

    override fun onBeforeSetContentLayout() {
        PublicPracticalMethodFromJAVA.getInstance()
            .transparentStatusBar(
                this,
                true, true,
                R.color.appwhite
            )
    }

    override fun setupComponent(myAppComponet: MyAppComponet) {
        DaggerNDKPractiseComponet.builder()
            .myAppComponet(myAppComponet)
            .nDKPractiseModule(NDKPractiseModule(this))
            .build()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ndk_practise)
        nativelib = nativelib()

        tv_one.clickWithTrigger(500) {
            val branch = et_one.text.toString().toInt()
            when (branch) {
                0 -> showShortToastSafe("请从序号1开始，哈哈")
                1 -> {
                    testOne()
                }
                2 -> {
                    testTwo()
                }
                /** 3-9 指针的定义和使用  **/
                3 -> {
                    testThree()
                }
                4 -> {
                    testFour()
                }
                5 -> {
                    testFive()
                }
                6 -> {
                    testSix()
                }
                7 -> {
                    testSeven()
                }
                8 -> {
                    testEight(3, 4)
                }
                9 -> {
                    testNine()
                }
                /** 10-11 动态内存分配  **/
                10 -> {
                    testTen()
                }
                11 -> {
                    testEleven()
                }
                /** 12 字符串  **/
                12 -> {
                    testtwelve()
                }
                /** 13 结构体  **/
                13 -> {
                    testStructure()
                }
                /** 14 共同体  **/
                14 -> {
                    testConsortium()
                }
                /** 15 枚举  **/
                15 -> {
                    testEnumerate()
                }
                /** 16 文件读写 **/
                16 -> {
                    testOperatingFile()
                }
                else -> showShortToastSafe("序号错误，请检查")
            }

        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_ndk_practise
    }

    /**
     * 1. 测试 NDK 是否能实现
     */
    fun testOne() {
        val a = nativelib.sum(3, 4)
        val b = nativelib.stringFromJNI()

        LogUtils.i(
            ConfigConstants.TAG_ALL,
            "a =-= $a",
            "b =-= $b"
        )
    }


    /**
     * 2. 测试 C语言中的 基本数据类型
     */
    fun testTwo() {
        nativelib.testbasictype()
    }

    /**
     * 3. 变量地址和指针
     */
    fun testThree() {
        nativelib.testVariableAddress()
    }

    /**
     * 4. 利用指针做简单的游戏外挂(DLL注入方式)
     */
    fun testFour() {
        nativelib.testPointerType()
    }

    /**
     * 5. 多级指针
     */
    fun testFive() {
        nativelib.testMultistagePointer()
    }

    /**
     * 6. 指针运算
     */
    fun testSix() {
        nativelib.testPointerOperation()
    }

    /**
     * 7. 通过使用指针给数组赋值
     */
    fun testSeven() {
        nativelib.testassignapointertoanarray()
    }

    /**
     * 8. 函数指针
     */
    fun testEight(a: Int, b: Int) {
        nativelib.testFunctionPointer(a, b)
    }

    /**
     * 9.写一个函数查找最小的值
     */
    fun testNine() {
        nativelib.testFindSmallestValue()
    }

    /**
     * 10.动态指定数组的大小
     */
    fun testTen() {
        nativelib.testDynamicArray()
    }


    /**
     * 11.重新分配内存
     */
    fun testEleven() {
        nativelib.testReallocateMemory()
    }

    /**
     * 12.字符串
     */
    fun testtwelve() {
        nativelib.testString()
    }

    /**
     * 13.结构体
     */
    fun testStructure() {
        nativelib.testStructure()
    }

    /**
     * 14.共同体
     */
    fun testConsortium() {
        nativelib.testConsortium()
    }

    /**
     * 15.枚举
     */
    fun testEnumerate() {
        nativelib.testEnumerate()
    }

    /**
     * 16.文件读写
     */
    fun testOperatingFile() {
        val path = SDCardUtils.getExternalFilesDir(this@NDKPractiseActivity, "").absolutePath + "/helloworld.txt"
        if (FileUtils.isFileExists(path)) {
            nativelib.testOperatingFile(path)
        }
    }
}
