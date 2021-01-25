package com.example.testlibrary;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.baselibrary.base.BaseActivity;
import com.example.baselibrary.constants.ConfigConstants;
import com.example.baselibrary.constants.RouterTag;
import com.example.baselibrary.di.componets.MyAppComponet;
import com.example.baselibrary.utils.LogUtils;
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA;
import com.example.baselibrary.utils.SDCardUtils;
import com.example.baselibrary.utils.ScreenUtils;
import com.example.baselibrary.utils.screenrecord.RecordService;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

@Route(path = RouterTag.TestScreenRecordActivity)
public class TestScreenRecordActivity extends BaseActivity {

    public MediaProjectionManager projectionManager;
    public MediaProjection mediaProjection;
    private RecordService recordService;
    private boolean bingflag = false;
    private final int RECORD_REQUEST_CODE = 999;
    private final int STORAGE_REQUEST_CODE = 998;
    private final int AUDIO_REQUEST_CODE = 997;


    private TextView tv_screenrecord;
    private TextView tv_screenrecord2;
    private TextView tv_screenrecord3;
    private Chronometer tv_screenrecord4;

    @Override
    protected void setupComponent(@NotNull MyAppComponet myAppComponet) {

    }

    @Override
    protected void onBeforeSetContentLayout() {
        PublicPracticalMethodFromJAVA.getInstance()
                .transparentStatusBar(
                        this,
                        false, true,
                        R.color.full_red
                );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_screenrecord_test;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecordService();

        tv_screenrecord = findViewById(R.id.tv_screenrecord);
        tv_screenrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecord();
            }
        });

        tv_screenrecord2 = findViewById(R.id.tv_screenrecord2);
        tv_screenrecord2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecord();
            }
        });

        tv_screenrecord3 = findViewById(R.id.tv_screenrecord3);
        tv_screenrecord3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recursionDeleteFile(SDCardUtils.getExternalStorageDirectory().getAbsolutePath() + File.separator + ConfigConstants.screenrecord_dir);
            }
        });

        tv_screenrecord4 = findViewById(R.id.tv_screenrecord4);
        tv_screenrecord4.setBase(SystemClock.elapsedRealtime());
    }

    public void initRecordService() {
        if (PublicPracticalMethodFromJAVA.getInstance().isHigherSpecifySDKVersion(Build.VERSION_CODES.LOLLIPOP)) {
            projectionManager = (MediaProjectionManager) this.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
            bindService();
        }

    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bingflag = true;
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            RecordService.RecordBinder binder = (RecordService.RecordBinder) service;
            recordService = binder.getRecordService();
            recordService.setConfig(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight(), metrics.densityDpi);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bingflag = false;
        }
    };


    private void bindService() {
        Intent intent = new Intent(this, RecordService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }


    private void startRecord() {
        if (PublicPracticalMethodFromJAVA.getInstance().isHigherSpecifySDKVersion(Build.VERSION_CODES.LOLLIPOP)) {
            if (recordService.isRunning()) {
                LogUtils.i(ConfigConstants.TAG_ALL, "=-= 录屏已经开始，不能重复点击");
                return;
            }
            LogUtils.i(ConfigConstants.TAG_ALL, "=-= 开始录屏");
            Intent captureIntent = projectionManager.createScreenCaptureIntent();
            startActivityForResult(captureIntent, RECORD_REQUEST_CODE);

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);
            }

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO}, AUDIO_REQUEST_CODE);
            }
        }
    }

    private void stopRecord() {
        if (PublicPracticalMethodFromJAVA.getInstance().isHigherSpecifySDKVersion(Build.VERSION_CODES.LOLLIPOP)) {
            if (mediaProjection != null) {
                if (recordService.isRunning()) {
                    if (recordService.stopRecord()) {
                        LogUtils.i(ConfigConstants.TAG_ALL, "=-= 录屏成功结束");
                        tv_screenrecord4.stop();
                    } else {
                        LogUtils.i(ConfigConstants.TAG_ALL, "=-= 录屏失败");
                    }

                } else {
                    LogUtils.i(ConfigConstants.TAG_ALL, "=-= 录屏还没有开始不能结束");
                }
            }
        }
    }

    private void recursionDeleteFile(String path) {
        if (recordService.recursionDeleteFile(path)) {
            LogUtils.i(ConfigConstants.TAG_ALL, "=-= 文件成功删除");
        } else {
            LogUtils.i(ConfigConstants.TAG_ALL, "=-= 文件删除失败");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            LogUtils.i(ConfigConstants.TAG_ALL, "----activity-->" + requestCode + "---result--->" + resultCode);
            if (requestCode == RECORD_REQUEST_CODE && resultCode == RESULT_OK) {
                if (PublicPracticalMethodFromJAVA.getInstance().isHigherSpecifySDKVersion(Build.VERSION_CODES.LOLLIPOP)) {
                    mediaProjection = projectionManager.getMediaProjection(resultCode, data);
                    recordService.setMediaProject(mediaProjection);
                    if (recordService.startRecord("")) {
                        tv_screenrecord4.start();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bingflag) {
            unbindService(connection);
        }
    }


}
