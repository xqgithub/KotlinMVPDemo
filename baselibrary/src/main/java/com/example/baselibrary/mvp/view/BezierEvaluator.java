package com.example.baselibrary.mvp.view;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

import com.example.baselibrary.utils.BezierUtil;

/**
 * Date:2021/4/27
 * Time:10:28
 * author:joker
 */
public class BezierEvaluator implements TypeEvaluator<PointF> {

    private PointF mControlPoint;

    public BezierEvaluator(PointF controlPoint) {
        this.mControlPoint = controlPoint;
    }

    @Override
    public PointF evaluate(float t, PointF startValue, PointF endValue) {
        return BezierUtil.CalculateBezierPointForQuadratic(t, startValue, mControlPoint, endValue);
    }

}
