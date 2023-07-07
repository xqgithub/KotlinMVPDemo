package com.example.kotlinmvpdemo.ndk

import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.utils.LogUtils

class Nativelib {


    var testAccessFieldName = "testAccessFieldName"

    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("nativehaha")
        }

        const val testAccessStaticFieldName = "testAccessStaticFieldName"

        fun testAccessStaticMethod() {
            LogUtils.i(ConfigConstants.TAG_ALL, " =-= 我被jni调用了,我是静态方法")
        }
    }


    /**
     * 测试jni调用kotlin中的方法
     */
    private fun testAccessMethod() {
        LogUtils.i(ConfigConstants.TAG_ALL, " =-= 我被jni调用了,我是非静态方法")
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    external fun sum(x: Int, y: Int): Int

    external fun testbasictype()

    external fun testVariableAddress()

    external fun testPointerType()

    external fun testMultistagePointer()

    external fun testPointerOperation()

    external fun testassignapointertoanarray()

    external fun testFunctionPointer(a: Int, b: Int)

    external fun testFindSmallestValue()

    external fun testDynamicArray()

    external fun testReallocateMemory()

    external fun testString()

    external fun testStructure()

    external fun testConsortium()

    external fun testEnumerate()

    external fun testOperatingFile(path: String)

    external fun testPrecompiled()

    external fun testAccessFieldAndMethod()

    external fun testAccessStaticFieldAndMethod()

    external fun sayHello(): String

    /**  JNI动态注册 **/
    external fun init()
    external fun init(age: Int)
    external fun init(name: String?): Boolean
    external fun update()
    /**  JNI动态注册 **/

    /**
     * 本数据类型 使用
     */
    external fun average(n1: Int, n2: Int): Double

    /**
     * 字符串的使用
     */
    external fun stringUse(parameter: String): String

}