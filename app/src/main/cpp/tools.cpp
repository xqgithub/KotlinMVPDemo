//
// Created by qi.xiao on 2023/7/7.
//
#include <jni.h>
#include <string>
#include <unistd.h>
#include <random>
#include <stdio.h>
#include <stdlib.h>

/**
 *  char 转 jstring
 */
jstring charToJstring(JNIEnv *env, const char *pat) {
    jclass strClass = (*env).FindClass("java/lang/String");
    jmethodID ctorID = (*env).GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
    jbyteArray bytes = (*env).NewByteArray(strlen(pat));
    (*env).SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte *) pat);
    jstring encoding = (*env).NewStringUTF("utf-8");

    return (jstring) (*env).NewObject(strClass, ctorID, bytes, encoding);
}


/**
 * jstring 转 char*
 */
char *jstringToChar(JNIEnv *env, jstring jstr) {
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

    //释放局部引用
    env->DeleteLocalRef(clsstring);
    env->DeleteLocalRef(strencode);

    (*env).ReleaseByteArrayElements(barr, ba, 0);
    return rtn;
}

/**
 * std::String 转 jstring
 */
jstring convertStdStringToJString(JNIEnv* env, const std::string& str){
    jbyteArray javaByteArray = env->NewByteArray(str.length());
    env->SetByteArrayRegion(javaByteArray, 0, str.length(), reinterpret_cast<const jbyte*>(str.c_str()));
    jclass stringClass = env->FindClass("java/lang/String");
    jmethodID constructor = env->GetMethodID(stringClass, "<init>", "([BLjava/lang/String;)V");
    jstring javaString = static_cast<jstring>(env->NewObject(stringClass, constructor, javaByteArray, env->NewStringUTF("UTF-8")));

    env->DeleteLocalRef(stringClass);
    env->DeleteLocalRef(javaByteArray);

    return javaString;
}



