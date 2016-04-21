package com.chn.halo.util;

/**
 * Description:JniUtils
 * Version: 1.0
 * Author: Halo-CHN
 * Email: halo-chn@outlook.com
 * Date: 16/4/21
 */
public class JniUtils {

    public static native String getKey();

    static {
        System.loadLibrary("JniModule");
    }
}