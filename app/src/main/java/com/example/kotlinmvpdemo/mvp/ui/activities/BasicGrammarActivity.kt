package com.example.kotlinmvpdemo.mvp.ui.activities

import android.os.Bundle
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.baselibrary.utils.clickWithTrigger
import com.example.kotlinmvpdemo.R
import com.example.kotlinmvpdemo.di.componets.DaggerBasicGrammarComponet
import com.example.kotlinmvpdemo.di.modules.BasicGrammarModule
import com.example.kotlinmvpdemo.mvp.views.BasicGrammarView
import example.com.testkotlin.haha.utils.showShortToastSafe
import kotlinx.android.synthetic.main.activity_basic_grammar.*

/**
 * kotlin-基本语法
 */
@Route(path = RouterTag.BasicGrammarActivity)
class BasicGrammarActivity : BaseActivity(), BasicGrammarView {

    override fun onBeforeSetContentLayout() {
        PublicPracticalMethodFromJAVA.getInstance()
            .transparentStatusBar(
                this,
                true, true,
                R.color.appwhite
            )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tv_one.clickWithTrigger(500) {
            val branch = et_one.text.toString().toInt()
            when (branch) {
                0 -> showShortToastSafe("请从序号1开始，哈哈")
                1 -> showShortToastSafe(sum(1, 2).toString())
                2 -> testLocalvariables()
                3 -> {
                    testArray(stringArray)
                    testArray(stringArray1)
                }
                4 -> {
                    LogUtils.i(ConfigConstants.TAG_ALL, "testConditionalJudgment  0 和 1 之间大的数字是：${testConditionalJudgment(0, 1)}")
                    LogUtils.i(ConfigConstants.TAG_ALL, "testConditionalJudgment2 0 和 1 之间大的数字是：${testConditionalJudgment2(0, 1)}")
                    testConditionalJudgment3("123456")
                    testConditionalJudgment4()
                }
                5 -> {
                    LogUtils.i(ConfigConstants.TAG_ALL, "parseInt =-= ${parseInt("")}")
                }
                6 -> {
                    LogUtils.i(ConfigConstants.TAG_ALL, "testAutomaticCasts =-= ${testAutomaticCasts("123")}")
                }
                7 -> {
                    testForCycle(stringArray, 0)
                    testForCycle(stringArray, 1)
                }
                8 -> {
                    testWhile(stringArray)
                }
                9 -> {
                    testWhen(7)
                }
                10 -> {
                    testRanges(1, 1)
                    testRanges(2, 11)
                    testRanges(3, 0)
                    testRanges(4, 0)
                }
                11 -> {
                    testContains(stringArray, "China")
                    testContains(stringArray, "USA")
                }
                12 -> {
                    traverseMapGather()
                }
                13 -> {
                    equality()
                }
                14 -> {
                    myTurtle(3, 4)
                }
                else -> showShortToastSafe("序号错误，请检查")
            }
        }
    }

    override fun setupComponent(myAppComponet: MyAppComponet) {
        DaggerBasicGrammarComponet.builder()
            .myAppComponet(myAppComponet)
            .basicGrammarModule(BasicGrammarModule(this))
            .build()
            .inject(this)
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_basic_grammar
    }

    /**
     *测试数据
     */
    val stringArray = arrayOf("China", "UK", "Germany", "Italy")
    val stringArray1 = emptyArray<String>()//长度为0的空数组


    /**
     *定义函数
     */
    fun sum(a: Int, b: Int): Int {
        return a + b
    }


    /**
     * 定义局部变量
     */
    fun testLocalvariables() {
        //常量
        val a: Int = 1
        val b = 1  // `Int` 类型自动推断
        val c: Int // 如果没有初始值，声明常量时，常量的类型不能省略
        c = 1 // 明确赋值
        LogUtils.i(ConfigConstants.TAG_ALL, "a =-= $a")
        LogUtils.i(ConfigConstants.TAG_ALL, "b =-= $b")
        LogUtils.i(ConfigConstants.TAG_ALL, "c =-= $c")

        //变量
        var x = 5 // `Int` 类型自动推断（ 5 默认是 `Int` ）
        x += 1
        LogUtils.i(ConfigConstants.TAG_ALL, "x =-= $x")
    }

    /**
     *字符串数组定义
     */
    fun testArray(args: Array<String>) {
        if (args.isEmpty()) {
            LogUtils.i(ConfigConstants.TAG_ALL, "Array is no datas")
            return
        } else {
            LogUtils.i(ConfigConstants.TAG_ALL, "First argument =-= ${args[0]}")
        }
    }

    /**
     *条件判断
     */
    //1.if的一般写法
    fun testConditionalJudgment(a: Int, b: Int): Int {
        if (a > b) {
            return a
        } else {
            return b
        }
    }

    //2.if作为表达式
    fun testConditionalJudgment2(a: Int, b: Int) = if (a > b) a else b

    //3.if not null 缩写
    fun testConditionalJudgment3(obj: Any) {
        LogUtils.i(ConfigConstants.TAG_ALL, "obj 不为空字符串 长度为  ${obj.toString().length}")
    }

    //4.if null 执行一个语句
    fun testConditionalJudgment4(a: String? = null) {
        LogUtils.i(ConfigConstants.TAG_ALL, a ?: "a 为 null")
    }

