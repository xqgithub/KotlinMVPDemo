package com.example.baselibrary.mvp.view.typeevaluator;

/**
 * Date:2021/4/29
 * Time:14:57
 * author:joker
 * 自定义估值器 实现圆的轨迹移动
 */

import android.animation.TypeEvaluator;

import com.example.baselibrary.mvp.entity.Point;

public class PointEvaluator implements TypeEvaluator {

    /**
     * 里写入对象动画过渡的逻辑
     */
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        //将动画初始值startValue 和 动画结束值endValue 强制类型转换成Point对象
        Point startPoint = (Point) startValue;
        Point endPoint = (Point) endValue;
        // 根据fraction来计算当前动画的x和y的值
        float x = startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX());
        float y = startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY());
        // 将计算后的坐标封装到一个新的Point对象中并返回
        return new Point((int) x, (int) y);
    }
}
