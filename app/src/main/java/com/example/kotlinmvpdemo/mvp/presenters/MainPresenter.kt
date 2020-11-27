package com.example.kotlinmvpdemo.mvp.presenters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import com.example.baselibrary.data.api.ApiResponse2
import com.example.baselibrary.data.api.ApiService
import com.example.baselibrary.data.api.BaseSubscriber
import com.example.baselibrary.data.database.datasource.User
import com.example.baselibrary.designpatterns.abstractfactory.ConcreteFactoryAbstract
import com.example.baselibrary.designpatterns.abstractfactory.FactoryAbstract
import com.example.baselibrary.designpatterns.builder.Builder
import com.example.baselibrary.designpatterns.builder.ConcreteBuilder
import com.example.baselibrary.designpatterns.builder.Director
import com.example.baselibrary.designpatterns.chainresponsibility.ConcretePostman
import com.example.baselibrary.designpatterns.chainresponsibility.Postman
import com.example.baselibrary.designpatterns.clonemode.Card
import com.example.baselibrary.designpatterns.factory.ConcreteFactory
import com.example.baselibrary.designpatterns.factory.Factory
import com.example.baselibrary.designpatterns.factory.Product
import com.example.baselibrary.designpatterns.iterator.Aggregate
import com.example.baselibrary.designpatterns.iterator.ConcreteAggregate
import com.example.baselibrary.designpatterns.iterator.ConcreteIteratorMe
import com.example.baselibrary.designpatterns.iterator.IteratorMe
import com.example.baselibrary.designpatterns.memo.Caretaker
import com.example.baselibrary.designpatterns.memo.Memento
import com.example.baselibrary.designpatterns.memo.Originator
import com.example.baselibrary.designpatterns.observer.ConcreteObservable
import com.example.baselibrary.designpatterns.observer.ConcreteObserver
import com.example.baselibrary.designpatterns.observer.Observerdesign
import com.example.baselibrary.designpatterns.simplefactory.ConcreteProductSimple
import com.example.baselibrary.designpatterns.simplefactory.FactorySimple
import com.example.baselibrary.designpatterns.state.ChangeState
import com.example.baselibrary.designpatterns.strategy.ConcreteStragety
import com.example.baselibrary.designpatterns.strategy.Environment
import com.example.baselibrary.designpatterns.template.ConcretePostManTemplate
import com.example.baselibrary.designpatterns.template.PostmanTemplate
import com.example.baselibrary.di.modules.DBHelperModule
import com.example.baselibrary.mvp.entity.Translation
import com.example.baselibrary.utils.LogUtils
import com.example.baselibrary.utils.NotificationHelperUtils
import com.example.baselibrary.utils.runRx
import com.example.kotlinmvpdemo.DaoSession
import com.example.kotlinmvpdemo.mvp.ui.activities.TestProductFlavorsActivity
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

    /**
     * 设计模式---工厂方法模式
     */
    fun testFactory() {
        //1.产品A
        val factoryA: Factory = ConcreteFactory.FactoryA()
        val productA: Product = factoryA.create()
        productA.show()
        //2.产品B
        val factoryB: Factory = ConcreteFactory.FactoryB()
        val productB: Product = factoryB.create()
        productB.show()
    }

    /**
     * 设计模式---简单工厂模式
     */
    fun testSimpleFactory() {
        //1.产品A
        FactorySimple.create("A")!!.show()
        //2.产品B
        FactorySimple.create("B")!!.show()
        //3.产品C
        try {
            FactorySimple.create("C")!!.show()
        } catch (e: Exception) {
            LogUtils.i("没有C产品")
        }

        //4.反射实现工厂类
        FactorySimple.create(ConcreteProductSimple.ProductA().javaClass)!!.show()
        FactorySimple.create(ConcreteProductSimple.ProductB().javaClass)!!.show()
    }

    /**
     * 设计模式---抽象工厂模式
     */
    fun testAbstractFactory() {

        //1.产品1
        val factoryabstract: FactoryAbstract = ConcreteFactoryAbstract.lianxiang()
        factoryabstract.createCPU().showCpu()
        factoryabstract.createMemory().showMemory()
        factoryabstract.createHD().showHD()
        //2.产品2
        val factoryabstract2: FactoryAbstract = ConcreteFactoryAbstract.HP()
        factoryabstract2.createCPU().showCpu()
        factoryabstract2.createMemory().showMemory()
        factoryabstract2.createHD().showHD()
    }

    /**
     * 设计模式---原型模式
     */
    fun testCloneMode() {
        //模型1
        val card1 = Card()
        card1.setNum(9527)
        card1.setSpec(10, 20)
        System.out.println(card1.toString())
        println("----------------------")
        //模型2
        val card2 = card1.clone()
        System.out.println(card2.toString())
        System.out.println("----------------------")
    }

    /**
     * 设计模式---策略模式
     */
    fun testStragety() {
        //策略1
        val environment = Environment(ConcreteStragety.ConcreteStragetyA())
        environment.chase()
        //策略2
        val environment2 = Environment(ConcreteStragety.ConcreteStragetyB())
        environment2.chase()
        //策略3
        val environment3 = Environment(ConcreteStragety.ConcreteStragetyC())
        environment3.chase()
    }

    /**
     * 设计模式---状态模式
     */
    fun testState() {
        //状态1
        val changestate: ChangeState = ChangeState()
        changestate.disappointmentInLove()
        changestate.movies()
        changestate.shopping()
        //状态2
        changestate.fallInLove()
        changestate.movies()
        changestate.shopping()
    }

    /**
     * 设计模式---责任链模式
     */
    fun testResponsibility() {
        //创建不同的快递员对象
        val beijingpostman: Postman = ConcretePostman.BeijingPostman()
        val shanghaipostman: Postman = ConcretePostman.ShangHaiPostman()
        val guangzhoupostman: Postman = ConcretePostman.GuangZhouPostman()

        beijingpostman.nextPostman = shanghaipostman
        shanghaipostman.nextPostman = guangzhoupostman

        //处理不同地区的快递，都是从首结点北京快递员开始
        LogUtils.i("有一个上海的快递需要派送")
        beijingpostman.handleCourier("shanghai")
        LogUtils.i("有一个广州的快递需要派送")
        beijingpostman.handleCourier("guangzhou")
    }

    /**
     * 设计模式---观察者模式
     */
    fun testObserver() {
        //快递员 被观察者
        val postman: ConcreteObservable = ConcreteObservable()

        //观察者1
        val observer1: Observerdesign = ConcreteObserver.Boy("路飞")
        //观察者2
        val observer2: Observerdesign = ConcreteObserver.Girl("娜美")

        //添加观察者
        postman.add(observer1)
        postman.add(observer2)

        //被观察者开始做动作
        postman.notify("快递到了，速度下来拿")
    }

    /**
     * 设计模式---模板方法模式
     */
    fun testTemplate() {
        //A
        val postmantemplateA: PostmanTemplate = ConcretePostManTemplate.PostManA()
        postmantemplateA.post()
        //B
        val postmantemplateB: PostmanTemplate = ConcretePostManTemplate.PostManB()
        postmantemplateB.post()
    }

    /**
     * 设计模式---迭代器模式
     */
    fun testIterator() {
        val aggregate: Aggregate = ConcreteAggregate()
        aggregate.add("1111")
        aggregate.add("2222")
        aggregate.add("3333")
        aggregate.add("9527")

        val iteratorme: IteratorMe = ConcreteIteratorMe(aggregate)
        while (iteratorme.hasnext()) {
            var tel = iteratorme.next().toString()
            LogUtils.i("当前号码为：${tel}")
        }
        LogUtils.i("后面没有了")
    }


    /**
     * 设计模式---备忘录模式
     */
    fun testMemo() {
        LogUtils.i("首次进入游戏")
        //创建游戏实例
        val originator = Originator()
        //开始玩游戏
        originator.play()
        //创建存档
        val memento: Memento = originator.createMemento()
        //开始存档
        val caretaker = Caretaker()
        caretaker.setMemento(memento)
        //退出游戏
        originator.exit()
        LogUtils.i("=======分割线=======")
        LogUtils.i("第二次进入游戏")
        val originator2 = Originator()
        //读取存档
        originator2.getMemento(caretaker.getMemento())
        //继续玩游戏
        originator2.play()
        //退出游戏
        originator2.exit()
    }

    /**
     * 弹出通知栏
     */
    //表示versionCode=19 也就是4.4的系统以及以上的系统生效。4.4以下系统默认全部打开状态。
    @SuppressLint("NewApi")
    fun testNotification(context: Context) {
        NotificationHelperUtils.getInstance().getNotificationManager(context)
        //1.发送普通通知
//        if (NotificationHelperUtils.getInstance().isNotifacationEnabled(context)) {
//            NotificationHelperUtils.getInstance().sendNotification(
//                context, TestProductFlavorsActivity::class.java,
//                111, "海贼王", "我要成为海贼王的男人"
//            )
//        } else {
//            NotificationHelperUtils.getInstance().openPermission(context as Activity)
//        }
        //2.发送自定义通知
        if (NotificationHelperUtils.getInstance().isNotifacationEnabled(context)) {
            //应为自定义中有点击事件，所有先注册广播
//            val receiver = NotificationBrodcaseReceiver()
//            NotificationHelperUtils.getInstance().registerNotificationBrodcaseRecever(
//                context as Activity, receiver, ConfigConstants.notifacatio_close
//            )

            NotificationHelperUtils.getInstance().sendNotification2(
                context, TestProductFlavorsActivity::class.java,
                111, "海贼王", "我要成为海贼王的男人"
            )
        } else {
            NotificationHelperUtils.getInstance().openPermission(context as Activity)
        }
    }
}