    /**
     * 空值与 null 检测
     * 当某个变量的值可以为 null 的时候，必须在声明处的类型后添加 ? 来标识该引用可为空。
     */
    fun parseInt(str: String): Int? {
        return str.toIntOrNull()
    }

    /**
     * 运算符用于类型判断
     * is 运算符检测一个表达式是否某类型的一个实例
     */
    fun testAutomaticCasts(obj: Any): Int? {
        //`obj` 在 `&&` 右边自动转换成 `String` 类型
        if (obj is String && obj.length > 0) {
            // `obj` 在该条件判断分支内自动转换成 `String`
            return obj.length
        }
        // 在离开类型判断分支后， `obj` 仍然是 `Any` 类型
        return null
    }

    /**
     *for循环
     */
    fun testForCycle(args: Array<String>, type: Int) {
        if (type == 0) {
            for (i in args.indices) {
                LogUtils.i(ConfigConstants.TAG_ALL, "args =-=  $i 是 ${args[i]}")
            }
        } else if (type == 1) {
            for (arg in args) {
                LogUtils.i(ConfigConstants.TAG_ALL, "args =-= ：$arg")
            }
        }
    }


    /**
     * while 循环
     */
    fun testWhile(args: Array<String>) {
        var index = 0
        while (index < args.size) {
            LogUtils.i(ConfigConstants.TAG_ALL, "args 在 $index 是 ${args[index]}")
            index++
        }
    }


    /**
     *when表达式
     */
    fun testWhen(obj: Any) {
        when (obj) {
            1 -> println("One")
            "Hello" -> println("Greeting")
            is Long -> println("Long")
            !is String -> println("Not a string")
            else -> println("Unknown")
        }
    }


    /**
     *使用区间（ranges）
     */
    fun testRanges(type: Int, i: Int) {
        if (type == 1) {
            if (i in 1..10) {// equivalent of 1 <= i && i <= 10
                LogUtils.i(ConfigConstants.TAG_ALL, "type=$type", "$i =-= 在区间范围内")
            } else if (i !in 1..10) {
                LogUtils.i(ConfigConstants.TAG_ALL, "type=$type", "$i =-= 不在区间范围内")
            }
        } else if (type == 2) {
            for (i in 1..10) {//遍历数字
                LogUtils.i(ConfigConstants.TAG_ALL, "type=$type", "在1-10范围内的数值：$i")
            }
        } else if (type == 3) {
            for (i in 10 downTo 1) {//遍历数字颠倒顺序
                LogUtils.i(ConfigConstants.TAG_ALL, "type=$type", "在1-10范围内的数值：$i")
            }
        } else if (type == 4) {
            for (i in 10 downTo 1 step 2) {//任意进行数量的迭代
                LogUtils.i(ConfigConstants.TAG_ALL, "type=$type", "在1-10范围内的数值：$i")
            }
        }
    }

    /**
     * 集合contains的用法
     * 使用 in 运算符来判断集合内是否包含某实例
     */
    fun testContains(args: Array<String>, arg: String) {
        if (arg in args) {
            LogUtils.i(ConfigConstants.TAG_ALL, "$arg =-=  在该集合内 ")
        } else {
            LogUtils.i(ConfigConstants.TAG_ALL, "$arg =-=  不在该集合内 ")
        }
    }


    /**
     * 遍历 map/list 中的键值对
     */
    fun traverseMapGather() {
        val map = mapOf("a" to "China", "b" to "UK", "c" to "Germany")
        for ((k, v) in map) {
            LogUtils.i(ConfigConstants.TAG_ALL, "k =-= $k", "v =-= $v")
        }
    }

    /**
     * 相等性
     * 1.三种运算符   ==, ===, equals()
     * 2.结构相等（用 equals() 检查） 由 ==（以及其否定形式 !=）操作判断
     * 3.引用相等（两个引用指向同一对象）
     */
    fun equality() {
        val s1 = "Doug"
        // 使用这种方式创建就是为了创建两个地址不同的字符串。
        val s2 = String(charArrayOf('D', 'o', 'u', 'g'))
        // 1.如果两个字符串的值相同那么hashCode也相等
        // 2. ==  equals , 比较的都是字符串的值。
        // 3. === 比较两个对象的引用是否相同。
        LogUtils.i(
            ConfigConstants.TAG_ALL, "s1 =-= $s1", "s2 =-= $s2",
            "s1.hashCode() =-= ${s1.hashCode()}", "s2.hashCode() =-= ${s2.hashCode()}",
            "s1 == s2 =-= ${s1 == s2}", "s1.equals(s2) =-= ${s1.equals(s2)}", "s1 === s2 =-= ${s1 === s2}"
        )
    }


    /**
     * 一个对象实例调用多个方法 （with）
     */
    class Turtle {
        //1.加法
        fun sum(a: Int, b: Int) {
            LogUtils.i(ConfigConstants.TAG_ALL, "a 和 b 之和 =-= ${a + b} ")
        }

        //2.减法
        fun subtraction(a: Int, b: Int) {
            LogUtils.i(ConfigConstants.TAG_ALL, "a 和 b 之差 =-= ${a - b} ")
        }
    }

    fun myTurtle(a: Int = 0, b: Int = 0) {
        val myturtle = Turtle()
        with(myturtle) {
            if (a > b) subtraction(a, b) else sum(a, b)
        }
    }


}
