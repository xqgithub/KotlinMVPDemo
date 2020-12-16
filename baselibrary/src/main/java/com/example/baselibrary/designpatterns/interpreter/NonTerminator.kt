package com.example.baselibrary.designpatterns.interpreter

/**
 * 解释器模式---非终结符表达式
 */
class NonTerminator {

    /**
     * 加法表达式，用来解释加法,如a+b
     */
    class AddExpression(left: ArithmeticExpression, right: ArithmeticExpression) : ArithmeticExpression() {

        var MLeft: ArithmeticExpression? = null
        var MRight: ArithmeticExpression? = null

        init {
            this.MLeft = left
            this.MRight = right
        }

        //解释加法表达式的结果，即算出left+right的结果
        override fun interpret(surroundings: Surroundings): Int {
            return surroundings.get(MLeft!!.interpret(surroundings) as String) + surroundings.get(MRight!!.interpret(surroundings) as String)
        }
    }

    /**
     * 等号表达式，用来解释变量赋值，如a=1024
     */
    class EqualExpression(left: ArithmeticExpression, right: ArithmeticExpression) : ArithmeticExpression() {
        var MLeft: ArithmeticExpression? = null
        var MRight: ArithmeticExpression? = null

        init {
            this.MLeft = left
            this.MRight = right
        }

        override fun interpret(surroundings: Surroundings): Any {
            surroundings.put(MLeft!!.interpret(surroundings) as String, MRight!!.interpret(surroundings) as Int)
            return -1024
        }
    }
}