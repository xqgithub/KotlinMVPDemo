#include <jni.h>
#include <string>

extern "C" JNIEXPORT jint JNICALL
Java_com_example_kotlinmvpdemo_ndk_Nativelib_sum(
        JNIEnv *env, jobject instance, jint x, jint y) {
    return x + y;
}