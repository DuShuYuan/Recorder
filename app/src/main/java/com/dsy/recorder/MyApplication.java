package com.dsy.recorder;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.zero.smallvideorecord.JianXiCamera;

import java.io.File;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 设置拍摄视频缓存路径
        JianXiCamera.setVideoCachePath(getCacheDir(this)+"/");
        // 初始化拍摄SDK，必须
        JianXiCamera.initialize(BuildConfig.DEBUG, null);
    }


    /**
     * 缓存路径
     * @param context
     * @return
     */
    public static String getCacheDir(Context context) {
        String path=null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File f=context.getExternalCacheDir();
            path= f != null ? f.getAbsolutePath() : null;
        }
        if (TextUtils.isEmpty(path)){
            path= context.getCacheDir().getAbsolutePath();
        }
        return path;
    }
}
