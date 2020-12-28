package com.example.testlibrary

import android.os.Bundle
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import example.com.testkotlin.haha.utils.showShortToastSafe
import kotlinx.android.synthetic.main.activity_lambda.*

/**
 *  lambda函数和高级函数的一些用法
 */
@Route(path = RouterTag.LambdaActivity)
class LambdaActivity : BaseActivity() {
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
        return R.layout.activity_lambda
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    /**
     * 初始化数据
     */
    fun initData() {
        tv_lambda.setOnClickListener {
            try {
                when (et_lambda.text.toString()) {
                    "1" -> {
                        testLambda()
                    }
                    "2" -> {
                        LogUtils.i("testa的值为：${testa(7)}")
                    }
                    "3" -> {
                        LogUtils.i("testb的值为：${testa(77)}")
                    }
                    "4" -> {
                        testHigherOrderFunction2(4, 3) { a, b -> a + b }
                        testHigherOrderFunction2(3, 4) { a, b -> a - b }
                    }
                    "5" -> {
                        testInlie { a, b -> "$a 成为 $b" }
                    }
                    else -> showShortToastSafe("序号错误，请检查")
                }
                (it as TextView).text = "运行了序号为${et_lambda.text}的方法"
            } catch (e: Exception) {
                LogUtils.e(e.message)
            }
        }
    }

    /**
     * Lambda 表达式
     * 1.lambda表达式就是一个匿名函数
     * a.表达式总是被大括号括着
     * 2.在lambda中不可用直接使用return,可以使用return+label这种形式
     * 3.lambda中最后一个表达式的值是默认的返回值
     * 4.在使用Lambda表达式的时候，可以用下划线(_)表示未使用的参数，表示不处理这个参数
     */
    val lambda1 = { LogUtils.i("这是无参数的lambda表达式") }
    val lambda2: (Int, Int) -> Unit = { a, b ->
        LogUtils.i("参数类型在前面  lambda表达式 $a + $b = ${a + b}")
    }
    val lambda3 = { a: Int, b: Int ->
        LogUtils.i("参数类型在后面  lambda表达式 $a - $b = ${a - b}")
    }

    //测试lambda表达式
    fun testLambda() {
        lambda1()
        lambda2(7, 4)
        lambda3(7, 4)
    }

    //inline
    //1.lambda会生成一个匿名函数,增大了编译代码的体积，也带来了额外的运行时开销
    //2.Kotlin 内联函数的作用是消除 lambda 带来的额外开销
    private inline fun testInlie(a: (String, String) -> String) {
        LogUtils.i("a的值为:${a("路飞", "海贼王")}")
    }

    /**
     * 高阶函数
     * 1.参数是函数，或者返回值是函数
     * 2.参数也可以是lambda表达式
     * 3.it是在当一个高阶函数中Lambda表达式的参数只有一个的时候可以使用it来使用此参数。it可表示为单个参数的隐式名称，是Kotlin语言约定的。
     * 4.双冒号函数、匿名函数、lambda 实际上面都是函数对象
     */
    //:: 创建一个和函数具有相同功能的对象使用双冒号
    val testa = ::testHigherOrderFunction

    private fun testHigherOrderFunction(param: Int): String {
        return param.toString()
    }

    //匿名函数赋值
    val testb = fun(param: Int): String {
        return param.toString()
    }

    //自定义一个高阶函数
    private fun testHigherOrderFunction2(a: Int, b: Int, c: (Int, Int) -> Int) {
        if (a >= b) {
            LogUtils.i("a和b相减的值为:${c(a, b)}")
        } else {
            LogUtils.i("a和b想加的值为:${c(a, b)}")
        }
    }
}