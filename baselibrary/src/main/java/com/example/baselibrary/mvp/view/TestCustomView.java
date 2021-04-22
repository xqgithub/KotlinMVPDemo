package com.example.baselibrary.mvp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.baselibrary.R;
import com.example.baselibrary.constants.ConfigConstants;
import com.example.baselibrary.utils.LogUtils;
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA;
import com.example.baselibrary.utils.ScreenUtils;
import com.example.baselibrary.utils.StringUtils;

/**
 * Created by  on 2021/2/2.
 */

public class TestCustomView extends View {

    private Context mContext;
    private Canvas mCanvas;
    private int methodNums = -1;

    public TestCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initPaint();
        initPaint2();
        initPaint3(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = mContext.getResources().getDimensionPixelSize(R.dimen.dimen_200x);
        int height = mContext.getResources().getDimensionPixelSize(R.dimen.dimen_200x);

        int view_width = getMySize(width, widthMeasureSpec);
        int view_height = getMySize(height, heightMeasureSpec);

        setMeasuredDimension(view_width, view_height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        LogUtils.i(ConfigConstants.TAG_ALL, "onDraw =-=");

        this.mCanvas = canvas;

        if (methodNums == -1) {
            return;
        }

        //清理屏幕
        mDrawClearScreen();
        //绘制背景
        mDrawBG(mCanvas);

        if (methodNums == 1) {
            mDrawColor(canvas);
        } else if (methodNums == 2) {
            mDrawPoints(canvas);
        } else if (methodNums == 3) {
            mDrawLine(canvas);
        } else if (methodNums == 4) {
            mDrawRect(canvas);
        } else if (methodNums == 5) {
            mDrawRoundRect(canvas);
        } else if (methodNums == 6) {
            mDrawOval(canvas);
        } else if (methodNums == 7) {
            mDrawCircle(canvas);
        } else if (methodNums == 8) {
            mDrawArc(canvas);
        } else if (methodNums == 9) {
            mDrawText(canvas);
        } else if (methodNums == 10) {
            mDrawText2(canvas);
        } else if (methodNums == 11) {
            mDrawText3(canvas);
        } else if (methodNums == 12) {
            mDrawPicture(canvas);
        } else if (methodNums == 13) {
            mDrawBitmap(canvas);
        } else if (methodNums == 14) {
            mDrawTranslate(canvas);
        } else if (methodNums == 15) {
            mDrawScale(canvas);
        } else if (methodNums == 16) {
            mDrawSkew(canvas);
        } else if (methodNums == 17) {
            mDrawClip(canvas);
        } else if (methodNums == 18) {
            mDrawSave(canvas);
        } else if (methodNums == 19) {
            mDrawSaveLayer(canvas);
        } else if (methodNums == 20) {
            mDrawSetXfermode(canvas);
        } else if (methodNums == 21) {
            mDrawPath(canvas);
        } else if (methodNums == 22) {
            mDrawPath2(canvas);
        } else if (methodNums == 23) {
            mDrawPath3(canvas);
        } else if (methodNums == 24) {
            mDrawPath4(canvas);
        } else if (methodNums == 25) {
            mDrawPath5(canvas);
        } else if (methodNums == 26) {
            mDrawPath6(canvas);
        } else if (methodNums == 27) {
            mDrawPath7(canvas);
        } else if (methodNums == 28) {
            mDrawPath8(canvas);
        } else if (methodNums == 29) {
            mDrawPath9(canvas);
        } else if (methodNums == 30) {
            mDrawPath10(canvas);
        } else if (methodNums == 31) {
            mDrawPath11(canvas);
        } else if (methodNums == 32) {
            mDrawPath12(canvas);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.i(ConfigConstants.TAG_ALL, "event.getAction() =-=" + event.getAction());
        return super.onTouchEvent(event);
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {//match_parent/具体宽高值
            mySize = size;
        } else if (mode == MeasureSpec.AT_MOST) {//wrap_content
            //默认值为defaultSize,此处要结合父控件给子控件的最多大小(要不然会填充父控件),所以采用最小值
            mySize = Math.min(defaultSize, size);
        } else if (mode == MeasureSpec.UNSPECIFIED) {//0  父控件没有对子控件施加任何约束，子控件可以得到任意想要的大小
            mySize = defaultSize;
        }
        return mySize;
    }


    /**
     * 画笔1
     */

    private Paint mPaint;

    public void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        //类型1：Paint.Style.FILLANDSTROKE（描边+填充）
        //类型2：Paint.Style.FILL（只填充不描边）
        //类型3：Paint.Style.STROKE（只描边不填充）
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(5f);
        mPaint.setAntiAlias(true);
    }

    /**
     * 画笔2
     */

    private Paint mPaint2;

    public void initPaint2() {
        mPaint2 = new Paint();
        mPaint2.setColor(Color.BLUE);
        mPaint2.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint2.setStrokeWidth(5f);
        mPaint2.setAntiAlias(true);
    }

    /**
     * 画笔2
     */

    private Paint mPaint3;

    public void initPaint3(Context context) {
        mPaint3 = new Paint();
        mPaint3.setColor(ContextCompat.getColor(context, R.color.update_normal));
        mPaint3.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint3.setStrokeWidth(1f);
        mPaint3.setAntiAlias(true);
        mPaint3.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.dimen_20x));
    }

    /**
     * 绘制画布的背景
     */
    public void mDrawBG(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(mContext, R.color.appgay));
        paint.setStrokeWidth(1f);
        paint.setAntiAlias(true);

        int ScreenWidth = ScreenUtils.getScreenWidth();
        int ScreenHeight = ScreenUtils.getScreenHeight();

        RectF m_rcBK = new RectF(0, 0, ScreenWidth, ScreenHeight);
        canvas.drawRect(m_rcBK, paint);
    }


    /**
     * 绘制颜色
     */

    public void mDrawColor(Canvas canvas) {
        // 传入一个Color类的常量参数来设置画布颜色
        // 绘制蓝色
        canvas.drawColor(Color.BLUE);
    }

    /**
     * 绘制点
     */

    public void mDrawPoints(Canvas canvas) {
        // 特别注意：需要用到画笔Paint
        // 所以之前记得创建画笔
        // 为了区分，这里使用了两个不同颜色的画笔

        // 描绘一个点
        // 在坐标(200,200)处
        canvas.drawPoint(300, 300, mPaint);

        // 绘制一组点，坐标位置由float数组指定
        // 此处画了3个点，位置分别是：（600,500）、（600,600）、（600,700）
        canvas.drawPoints(new float[]{
                600, 500,
                600, 600,
                600, 700
        }, mPaint2);
    }

    /**
     * 绘制直线
     */

    public void mDrawLine(Canvas canvas) {
        // 画一条直线
        // 在坐标(100,200)，(700,200)之间绘制一条直线
        canvas.drawLine(100, 200, 700, 200, mPaint);

        // 绘制一组线
        // 在坐标(400,500)，(500,500)之间绘制直线1
        // 在坐标(400,600)，(500,600)之间绘制直线2
        canvas.drawLines(new float[]{
                400, 500, 500, 500,
                400, 600, 500, 600
        }, mPaint2);
    }

    /**
     * 绘制矩形
     */

    public void mDrawRect(Canvas canvas) {
        // 关于绘制矩形，Canvas提供了三种重载方法

        // 方法1：直接传入两个顶点的坐标
        // 两个顶点坐标分别是：（100,100），（800,400）
        canvas.drawRect(100, 100, 800, 400, mPaint);

        // 方法2：将两个顶点坐标封装为RectRectF
        Rect rect = new Rect(100, 100, 800, 400);
        canvas.drawRect(rect, mPaint);

        // 方法3：将两个顶点坐标封装为RectF
        RectF rectF = new RectF(100, 100, 800, 400);
        canvas.drawRect(rectF, mPaint);

        // 特别注意：Rect类和RectF类的区别
        // 精度不同：Rect = int & RectF = float

        // 三种方法画出来的效果是一样的。
    }

    /**
     * 绘制圆角矩形
     */

    public void mDrawRoundRect(Canvas canvas) {
        // 方法1：直接传入两个顶点的坐标
        // API21时才可使用
        // 第5、6个参数：rx、ry是圆角的参数，下面会详细描述
        if (PublicPracticalMethodFromJAVA.getInstance().isHigherSpecifySDKVersion(Build.VERSION_CODES.LOLLIPOP)) {
            canvas.drawRoundRect(100, 100, 600, 400, 30, 30, mPaint);
        }
        // 方法2：使用RectF类
        RectF rectF = new RectF(100, 100, 600, 400);
        canvas.drawRoundRect(rectF, 30, 30, mPaint);
    }

    /**
     * 绘制椭圆
     */

    public void mDrawOval(Canvas canvas) {
        // 方法1：使用RectF类
        RectF rectF = new RectF(100, 100, 600, 400);
        canvas.drawOval(rectF, mPaint);

        if (PublicPracticalMethodFromJAVA.getInstance().isHigherSpecifySDKVersion(Build.VERSION_CODES.LOLLIPOP)) {
            // 方法2：直接传入与矩形相关的参数
            canvas.drawOval(100, 100, 600, 400, mPaint);
        }
        // 为了方便表示，画一个和椭圆一样参数的矩形
        canvas.drawRect(100, 100, 600, 400, mPaint2);
    }

    /**
     * 绘制圆
     */

    public void mDrawCircle(Canvas canvas) {
        // 参数说明：
        // 1、2：圆心坐标
        // 3：半径
        // 4：画笔
        // 绘制一个圆心坐标在(500,500)，半径为400 的圆。
        canvas.drawCircle(360, 360, 200, mPaint);
    }

    /**
     * 绘制圆弧
     */

    public void mDrawArc(Canvas canvas) {
        // 以下示例：绘制两个起始角度为0度、扫过90度的圆弧
        // 两者的唯一区别就是是否使用了中心点

        // 绘制圆弧1(无使用中心)
        RectF rectF = new RectF(100, 100, 600, 300);
        // 绘制背景矩形
        canvas.drawRect(rectF, mPaint);
        // 绘制圆弧
        canvas.drawArc(rectF, 0, 90, false, mPaint2);

        // 绘制圆弧2(使用中心)
        RectF rectF2 = new RectF(100, 600, 600, 900);
        // 绘制背景矩形
        canvas.drawRect(rectF2, mPaint);
        // 绘制圆弧
        canvas.drawArc(rectF2, 0, 90, true, mPaint2);
    }

    /**
     * 绘制文字
     * 情况1：指定文本开始的位置
     */

    public void mDrawText(Canvas canvas) {
        // 参数text：要绘制的文本
        // 参数x，y：指定文本开始的位置（坐标）
        canvas.drawText("abcdefg", 300, 400, mPaint3);

        // 仅绘制文本的一部分
        // 参数start，end：指定绘制文本的位置
        // 位置以下标标识，由0开始
        canvas.drawText("abcdefg", 1, 4, 300, 500, mPaint3);

        // 字符数组情况
        // 字符数组(要绘制的内容)
        char[] chars = "abcdefg".toCharArray();
        // 参数为 (字符数组 起始坐标 截取长度 基线x 基线y 画笔)
        canvas.drawText(chars, 1, 3, 300, 600, mPaint3);
    }

    /**
     * 绘制文字
     * 情况2：分别指定文本的位置
     */

    public void mDrawText2(Canvas canvas) {
        // 参数text：绘制的文本
        // 参数pos：数组类型，存放每个字符的位置（坐标）
        // 注意：必须指定所有字符位置
        // 实例
        canvas.drawPosText("abcde", new float[]{
                100, 100,    // 第一个字符位置
                200, 200,    // 第二个字符位置
                300, 300,    // ...
                400, 400,
                500, 500
        }, mPaint3);
    }

    /**
     * 绘制文字
     * 情况3：指定路径，并根据路径绘制文字
     */

    public void mDrawText3(Canvas canvas) {

        // 在路径(540,750,640,450,840,600)写上"在Path上写的字:Carson_Ho"字样
        // 1.创建路径对象
        Path path = new Path();
        // 2. 设置路径轨迹
        path.cubicTo(540, 750, 640, 450, 840, 600);
        // 3. 画路径
//        canvas.drawPath(path, mPaint3);
        // 4. 画出在路径上的字
        canvas.drawTextOnPath("在Path上写的字:Carson_Ho", path, 50, 0, mPaint3);
    }


    /**
     * 绘制图片
     * 情况1：绘制矢量图
     */

    public void mDrawPicture(Canvas canvas) {
        //方法2:
        //创建Picture对象
        Picture mPicture = new Picture();
        //开始录制
        Canvas recordingCanvas = mPicture.beginRecording(300, 300);// 注：要创建Canvas对象来接收beginRecording()返回的Canvas对象
        //绘制内容 or 操作Canvas
        // 位移
        // 将坐标系的原点移动到(450,650)
        recordingCanvas.translate(450, 650);
        // 记得先创建一个画笔
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        // 绘制一个圆
        // 圆心为（0，0），半径为100
        recordingCanvas.drawCircle(0, 0, 100, paint);
        //结束录制
        mPicture.endRecording();
        //将存储在Picture的绘制内容绘制出来

        // 实例1：将录制的内容显示（区域刚好布满图形）
        canvas.drawPicture(mPicture, new RectF(0, 0, mPicture.getWidth(), mPicture.getHeight()));

        // 实例2：将录制的内容显示在当前画布上（区域小于图形）
//        canvas.drawPicture(mPicture, new RectF(0, 0, mPicture.getWidth(), 200));
    }

    /**
     * 绘制图片
     * 情况2：绘制矢量图
     */
    @SuppressLint("ResourceType")
    public void mDrawBitmap(Canvas canvas) {
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.error_null);

        // 后两个参数matrix, paint是在绘制时对图片进行一些改变
        // 后面会专门说matrix
        // 如果只是将图片内容绘制出来只需将传入新建的matrix, paint对象即可：
        //方法1
