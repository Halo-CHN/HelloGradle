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

    public static final int POST_TYPE_POI = 1;
    public static final int POST_TYPE_TAG = 0;
    public static final int POST_TYPE_DEFAULT = 0;


    public static final float DEFAULT_PIXEL = 1242;                           //按iphone6设置
    public static final String PARAM_MAX_SIZE = "PARAM_MAX_SIZE";
    public static final String PARAM_EDIT_TEXT = "PARAM_EDIT_TEXT";
    public static final int ACTION_EDIT_LABEL = 8080;
    public static final int ACTION_EDIT_LABEL_POI = 9090;

    public static final String FEED_INFO = "FEED_INFO";


    public static final int REQUEST_CROP = 6709;
    public static final int REQUEST_PICK = 9162;
    public static final int RESULT_ERROR = 404;
}
