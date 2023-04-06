#include <jni.h>
#include <string>
#include <android/log.h>
#include <unistd.h>

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

/**
 * 基本类型
 */
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

/**
 *  变量地址和指针
 */
extern "C" JNIEXPORT void
Java_com_example_kotlinmvpdemo_ndk_nativelib_testVariableAddress(JNIEnv *env, jobject jobj) {

    /**  输出定义的变量地址 **/
    int var1;
    char var2[10];
    LOGI("var1 变量的地址 =-= %p", &var1);
    LOGI("var2 变量的地址 =-= %p", &var2);

    /** 指针变量 **/
    //实际变量的声明
    int var = 20;
    //指针变量的声明
    int *ip;
    //在指针变量中存储 var 的地址
    ip = &var;

    //变量 var 的地址
    LOGI("变量 var 的地址 =-= %p", &var);
    //在指针变量中存储的地址
    LOGI("在指针 ip 变量中存储的地址 =-= %p", ip);
    //使用指针访问值
    LOGI("指针 ip 访问的变量值 =-=  %d", *ip);

    //对ip存的地址指向的变量进行操作
    *ip = 66;
    LOGI("对ip存的地址指向的变量，即对var进行操作后的值 =-=  %d", var);

    /**  NULL 指针的使用 **/
    int *ptr = NULL;
    LOGI("ptr 的值 =-=  %p", ptr);
    //检查一个空指针，您可以使用 if 语句
    char *xiaoc;
    if (xiaoc) {
        LOGI("xiaoc 是null指针");
    } else {
        LOGI("xiaoc 不是null指针 =-= %c", xiaoc);
    }
}

/**
 * 指针有类型，地址没有类型，地址只是开始的位置，类型大小决定读取到什么位置结束
 */
extern "C" JNIEXPORT void
Java_com_example_kotlinmvpdemo_ndk_nativelib_testPointerType(JNIEnv *env, jobject jobj) {
    int i = 89;
    int *p = &i; //声明int 类型的指针

    double j = 78.9;

    LOGI("int类型所占的字节数%d", sizeof(int));
    LOGI("double类型所占的字节数%d\n", sizeof(double));

    //打印相同类型变量的值
    LOGI("指针p指向地址    =-=  %#x, =-= %d", p, *p);

    // p = &j;  直接报错
}

/**
 * 多级指针
 */
extern "C" JNIEXPORT void
Java_com_example_kotlinmvpdemo_ndk_nativelib_testMultistagePointer(JNIEnv *env, jobject jobj) {
    int a = -50;
    //p1上保存的是a的地址
    int *p1 = &a;
    //p2上保存的是p1的地址
    int **p2 = &p1;

    LOGI("指针p1 指向地址 =-= %#x, 指针p2 指向地址 =-= %#x", p1, p2);
    LOGI("指针p1 的值 =-= %d, 指针p2 值 =-= %d", *p1, **p2);
    LOGI("a 的值 =-= %d", a);

    **p2 = 90;

    LOGI("指针p1 的值 =-= %d, 指针p2 值 =-= %d", *p1, **p2);
    LOGI("a 的值 =-= %d", a);
    getchar();
}

/**
 * 指针运算（加减法）（与数组的操作相结合）
 */
extern "C" JNIEXPORT void
Java_com_example_kotlinmvpdemo_ndk_nativelib_testPointerOperation(JNIEnv *env, jobject jobj) {
    int ids[] = {78, 90, 23, 65, 19};
    //数组的变量名：ids就是数组首地址 3种方式意义一样
    LOGI("数组的地址 =-= %#x", ids);
    LOGI("数组的地址 =-= %#x", &ids);
    LOGI("数组的地址 =-= %#x", &ids[0]);

    //指针变量
    int *p = &ids[0];
    LOGI("指针p 指向地址 =-= %#x", p);

    //p++向前移动sizeof(数据类型)个字节
    p++;
    //指向数组的下一个元素地址 =  数组首地址值+4;
    LOGI("指针p 指向地址 =-= %#x", p);
    //指向数组的下一个元素
    LOGI("指针p 的值 =-= %d", *p);
}


/**
 * 通过使用指针给数组赋值
 */
extern "C" JNIEXPORT void
Java_com_example_kotlinmvpdemo_ndk_nativelib_testassignapointertoanarray(JNIEnv *env, jobject jobj) {
    int uids[5];
    //高级写法
//    int i = 0;
//    for (; i < 5; i++) {
//        uids[i] = i;
//    }

    //早些版本的写法
    int *p = uids;
    LOGI("指针p 指向地址 =-= %#x", p);
    int i = 0;
    //uids + 5 = uids的首地址值+5*4
    LOGI("uids + 5 指向地址 =-= %#x", uids + 5);
    for (; p < uids + 5; p++) {
        *p = i;
        i++;
    }

    LOGI("uids[0] 的值 =-= %d", uids[0]);
    LOGI("uids[1] 的值 =-= %d", uids[1]);

}


/**
 * 函数指针
 */
int add(int a, int b) {
    return a + b;
}

int minus(int a, int b) {
    return a - b;
}

//调用函数指针的函数
void msg(int(*function_p)(int a, int b), int a, int b) {
    int res = function_p(a, b);
    LOGI("res 的值 =-= %d", res);
}


/** 回调函数，函数指针作为某个函数的参数 **/
// 回调函数
void populate_array(int *array, size_t arraySize, int (*getNextValue)(void)) {
    for (size_t i = 0; i < arraySize; i++) {
        array[i] = getNextValue();
    }
}

// 获取随机值
int getNextRandomValue(void) {
    return rand() % 10;
}


extern "C" JNIEXPORT void
Java_com_example_kotlinmvpdemo_ndk_nativelib_testFunctionPointer(JNIEnv *env, jobject jobj, jint a, jint b) {
    //p 是函数指针
    int (*p)(int, int) =add;
    int (*p2)(int, int) =minus;
    int res = p2(p(a, b), a);
    LOGI("res 的值 =-= %d", res);

    //在函数指针中调用另一个函数
    msg(add, a, b);
    msg(minus, a, b);

    //函数回调
    int myarray[10];
    /* getNextRandomValue 不能加括号，否则无法编译，因为加上括号之后相当于传入此参数时传入了 int , 而不是函数指针*/
    populate_array(myarray, 10, getNextRandomValue);
    for (int i = 0; i < 10; ++i) {
        LOGI("myarray %d =-= %d", i, myarray[i]);
    }
}

/**
 * 创建一个数组，动态指定数组的大小
 */
extern "C" JNIEXPORT void
Java_com_example_kotlinmvpdemo_ndk_nativelib_testDynamicArray(JNIEnv *env, jobject jobj) {
    //静态内存分配创建数组，数组的大小是固定的
    //int i = 10;
    //int a[i];

    int len = 5;
    //开辟内存，大小内存len * 4 字节
    int *p = (int *) malloc(len * sizeof(int));//p:数组的首地址
    int i = 0;
    for (; i < len; i++) {
        p[i] = rand() % 100;
        LOGI("数组 =-= %d，其值 =-= %d, 内存地址 =-= %#x", i, p[i], &p[i]);
    }

    //手动释放内存
    free(p);
}



















