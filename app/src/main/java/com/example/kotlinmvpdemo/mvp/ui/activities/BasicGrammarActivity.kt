package com.example.kotlinmvpdemo.mvp.ui.activities

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.application.MyApplication
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.mvp.entity.*
import com.example.baselibrary.utils.*
import com.example.kotlinmvpdemo.R
import com.example.kotlinmvpdemo.di.componets.DaggerBasicGrammarComponet
import com.example.kotlinmvpdemo.di.modules.BasicGrammarModule
import com.example.kotlinmvpdemo.mvp.views.BasicGrammarView
import com.example.kotlinmvpdemo.utils.JavaAlternately
import example.com.testkotlin.haha.utils.showShortToastSafe
import kotlinx.android.synthetic.main.activity_basic_grammar.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.lang.Exception
import kotlin.properties.Delegates

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
                /*** 1-14 kotlin的基本语法及使用 ***/
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
                /*** 15-16 kotlin的 返回和跳转 ***/
                15 -> {
                    testLabel()
                }
                16 -> {
                    testReturn(1, 20)
                }
                /*** 17-18  kotlin的 类和继承  ***/
                17 -> {
                    testStudent()
                }
                18 -> {
                    testChildren()
                }
                /*** 19  kotlin的 数据类  ***/
                19 -> {
                    testDatatypeOne()
                }
                /*** 20-22  kotlin的  泛型  ***/
                20 -> {
                    testGeneric()
                }
                21 -> {
                    myGeneric(Box(10).value)
                    myGeneric(Box("lufei").value)
                }
                22 -> {
                    testBox()
                }
                /*** 23  kotlin的  集合  ***/
                23 -> {
                    testList()
                    testMutableList()
                }
                /*** 24  kotlin的  委托和委托属性  ***/
                24 -> {
                    testCommission()
                }
                /*** 25  kotlin的  延迟属性  ***/
                25 -> {
                    testLazy()
                }
                /*** 26-27  kotlin的  观察属性  ***/
                26 -> {
                    testObservable()
                }
                27 -> {
                    testVetoable()
                }
                /*** 28  kotlin的   map委托  ***/
                28 -> {
                    var map = mapOf("name" to "路飞", "age" to 25, "signup" to true)
                    testMapCommission(map)
                }
                /*** 29-31  kotlin的   函数  ***/
                29 -> {

                    LogUtils.i(
                        ConfigConstants.TAG_ALL,
                        "testFunction =-= ${testFunction()}",
                        "testFunction2 =-= ${testFunction(y = 4)}",
                    )
                }
                30 -> {
                    val testfunction2 = testFunction2(strings = *arrayOf("a", "b", "c"))
                    LogUtils.i(
                        ConfigConstants.TAG_ALL,
                        "testFunction2 =-= $testfunction2",
                    )
                }
                31 -> {
                    val testfunction4 = "雀雀" testFunction4 8
                    LogUtils.i(
                        ConfigConstants.TAG_ALL,
                        "testfunction4 =-= $testfunction4",
                    )
                }
                /*** 32  kotlin的   lambda表达式  ***/
                32 -> {
                    testLambda()
                }
                /*** 33-35  kotlin的  高阶函数  ***/
                33 -> {
                    val ints = arrayListOf(1, 2, 3, 4, 5)
                    var newInts = ints.testHSFunction1 { it.toString() + "谢谢观赏" }
                    LogUtils.i(
                        ConfigConstants.TAG_ALL,
                        "newInts =-= $newInts",
                    )
                }
                34 -> {
                    LogUtils.i(
                        ConfigConstants.TAG_ALL,
                        "testHSFunction2_type1 =-= ${testHSFunction2(2) { it > 5 }}",
                        "testHSFunction2_type2 =-= ${testHSFunction2(10) { it > 5 }}",
                    )
                }
                35 -> {
                    LogUtils.i(
                        ConfigConstants.TAG_ALL,
                        "两数相加 =-= ${testHSFunction3(3, 4) { a, b -> a + b }}",
                        "两数相减 =-= ${testHSFunction3(3, 4) { a, b -> a - b }}",
                        "两数相乘 =-= ${testHSFunction3(3, 4) { a, b -> a * b }}",
                        "两数相除 =-= ${testHSFunction3(3, 4) { a, b -> a / b }}"
                    )
                }
                /*** 36-44 kotlin的  标准库扩展函数集合(高阶段函数)  ***/
                36 -> {
                    testApply()
                }
                37 -> {
                    testLet()
                }
                38 -> {
                    testWith()
                }
                39 -> {
                    testRun1()
                    testRun2(3)
                    testRun3()
                }
                40 -> {
                    testUse()
                }
                41 -> {
                    testRepeat()
                }
                42 -> {
                    val file = File(filepath)
                    test_require_assert_check(file, true)
                }
                43 -> {
                    testTakeIf("ko")
                    testTakeIf("ok")
                }
                44 -> {
                    testTakeUnless("ko")
                    testTakeUnless("ok")
                }
                /*** 45-46 kotlin的  闭包  ***/
                45 -> {
                    LogUtils.i(
                        ConfigConstants.TAG_ALL,
                        "testClosure =-= ${testClosure(3)(5)}"
                    )
                }
                46 -> {
                    testClosure2()
                }
                /*** 47-48 kotlin的  inline内联函数  ***/
                47 -> {
                    testInline(3, 4) { a: Int, b: Int -> a * b }
                    testInline(3, 4) { a: Int, b: Int -> a + b }
                }
                48 -> {
                    testInline2(stringArray)
                }
                /*** 49 kotlin的  reified的使用  ***/
                49 -> {
                    try {
                        val a = testReified<Int>(10)
                        val b = testReified<Float>(10)

                        LogUtils.i(
                            ConfigConstants.TAG_ALL,
                            "a =-= $a",
                            "b =-= $b"
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                /*** 50 kotlin的  解构声明  ***/
                50 -> {
                    testDestructure()
                }
                /*** 51 kotlin的  空安全 ***/
                51 -> {
                    testEmptySafety(null)
                    testEmptySafety("lufei")
                }
                /*** 52 java代码 调用 kotlin代码 ***/
                52 -> {
                    val javaAlternately = JavaAlternately()
                    javaAlternately.testKotlinFromJava1()
                    javaAlternately.testKotlinFromJava2()
                    javaAlternately.testKotlinFromJava3()
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
     * 1
     *定义函数
     */
    fun sum(a: Int, b: Int): Int {
        return a + b
    }


    /**
     * 2
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
     * 3
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
     * 4
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
     * 5
     * 空值与 null 检测
     * 当某个变量的值可以为 null 的时候，必须在声明处的类型后添加 ? 来标识该引用可为空。
     */
    fun parseInt(str: String): Int? {
        return str.toIntOrNull()
    }

    /**
     * 6
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
     * 7
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
     * 8
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
     * 9
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
     * 10
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
     * 11
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
     * 12
     * 遍历 map/list 中的键值对
     */
    fun traverseMapGather() {
        val map = mapOf("a" to "China", "b" to "UK", "c" to "Germany")
        for ((k, v) in map) {
            LogUtils.i(ConfigConstants.TAG_ALL, "k =-= $k", "v =-= $v")
        }
    }

    /**
     * 13
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
     * 14
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


    /**
     * 15
     * break。终止最直接包围它的循环。
     * continue。继续下一次最直接包围它的循环。
     */
    fun testLabel() {
        for (i in 1..10) {
            loop@ for (j in 1..10) {
                if (j == 7) {
                    LogUtils.i(ConfigConstants.TAG_ALL, "i =-= $i", "j =-= $j")
                    break@loop
                }
            }
        }
    }

    /**
     * 16
     * forEach
     * return。默认从最直接包围它的函数或者匿名函数返回。
     */
    fun testReturn(a: Int, b: Int) {
        (a..b).forEach continuing@{
            if (it <= 10) {
                return@continuing
            }
            LogUtils.i(ConfigConstants.TAG_ALL, "大于10的数字有  =-= $it ")
        }
        LogUtils.i(ConfigConstants.TAG_ALL, "testReturn  end")
    }

    /**
     * 17
     * 测试 类
     */
    fun testStudent() {
        val student = Student("wahaha")
        student.lastName = "haizeiwang"
        LogUtils.i(ConfigConstants.TAG_ALL, "student.lastName =-= ${student.lastName}")

        student.num = 7
        LogUtils.i(ConfigConstants.TAG_ALL, "student.num =-= ${student.num}")

        student.num = 17
        LogUtils.i(ConfigConstants.TAG_ALL, "student.num =-= ${student.num}")


        val student2 = Student("xixixi", 17)
    }

    /**
     * 18
     * 测试类的继承
     */
    fun testChildren() {
        val children = Children("lufei", "monkey")
        LogUtils.i(
            ConfigConstants.TAG_ALL, "children.base_a =-= ${children.base_a}",
            "children.base_b =-= ${children.base_b}"
        )

        //调用子类的方法
        children.allowRewrite()

        //调用父类的方法
        children.unAllowRewrite()

        //内部类方法a
        children.SecondChild().testa()
        //内部类方法b
        children.SecondChild().testb()
    }

    /**
     * 19
     * 数据类
     */
    data class Datatype(var name: String, var age: Int)

    data class MyDatatype(var no: Int, var datatype: Datatype) {
        override fun hashCode(): Int {
            return super.hashCode()
        }
    }

    fun testDatatypeOne() {
        //1.copy()函数实现的功能是复制一个对象，然后修改其一部分属性，但保持其他属性保持不变
        //2.copy并不是简单的复制，它会创建新的对象，对于基本类型的属性，其值会被拷贝并赋值
        var datatype = Datatype("路飞", 17)
        var datatype2 = datatype.copy(name = "索隆")
        LogUtils.i(
            ConfigConstants.TAG_ALL, "datatype =-= $datatype",
            "datatype2 =-= $datatype2"
        )
        //3.“=”号赋值和copy()在执行机制上还是有区别的。在对copy使用时，还是要注意对引用类型的浅拷贝
        var datatype3 = Datatype("路飞", 17)
        var datatype4 = datatype3
        datatype3.name = "娜美"
        datatype3.age = 20
        LogUtils.i(
            ConfigConstants.TAG_ALL, "datatype3 =-= $datatype3",
            "datatype4 =-= $datatype4"
        )
        //
        var mydatatype: MyDatatype = MyDatatype(1, datatype)
        var datatype5 = Datatype("乌索普", 17)
        var mydatatype2: MyDatatype = mydatatype.copy(no = 2)
        var mydatatype3: MyDatatype = mydatatype.copy(datatype = datatype5)
        LogUtils.i(
            ConfigConstants.TAG_ALL, "mydatatype =-= $mydatatype",
            "mydatatype2 =-= $mydatatype2", "mydatatype3 =-= $mydatatype3"
        )
        //4.但是对于引用类型，其只是将其地址传递过去，还是进行浅拷贝，并没有创建新的对象
        mydatatype.datatype.name = "罗宾"
        LogUtils.i(
            ConfigConstants.TAG_ALL, "mydatatype =-= $mydatatype",
            "mydatatype2 =-= $mydatatype2", "mydatatype3 =-= $mydatatype3"
        )

        //5.数据的解构
        var datatype6 = Datatype("山治", 17)
        val (name, age) = datatype6
        LogUtils.i(
            ConfigConstants.TAG_ALL, "我的名字是 =-= $name",
            "我的年龄是 =-= $age"
        )
    }

    /**
     * 20
     * 泛型方法
     */
    fun <T> myGeneric(value: T) {
        when (value) {
            is Int -> {
                LogUtils.i(ConfigConstants.TAG_ALL, "value的值 =-= ${value / 2}")
            }
            is String -> {
                LogUtils.i(ConfigConstants.TAG_ALL, "value的值 =-= ${value.toUpperCase()}")
            }
            else -> LogUtils.i(ConfigConstants.TAG_ALL, "value的值 =-= 不是整型或者String")
        }
    }

    fun testGeneric() {
        val age = 23
        val name = "Jone"
        val person = true

        myGeneric(age)
        myGeneric(name)
        myGeneric(person)
    }


    /**
     * 21
     * 泛型类
     * 既将泛型作为函数参数，又将泛型作为函数的输出，那就既不用 in 或 out
     */
    class Box<T>(t: T) {
        var value = t
    }

    /**
     * 22
     * 声明处的类型变异
     * 生产者 out(协变)
     * 如果你的类是将泛型作为内部方法的返回，那么可以用 out
     */
    class Box1<out T>(val t: T) {
        fun foo(): T {
            return t
        }
    }

    /**
     * 消费者 in（逆变）
     * 如果你的类是将泛型对象作为函数的参数，那么可以用 in
     */
    class Box2<in T>() {
        fun foo(t: T): String {
            if (t is String) {
                return "这是一个String类型"
            } else {
                return "这不是一个String类型"
            }
        }
    }

    fun testBox() {
        var box1: Double = Box1(1.00).foo()
        var box2 = Box2<Any>().foo("haha")

        LogUtils.i(
            ConfigConstants.TAG_ALL, "box1 中的返回值 =-= $box1",
            "box2 =-= $box2"
        )
    }

    /**
     * 23
     * 集合归纳
     */

    //list在Kotlin中也就是一个只读的集合
    fun testList() {
        val list = listOf<String>(//新建list
            "瓦洛兰",
            "德玛西亚",
            "班德尔城",
            "诺克萨斯",
            "祖安",
            "皮尔特沃夫",
            "艾欧尼亚",
            "李青",
            "阿利斯塔",
            "希维尔",
            "潘森",
            "伊泽瑞尔",
            "雷克顿",
            "古拉加斯",
            "奥利安娜",
            "崔斯塔娜",
            "泰达米尔",
            "马尔扎哈",
            "卡西奥佩娅",
            "艾尼维亚"
        )

        val count = list.size     //集合中元素的数量  Int
        val isNull = list.isEmpty()   //判断集合是否为空  Boolean
        val isContains = list.contains("李青")  //判断集合中是否包含某种元素     Boolean
        val list2 = listOf<String>(
            "瓦洛兰",
            "德玛西亚",
            "班德尔城",
            "诺克萨斯",
            "祖安"
        )
        val isContainsAll = list.containsAll(list2)   //判断集合中是否包含另一个集合    Boolean
        val indexStr = list.get(2)    //查询集合中某个位置的元素值 <E>
        val index = list.indexOf("李青")    //返回集合中某个元素首次出现的索引，如果不存在则返回-1 Int
        val lastIndex = list.lastIndexOf("李青")    //返回集合中某个元素最后出现的索引，如果不存在则返回-1   Int

        val iterator = list.iterator()    //返回该只读集合的元素迭代器     Iterator
        val listIterator = list.listIterator()    //返回一个集合的迭代器    ListIterator
        val listIteratorIndex = list.listIterator(2)  //从指定位置开始，返回一个集合的迭代器    ListIterator
        val subList = list.subList(1, 9)   //返回集合中从1到9之间的集合    List

        LogUtils.i(
            ConfigConstants.TAG_ALL,
            "集合数量 count =-= $count",
            "集合数量是否为空 isNull =-= $isNull",
            "集合是否包含某种元素 isContains =-= $isContains",
            "集合是否包含另一个集合 isContainsAll =-= $isContainsAll",
            "集合中某个位置的元素值 indexStr =-= $indexStr",
            "集合中某个元素首次出现的位置 index =-= $index",
            "集合中某个元素最后出现的索引 lastIndex =-= $lastIndex"
        )
    }

    //对集合进行修改，我们应该使用MutableList
    fun testMutableList() {

        val mutableList = mutableListOf<String>(
            "伊泽瑞尔",
            "雷克顿",
            "古拉加斯",
            "奥利安娜",
            "崔斯塔娜",
            "泰达米尔",
            "马尔扎哈",
            "卡西奥佩娅",
            "艾尼维亚"
        )
        val list2 = listOf<String>(
            "瓦洛兰",
            "德玛西亚",
            "班德尔城",
            "诺克萨斯",
            "祖安"
        )

        val isAddOk = mutableList.add("祖安")     //添加一个元素，返回true或false   Boolean
        val isAddIndexOk = mutableList.add(2, "班德尔城")  //在指定位置添加一个元素   Unit
        val isRemoveOk = mutableList.remove("李青")    //移除集合中的一个元素，返回true或false    Boolean
        val isRemoveAtOk = mutableList.removeAt(3)    //移除指定为位置的元素    <E>
        val isAddAllOk = mutableList.addAll(list2)   //添加另一个集合，返回true或false       Boolean
        val isRemoveAllOk = mutableList.removeAll(list2)  //移除一个集合，返回true或false   Boolean
        val isSetOk = mutableList.set(2, "诺克萨斯")   //替换指定位置的元素，返回原元素   <E>
        val isClearOk = mutableList.clear()   //清空集合中得元素 Unit
        val list4 = mutableList.toList()  //tolist是一个扩展函数，可以赋值list内的内容，返回一个只读的list

    }

    /**
     * 24
     * 委托和委托属性
     * 1.有一种属性，在使用的时候每次都要手动实现它，但是可以做到只实现一次，并且放到库中，一直使用，这种属性称为委托属性
     */

    var test_sp: String by SPreferenceUtils(this, "xiaoqueque", "Company", "路飞")//默认存的值是路飞

    fun testCommission() {
        //委托类测试
        val realGamePlayer = RealGamePlayer("路飞")
        val delegateGamePlayer = DelegateGamePlayer(realGamePlayer)
        delegateGamePlayer.rank()
        delegateGamePlayer.upgrade()

        //委托属性测试
        realGamePlayer.delegated_properties_a = "the shy"
        realGamePlayer.delegated_properties_b = 20

        LogUtils.i(
            ConfigConstants.TAG_ALL, "delegated_properties_a =-= ${realGamePlayer.delegated_properties_a}",
            "delegated_properties_b =-= ${realGamePlayer.delegated_properties_b}"
        )

        test_sp = "小路飞"
        LogUtils.i(ConfigConstants.TAG_ALL, "test_sp =-= ${test_sp}")
    }

    /**
     * 25
     * 延迟属性 lazy()
     * 1.lazy()接收一个Lambdas表达式，然后并返回一个Lazy <T>的实例，它可以作为实现lazy属性的委托
     * 2.lazy属性，只能声明为 val的，即它是只能get的
     * 3.LazyThreadSafetyMode.SYNCHRONIZED 同步,使lazy延迟初始化线程安全
     * 4.LazyThreadSafetyMode.PUBLICATION  内部的计算过程可能被多次访问，但内部的属性是一个Volatile修饰的属性，所以在多线程环境中，只有第一个返回的值作为初始化的值
     * 5.LazyThreadSafetyMode.NONE         没有同步锁，多线程访问时候，初始化的值是未知的，非线程安全，一般情况下，不推荐使用这种方式，除非你能保证初始化和属性始终在同一个线程
     */

    val lazyValue: String by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        LogUtils.i(
            ConfigConstants.TAG_ALL, "只有第一次调用才会执行 lambda 表达式,后面调用只会返回lambda表达式的最终值"
        )
        "路飞，海贼王"
    }

    fun testLazy() {
        LogUtils.i(ConfigConstants.TAG_ALL, "延迟属性 lazyValue1 =-= $lazyValue")
        LogUtils.i(ConfigConstants.TAG_ALL, "延迟属性 lazyValue2 =-= $lazyValue")
    }

    /**
     * 26.观察属性 Observable
     * 1.Delegates.observable()有两个参数：初始化值和handler，每次对属性赋值操作，都会回调该handler方法（在属性赋值后执行），该方法里面有三个参数，分别是：被赋值的属性，旧值和新值。
     * 2.<no name>就是初始化值，{}包住的代码块就是handler方法，pro, old, new就是该方法的三个参数
     * 3.如果需要拦截修改属性值动作并禁止修改，可以使用vetoable()取代observable()
     * 4.property：在这指属性name;
     *   oldValue： 旧值，首次即为指定的初始值
     *   newValue：新set的值
     *   最终属性值=new值
     * 5.Delegates.vetoable() true:属性值=new值; false：属性值=old值
     */

    var observableValue: String by Delegates.observable("路飞") { prop, old, new ->
        LogUtils.i(
            ConfigConstants.TAG_ALL,
            "prop =-= $prop",
            "old =-= $old",
            "new =-= $new"
        )
    }

    fun testObservable() {
        observableValue = "索隆"
        observableValue = "娜美"
    }

    var vetoableValue: Int by Delegates.vetoable(0) { prop, old, new ->
        new > old
    }

    /**
     * 27 观察属性
     * Delegates.vetoable()
     */
    fun testVetoable() {
        vetoableValue = 10
        LogUtils.i(ConfigConstants.TAG_ALL, "vetoableValue =-= $vetoableValue")
        vetoableValue = 5
        LogUtils.i(ConfigConstants.TAG_ALL, "vetoableValue2 =-= $vetoableValue")
        vetoableValue = 20
        LogUtils.i(ConfigConstants.TAG_ALL, "vetoableValue3 =-= $vetoableValue")
    }

    /**
     * 28 Map委托属性
     * 可以用Map作为委托用于委托属性，多用于JSON解析上,暂时还没有发现这种使用场景的好处或者优势
     */

    fun testMapCommission(map: Map<String, Any?>) {
        val name: String by map
        val age: Int by map
        val signup: Boolean by map

        LogUtils.i(
            ConfigConstants.TAG_ALL,
            "name =-= $name",
            "age =-= $age",
            "signup =-= $signup"
        )
    }

    /**
     * 29 函数
     * 1.函数使用 fun 关键字声明
     * 2.参数使用 Pascal 表示法定义，即 name: type。参数用逗号隔开。每个参数必须有显式类型
     * 3.函数参数可以有默认值，当省略相应的参数时使用默认值。与其他语言相比，这可以减少重载数量
     * 4.覆盖方法总是使用与基类型方法相同的默认参数值。 当覆盖一个带有默认参数值的方法时，必须从签名中省略默认参数值
     */
    fun testFunction(x: Int = 1, y: Int = 2): Int {
        return 2 * (x + y)
    }

    /**
     * 30 可以通过使用星号操作符将可变数量参数（vararg） 以命名形式传入
     */
    fun testFunction2(vararg strings: String): String {
        return strings[0]
    }

    /**
     * 31 中缀表示法
     * 1.标有 infix 关键字的函数也可以使用中缀表示法（忽略该调用的点与圆括号）调用
     * 2.满足条件
     * a.它们必须是成员函数或扩展函数
     * b.它们必须只有一个参数
     * c.其参数不得接受可变数量的参数且不能有默认值
     * 3.中缀函数调用的优先级低于算术操作符、类型转换以及 rangeTo 操作符
     * 4.中缀函数调用的优先级高于布尔操作符 && 与 ||、is- 与 in- 检测以及其他一些操作符
     */
    infix fun String.testFunction4(x: Int): String {
        return if (x < 7) {
            "$this =-= 好小"
        } else {
            "$this =-= 好大"
        }
    }

    /**
     *  32 Lambda 表达式
     *  1.lambda表达式就是一个匿名函数
     *  a.表达式总是被大括号括着
     *  b.其参数(如果存在)在 -> 之前声明(参数类型可以省略)
     *  c.函数体(如果存在)在 -> 后面
     *  2.在lambda中不可用直接使用return
     *  3.可以使用return+label这种形式
     *  4.lambda中最后一个表达式的值是默认的返回值
     *  5.在使用Lambda表达式的时候，可以用下划线(_)表示未使用的参数，表示不处理这个参数
     */
    //无参数样式
    val test_lambda1 = { LogUtils.i(ConfigConstants.TAG_ALL, "这是lambda无参数样式") }

    //有参数样式
    val test_lambda2: (Int, Int) -> Unit = { a, b ->
        LogUtils.i(
            ConfigConstants.TAG_ALL,
            "a =-= $a",
            "b =-= $b",
            "a+b =-= ${a + b}",
        )
    }

    val test_lambda3 = { a: Int, b: Int ->
        LogUtils.i(
            ConfigConstants.TAG_ALL,
            "a =-= $a",
            "b =-= $b",
            "a+b =-= ${a + b}",
        )
    }

    var test_lambda4 = { a: Int, b: Int, c: Boolean ->
        if (c) {
            a - b
        } else {
            a + b
        }
    }


    fun testLambda() {
        test_lambda1()
        test_lambda2(1, 2)
        test_lambda3(3, 4)
        LogUtils.i(
            ConfigConstants.TAG_ALL,
            "test_lambda4 one =-= ${test_lambda4(3, 4, true)}",
            "test_lambda4 two =-= ${test_lambda4(3, 4, false)}"
        )
    }


    /**
     * 33 高阶段函数
     * 1.参数是函数，或者返回值是函数
     * 2.参数也可以是lambda表达式
     * 3.it是在当一个高阶函数中Lambda表达式的参数只有一个的时候可以使用it来使用此参数。it可表示为单个参数的隐式名称，是Kotlin语言约定的。
     */
    fun <T, R> List<T>.testHSFunction1(transform: (T) -> R): List<R> {
        val result = arrayListOf<R>()
        for (item in this)
            result.add(transform(item))
        return result
    }

    /**
     * 34 it的例子
     */
    fun testHSFunction2(num1: Int, bool: (Int) -> Boolean): Int {
        return if (bool(num1)) num1 else 0
    }

    /**
     * 35 自定义高阶函数
     */
    fun testHSFunction3(num1: Int, num2: Int, result: (Int, Int) -> Int): Int {
        return result(num1, num2)
    }

    /**
     * 36
     * Kotlin 标准库扩展函数集合(高阶段函数) 详情 请参考：Kotlin 扩展函数详解与应用 第四点   https://blog.csdn.net/ComWill/article/details/77206508
     * 1.apply      接受一个lambda表达式作为参数，并在apply调用时立即执行，apply返回原来的对象
     * 2.lazy       lazy延迟运算，当第一次访问时，调用相应的初始化函数
     * 3.also       T.also函数来说，它和T.apply很相似   不同的是T.also中只能使用it调用自身,而T.apply中只能使用this调用自身
     */
    fun testApply() {
        tv_testtwo.apply {
            text = "apply方法测试"
            textSize = 20f
            setTextColor(Color.parseColor("#ff4545"))
            setOnClickListener {
                showShortToastSafe("我是apply测试方法")
            }
        }
    }

    /**
     * 37
     * let        let 和 apply 类似， 唯一的不同是返回值，let返回的不是原来的对象，而是闭包里面的值
     */
    fun testLet() {
        val a = "kotlin".let {
            it.reversed()
        }.let {
            it.plus("-java")
        }
        LogUtils.i(
            ConfigConstants.TAG_ALL,
            "a =-= $a"
        )
    }

    /**
     * 38
     * with       with 是一个顶级函数， 当你想调用对象的多个方法但是不想重复对象引用
     */
    fun testWith() {
        val b = with(mutableListOf<String>()) {
            add("a")
            add("b")
            add("c")
            this
        }
        LogUtils.i(
            ConfigConstants.TAG_ALL,
            "b =-= $b"
        )
    }

    /**
     * 39
     * run        run 是 with和let 的组合
     * 1.当我们需要执行一个代码块的时候就可以用到这个函数,并且这个代码块是独立的。即我可以在run()函数中写一些和项目无关的代码，因为它不会影响项目的正常运行。
     */
    fun testRun1() {
        val a = "kotlin"
        run {
            val a = "java"
            LogUtils.i(
                ConfigConstants.TAG_ALL,
                "testRun1 a =-= $a"
            )
        }
        LogUtils.i(
            ConfigConstants.TAG_ALL,
            "testRun1 a =-= $a"
        )
    }

    /**
     * 2.因为run函数执行了我传进去的lambda表达式并返回了执行的结果，所以当一个业务逻辑都需要执行同一段代码而根据不同的条件去判断得到不同结果的时候。可以用到run函数
     */
    fun testRun2(index: Int) {
        val result = run {
            when (index) {
                0 -> "kotlin"
                1 -> "java"
                2 -> "php"
                3 -> "javaScript"
                else -> "none"
            }
        }
        LogUtils.i(
            ConfigConstants.TAG_ALL,
            "testRun2 result =-= $result"
        )
    }

    /**
     * 3.T.run()函数 所以当我们传入的lambda表达式想要使用当前对象的上下文的时候，我们可以使用这个函数
     */
    fun testRun3() {
        val str = "kotlin"
        str.run {
            LogUtils.i(
                ConfigConstants.TAG_ALL,
                "testRun3 str =-= $str"
            )
        }
    }

    /**
     * 40
     * use        自动关闭资源
     * use 用在 Java 上的 try-with-resources 表达式上 ,user高阶函数内部做了很多异常的处理和最后自动close释放资源
     */
    val filepath = SDCardUtils.getExternalFilesDir(MyApplication.myapplication.applicationContext, "xixihaha").absolutePath + File.separator + "testuse.txt"
    fun testUse() {
        FileUtils.deleteFile(filepath)
        FileUtils.createOrExistsFile(filepath)
        FileUtils.writeFileFromString(filepath, "路飞", true)
        FileUtils.writeFileFromString(filepath, "\r\n娜美", true)
        FileUtils.writeFileFromString(filepath, "\r\n索隆", true)

        BufferedReader(FileReader(filepath)).use {
            var line: String?
            while (true) {
                line = it.readLine() ?: break
                LogUtils.i(
                    ConfigConstants.TAG_ALL,
                    "line =-= $line"
                )
            }
        }
    }

    /**
     * 41
     * reapt      顾名思义，repeat 就是重复的意思，它接受函数和整数作为参数，函数会被调用 n 次，这个函数避免写循环
     */
    fun testRepeat() {
        repeat(5) { i ->
            LogUtils.i(
                ConfigConstants.TAG_ALL,
                "循环运行第${i + 1}次。"
            )
        }
    }

    /**
     * 42
     * require/assert/check
     * 1.require(boolean) 用来检测方法的参数，当参数boolean为false时，抛出IllegalArgumentException
     * 2.check(boolean)用来检测对象的状态（属性），如果boolean为false，抛出异常IllegalStateException
     * 3.assert(boolean) 用来检测执行结果，当boolean为false时，抛出AssertionError。但是需要在开启对应的JVM选项时才生效
     * 先使用check检测对象的状态，再使用require检测方法的参数合法性，执行操作后，使用assert校验结果
     */
    @SuppressLint("Assert")
    fun test_require_assert_check(file: File?, isStarted: Boolean) {
        try {
            check(isStarted) {
                LogUtils.i(
                    ConfigConstants.TAG_ALL,
                    "isStarted is false"
                )
            }

            requireNotNull(file) {
                LogUtils.i(
                    ConfigConstants.TAG_ALL,
                    "file 为空"
                )
            }

            assert(file.exists()) {
                LogUtils.i(
                    ConfigConstants.TAG_ALL,
                    "file 不存在"
                )
            }

            LogUtils.i(
                ConfigConstants.TAG_ALL,
                "file.absoluteFile =-= ${file.absoluteFile}"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    /**
     * 43
     * takeif  传入一个你希望的一个条件，如果对象符合你的条件则返回自身，反之，则返回null
     */
    fun testTakeIf(parameter: String) {
        val str = "kotlin"
        val result = str.takeIf {
            it.startsWith(parameter)
        }

        result?.let {
            LogUtils.i(
                ConfigConstants.TAG_ALL,
                "result =-= $result"
            )
        } ?: let {
            LogUtils.i(
                ConfigConstants.TAG_ALL,
                "result =-= 为空"
            )
        }
    }

    /**
     * 44
     * takeUnless 这个函数的作用和T.takeIf()函数的作用是一样的。只是和其的逻辑是相反的
     */
    fun testTakeUnless(parameter: String) {
        val str = "kotlin"
        val result = str.takeUnless {
            it.startsWith(parameter)
        }

        result?.let {
            LogUtils.i(
                ConfigConstants.TAG_ALL,
                "result =-= $result"
            )
        } ?: let {
            LogUtils.i(
                ConfigConstants.TAG_ALL,
                "result =-= 为空"
            )
        }
    }

    /**
     * 45 闭包
     * 所谓闭包，即是函数中包含函数，这里的函数我们可以包含(Lambda表达式，匿名函数，局部函数，对象表达式)
     */
    //让函数返回一个函数，并携带状态值
    fun testClosure(a: Int): (b: Int) -> String {
        return fun(b): String {
            return if ((a + b) > 7) "我比7要大" else "我比7要小"
        }
    }

    /**
     * 46
     * 引用外部变量，并改变外部变量的值
     */
    fun testClosure2() {
        var result = 0
        val arr = arrayOf(1, 3, 5, 7, 9)
        arr.filter {
            it < 7
        }.forEach {
            result += it
        }
        LogUtils.i(
            ConfigConstants.TAG_ALL,
            "result =-= $result"
        )
    }

    /**
     * 47  内联函数
     * 1. 使用高阶函数会带来一些运行时的效率损失：每一个函数都是一个对象，并且会捕获一个闭包。 即那些在函数体内会访问到的变量。 内存分配（对于函数对象和类）和虚拟调用会引入运行时间开销
     * 2. 但是在许多情况下通过内联化 lambda 表达式可以消除这类的开销
     * 3. 对频繁调用的代码内联化，可以提高代码效率降低调用成本
     * 4. 内联可能导致编译器生成的代码增加,但如果使用得当(不内联大函数),在性能上有很大提升,尤其是在循环的megamorphic处调用
     * 5. 内联目前主要还是应用在 函数参数是lambda表达式的代码中
     * 6. noinline 修饰符标  可以禁止
     */
    inline fun testInline(a: Int, b: Int, c: (a: Int, b: Int) -> Int) {
        LogUtils.i(
            ConfigConstants.TAG_ALL,
            "result =-= ${c(a, b)}"
        )
        val d = c(a, b)
        return
    }

    /**
     * 48
     * 非局部返回
     * 1.内联函数 如果被处理成了Lambda表达式的时候，return 返回的是外部那个调用内联函数的函数，而不是内联函数本身
     * 2.forEach 是一个内联函数
     * 3.crossinline 的作用是让被标记的lambda表达式不允许非局部返回
     */
    fun testInline2(args: Array<String>) {
        args.forEach {
            if (it == "Germany") {
                return
            }
        }
        LogUtils.i(
            ConfigConstants.TAG_ALL,
            "args 中没有找到相关数据"
        )
    }

    /**
     * 49
     * reified 具体化的类型参数,类型传递简化泛型参数
     */
    inline fun <reified T> testReified(value: Int): T {
        val temporary_result = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(), this.resources.displayMetrics
        )

        return when (T::class) {
            Float::class -> temporary_result as T
            Int::class -> temporary_result.toInt() as T
            else -> throw IllegalStateException("不支持该类型")
        }
    }

    /**
     * 50
     * 结构声明，将一个对象解构(destructure)为多个变量，也就是意味着一个解构声明会一次性创建多个变量
     * 1.声明了多个变量
     * 2.将对象的属性值赋值给相应的变量
     * 3.对于普通类的成员属性，编译器并不会自动推断产生componentN() ，此时componentN()，需要我们自己定义了
     * a.componentN()函数需要标记为 operator , 才可以在解构声明中使用
     * b.componentN()函数的返回值类型必须与属性类型一致
     */
    fun testDestructure() {
        var destructureEntity = DestructureEntity("路飞", 20, "东海")
        destructureEntity.mobile = "12345678"

        var (name, age, addr, mobile) = destructureEntity
        LogUtils.i(
            ConfigConstants.TAG_ALL,
            "name =-= $name",
            "age =-= $age",
            "addr =-=$addr",
            "mobile =-=$mobile"
        )
    }

    /**
     * 51 空安全
     * 1.if...else...判断  是否为空
     * 2.使用符号?.判断  如果可空类型变量为null时，返回null
     * 3.链式调用
     * 4.变量?.let{ ... }
     * 5.?:操作符  当我们定义了一个可空类型的变量时，如果该变量不为空，则使用，反之使用另外一个不为空的值
     * 6.!!操作符  !!操作符可谓是给爱好空引用异常（NullPointException）的开发者使用，因为在使用一个可空类型变量时，在该变量后面加上!!操作符，会显示的抛出NullPointException异常
     * 7.as?操作符 其实这里是指as操作符，表示类型转换，如果不能正常转换的情况下使用as?操作符。当使用as操作符的使用不能正常的转换的情况下会抛出类型转换（ClassCastException）异常，而使用as?操作符则会返回null,但是不会抛出异常
     */
    fun testEmptySafety(str: String?) {

        //第一种方式 if...else...
        if (str == null) {
            LogUtils.i(
                ConfigConstants.TAG_ALL,
                "one str =-= null"
            )
        } else {
            LogUtils.i(
                ConfigConstants.TAG_ALL,
                "one str =-= $str",
                "one 长度为 =-= ${str.length}"
            )
        }

        //第二种方式  ?.判断
        LogUtils.i(
            ConfigConstants.TAG_ALL,
            "two str =-= $str",
            "two 长度为 =-= ${str?.length}"
        )

        //第三种方式 ?:操作符
        LogUtils.i(
            ConfigConstants.TAG_ALL,
            "three str =-= $str",
            "three 长度为 =-= ${str?.length ?: -1}"
        )

        //第四种方式  !!操作符
        try {
            LogUtils.i(
                ConfigConstants.TAG_ALL,
                "four str =-= $str",
                "four 长度为 =-= ${str!!.length}"
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LogUtils.e(
                ConfigConstants.TAG_ALL,
                "four str =-= ${e.message}",
            )
        }

        //第五种方式  ?.let{ ... }
        str?.let {
            LogUtils.i(
                ConfigConstants.TAG_ALL,
                "five str =-= $it",
                "five 长度为 =-= ${it.length}"
            )
        } ?: let {
            LogUtils.i(
                ConfigConstants.TAG_ALL,
                "five str =-= null"
            )
        }
    }

    /**
     * 52   Java 调用 Kotlin
     */
    companion object {
        /**
         * 静态字段(Static Fields)
         * 1.@JvmField 注解    用@JvmField注解属性,使对应的java静态字段与kotlin属性可见性相同(默认public)
         * 2.lateinit 修饰符   用lateinit修饰属性,使对应的静态字段与属性访问器setter可见性相同(默认public)
         * 3.const 修饰符      用const修饰的(在类中以及在顶层)属性,在Java中会成为public静态字段
         */
        @JvmField
        val testa: Int = 3

        lateinit var testb: String

        const val testc = 7

        /**
         * 静态方法(Static Methods)
         * 1.在对象(object)或伴生对象(companion object)中的函数被@JvmStatic注解,那么编译器既会在该对象的类中生成静态方法,也会在对象自身中生成实例方法!
         * 2.此外,@JvmStatic注解也可用于对象或伴生对象的属性,使其getter和setter方法是静态成员!
         */
        @JvmStatic
        fun testa() {
            LogUtils.i(
                ConfigConstants.TAG_ALL,
                "java 调用 kotlin 方法testa"
            )
        }

        fun testb() {
            LogUtils.i(
                ConfigConstants.TAG_ALL,
                "java 调用 kotlin 方法testb"
            )
        }

        /**
         * 可见性(Visibility)
         * 1.private成员编译成Java的private成员;
         * 2.private顶层声明编译成Java的包级局部声明(package-local)；
         * 3.protected编译成Java的protected(Java允许访问同包中其它类的protected成员,所以Java类会访问权限更广);
         * 4.internal声明编译成Java的public;
         * 5.public编译成Java的public;
         */

        /**
         * KClass
         * 有时在java中调用Kotlin函数(有KClass类型参数),但无法把Java的Class自动转换成kotlin的KClass,所以必须通过调用Class<T>.kotlin扩展属性来手动转换
         * kotlin.jvm.JvmClassMappingKt.getKotlinClass(MainView.class)
         */

        /**
         * @JvmName解决java方法签名相同
         * 1.在Kotlin中用相同名称,需要用@JvmName标注其中的一个(或两个),并指定不同名称
         * 2.在Kotlin中,可以用相同名称filterValid()访问
         * 3.在Java中,需要分别用filterValid()和filterValidInt()访问
         */


        /**
         * Java方法重载
         * 1.如果Kotlin函数的参数有默认值并且使用@JvmOverloads注解,那么在Java中多个重载方法
         * 2.该注解也适用于构造函数和静态方法, 但不能用于抽象方法(包括接口的方法)
         * 3.对于次构造函数(Secondary Constructors), 如果所有参数都有默认值,那么会生成一个公有public的无参构造函数(没有@JvmOverloads注解也会生效)!
         */
        @JvmOverloads
        fun testc(a: String, b: Int = 0, c: String = "abc") {
            LogUtils.i(
                ConfigConstants.TAG_ALL,
                "a =-= $a",
                "b =-= $b",
                "c =-= $c"
            )
        }

        /**
         * 受检异常
         * 使用@Throws注解(相当于在Java中声明throws IOException)
         */

        /**
         * 型变泛型
         * 1.在默认没有通配符处要求java泛型通配符, 可以使用@JvmWildcard注解
         * 2.如果不需要泛型通配符,可以使用@JvmSuppressWildcards注解
         */

        /**
         * Nothing类型翻译
         * 1.类型Nothing是Kotlin特有的,在Java中没有对应类型
         * 2.Nothing类型不能在Java世界中准确表示,所以Nothing类型在java中会消失(原始类型raw type)
         */

    }
}



