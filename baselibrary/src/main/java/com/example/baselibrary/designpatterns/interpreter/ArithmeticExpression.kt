package com.example.baselibrary.designpatterns.interpreter

/**
 * 解释器模式---抽象算术表达式
 */
abstract class ArithmeticExpression {

    //抽象解释方法
    abstract fun interpret(surroundings: Surroundings): Any
}