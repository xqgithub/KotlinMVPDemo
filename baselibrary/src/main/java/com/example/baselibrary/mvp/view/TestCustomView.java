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
        super.onDraw(canvas);
//        mDrawColor(canvas);
//        mDrawPoints(canvas);
//        mDrawLine(canvas);
//        mDrawRect(canvas);
//        mDrawRoundRect(canvas);
//        mDrawOval(canvas);
//        mDrawCircle(canvas);
//        mDrawArc(canvas);
//        mDrawText(canvas);
//        mDrawText2(canvas);
//        mDrawText3(canvas);
//        mDrawPicture(canvas);
//        mDrawBitmap(canvas);
//        mDrawTranslate(canvas);
//        mDrawScale(canvas);
//        mDrawSkew(canvas);
//        mDrawClip(canvas);
        mDrawSaveLayer(canvas);
        this.mCanvas = canvas;
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

    private void initPaint() {
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

    private void initPaint2() {
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

    private void initPaint3(Context context) {
        mPaint3 = new Paint();
        mPaint3.setColor(ContextCompat.getColor(context, R.color.update_normal));
        mPaint3.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint3.setStrokeWidth(1f);
        mPaint3.setAntiAlias(true);
        mPaint3.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.dimen_20x));
    }

    /**
     * 绘制颜色
     */

    private void mDrawColor(Canvas canvas) {
        // 传入一个Color类的常量参数来设置画布颜色
        // 绘制蓝色
        canvas.drawColor(Color.BLUE);
    }

    /**
     * 绘制点
     */

    private void mDrawPoints(Canvas canvas) {
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

    private void mDrawLine(Canvas canvas) {
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

    private void mDrawRect(Canvas canvas) {
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

    private void mDrawRoundRect(Canvas canvas) {
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

    private void mDrawOval(Canvas canvas) {
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

    private void mDrawCircle(Canvas canvas) {
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

    private void mDrawArc(Canvas canvas) {
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

    private void mDrawText(Canvas canvas) {
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

    private void mDrawText2(Canvas canvas) {
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

    private void mDrawText3(Canvas canvas) {

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

    private void mDrawPicture(Canvas canvas) {
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
    private void mDrawBitmap(Canvas canvas) {
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

    private void mDrawTranslate(Canvas canvas) {
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

    private void mDrawScale(Canvas canvas) {
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

    private void mDrawSkew(Canvas canvas) {
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

    private void mDrawClip(Canvas canvas) {
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
     * saveLayer 使用
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
     * <p>saveLayer的saveFlags
     * MATRIX_SAVE_FLAG:只保存图层的matrix矩阵
     * CLIP_SAVE_FLAG:只保存大小信息
     * HAS_ALPHA_LAYER_SAVE_FLAG：表明该图层有透明度，和下面的标识冲突，都设置时以下面的标志为准（只适用于saveLayer）
     * FULL_COLOR_LAYER_SAVE_FLAG：完全保留该图层颜色（和上一图层合并时，清空上一图层的重叠区域，保留该图层的颜色）（只适用于saveLayer）
     * CLIP_TO_LAYER_SAVE_：创建图层时，会把canvas（所有图层）裁剪到参数指定的范围，如果省略这个flag将导致图层开销巨大（实际上图层没有裁剪，与原图层一样大）
     * ALL_SAVE_FLAG。//保存全部
     */
    private RectF m_rcBK;

    private void mDrawSaveLayer(Canvas canvas) {
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
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            saveCount = canvas.saveLayer(m_rcBK, paint);
//        } else {
//            saveCount = canvas.saveLayer(m_rcBK, paint, Canvas.ALL_SAVE_FLAG);
//        }

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

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            saveCount = canvas.saveLayer(m_rcBK, paint);
//        } else {
//            saveCount = canvas.saveLayer(m_rcBK, paint, Canvas.ALL_SAVE_FLAG);
//        }

            LogUtils.i(ConfigConstants.TAG_ALL, "saveCount =-= " + saveCount, "getSaveCount =-= " + canvas.getSaveCount());

            Bitmap bitmap2 = PublicPracticalMethodFromJAVA.getInstance().martixCompress(mContext, R.mipmap.app_logo_jingyong)
                    .copy(Bitmap.Config.ARGB_4444, true);
            Paint paint3 = new Paint();
            paint3.setAntiAlias(true);
            if (!StringUtils.isBlank(bitmap)) {
                canvas.translate(20, 20);
                Rect rect_image_src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(bitmap2, rect_image_src, rect_image_src, paint3);
            }
            LogUtils.i(ConfigConstants.TAG_ALL, "getSaveCount =-= " + canvas.getSaveCount());
        } else {
            //绘制背景
            Paint linePaint = new Paint();
            linePaint.setAntiAlias(true);
            linePaint.setColor(Color.RED);
            mCanvas.drawRoundRect(m_rcBK, 0, 0, linePaint);
        }
    }


    /**
     * 替换背景图
     */
    public void mDrawSave() {
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mCanvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        invalidate();
    }

}
