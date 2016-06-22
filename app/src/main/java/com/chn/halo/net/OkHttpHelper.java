package com.chn.halo.net;

import okhttp3.OkHttpClient;

/**
 * @author Halo-CHN
 * @version 1.0
 * @description 自定义http请求callback
 * @mail halo-chn@outlook.com
 * @date 2016年6月21日
 */
public class OkHttpHelper {

    private OkHttpHelper() {
    }

    private static OkHttpHelper instance;

    public static OkHttpHelper getInstance() {
        if (null == instance) {
            synchronized (OkHttpHelper.class) {
                if (null == instance) {
                    instance = new OkHttpHelper();
                }
            }
        }
        return instance;
    }

    OkHttpClient okHttpClient = new OkHttpClient();

    public void getAsync() {

    }
}