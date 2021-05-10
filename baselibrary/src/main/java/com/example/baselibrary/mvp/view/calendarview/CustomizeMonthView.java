package com.example.baselibrary.mvp.view.calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.core.content.ContextCompat;

import com.example.baselibrary.R;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

/**
 * Date:2021/5/10
 * Time:9:54
 * author:joker
 * 自定义月视图
 */
public class CustomizeMonthView extends MonthView {

    private int mRadius;

    private Context mContext;
    private Paint mPaint_selected_bg;
    private Paint mPaint_today_bg;

    private float mPointRadius;
    private Paint mPointPaint;

    public CustomizeMonthView(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
        mSchemePaint.setStyle(Paint.Style.STROKE);


        mPaint_selected_bg = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_selected_bg.setColor(ContextCompat.getColor(mContext, R.color.login_submit_bg_end));
        mPaint_selected_bg.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint_selected_bg.setStrokeWidth(1f);


        mPaint_today_bg = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_today_bg.setColor(ContextCompat.getColor(mContext, R.color.login_et_password_hint));
        mPaint_today_bg.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint_today_bg.setStrokeWidth(1f);


        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setColor(ContextCompat.getColor(mContext, R.color.login_submit_bg_end));
        mPointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPointPaint.setTextAlign(Paint.Align.CENTER);
        mPointPaint.setStrokeWidth(1f);
        mPointRadius = 10f;
    }

    @Override
    protected void onLoopStart(int x, int y) {

    }

    /**
     * 绘制选中的日子
     *
     * @param canvas
     * @param calendar  日历日历calendar
     * @param x         日历Card x起点坐标
     * @param y         日历Card y起点坐标
     * @param hasScheme hasScheme 非标记的日期
     * @return 返回true 则绘制onDrawScheme，因为这里背景色不是是互斥的，所以返回true
     */
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
//        int cx = x + mItemWidth / 2;
//        int cy = y + mItemHeight / 2;
//        canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);

        //圆角矩形
        RectF rectF = new RectF(x, y, x + mItemWidth, y + mItemHeight);
        canvas.drawRoundRect(rectF, 30, 30, mPaint_selected_bg);
        return true;
    }

    /**
     * 绘制标记的事件日子
     *
     * @param canvas
     * @param calendar
     * @param x
     * @param y
     */
    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
//        int cx = x + mItemWidth / 2;
//        int cy = y + mItemHeight / 2;
//        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);

        boolean isSelected = isSelected(calendar);
        if (isSelected) {
            mPointPaint.setColor(ContextCompat.getColor(mContext, R.color.appwhite));
        } else {
            mPointPaint.setColor(ContextCompat.getColor(mContext, R.color.login_submit_bg_end));
        }
        canvas.drawCircle(x + mItemWidth / 2, y + mItemHeight - 3 * 10, mPointRadius, mPointPaint);
    }

    /**
     * 绘制文本
     *
     * @param canvas
     * @param calendar
     * @param x
     * @param y
     * @param hasScheme
     * @param isSelected 是否选中
     */
    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;


        if (calendar.isCurrentDay() && !isSelected) {
            //绘制背景
            RectF rectF = new RectF(x, y, x + mItemWidth, y + mItemHeight);
            canvas.drawRoundRect(rectF, 30, 30, mPaint_today_bg);

            if (hasScheme) {
                canvas.drawCircle(x + mItemWidth / 2, y + mItemHeight - 3 * 10, mPointRadius, mPointPaint);
            }
        }

        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    mSelectTextPaint);
        } else if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }


    }


}