//        canvas.drawBitmap(bitmap, new Matrix(), new Paint());
        // 记得选取一种获取Bitmap的方式
        // 注：图片左上角位置默认为坐标原点。

        //方法2
        //参数 left、top指定了图片左上角的坐标(距离坐标原点的距离)：
//        canvas.drawBitmap(bitmap, 100, 200, new Paint());

        //方法3
        //参数（src，dst） = 两个矩形区域
        // Rect src：指定需要绘制图片的区域（即要绘制图片的哪一部分）
        // Rect dst 或RectF dst：指定图片在屏幕上显示(绘制)的区域
        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//        // 指定图片在屏幕上显示的区域
        int screenwidth = ScreenUtils.getScreenWidth();
        int screenheight = ScreenUtils.getScreenHeight();


        Rect dst = new Rect(0, 0, screenwidth, screenheight);
//        // 绘制图片
        canvas.drawBitmap(bitmap, src, dst, null);
    }

    /**
     * 画布变换---平移
     */

    public void mDrawTranslate(Canvas canvas) {
        // 记得先创建一个画笔
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        canvas.translate(100, 100);
        Rect rect = new Rect(50, 50, 150, 150);
        canvas.drawRect(rect, paint);
        // 将画布原点向右移200px，向下移100px

        // 注：位移是基于当前位置移动，而不是每次都是基于屏幕左上角的(0,0)点移动
    }

    /**
     * 画布变换---缩放
     */

    public void mDrawScale(Canvas canvas) {
        // 记得先创建一个画笔
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        Rect rect = new Rect(0, 0, 100, 100);
        canvas.drawRect(rect, paint);
        // 将画布放大到1.5倍
        // 不移动缩放中心，即缩放中心默认为（0，0）
//        canvas.scale(1.5f, 1.5f);
//        // 绘制放大1.5倍后的蓝色矩形(红色)
//        canvas.drawRect(rect, paint);


//        // 将画布放大到1.5倍,并将缩放中心移动到(100,0)
        canvas.scale(1.5f, 1.5f, -200, 0);
//        // 绘制放大1.5倍后的蓝色矩形(红色)
        canvas.drawRect(rect, paint);
    }

    /**
     * 画布变换---错切
     */

    public void mDrawSkew(Canvas canvas) {
        // 记得先创建一个画笔
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        canvas.translate(300, 300);
        Rect rect = new Rect(0, 0, 100, 100);
        canvas.drawRect(rect, paint);

        Paint paint2 = new Paint();
        paint2.setColor(Color.GREEN);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setAntiAlias(true);

        // 向X正方向倾斜45度
        canvas.skew(1f, 0);
        canvas.drawRect(0, 0, 100, 100, paint2);

    }

    /**
     * 画布裁剪
     */

    public void mDrawClip(Canvas canvas) {
        // 记得先创建一个画笔
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        // 为了方便观察,我将坐标系移到屏幕中央
        canvas.translate(300, 300);
        //原来画布设置为灰色
        canvas.drawColor(Color.GRAY);
        //第一次裁剪
        canvas.clipRect(0, 0, 300, 600);
        //将第一次裁剪后的区域设置为红色
        canvas.drawColor(Color.RED);
        //第二次裁剪,并显示第一次裁剪与第二次裁剪不重叠的区域
//        canvas.clipRect(0, 200, 300, 300, Region.Op.DIFFERENCE);
        canvas.clipRect(0, 200, 300, 300, Region.Op.REPLACE);
        //将第一次裁剪与第二次裁剪不重叠的区域设置为黑色
        canvas.drawColor(Color.BLACK);
    }

    /**
     * save的使用
     * 1.save()方法会保存当前Canvas的matrix和clip到一个私有栈中
     * 2.save方法调用之后，仍然可以像平常一样调用translate,scale,rotate,skew,concat或者clipRect
     * 3.如果稍后调用了restore()方法，那么之前调用的方法并不会影响之后的操作，会将canvas恢复至save之前的状态
     * 4.save()方法是有返回值的，通过restoreToCount方法返回到save之前的状态
     */
    public void mDrawSave(Canvas canvas) {
        LogUtils.i(ConfigConstants.TAG_ALL, "getSaveCount1 =-= " + canvas.getSaveCount());
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(1f);
        paint.setAntiAlias(true);
        Rect rect = new Rect(100, 100, 800, 400);
        canvas.drawRect(rect, paint);
        int count = canvas.save();
        LogUtils.i(ConfigConstants.TAG_ALL, "count =-= " + count);
        canvas.translate(100, 100);
        paint.setColor(Color.BLUE);
        canvas.drawRect(rect, paint);
        int count2 = canvas.save();
        LogUtils.i(ConfigConstants.TAG_ALL, "count2 =-= " + count2);
        canvas.restoreToCount(count);
        canvas.translate(150, 150);
        paint.setColor(Color.GREEN);
        canvas.drawRect(rect, paint);
        LogUtils.i(ConfigConstants.TAG_ALL, "getSaveCount3 =-= " + canvas.getSaveCount());
    }


    /**
     * saveLayer 使用
     *
     * <p>saveLayer的saveFlags
     * MATRIX_SAVE_FLAG:只保存图层的matrix矩阵
     * CLIP_SAVE_FLAG:只保存大小信息
     * HAS_ALPHA_LAYER_SAVE_FLAG：表明该图层有透明度，和下面的标识冲突，都设置时以下面的标志为准（只适用于saveLayer）
     * FULL_COLOR_LAYER_SAVE_FLAG：完全保留该图层颜色（和上一图层合并时，清空上一图层的重叠区域，保留该图层的颜色）（只适用于saveLayer）
     * CLIP_TO_LAYER_SAVE_：创建图层时，会把canvas（所有图层）裁剪到参数指定的范围，如果省略这个flag将导致图层开销巨大（实际上图层没有裁剪，与原图层一样大）
     * ALL_SAVE_FLAG。//保存全部
     * <p>
     * saveLayer与save的不同点
     * 1.saveLayer()是生成一个独立的图层，而save()只是保存了一下画布的状态，这里说的画布的状态类似一个还原点。
     * 2.saveLayer()由于会生成一个新的图层，所以更加耗费内存，需要慎用。
     * 3.saveLayer()可以保存特定的区域
     * 4.在使用混合模式setXfermode时会产生不同的影响
     */
    private RectF m_rcBK;

    public void mDrawSaveLayer(Canvas canvas) {
        int saveCount = 0;
        LogUtils.i(ConfigConstants.TAG_ALL, "getSaveCount =-= " + canvas.getSaveCount());

        int ScreenWidth = ScreenUtils.getScreenWidth();
        int ScreenHeight = ScreenUtils.getScreenHeight();
        if (StringUtils.isBlank(m_rcBK) || m_rcBK.isEmpty()) {
            //绘制背景
            m_rcBK = new RectF(0, 0, ScreenWidth, ScreenHeight);
            Paint linePaint = new Paint();
            linePaint.setAntiAlias(true);
            linePaint.setColor(Color.GREEN);
            canvas.drawRoundRect(m_rcBK, 0, 0, linePaint);

            Paint paint = new Paint();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                saveCount = canvas.saveLayer(m_rcBK, paint);
            } else {
                saveCount = canvas.saveLayer(m_rcBK, paint, Canvas.ALL_SAVE_FLAG);
            }

            LogUtils.i(ConfigConstants.TAG_ALL, "saveCount =-= " + saveCount, "getSaveCount =-= " + canvas.getSaveCount());

            Bitmap bitmap = PublicPracticalMethodFromJAVA.getInstance().martixCompress(mContext, R.mipmap.app_logo_shandian)
                    .copy(Bitmap.Config.ARGB_4444, true);
            Paint paint2 = new Paint();
            paint2.setAntiAlias(true);
            if (!StringUtils.isBlank(bitmap)) {
                canvas.translate(100, 100);
                Rect rect_image_src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                RectF rectf = new RectF(0, 0, bitmap.getWidth() + 20, bitmap.getHeight() + 20);
                //画圆角
                paint2.setColor(Color.RED);
                canvas.drawRoundRect(rectf, 20, 20, paint2);
                paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(bitmap, rect_image_src, rect_image_src, paint2);
            }
            //将画布移动恢复
            canvas.translate(-100, -100);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                saveCount = canvas.saveLayer(m_rcBK, paint);
            } else {
                saveCount = canvas.saveLayer(m_rcBK, paint, Canvas.ALL_SAVE_FLAG);
            }
            LogUtils.i(ConfigConstants.TAG_ALL, "saveCount =-= " + saveCount, "getSaveCount =-= " + canvas.getSaveCount());

            Bitmap bitmap2 = PublicPracticalMethodFromJAVA.getInstance().martixCompress(mContext, R.mipmap.app_logo_jingyong)
                    .copy(Bitmap.Config.ARGB_4444, true);
            if (!StringUtils.isBlank(bitmap)) {

                //绘制背景
                linePaint.setColor(Color.parseColor("#40ff4545"));
                canvas.drawRoundRect(m_rcBK, 0, 0, linePaint);

                canvas.translate(200, 200);


                Paint paint3 = new Paint();
                paint3.setAntiAlias(true);
                Rect rect_image_src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                canvas.drawBitmap(bitmap2, rect_image_src, rect_image_src, paint3);
            }
            LogUtils.i(ConfigConstants.TAG_ALL, "getSaveCount =-= " + canvas.getSaveCount());
        } else {
            //绘制背景
//            Paint linePaint = new Paint();
//            linePaint.setAntiAlias(true);
//            linePaint.setColor(Color.RED);
//            mCanvas.drawRoundRect(m_rcBK, 0, 0, linePaint);

            m_rcBK.setEmpty();
            mDrawSaveLayer(canvas);
        }
    }

    /**
     * setXfermode：设置图形混合模式
     * <p>
     * PorterDuff.Mode.CLEAR 清除画布上图像
     * PorterDuff.Mode.SRC 显示上层图像
     * PorterDuff.Mode.DST 显示下层图像
     * PorterDuff.Mode.SRC_OVER上下层图像都显示，上层居上显示
     * PorterDuff.Mode.DST_OVER 上下层都显示,下层居上显示
     * PorterDuff.Mode.SRC_IN 取两层图像交集部门,只显示上层图像
     * PorterDuff.Mode.DST_IN 取两层图像交集部门,只显示下层图像
     * PorterDuff.Mode.SRC_OUT 取上层图像非交集部门
     * PorterDuff.Mode.DST_OUT 取下层图像非交集部门
     * PorterDuff.Mode.SRC_ATOP 取下层图像非交集部门与上层图像交集部门
     * PorterDuff.Mode.DST_ATOP 取上层图像非交集部门与下层图像交集部门
     * PorterDuff.Mode.XOR 取两层图像的非交集部门
     * <p>
     */
    public void mDrawSetXfermode(Canvas canvas) {

        int saveCount = 0;
        int ScreenWidth = ScreenUtils.getScreenWidth();
        int ScreenHeight = ScreenUtils.getScreenHeight();
        //1.绘制背景
        m_rcBK = new RectF(0, 0, ScreenWidth, ScreenHeight);
        Paint linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(ContextCompat.getColor(mContext, R.color.nuts_515a90));
        canvas.drawRoundRect(m_rcBK, 0, 0, linePaint);
        //2.保存到新的图层
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            saveCount = canvas.saveLayer(m_rcBK, linePaint);
        } else {
            saveCount = canvas.saveLayer(m_rcBK, linePaint, Canvas.ALL_SAVE_FLAG);
        }
        //3.dest bitmap
        Bitmap bitmap = PublicPracticalMethodFromJAVA.getInstance().martixCompress(mContext, R.mipmap.app_logo_shandian)
                .copy(Bitmap.Config.ARGB_4444, true);
        Rect rect_image_src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        canvas.drawBitmap(bitmap, rect_image_src, rect_image_src, linePaint);
        //4.设置 Xfermode
        linePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //5.src bitmap
        canvas.translate(50, 50);
        Bitmap bitmap2 = PublicPracticalMethodFromJAVA.getInstance().martixCompress(mContext, R.mipmap.app_logo_jingyong)
                .copy(Bitmap.Config.ARGB_4444, true);
        Rect rect_image_src2 = new Rect(0, 0, bitmap2.getWidth(), bitmap2.getHeight());
        canvas.drawBitmap(bitmap2, rect_image_src2, rect_image_src2, linePaint);

    }

    /***** Path 功能 start *****/

    private Path path = new Path();

    /**
     * moveTo方法
     */
    public void mDrawPath(Canvas canvas) {
        // 使用moveTo（）
        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        //类型1：Paint.Style.FILLANDSTROKE（描边+填充）
        //类型2：Paint.Style.FILL（只填充不描边）
        //类型3：Paint.Style.STROKE（只描边不填充）
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3f);
        mPaint.setAntiAlias(true);

        //清除之前的绘制路径
        path.reset();

        // 起点默认是(0,0)
        //连接点(400,500)
        path.lineTo(400, 500);
        // 将当前点移动到(300, 300)
        path.moveTo(300, 300);
        //连接点(900, 800)
        path.lineTo(900, 800);
        //连接点(200,700)
        path.lineTo(200, 700);
        // 闭合路径，即连接当前点和起点
        // 即连接(200,700)与起点2(300, 300)
        // 注:此时起点已经进行变换
        path.close();
        // 画出路径
        canvas.drawPath(path, mPaint);
    }

    /**
     * setLastPoint方法
     */
    public void mDrawPath2(Canvas canvas) {
        // 使用setLastPoint（）
        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        //类型1：Paint.Style.FILLANDSTROKE（描边+填充）
        //类型2：Paint.Style.FILL（只填充不描边）
        //类型3：Paint.Style.STROKE（只描边不填充）
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3f);
        mPaint.setAntiAlias(true);

        //清除之前的绘制路径
        path.reset();

        // 起点默认是(0,0)
        //连接点(400,500)
        path.lineTo(400, 500);
        // 将当前点移动到(300, 300),会影响之前的操作,但不将此设置为新起点
        path.setLastPoint(300, 300);
        //连接点(900, 800)
        path.lineTo(900, 800);
        //连接点(200,700)
        path.lineTo(200, 700);
        // 闭合路径，即连接当前点和起点
        // 即连接(200,700)与起点2(300, 300)
        // 注:此时起点已经进行变换
        path.close();
        // 画出路径
        canvas.drawPath(path, mPaint);
    }

    /**
     * 添加圆弧
     * 方法1
     * addArc (RectF oval, float startAngle, float sweepAngle)
     * startAngle：确定角度的起始位置
     * sweepAngle ： 确定扫过的角度
     * <p>
     * 方法2
     * arcTo (RectF oval, float startAngle, float sweepAngle)
     * 与上面方法唯一不同的是：如果圆弧的起点和上次最后一个坐标点不相同，就连接两个点
     * <p>
     * 方法3
     * arcTo (RectF oval, float startAngle, float sweepAngle, boolean forceMoveTo)
     * 参数forceMoveTo：是否将之前路径的结束点设置为圆弧起点
     * true：在新的起点画圆弧，不连接最后一个点与圆弧起点，即与之前路径没有交集（同addArc（））
     * false：在新的起点画圆弧，但会连接之前路径的结束点与圆弧起点，即与之前路径有交集（同arcTo（3参数））
     */
    public void mDrawPath3(Canvas canvas) {
        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        //类型1：Paint.Style.FILLANDSTROKE（描边+填充）
        //类型2：Paint.Style.FILL（只填充不描边）
        //类型3：Paint.Style.STROKE（只描边不填充）
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3f);
        mPaint.setAntiAlias(true);

        //清除之前的绘制路径
        path.reset();

        // 将一个圆弧路径添加到一条直线路径里
        // 为了方便观察,平移坐标系
        canvas.translate(350, 500);
        // 先将原点(0,0)连接点(100,100)
        path.lineTo(50, 200);
        // 添加圆弧路径(2分之1圆弧)
        // 不连接最后一个点与圆弧起点
        RectF rectF = new RectF(200, 200, 300, 300);

