#include <jni.h>
#include <string>
#include <android/log.h>

#define  LOG    "Nativelib"
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG,__VA_ARGS__) // 定义LOGE类型

/**
 * 1. extern "C" :避免编绎器按照C++的方式去编绎C函数
 * (1). C不支持函数的重载，编译之后函数名不变；
 * (2). C++支持函数的重载（这点与Java一致），编译之后函数名会改变；
 * (3). 在C++中，存在函数的重载问题，函数的识别方式是通过：函数名，函数的返回类型，函数参数列表三者组合来完成的
 * (4). 如果希望编译后的函数名不变，应通知编译器使用C的编译方式编译该函数（即：加上关键字：extern “C”）
 *
 * 2. JNIEXPORT 用来表示该函数是否可导出（即：方法的可见性）
 * (1). 宏 JNIEXPORT 代表的就是右侧的表达式： __attribute__ ((visibility ("default"))), 或者 JNIEXPORT 是右侧表达式的别名
 * (2). 宏可表达的内容很多，如：一个具体的数值、一个规则、一段逻辑代码等
 * (3). attribute___((visibility ("default"))) 描述的是“可见性”属性 visibility
 * a. default ：表示外部可见，类似于public修饰符 (即：可以被外部调用)
 * b. hidden ：表示隐藏，类似于private修饰符 (即：只能被内部调用)
 *
 * 3. JNICALL 用来表示函数的调用规范（如：__stdcall）
 */
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_kotlinmvpdemo_ndk_nativelib_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT void
Java_com_example_kotlinmvpdemo_ndk_nativelib_testbasictype(JNIEnv *env, jobject jobj) {
    float f = 10.01;
    LOGI("f =-= %f\n", f);

    //求某个类型所占的字节数，具体跟操作系统有关
    LOGI("int类型所占的字节数%d\n", sizeof(int));
    LOGI("float类型所占的字节数%d\n", sizeof(float));
    LOGI("double类型所占的字节数%d\n", sizeof(double));


    //循环的标准写法，循环变量需要抽取出来，否则在Linux环境下GCC下编译 报错
    int n = 0;
    for (; n < 10; n++) {
        LOGI("n =-= %d\n", n);
    }

    //等待输入，目的是使得程序停留
//    getchar();
    //也可以使用
//    system("pause");
}




