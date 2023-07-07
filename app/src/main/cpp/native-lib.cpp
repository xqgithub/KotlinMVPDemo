#include "tools.h"

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
Java_com_example_kotlinmvpdemo_ndk_Nativelib_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

/**
 * 基本类型
 */
extern "C" JNIEXPORT void
Java_com_example_kotlinmvpdemo_ndk_Nativelib_testbasictype(JNIEnv *env, jobject jobj) {
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
Java_com_example_kotlinmvpdemo_ndk_Nativelib_testVariableAddress(JNIEnv *env, jobject jobj) {

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
Java_com_example_kotlinmvpdemo_ndk_Nativelib_testPointerType(JNIEnv *env, jobject jobj) {
    int i = 89;
    int *p = &i; //声明int 类型的指针

    double j = 78.9;

    LOGI("int类型所占的字节数%d", sizeof(int));
    LOGI("double类型所占的字节数%d\n", sizeof(double));

    //打印相同类型变量的值
    LOGI("指针p指向地址    =-=  %#x, =-= %d", p, *p);

//     p = &j;  //直接报错
}

/**
 * 多级指针
 */
extern "C" JNIEXPORT void
Java_com_example_kotlinmvpdemo_ndk_Nativelib_testMultistagePointer(JNIEnv *env, jobject jobj) {
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
Java_com_example_kotlinmvpdemo_ndk_Nativelib_testPointerOperation(JNIEnv *env, jobject jobj) {
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
Java_com_example_kotlinmvpdemo_ndk_Nativelib_testassignapointertoanarray(JNIEnv *env, jobject jobj) {
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
    LOGI("msg方法： res 的值 =-= %d", res);
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
Java_com_example_kotlinmvpdemo_ndk_Nativelib_testFunctionPointer(JNIEnv *env, jobject jobj, jint a, jint b) {
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


/**
 * 用随机数生成一个数组，写一个函数查找最小的值，并返回最小数的地址，在主函数中打印出来
 */

int *getMinPointer(int ids[], int len) {
    int i = 0;
    int *p = &ids[0];
    for (; i < len; i++) {
        if (ids[i] < *p) {
            p = &ids[i];
        }
    }
    return p;
}

extern "C" JNIEXPORT void
Java_com_example_kotlinmvpdemo_ndk_Nativelib_testFindSmallestValue(JNIEnv *env, jobject jobj) {
    int ids[10];
    int i = 0;
    //初始化随机数发生器，设置种子，种子不一样，随机数才不一样
    //当前时间作为种子 有符号 int -xx - > +xx
    srand((unsigned) time(NULL));
    for (; i < 10; i++) {
        //100范围内
        ids[i] = rand() % 100;
        LOGI("数组 序号 =-= %d, 数组的值 =-= %d,内存地址 =-= %#x\n", i, ids[i], &ids[i]);
    }

    int *p = getMinPointer(ids, sizeof(ids) / sizeof(int));
    LOGI("p的地址 =-=  %#x,*p的值 =-= %d\n", p, *p);
}

/**
 * 创建一个数组，动态指定数组的大小
 */
extern "C" JNIEXPORT void
Java_com_example_kotlinmvpdemo_ndk_Nativelib_testDynamicArray(JNIEnv *env, jobject jobj) {
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

/**
 * 重新分配内存
 */
extern "C" JNIEXPORT void
Java_com_example_kotlinmvpdemo_ndk_Nativelib_testReallocateMemory(JNIEnv *env, jobject jobj) {
    int len = 5;
    LOGI("第一次数组长度 =-= %d", len);
    //开辟内存，大小内存len*4字节
    int *p = (int *) malloc(len * sizeof(int));//p:数组的首地址
    int i = 0;
    for (; i < len; i++) {
        p[i] = rand() % 100;
        LOGI("数组 1 =-= %d，其值 =-= %d, 内存地址 =-= %#x", i, p[i], &p[i]);
    }

    int addLen = 3;
    LOGI("数组增加长度 =-= %d", addLen);
    //增加数组内存
    int *p2 = (int *) realloc(p, sizeof(int) * (len + addLen));
    if (p2 == NULL) {
        LOGI("重新分配失败......");
    }

    //------------新数组-------------------
    i = 0;
    for (; i < len + addLen; i++) {
        p2[i] = rand() % 200;
        LOGI("数组 2 =-= %d，其值 =-= %d, 内存地址 =-= %#x", i, p2[i], &p2[i]);
    }

    //手动释放内存 p2释放内存 p也会释放，因为给p2分配内存的时候要么p已经释放，要么p2、p指向统一地址区域
    if (p2 != NULL) {
        free(p2);
        p2 = NULL;
    }
}

/**
 * 字符串
 */
extern "C" JNIEXPORT void
Java_com_example_kotlinmvpdemo_ndk_Nativelib_testString(JNIEnv *env, jobject jobj) {
    char *str = "how are you？";
    LOGI("字符串指针 str =-= %s", str);
//    str[1] = "w" ; //字符指针不可以修改其中某一个值

    str = "hello world";
    LOGI("字符串指针 str =-= %s", str);

    //使用指针加法，截取字符串
    str += 3; //指向第四个字符首地址

    while (*str) {
        LOGI("字符 str =-= %c", *str);
        str++;
    }

    /** strcpy字符串拼接 **/
    char dest[50];
    char *a = "chian";
    char *b = " is powerful!";
    strcpy(dest, a);
    strcat(dest, b);
    LOGI("字符串 dest =-= %s", dest);

    /** strchr字符串中查找字符 **/
    char *c = "I want go to China!";
    char *p = strchr(c, 'go');
    if (p) {
        LOGI("索引的位置  =-= %d", p - c);
    } else {
        LOGI("没有找到");
    }
}


/**
 * 结构体
 * 1.结构体是一种构造数据类型
 * 2.把不同的数据类型整合起来成为一个自定义的数据类型
 */
struct Man {
    //成员
    char name[20];
    int age;
};

extern "C" JNIEXPORT void
Java_com_example_kotlinmvpdemo_ndk_Nativelib_testStructure(JNIEnv *env, jobject jobj) {
    //初始化结构体的变量
    //第一种方式
    struct Man m1 = {"路飞", 21};
    LOGI("名字 %s,年龄 %d", m1.name, m1.age);

    //第二种方式
    struct Man m2;
    m2.age = 23;
    //m2.name = "rose"; 不能这样赋值
    strcpy(m2.name, "索隆");//或者sprintf(m2.name, "Jason");
    LOGI("名字 %s,年龄 %d", m2.name, m2.age);

    /** 结构体和指针 **/
    struct Man m3 = {"乌索普", 30};
    //结构体指针
    struct Man *p = &m3;
    LOGI("名字 %s,年龄 %d", m3.name, m3.age);
    LOGI("名字 %s,年龄 %d", p->name, p->age);

    /** 结构体数组和指针 **/
    struct Man mans[] = {{"乔巴", 20},
                         {"罗宾", 19}};
    //遍历数组---第一种方式
    struct Man *p1 = mans;
    for (; p1 < mans + 2; p1++) {
        LOGI("名字 %s,年龄 %d", p1->name, p1->age);
    }

    /** 结构体和动态内存分配 **/
    struct Man *m_p = (struct Man *) malloc(sizeof(struct Man) * 10);
    struct Man *p2 = m_p;
    //赋值
    strcpy(p2->name, "路飞是海贼王");
    p2->age = 20;
    p2++;
    strcpy(p2->name, "索隆是船副");
    p2->age = 19;


    struct Man *loop_p = m_p;
    for (; loop_p < m_p + 2; loop_p++) {
        LOGI("名字 %s,年龄 %d", loop_p->name, loop_p->age);
    }
    free(m_p);
    m_p = NULL;
}

/**
 * 共用体
 */
union Data {
    int i;
    int j;
    float f;
    char str[20];
};

extern "C" JNIEXPORT void
Java_com_example_kotlinmvpdemo_ndk_Nativelib_testConsortium(JNIEnv *env, jobject jobj) {
    union Data data;
    //共用体占用的内存应足够存储共用体中最大的成员
    LOGI("Data 的内存大小为： %d", sizeof(data));
    //联合变量任何时刻只有一个变量存在，最后一次赋值有效
    data.f = 20.1;
    data.i = 10;
    strcpy(data.str, "路飞是海贼王");
    LOGI("i =-= %d,j =-= %d,f =-= %f, str =-= %s", data.i, data.j, data.f, data.str);
}

/**
 * 枚举
 */
enum Season {
    spring, summer = 100, fall = 96, winter
};


enum Day {
    Monday,//默认为0，后续枚举成员的值在前一个成员上加1
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday,
    Sunday
} Weekday;

extern "C" JNIEXPORT void
Java_com_example_kotlinmvpdemo_ndk_Nativelib_testEnumerate(JNIEnv *env, jobject jobj) {
    //枚举的值，必须是括号中的值
    enum Day d = Monday;
    LOGI("day 的物理地址 =-= %#x, 实际的值 =-=  %d", &d, d);
    d = Wednesday;
    LOGI("day 的物理地址 =-= %#x, 实际的值 =-=  %d", &d, d);

    /** Season **/
    LOGI("Season spring =-=  %d", spring);
    LOGI("Season summer =-=  %d  %c ", summer, summer);
    LOGI("fall + winter =-=  %d ", fall + winter);

    enum Season mySeason = winter;
    if (winter == mySeason) {
        LOGI("mySeason is winter");
    }

    int x = 100;
    if (x == summer) {
        LOGI("x is equal to summer\n");
    }
}

/**
 * 文件操作
 */

//文件加密
void crypt(char normal_path[], char crypt_path[]) {
    //打开文件
    FILE *normal_fp = fopen(normal_path, "r");
    FILE *crypt_fp = fopen(crypt_path, "w");
    //一次读一个字符
    int ch;
    while ((ch = fgetc(normal_fp)) != EOF) { //End of File
        //写入（异或运算）
        fputc(ch ^ 9, crypt_fp);
    }    //  关闭
    fclose(crypt_fp);
    fclose(normal_fp);
}

//文件解密
void decrypt(char crypt_path[], char decrypt_path[]) {
    //打开文件
    FILE *normal_fp = fopen(crypt_path, "r");
    FILE *crypt_fp = fopen(decrypt_path, "w");
    //一次读取一个字符
    int ch;
    while ((ch = fgetc(normal_fp)) != EOF)//End of File
    {
        //写入(异或运算)
        fputc(ch ^ 9, crypt_fp);
    }
    //关闭
    fclose(crypt_fp);
    fclose(normal_fp);


}


extern "C" JNIEXPORT void JNICALL
Java_com_example_kotlinmvpdemo_ndk_Nativelib_testOperatingFile(JNIEnv *env, jobject jobj, jstring path) {
    //打开文件
    char *_path = jstringToChar(env, path);
    FILE *fp = fopen(_path, "r+");
    if (fp == NULL) {
        LOGI("文件打开失败");
        return;
    }
    //读取文件
    char buff[50];//缓冲
    while (fgets(buff, 50, fp)) {
        LOGI("文件内容：=-= %s", buff);
    }

    //写入文本文件
    char *text = "\n我是路飞\n要成为海贼王的男人!";
    fputs(text, fp);

    //关闭
    fclose(fp);


    /** 读写二进制I/O文件 **/
    char *read_path = "/storage/emulated/0/Android/data/com.example.kotlinmvpdemo.ceshi/files/xisuo.png";
//    char *write_path = "/storage/emulated/0/Android/data/com.example.kotlinmvpdemo.ceshi/files/xisuo2.png";
//    //b字符表示操作二进制文件binary
//    FILE *read_fp = fopen(read_path, "rb");
//    //写的文件
//    FILE *write_fp = fopen(write_path, "wb");
//    //复制
//    int buff2[50]; //缓冲区域
//    int len = 0;//每次读到的数据长度
//    while ((len = fread(buff2, sizeof(int), 50, read_fp)) != 0) {//50 是写的比较大的一个数
//        //将读到的内容写入新的文件
//        fwrite(buff2, sizeof(int), len, write_fp);
//    }
//    fclose(read_fp);
//    fclose(write_fp);

    /** 获取文件的大小 **/
    FILE *fp2 = fopen(read_path, "r");
    //重新定位文件指针
    //SEEK_END文件末尾，0偏移量
    fseek(fp2, 0, SEEK_END);
    //返回当前的文件指针，相对于文件开头的位移量
    long filesize = ftell(fp2);
    LOGI("filesize ：=-= %d", filesize);

    /** 文件简单加密、解密 **/
//    char *normal_path = jstringToChar(env, path);
//    char *crypt_path = "/storage/emulated/0/Android/data/com.example.kotlinmvpdemo.ceshi/files/helloworld_crypt.text";
//    char *decrypt_path = "/storage/emulated/0/Android/data/com.example.kotlinmvpdemo.ceshi/files/helloworld_decrypt.text";
//
//    //加密文件
//    crypt(normal_path, crypt_path);
//    //解密文件
//    decrypt(crypt_path, decrypt_path);
}


/**
 * 预编译
 */

#define  message_for(a, b)  \
    LOGI(#a " and " #b ": We love you!\n")

#define tokenpaster(n) LOGI ("token" #n " = %d", token##n)


//方法名很长(方法名称有规律)
int com_haocai_ndk_get_min(int a, int b) {
    return a < b ? a : b;
}

int com_haocai_ndk_get_max(int a, int b) {
    return a > b ? a : b;
}
//语法规范
// #define 标示名(方法名,A,B) com_tz_ndk_get_##NAME(A,B)
#define call(NAME, A, B) com_haocai_ndk_get_##NAME(A,B)

#if !defined (MESSAGE)
#define MESSAGE "You wish!"
#endif

#define square(x) ((x) * (x))
#define MAX(x, y) ((x) > (y) ? (x) : (y))

extern "C" JNIEXPORT void JNICALL
Java_com_example_kotlinmvpdemo_ndk_Nativelib_testPrecompiled(JNIEnv *env, jobject jobj) {
    /** 预定义宏 **/
    LOGI("File =-= %s", __FILE__);
    LOGI("Date =-= %s", __DATE__);
    LOGI("Time =-= %s", __TIME__);
    LOGI("Line =-= %d", __LINE__);
    LOGI("ANSI =-= %d", __STDC__);

    /**
     * 预处理运算符
     * 1. \  宏延续运算符
     * 2. #  字符串常量化运算符
     * 3. ## 标记粘贴运算符
     * **/
    message_for("路飞", "索隆");

    int token34 = 40;
    tokenpaster(34);

    int c = call(min, 100, 200);
    LOGI("最小值 =-= %d", c);
    int d = call(max, 100, 200);
    LOGI("最大值 =-= %d", d);

    /** defined() 运算符 **/
    LOGI("Here is the message: %s", MESSAGE);

    /** 参数化的宏 **/
    LOGI("Here is the message: %s", MESSAGE);


}

/**
 * 1.访问Java的非静态属性
 * 2.访问Java的非静态方法
 */
extern "C" JNIEXPORT void JNICALL
Java_com_example_kotlinmvpdemo_ndk_Nativelib_testAccessFieldAndMethod(JNIEnv *env, jobject job) {

    /** 访问非静态属性 **/
    //1.找类
    jclass cls = (*env).FindClass("com/example/kotlinmvpdemo/ndk/Nativelib");
    //2.找属性ID
    jfieldID name = (*env).GetFieldID(cls, "testAccessFieldName", "Ljava/lang/String;");
    jstring jstr = (jstring) (*env).GetObjectField(job, name);
    char *_jstr = jstringToChar(env, jstr);
    LOGI("_jstr =-= %s", _jstr);
    //修改它的值
    char *_a = "testAccessFieldName 我被修改了，哈哈";
    (*env).SetObjectField(job, name, charToJstring(env, _a));

    /** 访问非静态方法 **/
    //1.找类
    jclass cls2 = (*env).FindClass("com/example/kotlinmvpdemo/ndk/Nativelib");
    //2.找方法ID
    jmethodID methodID = (*env).GetMethodID(cls2, "testAccessMethod", "()V");
    //3.调用
    (*env).CallVoidMethod(job, methodID);
}

/**
 * 访问Java的静态属性
 */
extern "C" JNIEXPORT void JNICALL
Java_com_example_kotlinmvpdemo_ndk_Nativelib_testAccessStaticFieldAndMethod(JNIEnv *env, jobject jobject) {
    /** 访问静态属性 **/
    //1.找类
    jclass cls = (*env).FindClass("com/example/kotlinmvpdemo/ndk/Nativelib");
    //2.找属性ID
    jfieldID name = (*env).GetStaticFieldID(cls, "testAccessStaticFieldName", "Ljava/lang/String;");
    jstring jstr = (jstring) (*env).GetStaticObjectField(cls, name);
    char *_jstr = jstringToChar(env, jstr);
    LOGI("_jstr =-= %s", _jstr);

    /** 访问静态方法 **/
    //1.找类
    jclass cls2 = (*env).FindClass("com/example/baselibrary/utils/LogUtils");
    //2.找到方法ID
    jmethodID methodID = (*env).GetStaticMethodID(cls2, "i", "(Ljava/lang/Object;)V");
    //3.调用方法
    char *_a = " =-= 我被jni调用了,我是静态方法";
    (*env).CallStaticVoidMethod(cls2, methodID, charToJstring(env, _a));
}














































































