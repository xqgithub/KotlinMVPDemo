#include "tools.h"

extern "C" JNIEXPORT jint JNICALL
Java_com_example_kotlinmvpdemo_ndk_Nativelib_sum(
        JNIEnv *env, jobject instance, jint x, jint y) {
    return x + y;
}

/**
 *  #ifdef __cplusplus
 *  #endif
 *  用来判断当前代码是否是在 C++ 环境下编译的，当需要编写既可以在 C 也可以在 C++ 中使用的代码时，可以使用 #ifdef __cplusplus 来判断当前代码是否是在 C++ 环境下编译的，从而做出相应的处理
 */
#ifdef __cplusplus
extern "C" {
#endif
//1.实现java层本地方法
JNIEXPORT void JNICALL
c_init1(JNIEnv *env, jobject thiz) {
    LOGI("c_init1");
}

JNIEXPORT void JNICALL
c_init2(JNIEnv *env, jobject thiz, jint age) {
    LOGI("c_init2  我的年龄 =-= %d", age);
}

JNIEXPORT jboolean JNICALL
c_init3(JNIEnv *env, jobject thiz, jstring name) {
    char *_jstr = jstringToChar(env, name);
    LOGI("c_init3 我的名字 =-= %s", _jstr);
    return true;
}


JNIEXPORT void JNICALL
c_update(JNIEnv *env, jobject thiz) {
    LOGI("c_update");
}

#ifdef __cplusplus
}
#endif

//typedef struct {
//    char *name; //java native方法名称
//    char *signature; //方法签名
//    void *fnPtr;   //对应的本地函数指针
//
//} JNINativeMethod;

static JNINativeMethod methods[] = {
        {"init",   "()V",                   (void *) c_init1},
        {"init",   "(I)V",                  (void *) c_init2},
        {"init",   "(Ljava/lang/String;)Z", (void *) c_init3},
        {"update", "()V",                   (void *) c_update},
};

JNIEXPORT jint JNICALL
JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    jint result = -1;

    // 获取JNI env变量
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        // 失败返回-1
        return result;
    }

    // 获取native方法所在类
    const char *className = "com/example/kotlinmvpdemo/ndk/Nativelib";
    jclass clazz = env->FindClass(className);
    if (clazz == NULL) {
        return result;
    }

    // 动态注册native方法
    if (env->RegisterNatives(clazz, methods, sizeof(methods) / sizeof(methods[0])) < 0) {
        return result;
    }

    // 返回成功
    result = JNI_VERSION_1_6;
    return result;
}


/**
 * 基本数据类型不需要转换直接使用
 */
extern "C" JNIEXPORT jdouble JNICALL
Java_com_example_kotlinmvpdemo_ndk_Nativelib_average(JNIEnv *env, jobject jobj, jint n1, jint n2) {
    //基本类型不用做转换，直接使用
    return jdouble(n1 + n2) / 2.0;
}

/**
 * 字符串的使用
 * @param env
 * @param jobj
 * @param str
 * @return
 */
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_kotlinmvpdemo_ndk_Nativelib_stringUse(JNIEnv *env, jobject jobj, jstring str) {
    //jstring -> char*
    jboolean isCopy;
    //GetStringChars 用于 unicode 编码
    //GetStringUTFChars 用于 utf-8 编码
    const char *cStr = env->GetStringUTFChars(str, &isCopy);
    if (nullptr == cStr) {
        return nullptr;
    }
    if (JNI_TRUE == isCopy) {
        LOGI("C 字符串是 java 字符串的一份拷贝");
    } else {
        LOGI("字符串指向 java 层的字符串");
    }
    //通过JNI GetStringChars 函数和 GetStringUTFChars 函数获得的C字符串在原生代码中
    //使用完之后需要正确地释放，否则将会引起内存泄露。
    env->ReleaseStringUTFChars(str, cStr);
    std::string outString = "Hello, JNI";
    return env->NewStringUTF(outString.c_str());
}






