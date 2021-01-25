package com.example.baselibrary.utils.screenrecord;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.os.Binder;
import android.os.Build;
import android.os.HandlerThread;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.baselibrary.constants.ConfigConstants;
import com.example.baselibrary.utils.FileUtils;
import com.example.baselibrary.utils.PublicPracticalMethodFromJAVA;
import com.example.baselibrary.utils.SDCardUtils;
import com.example.baselibrary.utils.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * 录屏服务
 */
public class RecordService extends Service {

    private MediaProjection mediaProjection;
    private MediaRecorder mediaRecorder;
    private VirtualDisplay virtualDisplay;
    private boolean running;
    private int width = 320;
    private int height = 420;
    private int dpi;
    private Context context;

    public RecordService() {

    }


    public class RecordBinder extends Binder {
        public RecordService getRecordService() {
            return RecordService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread serviceThread = new HandlerThread("service_thread",
                android.os.Process.THREAD_PRIORITY_BACKGROUND);
        serviceThread.start();
        running = false;
        mediaRecorder = new MediaRecorder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setMediaProject(MediaProjection project) {
        mediaProjection = project;
    }

    public boolean isRunning() {
        return running;
    }

    public void setConfig(int width, int height, int dpi) {
        this.width = width;
        this.height = height;
        this.dpi = dpi;
    }

    public boolean startRecord(String temp) {
        if (mediaRecorder != null) {
            if (mediaProjection == null || running) {
                return false;
            }
            try {
                initRecorder(temp);
                createVirtualDisplay();
                mediaRecorder.start();
                running = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean stopRecord() {
        if (mediaRecorder != null) {
            if (!running) {
                return false;
            }
            try {
                running = false;
                mediaRecorder.stop();
                mediaRecorder.reset();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    virtualDisplay.release();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mediaProjection.stop();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return true;
    }


    private void createVirtualDisplay() {
        if (PublicPracticalMethodFromJAVA.getInstance().isHigherSpecifySDKVersion(Build.VERSION_CODES.LOLLIPOP)) {
            virtualDisplay = mediaProjection.createVirtualDisplay(ConfigConstants.screenrecord_name, width, height, dpi,
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mediaRecorder.getSurface(), null, null);
        }
    }


    private void initRecorder(String temp) {
        if (mediaRecorder != null) {
            try {
                // mediaRecorder.setAudioSource(MediaRecorder.AudioSource.);
                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                if (StringUtils.isBlank(temp)) {
                    mediaRecorder.setOutputFile(getsaveDirectory(ConfigConstants.screenrecord_dir, System.currentTimeMillis() + ConfigConstants.screenrecord_filename));
                } else {
                    mediaRecorder.setOutputFile(getsaveDirectory(ConfigConstants.screenrecord_dir, temp + ConfigConstants.screenrecord_filename));
                }
                //mediaRecorder.setVideoSize(width, height);
                mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
                // mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                mediaRecorder.setVideoEncodingBitRate(1024 * 1024);
                mediaRecorder.setVideoFrameRate(18);
                //设置要捕获的视频的宽度和高度
                //mSurfaceHolder.setFixedSize(320, 240);//最高只能设置640x480
                mediaRecorder.setVideoSize(width, height);//最高只能设置640x480
                //mediaRecorder.setOrientationHint(90);
                //mediaRecorder.setMaxDuration(30*1000);
                mediaRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String filePath = null;

    public String getsaveDirectory(String dirName, String flieName) {
        if (SDCardUtils.isSDCardEnable()) {
            filePath = SDCardUtils.getExternalStorageDirectory().getAbsolutePath() + File.separator + dirName + File.separator + flieName + ".mp4";
            boolean isExists = FileUtils.createOrExistsFile(filePath);
            if (isExists) {
                return filePath;
            } else {
                filePath = null;
            }
        }
        return filePath;
    }


    /**
     * 递归删除文件
     */
    public boolean recursionDeleteFile(String dirPath) {
        return FileUtils.deleteFilesInDir(dirPath);
    }

    /**
     * 得到文件路径
     */
    public String getFilePath() {
        return filePath;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new RecordBinder();
    }
}
