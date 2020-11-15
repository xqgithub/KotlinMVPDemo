package com.example.kotlinmvpdemo.mvp.presenters

import android.content.Context
import com.example.baselibrary.data.api.ApiResponse2
import com.example.baselibrary.data.api.ApiService
import com.example.baselibrary.data.api.BaseSubscriber
import com.example.baselibrary.data.database.datasource.User
import com.example.baselibrary.designpatterns.builder.Builder
import com.example.baselibrary.designpatterns.builder.ConcreteBuilder
import com.example.baselibrary.designpatterns.builder.Director
import com.example.baselibrary.di.modules.DBHelperModule
import com.example.baselibrary.mvp.entity.Translation
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.runRx
import com.example.kotlinmvpdemo.DaoSession
import com.example.kotlinmvpdemo.mvp.views.MainView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * 首页
 */
class MainPresenter @Inject constructor(
    val mainview: MainView,
    val apiService: ApiService,
    val dbHelperModule: DBHelperModule,
    val daoSession: DaoSession
) {
    /**
     * 初始化
     */
    fun init() {
        mainview.init()
    }

    /**
     * 测试请求接口
     */
    fun getTestData(context: Context) {
        val testurl = "ajax.php?a=fy&f=auto&t=auto&w=hi%20world"
        //1.正常请求，没有封装过的
        apiService.getTestData(testurl)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseSubscriber<ApiResponse2<Translation>>(
                context, true, true
            ) {
                override fun onSuccess(t: ApiResponse2<Translation>) {
                    LogUtils.i(
                        "onSuccess",
                        t.status,
                        t.content.ciba_out,
                        t.content.ciba_use,
                        t.content.from,
                        t.content.out,
                        t.content.to,
                        t.content.vendor
                    )
                }


                override fun onFailure(message: String?, error_code: Int) {
                    LogUtils.e("onFailure =-= $message+ | $error_code")
                }
            })

        //2.正常请求，封装过后的方法
        runRx(apiService.getTestData(testurl),
            object : BaseSubscriber<ApiResponse2<Translation>>(
                context, true, true
            ) {
                override fun onSuccess(t: ApiResponse2<Translation>) {
                    LogUtils.i(
                        "onSuccess",
                        t.status,
                        t.content.ciba_out,
                        t.content.ciba_use,
                        t.content.from,
                        t.content.out,
                        t.content.to,
                        t.content.vendor
                    )
                }

                override fun onFailure(message: String?, error_code: Int) {
                    LogUtils.e("onFailure =-= $message+ | $error_code")
                }
            })
    }


    /**
     * 测试请求接口，无条件轮询
     * 1.采用interval（）延迟发送，无数次数轮询
     * 2.此处主要展示无限次轮询，若要实现有限次轮询，仅需将interval（）改成intervalRange（），有限次数
     */
    fun getTestData2(context: Context) {
        val testurl = "ajax.php?a=fy&f=auto&t=auto&w=hi%20world"

        /**
         * 参数1 = 第1次延迟时间；
         * 参数2 = 间隔时间数字；
         * 参数3 = 时间单位；
         * 该例子发送的事件特点：延迟1s后发送事件，每隔5秒产生1个数字（从0开始递增1，无限个）
         */
//        Observable.interval(1, 5, TimeUnit.SECONDS)
//            .doOnNext {
//                LogUtils.i("第 $it 次轮询")
//                runRx(apiService.getTestData(testurl),
//                    object : BaseSubscriber<ApiResponse2<Translation>>(
//                        context, true, true
//                    ) {
//                        override fun onSuccess(t: ApiResponse2<Translation>) {
//                            LogUtils.i("onSuccess =-= ${t.content.out}")
//                        }
//
//                        override fun onFailure(message: String?, error_code: Int) {
//                            LogUtils.e("onFailure =-= $message+ | $error_code")
//                        }
//                    })
//            }.subscribe(object : Observer<Long> {
//                override fun onComplete() {
//                }
//
//                override fun onSubscribe(d: Disposable?) {
//                }
//
//                override fun onNext(value: Long?) {
//                }
//
//                override fun onError(e: Throwable?) {
//                }
//            })
        Observable.intervalRange(0, 5, 3, 5, TimeUnit.SECONDS)
            .doOnNext {
                LogUtils.i("第 $it 次轮询")
                runRx(apiService.getTestData(testurl),
                    object : BaseSubscriber<ApiResponse2<Translation>>(
                        context, true, true
                    ) {
                        override fun onSuccess(t: ApiResponse2<Translation>) {
                            LogUtils.i("onSuccess =-= ${t.content.out}")
                        }

                        override fun onFailure(message: String?, error_code: Int) {
                            LogUtils.e("onFailure =-= $message+ | $error_code")
                        }
                    })
            }.subscribe(object : Observer<Long> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(value: Long?) {
                }

                override fun onError(e: Throwable?) {
                }
            })
    }


    /**
     * 有条件的轮询需求
     */
    fun getTestData3(context: Context) {
        //模拟轮询次数
        var i = 0
        val testurl = "ajax.php?a=fy&f=auto&t=auto&w=hi%20world"
        apiService.getTestData(testurl)
            .repeatWhen { objectObservable ->
                objectObservable.flatMap {
                    if (i > 3) {
                        // 此处选择发送onError事件以结束轮询，因为可触发下游观察者的onError（）方法回调
                        Observable.error(Throwable("轮询结束"))
                    } else {
                        //若轮询次数＜5次，则发送1Next事件以继续轮询
                        Observable.just(1).delay(2000, TimeUnit.MILLISECONDS)
                    }
                }
            }.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseSubscriber<ApiResponse2<Translation>>(
                context, true, true
            ) {
                override fun onSuccess(t: ApiResponse2<Translation>) {
                    LogUtils.i("onSuccess =-= ${t.content.out}")
                    i++
                }


                override fun onFailure(message: String?, error_code: Int) {
                    LogUtils.e("onFailure =-= $message+ | $error_code")
                }
            })
    }


    /**
     * 网络请求嵌套回调
     */
    fun getTestData4(context: Context) {
        val testurl = "ajax.php?a=fy&f=auto&t=auto&w=hi%20world"
        apiService.getTestData(testurl)
            .subscribeOn(Schedulers.io())// （初始被观察者）切换到IO线程进行网络请求1
            .observeOn(AndroidSchedulers.mainThread())// （新观察者）切换到主线程 处理网络请求1的结果
            .doOnNext {
                LogUtils.i("第一次请求成功 =-= ${it.content.out}")
            }
            .observeOn(Schedulers.io())//（新被观察者，同时也是新观察者）切换到IO线程去发起登录请求
            .flatMap {
                apiService.getTestData(testurl)
            }.observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                LogUtils.i("第二次请求成功 =-= ${it.content.out}")
            }, {
                LogUtils.e("Throwable =-= ${it.message}")
            })
    }


    /**
     * 网络请求出错重连
     */
    fun getTestData5(context: Context) {
        //当前已经重试的次数
        var currentRetryCount = 0
        // 最大重试次数
        val maxConnectCount = 3

        val testurl = "ajax.php?a=fy&f=auto&t=auto&w=hi%20world"
        apiService.getTestData(testurl)
            .retryWhen { throwableObservable ->
                throwableObservable.flatMap<Any> { throwable ->
                    if (throwable is IOException) {//当发生的异常 = 网络异常 = IO异常 才选择重试
                        if (currentRetryCount < maxConnectCount) {
                            currentRetryCount++
                            Observable.just(1).delay(1000, TimeUnit.MILLISECONDS)
                        } else {
                            Observable.error(Throwable("重试次数已经超过"))
                        }
                    } else {//若发生的异常不属于I/O异常，则不重试
                        Observable.error(Throwable("发生了非网络异常（非I/O异常）"))
                    }
                }
            }.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseSubscriber<ApiResponse2<Translation>>(
                context, true, true
            ) {
                override fun onSuccess(t: ApiResponse2<Translation>) {
                    LogUtils.i("onSuccess =-= ${t.content.out}")
                }

                override fun onFailure(message: String?, error_code: Int) {
                    LogUtils.e("onFailure =-= $message+ | $error_code")
                }
            })
    }


    /**
     * 插入单条数据
     */
    fun insertData() {
        val user = User()
        user.id = 1
        user.name = "路飞"
        user.sex = "男"
        dbHelperModule.insert(user, daoSession)
    }

    /**
     * 插入多条数据
     */
    fun insertData2() {
        val user = User()
        user.id = 2
        user.name = "索隆"
        user.sex = "男"

        val user2 = User()
        user2.primaryid = 9
        user2.id = 3
        user2.name = "娜美"
        user2.sex = "女"

        val userList = mutableListOf<User>()
        userList.add(user)
        userList.add(user2)
        dbHelperModule.insert(userList, daoSession)
    }

    /**
     * 删除User表中的数据
     */
    fun deleteDatas() {
        dbHelperModule.deleteAll(User::class.java, daoSession)
    }

    /**
     * 设计模式---建造者模式
     */
    fun testBuilder() {
        //1.创建建造者实例
        val builder: Builder = ConcreteBuilder()
        //2.创建指挥者实例，并分配相应的建造者
        val director = Director(builder)
        //3.开始干活
        director.Constructor("i7-6700", "三星DDR4", "希捷1T")
        LogUtils.i(
            "testBuilder",
            "cpu:${builder.getCreateComputer().mCPU}",
            "memory:${builder.getCreateComputer().mMemory}",
            "mHD:${builder.getCreateComputer().mHD}"
        )
    }
}


