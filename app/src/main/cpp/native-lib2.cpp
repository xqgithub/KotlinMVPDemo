#include <jni.h>
#include <string>
#include <android/log.h>
#include <exception>

#define  LOG    "Nativelib2"
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG,__VA_ARGS__) // 定义LOGE类型

extern "C" JNIEXPORT jint JNICALL
Java_com_example_kotlinmvpdemo_ndk_Nativelib_sum(
        JNIEnv *env, jobject instance, jint x, jint y) {
    return x + y;
}

//jstring 转 char*
char *jstringToChar222(JNIEnv *env, jstring jstr) {
    char *rtn = NULL;
    jclass clsstring = (*env).FindClass("java/lang/String");
    jstring strencode = (*env).NewStringUTF("utf-8");
    jmethodID mid = (*env).GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) (*env).CallObjectMethod(jstr, mid, strencode);
    jsize alen = (*env).GetArrayLength(barr);
    jbyte *ba = (*env).GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char *) malloc(alen + 1);
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    (*env).ReleaseByteArrayElements(barr, ba, 0);
    return rtn;
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
    char *_jstr = jstringToChar222(env, name);
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

