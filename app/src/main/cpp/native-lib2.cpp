#include <iostream>
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
        LOGI("C 字符串指向 java 层的字符串");
    }
    LOGI("C/C++ 层接收到的字符串是 =-= %c", cStr);

    //通过JNI GetStringChars 函数和 GetStringUTFChars 函数获得的C字符串在原生代码中
    //使用完之后需要正确地释放，否则将会引起内存泄露。
    env->ReleaseStringUTFChars(str, cStr);
    std::string outString = "Hello, JNI";
    return env->NewStringUTF(outString.c_str());
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_example_kotlinmvpdemo_ndk_Nativelib_localReferenceManagement(JNIEnv *env, jobject jobj) {
    jint len = 20;
    jstring jstr;
    if (env->PushLocalFrame(len) == 0) {
        std::string str;
        for (int i = 0; i < len; i++) {
            str = str + std::to_string(i);
        }
        jstr = env->NewStringUTF(str.c_str());
        env->PopLocalFrame(NULL);
    } else {
        jstr = env->NewStringUTF("内存不够使用了");
    }
    return jstr;
}

/** 创建全局引用  start **/
jclass g_cls = NULL;
jmethodID g_mid = NULL;

/**
 * 使用全局引用
 */
void callMethodGlobalReference(JNIEnv *env, jobject jobj) {
    if (g_cls == NULL || g_mid == NULL) {
        return;
    }
    env->CallVoidMethod(jobj, g_mid);
}

/**
 * 销毁全局引用
 */
void destroyGlobalReference(JNIEnv *env, jobject jobj) {
    //使用DeleteGlobalRef()函数销毁全局引用g_cls
    if (g_cls != NULL) {
        env->DeleteGlobalRef(g_cls);
        g_cls = NULL;
    }
}

/**
 * 全局引用初始化
 */
extern "C" JNIEXPORT void JNICALL
Java_com_example_kotlinmvpdemo_ndk_Nativelib_globalReferenceUse(JNIEnv *env, jobject jobj) {
    //引用了Java类com.example.kotlinmvpdemo.mvp.ui.activities.NDKPractiseActivity
    jclass cls = (*env).FindClass("com/example/kotlinmvpdemo/ndk/Nativelib");
    g_cls = (jclass) (*env).NewGlobalRef(cls);
    //引用了testAccessMethod
    g_mid = (*env).GetMethodID(g_cls, "testAccessMethod", "()V");
    //释放cls引用
    env->DeleteLocalRef(cls);
    callMethodGlobalReference(env, jobj);
    destroyGlobalReference(env, jobj);
}
/** 创建全局引用  end **/


/**
 *  字符串处理
 */
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_kotlinmvpdemo_ndk_Nativelib_stringHandling(JNIEnv *env, jobject jobj, jstring str) {
    jboolean isCopy;
    //GetStringUTFChars 用于 utf-8 编码
    const char *cStr = env->GetStringUTFChars(str, &isCopy);
    //GetStringChars 用于 utf-16 编码
    const jchar *cStr2 = env->GetStringChars(str, &isCopy);

    //异常处理，后面会专门讲，这里了解即可
    if (nullptr == cStr || nullptr == cStr2) {
        return nullptr;
    }

    //这个取决于 jvm 的实现，不影响我们的编程
    if (JNI_TRUE == isCopy) {
        LOGI("C 字符串是 java 字符串的一份拷贝");
    } else {
        LOGI("C 字符串指向 java 层的字符串");
    }

    //原始字符串长度
    jint strLength = env->GetStringLength(str);
    LOGI("str字符串的长度 =-= %d", strLength);
    for (int i = 0; i < strLength; i++) {
        printf("%c", cStr2[i]);
    }

    LOGI("C/C++ 层收到的字符串是: %s", cStr);
    std::string outString = "嘻def";
    outString = cStr + outString;
    //字符串长度,1个中文长度=3个英文长度
    jint outStringLength = env->GetStringUTFLength(convertStdStringToJString(env, outString));
    LOGI("添加后的字符串长度 =-= %d", outStringLength);



    //通过JNI GetStringChars 函数和 GetStringUTFChars 函数获得的C字符串在原生代码中
    //使用完之后需要正确地释放，否则将会引起内存泄露。
    env->ReleaseStringUTFChars(str, cStr);
    env->ReleaseStringChars(str, cStr2);

    return env->NewStringUTF(outString.c_str());
}


/**
 *  数组访问 --- 基本类型
 */
