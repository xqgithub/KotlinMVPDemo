package com.example.testlibrary

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.baselibrary.base.BaseActivity
import com.example.baselibrary.constants.ConfigConstants
import com.example.baselibrary.constants.RouterTag
import com.example.baselibrary.di.componets.MyAppComponet
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.clickWithTrigger
import example.com.testkotlin.haha.utils.showShortToastSafe
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rxjava2_use.*
import java.util.concurrent.TimeUnit


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
                2 -> testrxjava2_2()
                3 -> testrxjava2_3()
                4 -> testrxjava2_4()
                5 -> testrxjava2_5()
                6 -> testrxjava2_6()
                else -> showShortToastSafe("序号错误，请检查")
            }
        }
    }

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
     * concat:发生器B把自己的元素传递给了发生器A
     */
    fun testrxjava2_4() {
        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6)).subscribe {
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
        }).concatMap(Function<Int, ObservableSource<String>> {
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

}