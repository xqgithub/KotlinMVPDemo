package com.example.baselibrary.mvp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.example.baselibrary.R;
import com.example.baselibrary.utils.StringUtils;

import java.lang.reflect.Field;

/**
 * Date:2021/5/27
 * Time:17:48
 * author:joker
 * 自定义EditText,后期可以扩展
 */
public class EditTextCustomize extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {


    private Context mContext;
    private onEditTextListener onEditTextListener;


    //清除输入框内容 图片按钮
    private Drawable mClearDrawable;
    private int clearDrawableRes;
    //删除按钮的宽
    private int clearDrawable_width = 0;
    //删除按钮的高
    private int clearDrawable_height = 0;
    //删除按钮距离EditText右边距
    private int clearDrawablePaddingRight = 0;

    //是否显示删除按钮
    private boolean isShowClearDrawable;
    //焦点
    private boolean hasFocus = false;

    public EditTextCustomize(@NonNull Context context) {
        super(context);
    }

    public EditTextCustomize(@NonNull Context context, @Nullable AttributeSet attrs) {
        // 这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
        this.mContext = context;

    }

    public EditTextCustomize(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    /**
     * 设置监听
     */
    public void setOnEditTextListener(onEditTextListener onEditTextListener) {
        this.onEditTextListener = onEditTextListener;
    }


    /**
     * 初始化
     */
    private void init(Context context, AttributeSet attrs) {
        // 获取控件资源
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextCustomize);
        isShowClearDrawable = typedArray.getBoolean(R.styleable.EditTextCustomize_isShowClearDrawable, false);
        clearDrawableRes = typedArray.getResourceId(R.styleable.EditTextCustomize_clearDrawableRes, R.drawable.icon_search_clear);
        mClearDrawable = context.getResources().getDrawable(clearDrawableRes, null);
        clearDrawable_width = typedArray.getDimensionPixelOffset(R.styleable.EditTextCustomize_clearDrawableWidth, 0);
        clearDrawable_height = typedArray.getDimensionPixelOffset(R.styleable.EditTextCustomize_clearDrawableHeight, 0);
        clearDrawablePaddingRight = typedArray.getDimensionPixelOffset(R.styleable.EditTextCustomize_clearDrawablePaddingRight, 0);
        mClearDrawable.setBounds(-clearDrawablePaddingRight, 0, clearDrawable_width - clearDrawablePaddingRight, clearDrawable_height);

        // 设置焦点改变的监听
        setOnFocusChangeListener(this);
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
        //设置光标的颜色
        setCursorColor();
    }


    /**
     * 设置EditText框的背景
     *
     * @param view         要改变的view
     * @param CornerRadius 圆角度数
     * @param strokewidth  边的宽度
     * @param strokeColor  边的颜色
     * @param bgcolor      背景色
     */

    public void setEditTextBG(View view, int CornerRadius, int strokewidth, String strokeColor, String bgcolor) {
        setDynamicShapeRECTANGLE(mContext, view, CornerRadius, strokewidth, strokeColor, bgcolor);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (!StringUtils.isBlank(onEditTextListener)) {
            onEditTextListener.beforeTextChanged(s, start, count, after);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!StringUtils.isBlank(onEditTextListener)) {
            onEditTextListener.onTextChanged(s, start, before, count);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!StringUtils.isBlank(onEditTextListener)) {
            onEditTextListener.afterTextChanged(s);
        }

        if (s.length() > 0 && hasFocus) {
            //焦点存在，而且有输入值，显示右侧删除图标
            setClearDrawable(true);
        } else {
            setClearDrawable(false);
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!StringUtils.isBlank(onEditTextListener)) {
            onEditTextListener.onFocusChange(v, hasFocus);
        }

        this.hasFocus = hasFocus;
        if (hasFocus) {
            setClearDrawable(getText().length() > 0);
        } else {
            setClearDrawable(false);
        }
    }

//    @Override
//    protected void onSelectionChanged(int selStart, int selEnd) {
//        super.onSelectionChanged(selStart, selEnd);
//        //保证光标始终在最后面
//        if (selStart == selEnd) {//防止不能多选
//            setSelection(getText().length());
//        }
//    }

    /**
     * 动态设置Shape  RECTANGLE
     */
    private void setDynamicShapeRECTANGLE(Context mContext, View view, float CornerRadius, int strokewidth, String strokeColor, String bgcolor) {
        GradientDrawable drawable = new GradientDrawable();
        //设置shape的形状
        drawable.setShape(GradientDrawable.RECTANGLE);

        //设置shape的圆角度数
        if (!StringUtils.isBlank(CornerRadius) && CornerRadius != -1) {
            drawable.setCornerRadius(CornerRadius);
        }

        //设置shape的边的宽度和颜色
        if (!StringUtils.isBlank(strokewidth) && strokewidth != -1
                && !StringUtils.isBlank(strokeColor)) {
//            drawable.setStroke(strokewidth, ContextCompat.getColor(mContext, R.color.appblack));
            drawable.setStroke(strokewidth, Color.parseColor(strokeColor));
        }

        //设置shape的背景色
        if (!StringUtils.isBlank(bgcolor)) {
//            drawable.setColor(ContextCompat.getColor(mContext, bgcolor));
            drawable.setColor(Color.parseColor(bgcolor));
        }
        view.setBackground(drawable);
    }


    /**
     * 设置光标的颜色
     */
    private void setCursorColor() {
        try {
            GradientDrawable myGrad1 = (GradientDrawable) getResources().getDrawable(R.drawable.edit_cursor_color, null);
            myGrad1.setColor(Color.parseColor("#1d6aff"));
//        myGrad1.setSize(4, 20);

            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(this, myGrad1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置删除按钮图标
     */
    private void setClearDrawable(boolean visible) {
        if (isShowClearDrawable) {
            Drawable drawable = visible ? mClearDrawable : null;
            setCompoundDrawables(null, null, drawable, null);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //设置右内边距, 防止清除按钮和文字重叠
        if (isShowClearDrawable) {
//            setPadding(getPaddingLeft(), getPaddingTop(), mTextPaddingRight, getPaddingBottom());
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (isShowClearDrawable) {
                    Drawable drawable = mClearDrawable;
                    if (drawable != null && event.getX() <= (getWidth() - getPaddingRight())
                            && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width() - clearDrawablePaddingRight)) {
                        setText("");
//                        ToastUtils.showShortToastFromText("我点击了删除按钮", ToastUtils.bottom);
                    }
                }
                break;
        }


        return super.onTouchEvent(event);
    }

    /**
     * 接口输出
     */
    public interface onEditTextListener {

        void beforeTextChanged(CharSequence s, int start, int count, int after);

        void onTextChanged(CharSequence s, int start, int before, int count);

        void afterTextChanged(Editable s);

        void onFocusChange(View v, boolean hasFocus);
    }
}
