package com.example.testlibrary

import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.example.baselibrary.utils.ScreenUtils
import kotlinx.android.synthetic.main.activity_main_test.*
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis


@Route(path = RouterTag.TestMainActivity)
class TestMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_test)
        initData()
    }


    /**
     * 初始化数据
     */

    fun initData() {

        tv_test_main.apply {
            text = "跳转到主程序app中的MainActivity"
        }.setOnClickListener {
            ARouter.getInstance().build(RouterTag.MainActivity).navigation()
            finish()
        }

        addUiDynamic()


        tv_test_main2.setOnClickListener {
//            testCoroutines()
//            testCoroutines2()
//            testCoroutines3()
//            testCoroutines4()
//            testCoroutines5()
//            testCoroutines6()
//            testCoroutines7()
//            testCoroutines8()
//            testCoroutines9()
//            testCoroutines10()
//            testCoroutines11()
//            testCoroutines12()
//            testCoroutines13()
//            testCoroutines14()
            testCoroutines15()
        }
    }

    /**
     *  动态添加UI控件
     */
    private fun addUiDynamic() {
        PublicPracticalMethodFromJAVA.getInstance().tenHexToHexadecimal(99999)
        //1.自定义一个TextView1
        val textView = TextView(this@TestMainActivity)
        textView.apply {
            text = "动态添加的TextView1"
            textSize = this.resources.getDimensionPixelSize(R.dimen.dimen_14x) / ScreenUtils.getScreenDensity(context)
            setTextColor(ContextCompat.getColor(this@TestMainActivity, R.color.black))
            setBackgroundColor(ContextCompat.getColor(this@TestMainActivity, R.color.full_red))
            gravity = Gravity.LEFT
            //自定义控件id
            val typedArray = this@TestMainActivity.obtainStyledAttributes(R.styleable.textviewhaha)
            val textviewID = typedArray.getInt(
                R.styleable.textviewhaha_textview1,
                99999
            )
            id = textviewID
        }
        val rlp = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        textView.layoutParams = rlp
        //添加控件
        rl_test_main.addView(textView)

        //2.自定义一个textView2
        val textView2 = TextView(this@TestMainActivity)
        textView2.apply {
            val lambdaTest = { a: String, b: String -> "$a VS $b" }
            val lambdaTest2: (String, String) -> String = { a, b -> "$a VS $b" }
            val lambdaTest3 = {
                val a = "路飞"
                val b = "索隆"
                "$a VS $b"
            }
            text = "动态添加的TextView_${lambdaTest3()}"
            textSize = this.resources.getDimensionPixelSize(R.dimen.dimen_14x) / ScreenUtils.getScreenDensity(context)
            setTextColor(ContextCompat.getColor(this@TestMainActivity, R.color.black))
            setBackgroundColor(ContextCompat.getColor(this@TestMainActivity, R.color.crowded_yellow))
            gravity = Gravity.LEFT
        }
        val rlp2 = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        /**
         *layoutParams.alignWithParent = true  如果对应的兄弟元素找不到的话就以父元素做参照物
         *RelativeLayout.CENTER_HORIZONTAL   在父控件中水平居中
         *RelativeLayout.CENTER_VERTICAL   在父控件中垂直居中
         *RelativeLayout.CENTER_IN_PARENT  相对于父控件完全居中
         *RelativeLayout.ALIGN_PARENT_BOTTOM  紧贴父控件的下边缘
         *RelativeLayout.ALIGN_PARENT_TOP  紧贴父控件的上边缘
         *RelativeLayout.ALIGN_PARENT_LEFT紧贴父控件的左边边缘
         *RelativeLayout.ALIGN_PARENT_RIGHT  紧贴父控件的右边缘
         *RelativeLayout.ABOVE 在某元素的上方  需要第二个参数为某元素的ID
         *RelativeLayout.BELOW 在某元素的下方 需要第二个参数为某元素的ID
         *RelativeLayout.LEFT_OF 在某元素的左边 需要第二个参数为某元素的ID
         *RelativeLayout.RIGHT_OF  在某元素的右边 需要第二个参数为 某元素的ID
         *RelativeLayout.ALIGN_TOP 本元素的上边缘和某元素的的上边缘对齐 需要第二个参数为某元素的ID
         *RelativeLayout.ALIGN_BOTTOM 本元素的上边缘和某元素的的下边缘对齐 需要第二个参数为某元素的ID
         *RelativeLayout.ALIGN_LEFT  本元素的上边缘和某元素的的左边缘对齐 需要第二个参数为某元素的ID
         *RelativeLayout.ALIGN_RIGHT  本元素的上边缘和某元素的的右边缘对齐 需要第二个参数为某元素的ID
         *RelativeLayout.ALIGN_BASELINE   本元素的基线和某元素的的基线对齐 需要第二个参数为某元素的ID
         */
        rlp2.addRule(RelativeLayout.BELOW, textView.id)
        rlp2.setMargins(0, this.resources.getDimensionPixelSize(R.dimen.dimen_10x), 0, 0)
        textView2.layoutParams = rlp2
        //添加控件
        rl_test_main.addView(textView2)
    }

    /**
     * 协程的使用
     * 线程（Thraed）是比进程小一级的的运行单位，多线程实现系统资源上下文调用，是编程语言交付系统内核来进行的（可能是并发，也可能是伪并发），大部分的编程语言的多线程实现都是抢占式的，而对于这些线程的控制，编程语言无法直接控制，需要通过系统内核来进行，由系统内核决定最终的行为
     * 协程（Coroutine）是在语言层面实现“多线程”这个过程，一般在代码中以串行的方式表达并发逻辑，由于是在编程语言层面模拟这一过程，而非涉及到硬件层面，在编程语言层面可以完全控制这一过程，可以这么说，协程是软件层面模拟硬件层面的多线程；但不是说协程就一定是单线程，具体的实现要看具体编程语言的实现，kotlin的协程实现可能是单线程，也可能是多线程；
     *
     * 协程可以用于解决高负荷网络 IO、文件 IO、CPU/GPU 密集型任务等
     * 1.特殊修饰符 suspend 修饰的函数被称为挂起函数, suspend方法的本质是异步返回，suspend方法并不总是引起协程挂起, 只有其内部的数据未准备好时才会
     * 2.挂起函数只能在协程中和其他挂起函数中调用，不能在其他部分使用，所以要启动一个协程，挂起函数是必须的
     * 3.async方法作用和launch方法基本一样, 创建一个协程并立即启动,但是async创建的协程可以携带返回值.
     */
    private fun testCoroutines() {
        //1.GlobalScope启动协程这意味着新协程的生命周期只受整个应用程序的生命周期限制
        //2.GlobalScope启动的活动协程并不会使进程保活。它们就像守护线程
        val job = GlobalScope.launch {
            delay(1000)//协程的delay作用等同于线程里的sleep, 都是休息一段时间, 但不同的是delay不会阻塞当前线程
            LogUtils.i(ConfigConstants.TAG_ALL, "${Thread.currentThread().name}  World!")
        }
        LogUtils.i(ConfigConstants.TAG_ALL, "${Thread.currentThread().name}  Hello!")
        Thread.sleep(2000L) // 阻塞主线程 2 秒钟来保证 JVM 存活
    }


    //1.调用了 runBlocking 的主线程会一直 阻塞 直到 runBlocking 内部的协程执行完毕
    //2.runBlocking<Unit> { …… } 作为用来启动顶层主协程的适配器
    private fun testCoroutines2() = runBlocking<Unit> { // 开始执行主协程
        GlobalScope.launch { // 在后台启动一个新的协程并继续
            delay(1000L)
            println("World!")
        }
        println("Hello,") // 主协程在这里会立即执行
        delay(2000L)      // 延迟 2 秒来保证 JVM 存活
    }


    //1.使用join方法等待协程执行完
    private fun testCoroutines3() = runBlocking {
        val job = GlobalScope.launch { // 启动一个新协程并保持对这个作业的引用
            delay(1000L)
            println("World!")
        }
        println("Hello,")
        job.join() // 等待直到子协程执行结束
    }


    //1.可以在这个作用域中启动协程而无需显式 join 之，因为外部协程（示例中的 runBlocking）直到在其作用域中启动的所有协程都执行完毕后才会结束
    private fun testCoroutines4() = runBlocking { // this: CoroutineScope
        launch { // 在 runBlocking 作用域中启动一个新协程
            delay(1000L)
            println("World!")
        }
        println("Hello,")
    }

    //1.可以使用 coroutineScope 构建器声明自己的作用域。它会创建一个协程作用域并且在所有已启动子协程执行完毕之前不会结束
    //2.(1)runBlocking 与 coroutineScope 可能看起来很类似，因为它们都会等待其协程体以及所有子协程结束。
    // (2)主要区别在于，runBlocking 方法会阻塞当前线程来等待， 而 coroutineScope 只是挂起，会释放底层线程用于其他用途。
    //(3)由于存在这点差异，runBlocking 是常规函数，而 coroutineScope 是挂起函数
    private fun testCoroutines5() {
        runBlocking { // this: CoroutineScope
            launch {
                delay(2000L)
                println("这个是第二个执行的")
            }
            coroutineScope { // 创建一个协程作用域
                launch {
                    delay(5000L)
                    println("这个是第三个执行的")
                }
                delay(1000L)
                println("这是第一个执行的") // 这一行会在内嵌 launch 之前输出
            }
            println("这个是第四个执行的") // 这一行在内嵌 launch 执行完毕后才输出
        }
        println("这个会最后执行吗？")
    }

    //提取函数重构
    private fun testCoroutines6() {
        runBlocking {
            launch {
                doWorld()
            }
            println("Hello,")
        }
        println("Merry Christmas")
    }

    //挂起函数
    suspend fun doWorld() {
        delay(1000L)
        println("World!")
    }


    //1.超时
    //2.withTimeout与runBlocking是一样的，都会阻塞当前线程
    private fun testCoroutines7() {
        runBlocking {
            try {
                withTimeout(1000L) {
                    LogUtils.i(ConfigConstants.TAG_ALL, "我第一个")
                    delay(2000L)
                    LogUtils.i(ConfigConstants.TAG_ALL, "我第二个")
                }
            } catch (e: Exception) {
                LogUtils.e(ConfigConstants.TAG_ALL, e.message)
            } finally {
                LogUtils.i(ConfigConstants.TAG_ALL, "finally, I will do something")
            }
        }
    }

    //1.取消协程
    //2.调用了 job.cancel，我们在它的协程中就看不到任何输出，因为它被取消了
    //3.主人结束工作，助手继续工作
    private fun testCoroutines8() {
        runBlocking {
            val job = launch {
                repeat(10) {
                    LogUtils.i(ConfigConstants.TAG_ALL, "主人开始工作了：${it}")
                    delay(1000L)
                }
            }
            launch {
                repeat(10) {
                    LogUtils.i(ConfigConstants.TAG_ALL, "助手开始工作了：${it}")
                    delay(1000L)
                }
            }
            delay(3000L)
            LogUtils.i(ConfigConstants.TAG_ALL, "主人很累了啊，准备结束工作")
            job.cancel()// 取消该作业
            job.join()// 等待作业执行结束
            LogUtils.i(ConfigConstants.TAG_ALL, "现在主人可以休息了")
        }
    }


    //1.协程正在执行一些比较繁重的计算，比如从多个文件中读取数据，则不会有任何东西可以让此协程自动停止
    //2.这个时候需要用到isActive 来判断协程是否还在活动
    private fun testCoroutines9() {
        runBlocking {
            val startTime = System.currentTimeMillis()
            val job = launch(Dispatchers.Default) {
                var nextPrintTime = startTime
                var i = 0
                while (i < 10 && isActive) {
                    if (System.currentTimeMillis() >= nextPrintTime) {
                        LogUtils.i(ConfigConstants.TAG_ALL, "主人开始工作了：${i}")
                        i++
                        nextPrintTime += 500L
                    }
                }
            }
            delay(1000L)
            LogUtils.i(ConfigConstants.TAG_ALL, "主人很累了啊，准备结束工作")
            job.cancel()
            job.join()
            LogUtils.i(ConfigConstants.TAG_ALL, "现在主人可以休息了")
        }
    }

    //组合挂起函数
    //两个协程并发执行,节省时间
    private fun testCoroutines10() {
        //1.普通执行
//        runBlocking {
//            val time = measureTimeMillis {//返回持续的时间（以毫秒为单位）。
//                val one = doSomethingUsefulOne()
//                val two = doSomethingUsefulTwo()
//                LogUtils.i(ConfigConstants.TAG_ALL, "计算的结果是：${one + two}")
//            }
//            LogUtils.i(ConfigConstants.TAG_ALL, "总共用时为：${time} ms")
//        }

        //2.协程并行
        runBlocking {
            val time = measureTimeMillis {//返回持续的时间（以毫秒为单位）。

                val one = async {
                    doSomethingUsefulOne()
                }
                val two = async {
                    doSomethingUsefulTwo()
                }
                LogUtils.i(ConfigConstants.TAG_ALL, "计算的结果是：${one.await() + two.await()}")
            }
            LogUtils.i(ConfigConstants.TAG_ALL, "总共用时为：${time} ms")
        }
    }

    //惰性启动的 async
    //1.async 可以通过将 start 参数设置为 CoroutineStart.LAZY 而变为惰性的
    //2.在这个模式下，只有结果通过 await 获取的时候协程才会启动，或者在 Job 的 start 函数调用的时候
    private fun testCoroutines11() {
        runBlocking {
            val time = measureTimeMillis {//返回持续的时间（以毫秒为单位）。

                val one = async(start = CoroutineStart.LAZY) {
                    doSomethingUsefulOne()
                }
                val two = async(start = CoroutineStart.LAZY) {
                    doSomethingUsefulTwo()
                }
                one.start() // 启动第一个
                two.start() // 启动第二个
                LogUtils.i(ConfigConstants.TAG_ALL, "计算的结果是：${one.await() + two.await()}")
            }
            LogUtils.i(ConfigConstants.TAG_ALL, "总共用时为：${time} ms")
        }
    }

    suspend fun doSomethingUsefulOne(): Int {
        delay(1000L) // 假设我们在这里做了一些有用的事
        return 7
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L) // 假设我们在这里也做了一些有用的事
        return 10
    }

    //协程的上下文和调度器
    private fun testCoroutines12() {
        runBlocking {
            launch { // 运行在父协程的上下文中，即 runBlocking 主协程
                LogUtils.i(ConfigConstants.TAG_ALL, "main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Unconfined) {
                //1. 不受限的——将工作在主线程中
                //2. launch(Dispatchers.Default) { …… } 与 GlobalScope.launch { …… } 使用相同的调度器
                //3. 如果在delay后，恢复到默认的执行者线程中恢复执行
                LogUtils.i(ConfigConstants.TAG_ALL, "Unconfined1            : I'm working in thread ${Thread.currentThread().name}")
                delay(1000L)
                LogUtils.i(ConfigConstants.TAG_ALL, "Unconfined2           : I'm working in thread ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Default) { // 将会获取默认调度器
                LogUtils.i(ConfigConstants.TAG_ALL, "Default               : I'm working in thread ${Thread.currentThread().name}")
            }
            launch(newSingleThreadContext("MyOwnThread")) { // 将使它获得一个新的线程
                LogUtils.i(ConfigConstants.TAG_ALL, "newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
            }
        }
    }

    //父协程的职责
    //一个父协程总是等待所有的子协程执行结束。父协程并不显式的跟踪所有子协程的启动，并且不必使用 Job.join 在最后的时候等待它们
    private fun testCoroutines13() {
        runBlocking<Unit> {
            // 启动一个协程来处理某种传入请求（request）
            val request = launch {
                repeat(5) { i -> // 启动少量的子作业
                    launch {
                        delay((i + 1) * 200L) // 延迟 200 毫秒、400 毫秒、600、800、1000 毫秒的时间
                        LogUtils.i(ConfigConstants.TAG_ALL, "Coroutine $i is done")
                    }
                }
                LogUtils.i(ConfigConstants.TAG_ALL, "request: 我完成了，等待我的子孙们继续执行")
            }
            request.join() // 等待请求的完成，包括其所有子协程
            LogUtils.i(ConfigConstants.TAG_ALL, "至此，请求处理完成")
        }
    }

    //协程作用域
    private val mainScope = CoroutineScope(Dispatchers.Default) // 全局作用域
    private fun testCoroutines14() {
        runBlocking {
            // 在示例中启动了 10 个协程，且每个都工作了不同的时长
            repeat(10) { i ->
                mainScope.launch {
                    delay((i + 1) * 200L) // 延迟 200 毫秒、400 毫秒、600 毫秒等等不同的时间
                    LogUtils.i(ConfigConstants.TAG_ALL, "Coroutine $i is done")
                }
            }
            delay(500L)
            LogUtils.i(ConfigConstants.TAG_ALL, "Destroying activity")
            onDestroy()
            delay(3000L)
        }
    }

    //线程的局部变量
    val threadLocal = ThreadLocal<String?>() // 声明线程局部变量
    private fun testCoroutines15() {
        runBlocking<Unit> {
            threadLocal.set("main")
            LogUtils.i(ConfigConstants.TAG_ALL, "Pre-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
            val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {
                LogUtils.i(ConfigConstants.TAG_ALL, "Launch start, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
                yield()
                LogUtils.i(ConfigConstants.TAG_ALL, "After yield, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
            }
            job.join()
            LogUtils.i(ConfigConstants.TAG_ALL, "Post-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //在activity销毁的时候 退出所有的协程
        mainScope.cancel()
    }


}