package com.chn.halo.view.smartcamera.core;

import android.os.Environment;

/**
 * Description:相机配置项
 * Version: 1.0
 * Author: Halo-CHN
 * Email: halo-chn@outlook.com
 * Date: 15/11/12
 */
public class CameraConfig {
    public static final String APP_NAME = "HelloGradel";
    public static final String APP_DIR = Environment.getExternalStorageDirectory() + "/" + APP_NAME;
    public static final String APP_TEMP = APP_DIR + "/temp";
    public static final String APP_IMAGE = APP_DIR + "/image";
    public static final String APP_SYSTEMPHOTOPATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera";
    public static final String APP_BASEPATH = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/" + CameraConfig.APP_NAME + "/";
    public static final String APP_FOLDER = "/hellogradle/";
}
