package com.example.baselibrary.utils.glideutils;

import android.content.Context;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.example.baselibrary.constants.ConfigConstants;


/**
 * 改写Glide的存储路径
 */
@GlideModule
public class MyGlideMoudle extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new DiskLruCacheFactory(ConfigConstants.INSTANCE.getGILDE_DISKCACHEDIR(), ConfigConstants.GILDE_DISKCACHESIZEBYTES));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