//        path.addArc(rectF, 0, 180);
//        path.arcTo(rectF, 0, 180, true);

        //连接之前路径的结束点与圆弧起点
//        path.arcTo(rectF, 0, 180);
        path.arcTo(rectF, 0, 180, false);


        // 画出路径
        canvas.drawPath(path, mPaint);
    }

    /**
     * 加入圆形路径
     * 起点：x轴正方向的0度
     * 其中参数dir：指定绘制时是顺时针还是逆时针:CW为顺时针，  CCW为逆时针
     * 路径起点变为圆在X轴正方向最大的点
     * addCircle(float x, float y, float radius, Path.Direction dir)
     * <p>
     * 图形绘制的本质：先画点，再将点连接起来。所以，点与点之间是存在一个先后顺序的；顺时针和逆时针用户确定这些点的顺序
     * 图形的方向影响的是：添加图形时确定闭合顺序(各个点的记录顺序)；图形的渲染结果(是判断图形渲染的重要条件)
     */
    public void mDrawPath4(Canvas canvas) {
        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        //类型1：Paint.Style.FILLANDSTROKE（描边+填充）
        //类型2：Paint.Style.FILL（只填充不描边）
        //类型3：Paint.Style.STROKE（只描边不填充）
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3f);
        mPaint.setAntiAlias(true);

        //清除之前的绘制路径
        path.reset();
        // 为了方便观察,平移坐标系
        canvas.translate(350, 500);
        // 先将原点(0,0)连接点(100,100)
        path.lineTo(50, 200);

        //添加圆
        path.addCircle(50, 200, 50, Path.Direction.CW);

        // 画出路径
        canvas.drawPath(path, mPaint);
    }

    /**
     * 加入椭圆形路径
     * 其中，参数oval作为椭圆的外切矩形区域
     * addOval(RectF oval, Path.Direction dir)
     */
    public void mDrawPath5(Canvas canvas) {
        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        //类型1：Paint.Style.FILLANDSTROKE（描边+填充）
        //类型2：Paint.Style.FILL（只填充不描边）
        //类型3：Paint.Style.STROKE（只描边不填充）
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3f);
        mPaint.setAntiAlias(true);

        //清除之前的绘制路径
        path.reset();
        // 为了方便观察,平移坐标系
        canvas.translate(350, 500);
        // 先将原点(0,0)连接点(100,100)
        path.lineTo(50, 200);

        //添加椭圆
        RectF rectF = new RectF(200, 100, 300, 300);
        path.addOval(rectF, Path.Direction.CW);

        // 画出路径
        canvas.drawPath(path, mPaint);
    }


    /**
     * 添加矩形
     * 路径起点变为矩形的左上角顶点
     * addRect(RectF rect, Path.Direction dir)
     */
    public void mDrawPath6(Canvas canvas) {
        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        //类型1：Paint.Style.FILLANDSTROKE（描边+填充）
        //类型2：Paint.Style.FILL（只填充不描边）
        //类型3：Paint.Style.STROKE（只描边不填充）
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3f);
        mPaint.setAntiAlias(true);

        //清除之前的绘制路径
        path.reset();
        // 为了方便观察,平移坐标系
        canvas.translate(350, 500);
        // 先将原点(0,0)连接点(100,100)
        path.lineTo(50, 200);

        //添加矩形
        RectF rectF = new RectF(200, 200, 300, 300);
        path.addRect(rectF, Path.Direction.CW);

        // 画出路径
        canvas.drawPath(path, mPaint);
    }

    /**
     * 添加圆角矩形路径
     * 路径起点变为矩形的左上角顶点
     * addRoundRect(RectF rect, float rx, float ry, Path.Direction dir)
     */
    public void mDrawPath7(Canvas canvas) {
        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        //类型1：Paint.Style.FILLANDSTROKE（描边+填充）
        //类型2：Paint.Style.FILL（只填充不描边）
        //类型3：Paint.Style.STROKE（只描边不填充）
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3f);
        mPaint.setAntiAlias(true);

        //清除之前的绘制路径
        path.reset();
        // 为了方便观察,平移坐标系
        canvas.translate(350, 500);
        // 先将原点(0,0)连接点(100,100)
        path.lineTo(50, 200);

        //添加矩形
        RectF rectF = new RectF(200, 200, 300, 300);
        path.addRoundRect(rectF, 10, 10, Path.Direction.CW);

        // 画出路径
        canvas.drawPath(path, mPaint);
    }


    /**
     * 关于加入图形路径后会影响路径的起点
     */
    public void mDrawPath8(Canvas canvas) {

        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        //类型1：Paint.Style.FILLANDSTROKE（描边+填充）
        //类型2：Paint.Style.FILL（只填充不描边）
        //类型3：Paint.Style.STROKE（只描边不填充）
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3f);
        mPaint.setAntiAlias(true);

        //清除之前的绘制路径
        path.reset();

        //轨迹1
