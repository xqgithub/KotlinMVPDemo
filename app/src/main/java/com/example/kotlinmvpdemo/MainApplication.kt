package com.example.kotlinmvpdemo

import android.content.Context
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import com.example.baselibrary.application.MyApplication
import com.example.kotlinmvpdemo.utils.PictureSelectorEngineImp
import com.luck.picture.lib.app.IApp
import com.luck.picture.lib.app.PictureAppMaster
import com.luck.picture.lib.engine.PictureSelectorEngine

/**
 * Date:2021/8/5
 * Time:15:07
 * author:joker
 */
class MainApplication : MyApplication(), IApp, CameraXConfig.Provider {


    override fun onCreate() {
        super.onCreate()
        PictureAppMaster.getInstance().app = this
    }

    override fun getAppContext(): Context {
        return this
    }

    override fun getPictureSelectorEngine(): PictureSelectorEngine {
        return PictureSelectorEngineImp()
    }

    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }


}