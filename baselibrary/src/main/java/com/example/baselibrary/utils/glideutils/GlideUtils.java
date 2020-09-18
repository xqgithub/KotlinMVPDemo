package com.example.baselibrary.utils.glideutils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.disklrucache.DiskLruCache;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.EmptySignature;
import com.example.baselibrary.constants.ConfigConstants;
import com.example.baselibrary.utils.LogUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by beijixiong on 2019/3/1.
 * Glide 工具类
 */

public class GlideUtils {

    private static volatile GlideUtils mInstance;

    public static GlideUtils getInstance() {
        if (mInstance == null) {
            synchronized (GlideUtils.class) {
                if (mInstance == null) {
                    mInstance = new GlideUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 00001
     * 加载网络资源绑定Context
     */
    public void loadUrlToImagaViewContext(Context context,
                                          String url,
                                          ImageView imageview,
                                          int imagewidth,
                                          int imageheight,
                                          @DrawableRes int placeholder,
                                          @DrawableRes int errorpic) {
        Glide.with(context).load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        LogUtils.i("loadUrlToImagaViewFragmentActivity =-= 图片加载失败");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        LogUtils.i("loadUrlToImagaViewFragmentActivity =-= 图片加载成功");
                        return false;
                    }
                })
                .override(imagewidth, imageheight)
                .placeholder(placeholder)
                .error(errorpic)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)//DiskCacheStrategy.ALL  缓存源资源和转换后的资源;DiskCacheStrategy.NONE  不作任何磁盘缓存;DiskCacheStrategy.RESOURCE  缓存源资源;DiskCacheStrategy.RESULT  缓存转换后的资源
                .into(imageview);
    }

    /**
     * 00001
     * 加载网络资源绑定Activiy
     */
    public void loadUrlToImagaViewActivity(Activity activity,
                                           String url,
                                           ImageView imageview,
                                           int imagewidth,
                                           int imageheight,
                                           @DrawableRes int placeholder,
                                           @DrawableRes int errorpic) {
        Glide.with(activity).load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        LogUtils.i("loadUrlToImagaViewActivity =-= 图片加载失败");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        LogUtils.i("loadUrlToImagaViewActivity =-= 图片加载成功");
                        return false;
                    }
                })
                .override(imagewidth, imageheight)
                .placeholder(placeholder)
                .error(errorpic)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)//DiskCacheStrategy.ALL  缓存源资源和转换后的资源;DiskCacheStrategy.NONE  不作任何磁盘缓存;DiskCacheStrategy.RESOURCE  缓存源资源;DiskCacheStrategy.RESULT  缓存转换后的资源
                .into(imageview);
    }

    /**
     * 00001
     * 加载网络资源绑定Fragment
     */
    public void loadUrlToImagaViewFragment(Fragment fragment,
                                           String url,
                                           ImageView imageview,
                                           int imagewidth,
                                           int imageheight,
                                           @DrawableRes int placeholder,
                                           @DrawableRes int errorpic) {
        Glide.with(fragment).load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        LogUtils.i("loadUrlToImagaViewFragment =-= 图片加载失败");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        LogUtils.i("loadUrlToImagaViewFragment =-= 图片加载成功");
                        return false;
                    }
                })
                .override(imagewidth, imageheight)
                .placeholder(placeholder)
                .error(errorpic)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)//DiskCacheStrategy.ALL  缓存源资源和转换后的资源;DiskCacheStrategy.NONE  不作任何磁盘缓存;DiskCacheStrategy.RESOURCE  缓存源资源;DiskCacheStrategy.RESULT  缓存转换后的资源
                .into(imageview);
    }

    /**
     * 00001
     * 加载网络资源绑定Fragment
     */
    public void loadUrlToImagaViewFragmentActivity(FragmentActivity fragmentActivity,
                                                   String url,
                                                   ImageView imageview,
                                                   int imagewidth,
                                                   int imageheight,
                                                   @DrawableRes int placeholder,
                                                   @DrawableRes int errorpic) {
        Glide.with(fragmentActivity).load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        LogUtils.i("loadUrlToImagaViewFragmentActivity =-= 图片加载失败");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        LogUtils.i("loadUrlToImagaViewFragmentActivity =-= 图片加载成功");
                        return false;
                    }
                })
                .override(imagewidth, imageheight)
                .placeholder(placeholder)
                .error(errorpic)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)//DiskCacheStrategy.ALL  缓存源资源和转换后的资源;DiskCacheStrategy.NONE  不作任何磁盘缓存;DiskCacheStrategy.RESOURCE  缓存源资源;DiskCacheStrategy.RESULT  缓存转换后的资源
                .into(imageview);
    }


    /**
     * 00002
     * 加载Resources资源
     */
    public void loadResourcesToImagaView(Context context,
                                         @DrawableRes int resources,
                                         ImageView imageview,
                                         int imagewidth,
                                         int imageheight) {
        Glide.with(context).load(resources)
                .override(imagewidth, imageheight)
                .into(imageview);
    }


    /**
     * 00003
     * 根据url 得到图片缓存地址
     */
    public File getCacheFile(String picurl) {
        OriginalKey originalKey = new OriginalKey(picurl, EmptySignature.obtain());
        SafeKeyGenerator safeKeyGenerator = new SafeKeyGenerator();
        String safeKey = safeKeyGenerator.getSafeKey(originalKey);
        try {
//            DiskLruCache diskLruCache = DiskLruCache.open(new File(getCacheDir(), DiskCache.Factory.DEFAULT_DISK_CACHE_DIR), 1, 1, DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE);
            DiskLruCache diskLruCache = DiskLruCache.open(new File(ConfigConstants.INSTANCE.getGILDE_DISKCACHEDIR()), 1, 1, ConfigConstants.GILDE_DISKCACHESIZEBYTES);
            DiskLruCache.Value value = diskLruCache.get(safeKey);
            if (value != null) {
                return value.getFile(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 00004
     * 清除Glide的缓存
     *
     * @return
     */
    public boolean clearCacheDiskSelf(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 00005
     * 更新图片
     */
    public <T> void updateImage(Context context, String url,
                                ImageView imageview,
                                int imagewidth,
                                int imageheight,
                                @DrawableRes int placeholder,
                                @DrawableRes int errorpic,
                                final int id,
                                final long update_at) {
        Glide.with(context).load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        LogUtils.i("updateImage =-= 图片加载失败");
                        try {
                        } catch (Exception e1) {
                            LogUtils.i(e1.getMessage());
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        LogUtils.i("updateImage =-= 图片加载成功");
                        try {
                        } catch (Exception e1) {
                            LogUtils.e(e1.getMessage());
                        }
                        return false;
                    }
                })
                .override(imagewidth, imageheight)
                .placeholder(placeholder)
                .error(errorpic)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)//DiskCacheStrategy.ALL  缓存源资源和转换后的资源;DiskCacheStrategy.NONE  不作任何磁盘缓存;DiskCacheStrategy.RESOURCE  缓存源资源;DiskCacheStrategy.RESULT  缓存转换后的资源
                .into(imageview);
    }
}