/*        // 为了方便观察,平移坐标系
        canvas.translate(400, 500);
        // 起点是(0,0)，连接点(-100,0)
        path.lineTo(-100, 0);
        // 连接点(-100,200)
        path.lineTo(-100, 200);
        // 连接点(200,200)
        path.lineTo(200, 200);
        // 闭合路径，即连接当前点和起点
        // 即连接(200,200)与起点是(0,0)
        path.close();*/

        //轨迹2
        // 将Canvas坐标系移到屏幕正中
        canvas.translate(400, 500);
        // 起点是(0,0)，连接点(-100,0)
        path.lineTo(-100, 0);
        // 画圆：圆心=(0,0)，半径=100px
        // 此时路径起点改变 = (0,100)（记为起点2）
        // 起点改变原则：新画图形在x轴正方向的最后一个坐标
        // 后面路径的变化以这个点继续下去
        path.addCircle(0, 0, 100, Path.Direction.CCW);
        // 起点为：(0,100)，连接 (-100,200)
        path.lineTo(-100, 200);
        // 连接 (200,200)
        path.lineTo(200, 200);
        // 闭合路径，即连接当前点和起点（注：闭合的是起点2）
        // 即连接(200,200)与起点2(0,100)
        path.close();


        // 画出路径
        canvas.drawPath(path, mPaint);
    }

    /**
     * 添加路径
     * 将路径1加到路径2里
     * <p>
     * 方法1
     * addPath (Path src)
     * <p>
     * 方法2
     * 先将src进行（x,y）位移之后再添加到当前path
     * addPath (Path src, float dx, float dy)
     * <p>
     * 方法3
     * 先将src进行Matrix变换再添加到当前path
     * addPath (Path src, Matrix matrix)
     */
    //矩形路径对象
    Path pathRect = new Path();
    //圆形路径对象
    Path pathCircle = new Path();

    public void mDrawPath9(Canvas canvas) {

        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        //类型1：Paint.Style.FILLANDSTROKE（描边+填充）
        //类型2：Paint.Style.FILL（只填充不描边）
        //类型3：Paint.Style.STROKE（只描边不填充）
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3f);
        mPaint.setAntiAlias(true);

        //清除之前的绘制路径
        pathRect.reset();

        // 实例：合并矩形路径和圆形路径
        // 为了方便观察,平移坐标系
        canvas.translate(350, 500);
        // 画一个矩形路径
        pathRect.addRect(-200, -200, 200, 200, Path.Direction.CW);
        //画一个圆形路径
        pathCircle.addCircle(0, 0, 100, Path.Direction.CW);

        // 将圆形路径移动(0,200),再添加到矩形路径里
        pathRect.addPath(pathCircle, 0, 200);

        // 画出路径
        canvas.drawPath(pathRect, mPaint);
    }


    /**
     * 判断路径属性
     * 1.isEmpty ():判断path中是否包含内容
     * 2.isRect (RectF rect):判断path是否是一个矩形,如果是一个矩形的话，会将矩形的信息存放进参数rect中。
     * 3.set (Path src):将新的路径替代现有路径
     * <p>
     * 3.平移路径:与Canvas.translate （）平移画布类似
     * 方法1：
     * 参数x,y：平移位置
     * offset (float dx, float dy)
     * 方法2：
     * 参数dst：存储平移后的路径状态，但不影响当前path，可通过dst参数绘制存储的路径
     * offset (float dx, float dy, Path dst)
     */
    public void mDrawPath10(Canvas canvas) {

        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        //类型1：Paint.Style.FILLANDSTROKE（描边+填充）
        //类型2：Paint.Style.FILL（只填充不描边）
        //类型3：Paint.Style.STROKE（只描边不填充）
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3f);
        mPaint.setAntiAlias(true);

        //清除之前的绘制路径
        path.reset();

        //判断path路径是否为空
