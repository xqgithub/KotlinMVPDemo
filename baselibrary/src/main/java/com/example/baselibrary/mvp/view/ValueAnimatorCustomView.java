package com.example.baselibrary.mvp.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.baselibrary.mvp.entity.Point;
import com.example.baselibrary.mvp.view.typeevaluator.PointEvaluator;
import com.example.baselibrary.utils.StringUtils;

/**
 * Date:2021/4/28
 * Time:9:54
 * author:joker
 * 属性动画测试，自定义View
 */
public class ValueAnimatorCustomView extends View implements View.OnClickListener {

    public static final float radius = 70f;// 圆的半径 = 70
    private Point currentPoint;// 当前点坐标
    private Paint mPaint;// 绘图画笔

    public ValueAnimatorCustomView(Context context) {
        super(context);
    }

    public ValueAnimatorCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        setOnClickListener(this);
    }

    public ValueAnimatorCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (StringUtils.isBlank(currentPoint)) {
            //创建一个点对象 坐标是（70,70）
            currentPoint = new Point(70, 70);

            //在该点上画一个圆
            float x = currentPoint.getX();
            float y = currentPoint.getY();
            canvas.drawCircle(x, y, radius, mPaint);
        } else {
            //坐标不为null，直接画圆
            float x = currentPoint.getX();
            float y = currentPoint.getY();
            canvas.drawCircle(x, y, radius, mPaint);
        }
    }

    @Override
    public void onClick(View v) {
        //创建初始动画时的对象点  & 结束动画时的对象点
        Point startPoint = new Point(70, 70);// 初始点为圆心(70,70)
        Point endPoint = new Point(700, 1000);// 结束点为(700,1000)

        //创建动画对象 & 设置初始值 和 结束值
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        valueAnimator.setDuration(3000);//动画时长
        valueAnimator.setStartDelay(0);//动画延迟播放时间
        valueAnimator.setRepeatCount(0);//动画播放次数=重放次数+1, infinite动画无限重复
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);//设置重复播放动画模式：RESTART 正序重放;REVERSE:倒序回放
        //设置更新监听器：即数值每次变化更新都会调用该方法
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        //启动动画
        valueAnimator.start();
    }
}
