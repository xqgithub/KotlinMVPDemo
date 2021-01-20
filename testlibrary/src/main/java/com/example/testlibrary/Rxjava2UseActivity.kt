package com.example.testlibrary

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.clickWithTrigger
import com.example.baselibrary.utils.flowableToMain
import com.example.baselibrary.utils.observableToMain
import com.trello.rxlifecycle3.android.ActivityEvent
import example.com.testkotlin.haha.utils.showShortToastSafe
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rxjava2_use.*
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * Rxjava2使用方法
 */
@Route(path = RouterTag.Rxjava2UseActivity)
class Rxjava2UseActivity : BaseActivity() {

    override fun setupComponent(myAppComponet: MyAppComponet) {
    }

    override fun onBeforeSetContentLayout() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_rxjava2_use
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tv_rxjava2.clickWithTrigger(500) {
            val brand = et_rxjava2.text.toString().toInt()
            when (brand) {
                0 -> showShortToastSafe("请从序号1开始，哈哈")
                1 -> testrxjava2()
                2 -> testrxjava2_2()//map
                3 -> testrxjava2_3()//zip
                4 -> testrxjava2_4()//concat
                5 -> testrxjava2_5()//flatMap
                6 -> testrxjava2_6()//concatMap
                7 -> testrxjava2_7()//distinct
                8 -> testrxjava2_8()//Filter
                9 -> testrxjava2_9()//buffer
                10 -> testrxjava2_10()//timer
                11 -> testrxjava2_11()//interval
                12 -> testrxjava2_12()//doOnNext
                13 -> testrxjava2_13()//skip
                14 -> testrxjava2_14()//take
                15 -> testrxjava2_15()//Single
                16 -> testrxjava2_16()//debounce
                17 -> testrxjava2_17()//defer
                18 -> testrxjava2_18()//last
                19 -> testrxjava2_19()//merge
                20 -> testrxjava2_20()//reduce
                21 -> testrxjava2_21()//scan
                22 -> testrxjava2_22()//window
                23 -> testrxjava2_23()//backpressure
                else -> showShortToastSafe("序号错误，请检查")
            }
        }
    }

    /**
     * Rxjava2
     * 一、两种观察者模式
     * 1.Observable ( 被观察者 ) / Observer ( 观察者 )：不支持背压
     * 2.Flowable （被观察者）/ Subscriber （观察者）：支持背压
     *
     * 二、四种线程模式
     * 1.Schedulers.io():代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
     * 2.Schedulers.computation():代表CPU计算密集型的操作, 例如需要大量计算的操作
     * 3.Schedulers.newThread():代表一个常规的新线程
     * 4.AndroidSchedulers.mainThread():代表Android的主线程
     *
     * 三、背压产生的条件：必须是异步的场景下才会出现，即被观察者和观察者处于不同的线程中
     * 1.Rxjava1有背压处理方案：
     * (1)onBackpressureBuffer，onBackpressureDrop，onBackpressureLatest方法
     * (2)但是设计部够完善，缓存池大小只有16，而且被观察者无法得知下游观察者对事件的处理速度，一次性把事件抛给了下游观察者
     * 2.Rxjava2背压处理
     * (1)Flowable用来专门支持背压，默认队列大小128，并且其所有的操作符都强制支持背压
     * (2)Flowable使用create的时候使用背压策略来处理
     * (3)Flowable 也可以使用 onBackpressureBuffer，onBackpressureDrop，onBackpressureLatest方法
     */


    /**
     * 1.Disposable.dispose 方法可以直接切断接受事件，但是事件还是会继续发送
     * 2.e.onComplete()方法会调用onComplete,虽然无法接收事件，但发送事件还是继续的
     */
    fun testrxjava2() {
        Observable.create(ObservableOnSubscribe<Int> { e ->
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 1")
            e.onNext(1)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 2")
            e.onNext(2)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 3")
            e.onNext(3)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 4")
            e.onNext(4)
        }).subscribe(object : Observer<Int> {
            lateinit var mDisposable: Disposable

            override fun onSubscribe(d: Disposable) {
                this.mDisposable = d
                LogUtils.i(ConfigConstants.TAG_ALL, "onSubscribe : " + d.isDisposed)
            }

            override fun onNext(value: Int) {
                LogUtils.i(ConfigConstants.TAG_ALL, "onNext : value的值为-> $value")
                if (value == 2) {
                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
                    mDisposable.dispose()
                    onComplete()
                }
            }

            override fun onComplete() {
                LogUtils.i(ConfigConstants.TAG_ALL, "onComplete : 事件完成")
            }


            override fun onError(e: Throwable) {
                LogUtils.i(ConfigConstants.TAG_ALL, "onError : ${e.message}")
            }
        })
    }


    /**
     * 1.map 基本作用就是将一个 Observable 通过某种函数关系，转换为另一种 Observable
     * 2.int 转化成了 String
     */
    fun testrxjava2_2() {
        Observable.create(ObservableOnSubscribe<Int> { e ->
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 1")
            e.onNext(1)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 2")
            e.onNext(2)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 3")
            e.onNext(3)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 4")
            e.onNext(4)
        }).map {
            "This is result $it"
        }.subscribe {
            LogUtils.i(ConfigConstants.TAG_ALL, "map subscribe: $it")
        }
    }

    /**
     * 1.zip 组合事件的过程就是分别从发射器 A 和发射器 B 各取出一个事件来组合，并且一个事件只能被使用一次，组合的顺序是严格按照事件发送的顺序来进行的
     * 2.最终接收器收到的事件数量是和发送器发送事件最少的那个发送器的发送事件数目相同
     */
    fun testrxjava2_3() {
        Observable.zip(getStringObservable(), getIntObservable(), BiFunction<String, Int, String> { t1, t2 ->
            t1 + t2
        }).subscribe {
            LogUtils.i(ConfigConstants.TAG_ALL, "zip subscribe: $it")
        }
    }

    private fun getIntObservable(): Observable<Int> {
        return Observable.create { e ->
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 1")
            e.onNext(1)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 2")
            e.onNext(2)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 3")
            e.onNext(3)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 4")
            e.onNext(4)
        }
    }

    private fun getStringObservable(): Observable<String> {
        return Observable.create { e ->
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit A")
            e.onNext("A")
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit B")
            e.onNext("B")
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit C")
            e.onNext("C")
        }
    }

    /**
     * concat
     * 1.发射器B把自己的元素传递给了发射器A
     */
    fun testrxjava2_4() {
        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6))
            .subscribe {
                LogUtils.i(ConfigConstants.TAG_ALL, "concat subscribe: $it")
            }
    }

    /**
     * flatMap
     * 1.把一个发射器 Observable 通过某种方法转换为多个 Observables，然后再把这些分散的 Observables装进一个单一的发射器 Observable
     * 2.flatMap 并不能保证事件的顺序
     */
    fun testrxjava2_5() {
        Observable.create(ObservableOnSubscribe<Int> { e ->
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 1")
            e.onNext(1)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 2")
            e.onNext(2)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 3")
            e.onNext(3)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 4")
            e.onNext(4)
        }).flatMap(Function<Int, ObservableSource<String>> {
            val list = ArrayList<String>()
            for (i in 0 until 2) {
                list.add("I am value $it")
            }
            Observable.fromIterable(list).delay((1..5).random() * 100L, TimeUnit.MILLISECONDS)
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                LogUtils.i(ConfigConstants.TAG_ALL, "flatMap subscribe: $it")
            }
    }

    /**
     * concatMap
     * 1.concatMap 与 FlatMap 的唯一区别就是 concatMap 保证了顺序
     */
    fun testrxjava2_6() {
        Observable.create(ObservableOnSubscribe<Int> { e ->
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 1")
            e.onNext(1)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 2")
            e.onNext(2)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 3")
            e.onNext(3)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 4")
            e.onNext(4)
        }).concatMap {
            val list = ArrayList<String>()
            for (i in 0 until 2) {
                list.add("I am value $it")
            }
            Observable.fromIterable(list).delay((1..5).random() * 100L, TimeUnit.MILLISECONDS)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                LogUtils.i(ConfigConstants.TAG_ALL, "flatMap subscribe: $it")
            }
    }

    /**
     * distinct
     * 1.去重
     */
    fun testrxjava2_7() {
        Observable.create(ObservableOnSubscribe<Int> { e ->
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 1")
            e.onNext(1)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 2")
            e.onNext(2)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 1")
            e.onNext(1)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 2")
            e.onNext(2)
        }).distinct()
            .subscribe {
                LogUtils.i(ConfigConstants.TAG_ALL, "distinct subscribe: $it")
            }
    }

    /**
     * Filter
     * 1.过滤
     */
    fun testrxjava2_8() {
        Observable.create(ObservableOnSubscribe<Int> { e ->
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 1")
            e.onNext(1)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 2")
            e.onNext(2)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 3")
            e.onNext(3)
            LogUtils.i(ConfigConstants.TAG_ALL, "Observable emit 4")
            e.onNext(4)
        }).filter {
            it < 2
        }.subscribe {
            LogUtils.i(ConfigConstants.TAG_ALL, "filter subscribe: $it")
        }
    }

    /**
     * buffer
     * 1.buffer 操作符接受两个参数，buffer(count,skip)，作用是将 Observable 中的数据按 skip (步长) 分成最大不超过 count 的 buffer ，然后生成一个 Observable
     */
    fun testrxjava2_9() {
        Observable.create(ObservableOnSubscribe<Int> { e ->
            e.onNext(1)
            e.onNext(2)
            e.onNext(3)
            e.onNext(4)
            e.onNext(5)
            e.onNext(6)
        }).buffer(2, 3)
            .subscribe {
                LogUtils.i(ConfigConstants.TAG_ALL, "buffer subscribe->size:${it.size}")
                it.forEach {
                    LogUtils.i(ConfigConstants.TAG_ALL, "buffer subscribe->value:$it")
                }
            }
    }

    /**
     * timer
     * 1.相当于一个定时任务
     * 2.默认在新线程
     */
    fun testrxjava2_10() {
        LogUtils.i(ConfigConstants.TAG_ALL, "timer Start->")
        //延迟两秒开始执行
        Observable.timer(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                LogUtils.i(ConfigConstants.TAG_ALL, "timer subscribe->:$it")
            }
    }


    /**
     * interval
     * 1.间隔时间执行某个操作
     * 2.默认在新线程
     * 3.取消间隔任务使用 disposable.dispose()方法
     *
     * compose
     * 1.封装成一个简单的工具类来使用
     * 2.rxlifecycle使用
     */
    private var disposable11: Disposable? = null
    fun testrxjava2_11() {
        LogUtils.i(ConfigConstants.TAG_ALL, "interval Start->")
        //初始化延迟3秒执行一次，后续每2秒执行一次
//        disposable11 = Observable.interval(3, 2, TimeUnit.SECONDS)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                LogUtils.i(ConfigConstants.TAG_ALL, "interval subscribe->:$it")
//            }


        Observable.interval(3, 2, TimeUnit.SECONDS)
            .doOnDispose {
                LogUtils.i(ConfigConstants.TAG_ALL, "interval 已经停止")
            }
//            .compose(this.bindToLifecycle())
            .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
            .compose(observableToMain())
            .subscribe {
                LogUtils.i(ConfigConstants.TAG_ALL, "interval subscribe->:$it")
            }


    }

    /**
     * doOnNext
     * 1.不算一个操作符,但是比较常用
     * 2.让订阅者在接收到数据之前干点有意思的事情
     */
    fun testrxjava2_12() {
        Observable.create(ObservableOnSubscribe<Int> { e ->
            e.onNext(1)
            e.onNext(2)
            e.onNext(3)
            e.onNext(4)
            e.onNext(5)
        }).doOnNext {
            LogUtils.i(ConfigConstants.TAG_ALL, "doOnNext 保存->:$it")
        }.subscribe {
            LogUtils.i(ConfigConstants.TAG_ALL, "doOnNext subscribe->:$it")
        }
    }

    /**
     * skip
     * 1.接受一个 long 型参数 count ，代表跳过 count 个数目开始接收
     */
    fun testrxjava2_13() {
        Observable.create(ObservableOnSubscribe<Int> { e ->
            e.onNext(1)
            e.onNext(2)
            e.onNext(3)
            e.onNext(4)
            e.onNext(5)
        }).skip(2)
            .subscribe {
                LogUtils.i(ConfigConstants.TAG_ALL, "skip subscribe->:$it")
            }
    }


    /**
     * take
     * 1.接受一个 long 型参数 count ，代表至多接收 count 个数据
     */
    fun testrxjava2_14() {
        Observable.create(ObservableOnSubscribe<Int> { e ->
            e.onNext(1)
            e.onNext(2)
            e.onNext(3)
            e.onNext(4)
            e.onNext(5)
        }).take(2)
            .subscribe {
                LogUtils.i(ConfigConstants.TAG_ALL, "take subscribe->:$it")
            }
    }

    /**
     * Single
     * 1.只会接收一个参数，而 SingleObserver 只会调用 onError() 或者 onSuccess()
     */
    fun testrxjava2_15() {
        Single.just(Random.nextInt(100))
            .subscribe(object : SingleObserver<Int> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(value: Int) {
                    LogUtils.i(ConfigConstants.TAG_ALL, "Single onSuccess->:$value")
                }


                override fun onError(e: Throwable) {
                    LogUtils.i(ConfigConstants.TAG_ALL, "Single onError->:${e.message}")
                }
            })
    }

    /**
     * debounce
     * 1.去除发送频率过快的项
     */
    fun testrxjava2_16() {
        Observable.create(ObservableOnSubscribe<Int> { e ->
            e.onNext(1)
            Thread.sleep(400)

            e.onNext(2)
            Thread.sleep(505)

            e.onNext(3)
            Thread.sleep(300)

            e.onNext(4)
            Thread.sleep(510)

            e.onNext(5)
            Thread.sleep(520)
            //去除间隔时间小于500ms的事件
        }).debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                LogUtils.i(ConfigConstants.TAG_ALL, "debounce subscribe->:${it}")
            }
    }

    /**
     * defer
     * 1.每次订阅都会创建一个新的 Observable，并且如果没有被订阅，就不会产生新的 Observable。
     */
    fun testrxjava2_17() {
        val observable = Observable.defer(Callable<ObservableSource<Int>> {
            Observable.just(1, 2, 3, 4, 5)
        })

        observable.subscribe(object : Observer<Int> {

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(value: Int) {
                LogUtils.i(ConfigConstants.TAG_ALL, "defer onNext->:${value}")
            }


            override fun onComplete() {
                LogUtils.i(ConfigConstants.TAG_ALL, "defer onComplete->:")
            }

            override fun onError(e: Throwable) {
                LogUtils.i(ConfigConstants.TAG_ALL, "defer onError->:${e.message}")
            }
        })
    }

    /**
     * last
     * 1.仅取出可观察到的最后一个值，或者是满足某些条件的最后一项
     */
    fun testrxjava2_18() {
        Observable.just(1, 2, 3).last(1)
            .subscribe(Consumer<Int> {
                LogUtils.i(ConfigConstants.TAG_ALL, "last subscribe->:${it}")
            })
    }

    /**
     * merge
     * 1.把多个 Observable 结合起来，接受可变参数，也支持迭代器集合
     * 2.和 concat 的区别在于，不用等到 发射器 A 发送完所有的事件再进行发射器 B 的发送
     */
    fun testrxjava2_19() {
        Observable.merge(Observable.just(1, 3), Observable.just(2, 4, 5))
            .subscribe {
                LogUtils.i(ConfigConstants.TAG_ALL, "merge subscribe->:${it}")
            }
    }

    /**
     * reduce
     * 1.每次用一个方法处理一个值，可以有一个 seed 作为初始值
     * 2.只追求结果
     */
    fun testrxjava2_20() {
        Observable.just(1, 2, 3, 4, 5)
            .reduce { t1, t2 ->
                t1 + t2
            }.subscribe {
                LogUtils.i(ConfigConstants.TAG_ALL, "reduce subscribe->:${it}")
            }
    }

    /**
     * scan
     * 1.scan 操作符作用和上面的 reduce 一致
     * 2.scan 会始终如一地把每一个步骤都输出
     */
    fun testrxjava2_21() {
        Observable.just(1, 2, 3, 4, 5)
            .scan { t1, t2 ->
                t1 + t2
            }.subscribe {
                LogUtils.i(ConfigConstants.TAG_ALL, "scan subscribe->:${it}")
            }
    }

    /**
     * window
     * 1.按照实际划分窗口，将数据发送给不同的 Observable
     */
    fun testrxjava2_22() {
        val disposable22 = Observable.interval(1, TimeUnit.SECONDS)//间隔一秒发一次
            .take(15)//最多接受15个
            .window(3, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                LogUtils.i(ConfigConstants.TAG_ALL, "Sub Divide begin...")
                it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        LogUtils.i(ConfigConstants.TAG_ALL, "window subscribe->:${it}")
                    }
            }
    }


    /**
     * 背压实例
     * 1.背压的模式：
     * (1).BackpressureStrategy.MISSING 不支持背压
     * (2).BackpressureStrategy.ERROR  出现背压就抛出异常
     * (3).BackpressureStrategy.BUFFER  指定无限大小的缓存池，此时不会出现异常，但无限制大量发送会发生OOM
     * (4).BackpressureStrategy.DROP  如果缓存池满了就丢弃掉之后发出的事件
     * (5).BackpressureStrategy.LATEST 在DROP的基础上，强制将最后一条数据加入到缓存池中
     */
    private var disposable23: Disposable? = null
    fun testrxjava2_23() {
        disposable23 = Flowable.create(FlowableOnSubscribe<String> { fle ->
            for (i in 0..500) {
                fle.onNext("路飞海贼王：$i")
            }
        }, BackpressureStrategy.BUFFER)
            .compose(flowableToMain())
            .subscribe {
                LogUtils.i(ConfigConstants.TAG_ALL, "backpressure subscribe->:${it}")
            }
    }

    override fun onDestroy() {
        super.onDestroy()
//        if (!StringUtils.isBlank(disposable11) && !disposable11!!.isDisposed) disposable11!!.dispose()
//        if (!StringUtils.isBlank(disposable23) && !disposable23!!.isDisposed) disposable23!!.dispose()
    }

}