//        boolean pathisEmpty = path.isEmpty();
//        LogUtils.i(ConfigConstants.TAG_ALL, "pathisEmpty1 =-=" + pathisEmpty);
//        path.lineTo(100, 100);
//        boolean pathisEmpty2 = path.isEmpty();
//        LogUtils.i(ConfigConstants.TAG_ALL, "pathisEmpty2 =-=" + pathisEmpty2);

        //判断path路径是否是一个矩形
//        path.lineTo(0, 400);
//        path.lineTo(400, 400);
//        path.lineTo(400, 0);
//        path.lineTo(0, 0);
//        RectF rect = new RectF();
//        boolean pathisRectF = path.isRect(rect);
//        LogUtils.i(ConfigConstants.TAG_ALL, "pathisRectF =-=" + pathisRectF);


        //将新的路径替代现有路径
//        canvas.translate(300, 500);
//        Path path = new Path();
//        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
//        // 设置一圆形路径
//        Path src = new Path();
//        src.addCircle(0, 0, 100, Path.Direction.CW);
//        // 将圆形路径代替矩形路径
//        path.set(src);

        //平移路径
        canvas.translate(350, 500);
        // path中添加一个圆形(圆心在坐标原点)
        path = new Path();
        path.addCircle(0, 0, 100, Path.Direction.CW);
        // 平移路径并存储平移后的状态
        Path dst = new Path();
        path.offset(400, 0, dst);
        // 画出路径
        canvas.drawPath(path, mPaint);
        // 通过dst绘制平移后的图形(蓝色)
        mPaint.setColor(Color.BLUE);
        canvas.drawPath(dst, mPaint);

    }

    /**
     * 设置路径填充颜色
     * 1.path.setFillType():设置填充规则
     * 2.path.getFillType():获取当前填充规则
     * 3.path.isInverseFillType():判断是否是反向(INVERSE)规则
     * 4.path.toggleInverseFillType():切换填充规则(即原有规则与反向规则之间相互切换)
     */
    public void mDrawPath11(Canvas canvas) {

        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        //类型1：Paint.Style.FILLANDSTROKE（描边+填充）
        //类型2：Paint.Style.FILL（只填充不描边）
        //类型3：Paint.Style.STROKE（只描边不填充）
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3f);
        mPaint.setAntiAlias(true);

        //清除之前的绘制路径
        path.reset();

        // 为了方便观察,平移坐标系
        canvas.translate(350, 500);
        // 在Path中添加一个矩形
        path.addCircle(300, 300, 150, Path.Direction.CW);
        path.addCircle(380, 380, 150, Path.Direction.CW);
