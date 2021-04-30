package com.example.baselibrary.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.widget.ImageView;

import com.example.baselibrary.R;

import java.lang.ref.SoftReference;

/**
 * Date:2021/4/30
 * Time:16:05
 * author:joker
 * 帧动画 优化工具类
 */
public class AnimationsContainer {

    private static volatile AnimationsContainer mInstance;

    /**
     * 单例模式
     */
    public static AnimationsContainer getInstance() {
        if (mInstance == null) {
            synchronized (AnimationsContainer.class) {
                if (mInstance == null) {
                    mInstance = new AnimationsContainer();
                }
            }
        }
        return mInstance;
    }

    private Context mContext;
    // 每秒播放帧数，fps = 1/t，t-动画两帧时间间隔
    private int FPS = 58;
    //动画图片默认资源
    private int resId = R.array.loading_anim;

    /**
     * 设置资源的值
     */
    public void setResourceValue(Context mContext, int resId, int fps) {
        this.mContext = mContext;
        this.resId = resId;
        this.FPS = fps;
    }


    /**
     * @param imageView
     * @return progress dialog animation
     */
    public FramesSequenceAnimation createProgressDialogAnim(ImageView imageView) {
        return new FramesSequenceAnimation(imageView, getData(resId), FPS);
    }


    /**
     * 循环读取帧---循环播放帧
     */
    public class FramesSequenceAnimation {
        private int[] mFrames; // 帧数组
        private int mIndex; // 当前帧
        private boolean mShouldRun; // 开始/停止播放用
        private boolean mIsRunning; // 动画是否正在播放，防止重复播放
        private SoftReference<ImageView> mSoftReferenceImageView; // 软引用ImageView，以便及时释放掉
        private Handler mHandler;
        private int mDelayMillis;
        private OnAnimationListener onAnimationListener;


        private Bitmap mBitmap = null;
        private BitmapFactory.Options mBitmapOptions;//Bitmap管理类，可有效减少Bitmap的OOM问题

        public FramesSequenceAnimation(ImageView imageView, int[] frames, int fps) {
            mHandler = new Handler();
            mFrames = frames;
            mIndex = -1;
            mSoftReferenceImageView = new SoftReference<ImageView>(imageView);
            mShouldRun = false;
            mIsRunning = false;
            mDelayMillis = 1000 / fps;//帧动画时间间隔，毫秒

            imageView.setImageResource(mFrames[0]);
            // 当图片大小类型相同时进行复用，避免频繁GC
            if (Build.VERSION.SDK_INT >= 11) {
                Bitmap bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                int width = bmp.getWidth();
                int height = bmp.getHeight();
                Bitmap.Config config = bmp.getConfig();
                mBitmap = Bitmap.createBitmap(width, height, config);
                mBitmapOptions = new BitmapFactory.Options();
                //设置Bitmap内存复用
                mBitmapOptions.inBitmap = mBitmap;//Bitmap复用内存块，类似对象池，避免不必要的内存分配和回收
                mBitmapOptions.inMutable = true;//解码时返回可变Bitmap
                mBitmapOptions.inSampleSize = 1;//缩放比例
            }
        }


        //循环读取下一帧
        private int getNext() {
            mIndex++;
            if (mIndex >= mFrames.length)
                mIndex = 0;
            return mFrames[mIndex];
        }

        /**
         * 播放动画，同步锁防止多线程读帧时，数据安全问题
         */
        public synchronized void start() {
            mShouldRun = true;
            if (mIsRunning) {
                return;
            }

            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    ImageView imageView = mSoftReferenceImageView.get();
                    if (!mShouldRun || imageView == null) {
                        mIsRunning = false;
                        if (onAnimationListener != null) {
                            onAnimationListener.AnimationStopped();
                        }
                        return;
                    }
                    mIsRunning = true;
                    if (onAnimationListener != null) {
                        onAnimationListener.AnimationStarted();
                    }
                    //新开线程去读下一帧
                    mHandler.postDelayed(this, mDelayMillis);
                    if (imageView.isShown()) {
                        int imageRes = getNext();
                        if (mBitmap != null) {
                            Bitmap bitmap = null;
                            try {
                                bitmap = BitmapFactory.decodeResource(imageView.getResources(), imageRes, mBitmapOptions);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (bitmap != null) {
                                imageView.setImageBitmap(bitmap);
                            } else {
                                imageView.setImageResource(imageRes);
                                mBitmap.recycle();
                                mBitmap = null;
                            }
                        } else {
                            imageView.setImageResource(imageRes);
                        }
                    }
                }
            };
            mHandler.post(runnable);
        }


        /**
         * 停止播放
         */
        public synchronized void stop() {
            mShouldRun = false;
        }

        public boolean ismIsRunning() {
            return mIsRunning;
        }


        /**
         * 设置停止播放监听
         */
        public void setOnAnimListener(OnAnimationListener onAnimationListener) {
            this.onAnimationListener = onAnimationListener;
        }

    }

    /**
     * 从xml中读取帧数组
     *
     * @param resId
     * @return
     */
    private int[] getData(int resId) {
        TypedArray array = mContext.getResources().obtainTypedArray(resId);

        int len = array.length();
        int[] intArray = new int[array.length()];

        for (int i = 0; i < len; i++) {
            intArray[i] = array.getResourceId(i, 0);
        }
        array.recycle();
        return intArray;
    }


    /**
     * 动画监听
     */
    public interface OnAnimationListener {
        //动画停止
        void AnimationStopped();

        //动画开始
        void AnimationStarted();
    }


}
