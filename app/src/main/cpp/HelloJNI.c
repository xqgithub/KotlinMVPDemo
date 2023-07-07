//
// Created by qi.xiao on 2023/6/21.
//

#include <stdio.h>
#include <stdlib.h>
#include <jni.h>

#define JNICALL

//方法名要和 Java 层包名对应上
JNIEXPORT jstring JNICALL
Java_com_example_kotlinmvpdemo_ndk_Nativelib_sayHello(JNIEnv *env, jobject obj) {
    return (*env)->NewStringUTF(env, "Hello from JNI !");
}
