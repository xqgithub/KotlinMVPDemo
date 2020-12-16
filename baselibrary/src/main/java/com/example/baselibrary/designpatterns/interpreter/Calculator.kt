package com.example.baselibrary.designpatterns.interpreter

/**
 * 解释器模式---计算器类
 */
class Calculator {
    val surroundings = Surroundings()

    private lateinit var mExpression: ArithmeticExpression

    /**
     * 读取表达式
     */
    fun read(expression: String) {
        //表达式以空格隔开，方便拆分
        val split = expression.split(" ")
        when (split[1]) {
            "+" -> mExpression = NonTerminator.AddExpression(Terminator.VarExpression(split[0]), Terminator.VarExpression(split[2]))
            "=" -> NonTerminator.EqualExpression(Terminator.VarExpression(split[0]), Terminator.NumExpression(split[2])).interpret(surroundings)
        }
    }

    /**
     *计算结果
     */
    fun calculate(): Int {
        return mExpression.interpret(surroundings).toString().toInt()
    }

}