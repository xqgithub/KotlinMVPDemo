package com.example.baselibrary.designpatterns.interpreter

/**
 * 解释器模式---终结符表达式
 */
class Terminator {

    /**
     * 数字表达式，用来解释数字
     */
    class NumExpression(num: Any) : ArithmeticExpression() {
        var mNum: Any? = null

        init {
            this.mNum = num
        }

        override fun interpret(surroundings: Surroundings): Int {
            return if (mNum is Int) (mNum as Int) else mNum.toString().toInt()
        }
    }

    /**
     * 变量表达式，用来解释变量
     */
    class VarExpression(varr: String) : ArithmeticExpression() {
        var mvarr: String? = null

        init {
            this.mvarr = varr
        }

        //解释变量
        override fun interpret(surroundings: Surroundings): String {
            return mvarr!!
        }
    }
}