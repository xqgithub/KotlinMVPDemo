//
// Created by qi.xiao on 2023/7/7.
//

#include <jni.h>
#include <string>
#include <unistd.h>
#include <random>
#include <stdio.h>
#include <stdlib.h>
#include <android/log.h>

#define  LOG    "Nativelib"
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG,__VA_ARGS__) // 定义LOGE类型

#ifndef KOTLINMVPDEMO_TOOLS_H
#define KOTLINMVPDEMO_TOOLS_H

extern char *jstringToChar(JNIEnv *env, jstring jstr);

extern jstring charToJstring(JNIEnv *env, const char *pat);

extern jstring convertStdStringToJString(JNIEnv* env, const std::string& str);

//class Tools {
//
//};

#endif //KOTLINMVPDEMO_TOOLS_H