extern "C" JNIEXPORT jdoubleArray JNICALL
Java_com_example_kotlinmvpdemo_ndk_Nativelib_arrayUse(JNIEnv *env, jobject jobj, jintArray inJNIArray) {
    //类型转换 jintArray -> jint*
    jboolean isCopy;
    jint *inArray = env->GetIntArrayElements(inJNIArray, &isCopy);
    if (JNI_TRUE == isCopy) {
        LOGI("C 层的数组是 java 字符串的一份拷贝");
    } else {
        LOGI("C 层的数组指向 java 层的字符串");
    }

    if (nullptr == inArray) {
        return nullptr;
    }

    //获取数组长度
    jsize length = env->GetArrayLength(inJNIArray);
    jint sum = 0;
    for (jint i = 0; i < length; ++i) {
        sum += inArray[i];
    }
    jdouble average = (jdouble) sum / length;

    //释放数组
    env->ReleaseIntArrayElements(inJNIArray, inArray, 0); // release resource
    //构造返回数据，outArray 是指针类型，需要 free 或者 delete 吗？要的
    jdouble outArray[] = {static_cast<jdouble>(sum), average};
    jdoubleArray outJNIArray = env->NewDoubleArray(2);
    if (NULL == outJNIArray) return NULL;
    //向 jdoubleArray 写入数据
    env->SetDoubleArrayRegion(outJNIArray, 0, 2, outArray);
    return outJNIArray;
}

/**
 * 数组访问 --- 引用类型
 */
extern "C" JNIEXPORT jobjectArray  JNICALL
Java_com_example_kotlinmvpdemo_ndk_Nativelib_arrayUse2(JNIEnv *env, jobject jobj, jobjectArray objectArray_in) {
    //获取长度信息
    jsize size = env->GetArrayLength(objectArray_in);
    /*******获取从JNI传过来的String数组数据**********/
    for (int i = 0; i < size; ++i) {
        jstring string_in = (jstring) env->GetObjectArrayElement(objectArray_in, i);
        jboolean isCopy;
        const char *char_in = env->GetStringUTFChars(string_in, nullptr);
        LOGI("从Java层接受到的数据 =-= %s", char_in);
    }

    /***********从JNI返回String数组给Java层**************/
    jclass clazz = env->FindClass("java/lang/String");
    jobjectArray objectArray_out;
    const int len_out = 5;
    objectArray_out = env->NewObjectArray(len_out, clazz, NULL);
    char *char_out[] = {"Hello,", "world!", "JNI", "is", "fun"};
    jstring temp_string;
    for (int i = 0; i < len_out; i++) {
        temp_string = env->NewStringUTF(char_out[i]);
        env->SetObjectArrayElement(objectArray_out, i, temp_string);
    }
    return objectArray_out;
}

/**
 * 数组访问 --- 二维数组
 */
extern "C" JNIEXPORT jobjectArray JNICALL
Java_com_example_kotlinmvpdemo_ndk_Nativelib_arrayUse3(JNIEnv *env, jobject jobj, jobjectArray objectArray_in) {
    /**********	解析从Java得到的int型二维数组 **********/
    int i, j;
    const int row = env->GetArrayLength(objectArray_in);//获取二维数组的行数
    jarray array = (jarray) env->GetObjectArrayElement(objectArray_in, 0);
    const int col = env->GetArrayLength(array);//获取二维数组每行的列数

    //根据行数和列数创建int型二维数组
    jint intDimArrayIn[row][col];
    for (i = 0; i < row; i++) {
        array = (jintArray) env->GetObjectArrayElement(objectArray_in, i);

        //操作方式一，这种方法会申请natvie memory内存
        jint *coldata = env->GetIntArrayElements((jintArray) array, NULL);
        for (j = 0; j < col; j++) {
            intDimArrayIn[i][j] = coldata[j]; //取出JAVA类中int二维数组的数据,并赋值给JNI中的数组
            LOGI("java层的二维数组 =-=  %d", intDimArrayIn[i][j]);
        }

        //操作方式二，赋值,这种方法不会申请内存
        //  env->GetIntArrayRegion((jintArray)array, 0, col, (jint*)&intDimArrayIn[i]);

        env->ReleaseIntArrayElements((jintArray) array, coldata, 0);
    }


    /**************创建一个int型二维数组返回给Java**************/
    const int row_out = 2;//行数
    const int col_out = 2;//列数

    //获取数组的class
    jclass clazz = env->FindClass("[I");//一维数组的类
    //新建object数组，里面是int[]
    jobjectArray intDimArrayOut = env->NewObjectArray(row_out, clazz, NULL);

    int tmp_array[row_out][col_out] = {{0, 1},
                                       {2, 3}};
    for (i = 0; i < row_out; i++) {
        jintArray intArray = env->NewIntArray(col_out);
        env->SetIntArrayRegion(intArray, 0, col_out, (jint *) &tmp_array[i]);
        env->SetObjectArrayElement(intDimArrayOut, i, intArray);
    }
    return intDimArrayOut;


}







