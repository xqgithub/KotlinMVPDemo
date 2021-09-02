package com.example.kotlinmvpdemo.utils;

import com.example.baselibrary.constants.ConfigConstants;
import com.example.baselibrary.utils.LogUtils;
import com.example.kotlinmvpdemo.mvp.ui.activities.BasicGrammarActivity;

/**
 * Date:2021/9/2
 * Time:15:12
 * author:joker
 * java 代码汇总，方便和kotlin进行互调用
 */
public class JavaAlternately {


    public void testKotlinFromJava1() {

        //testa 字段是 public static final
        int testa = BasicGrammarActivity.testa;

        //testb 字段是 public static 非final
        BasicGrammarActivity.testb = "我是 testb";
        String testb = BasicGrammarActivity.testb;

        //testc 字段是 public static final
        int testc = BasicGrammarActivity.testc;

        LogUtils.i(ConfigConstants.TAG_ALL,
                "testa =-= " + testa,
                "testb =-= " + testb,
                "testc =-= " + testc
        );
    }

    public void testKotlinFromJava2() {
        //静态方法
        BasicGrammarActivity.testa();
        //实例方法
        BasicGrammarActivity.Companion.testb();
    }

    public void testKotlinFromJava3() {
        String a = "aaa";
        int b = 1;
        String c = "ccc";
        BasicGrammarActivity.Companion.testc(a, b, c);
        BasicGrammarActivity.Companion.testc(a, b);
        BasicGrammarActivity.Companion.testc(a);
    }
}
