package com.example.baselibrary.mvp.view.calendarview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.core.content.ContextCompat;

import com.example.baselibrary.R;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.WeekView;

/**
 * Date:2021/5/10
 * Time:9:56
 * author:joker
 * 自定义周视图(在使用自定义星期栏后，才会生效)
 */
public class CustomizeWeekView extends WeekView {

    private int mRadius;

    private Context mContext;
    private Paint mPaint_selected_bg;
    private Paint mPaint_today_bg;

    public CustomizeWeekView(Context context) {
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

    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, boolean hasScheme) {
//        int cx = x + mItemWidth / 2;
//        int cy = mItemHeight / 2;
//        canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);

        //圆角矩形
        RectF rectF = new RectF(x, 0, x + mItemWidth, 0 + mItemHeight);
        canvas.drawRoundRect(rectF, 30, 30, mPaint_selected_bg);
        return false;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x) {
        int cx = x + mItemWidth / 2;
        int cy = mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine;
        int cx = x + mItemWidth / 2;


        if (calendar.isCurrentDay() && !isSelected) {
            //绘制背景
            RectF rectF = new RectF(x, 0, x + mItemWidth, 0 + mItemHeight);
            canvas.drawRoundRect(rectF, 30, 30, mPaint_today_bg);
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
                            calendar.isCurrentMonth() ? mSchemeTextPaint : mSchemeTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() ? mCurMonthTextPaint : mCurMonthTextPaint);
        }
    }


}