//        path.setFillType(Path.FillType.EVEN_ODD);
//        path.setFillType(Path.FillType.INVERSE_EVEN_ODD);
//        path.setFillType(Path.FillType.WINDING);
        path.setFillType(Path.FillType.INVERSE_WINDING);
        // 画出路径
        canvas.drawPath(path, mPaint);
    }

    /**
     * 布尔操作
     * 1.op (Path path, Path.Op op)
     * 对 path1 和 path2 执行布尔运算，运算方式由第二个参数指定,运算结果存入到path1中
     * <p>
     * 2.op (Path path1, Path path2, Path.Op op)
     * 对 path1 和 path2 执行布尔运算，运算方式由第三个参数指定，运算结果存入到path3中
     */
    public void mDrawPath12(Canvas canvas) {

        Paint mPaint = new Paint();
        mPaint.setColor(Color.RED);
        //类型1：Paint.Style.FILLANDSTROKE（描边+填充）
        //类型2：Paint.Style.FILL（只填充不描边）
        //类型3：Paint.Style.STROKE（只描边不填充）
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3f);
        mPaint.setAntiAlias(true);

        //清除之前的绘制路径
        path.reset();

        // 为了方便观察,平移坐标系
        canvas.translate(550, 550);


        Path path1 = new Path();
        Path path2 = new Path();

        // 画两个圆
        // 圆1:圆心 = (0,0),半径 = 100
        // 圆2:圆心 = (50,0),半径 = 100
        path1.addCircle(0, 0, 100, Path.Direction.CW);
        path2.addCircle(50, 0, 100, Path.Direction.CW);
//        path1.op(path2, Path.Op.DIFFERENCE);
//        path1.op(path2, Path.Op.REVERSE_DIFFERENCE);
//        path1.op(path2, Path.Op.INTERSECT);
//        path1.op(path2, Path.Op.UNION);
        path1.op(path2, Path.Op.XOR);
        canvas.drawPath(path1, mPaint);
    }


    /***** Path 功能 end ***** /

     /**
     * 清理屏幕
     */
    public void mDrawClearScreen() {
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mCanvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    }

    /**
     * 刷新View
     */
    public void refreshInvalidate() {
        invalidate();
    }


    public Canvas getmCanvas() {
        return mCanvas;
    }

    public int getMethodNums() {
        return methodNums;
    }

    public void setMethodNums(int methodNums) {
        this.methodNums = methodNums;
    }


}
