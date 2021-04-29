package com.example.baselibrary.mvp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Date:2021/4/28
 * Time:17:36
 * author:joker
 * 属性动画测试，自定义View
 */
public class ObjectAnimatorCustomView extends View {

    public static final float RADIUS = 100f;// 圆的半径 = 100
    private Paint mPaint;// 绘图画笔
    private String color;// 设置背景颜色属性

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        // 将画笔的颜色设置成方法参数传入的颜色
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }


    public ObjectAnimatorCustomView(Context context) {
        super(context);
    }

    public ObjectAnimatorCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
    }

    public ObjectAnimatorCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(500, 500, RADIUS, mPaint);
    }
}
