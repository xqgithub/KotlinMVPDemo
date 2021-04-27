package com.example.baselibrary.mvp.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.baselibrary.utils.BezierUtil;

/**
 * Date:2021/4/27
 * Time:11:03
 * author:joker
 * 通过计算模拟二阶、三阶贝塞尔曲线
 */
public class CalculateBezierPointView extends View implements View.OnClickListener {

    private Paint mPaint;
    private ValueAnimator mAnimatorQuadratic;
    private ValueAnimator mAnimatorCubic;
    private PointF mPointQuadratic;
    private PointF mPointCubic;

    public CalculateBezierPointView(Context context) {
        super(context);
    }

    public CalculateBezierPointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAnimatorQuadratic = ValueAnimator.ofFloat(0, 1);
        mAnimatorQuadratic.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF point = BezierUtil.CalculateBezierPointForQuadratic(valueAnimator.getAnimatedFraction(),
                        new PointF(100, 100), new PointF(500, 100), new PointF(500, 500));
                mPointQuadratic.x = point.x;
                mPointQuadratic.y = point.y;
                invalidate();
            }
        });

        mAnimatorCubic = ValueAnimator.ofFloat(0, 1);
        mAnimatorCubic.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF point = BezierUtil.CalculateBezierPointForCubic(valueAnimator.getAnimatedFraction(),
                        new PointF(100, 600), new PointF(100, 1100), new PointF(500, 1000), new PointF(500, 600));
                mPointCubic.x = point.x;
                mPointCubic.y = point.y;
                invalidate();
            }
        });


        mPointQuadratic = new PointF();
        mPointQuadratic.x = 100;
        mPointQuadratic.y = 100;

        mPointCubic = new PointF();
        mPointCubic.x = 100;
        mPointCubic.y = 600;


        setOnClickListener(this);
    }

    public CalculateBezierPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mPointQuadratic.x, mPointQuadratic.y, 10, mPaint);
        canvas.drawCircle(mPointCubic.x, mPointCubic.y, 10, mPaint);
    }

    @Override
    public void onClick(View v) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(mAnimatorQuadratic, mAnimatorCubic);
        set.setDuration(2000);
        set.start();
    }
}
