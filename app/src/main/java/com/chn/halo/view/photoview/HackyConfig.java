package com.chn.halo.view.photoview;

/**
 * @author 张伟光
 * @ClassName: HackyConfig
 * @date 2016-1-18
 * @Description: 配置文件
 */
public class HackyConfig {

    /**
     * 查看图片-图片列表
     */
    public static String HACKY_PHOTO_ARRAY = "photo_array";
    /**
     * 查看图片-备注列表
     */
    public static String HACKY_TITLE_ARRAY = "title_array";
    /**
     * 查看图片-图片位置
     */
    public static String HACKY_PHOTO_POSITION = "photo_position";

    private static int mCurrentIndex = 0;

    public static void resetCurrentIndex() {
        mCurrentIndex = 0;
    }

    public static int getCurrentIndex() {
        return mCurrentIndex;
    }

    public static void setCurrentIndex(int mCurrentIndex) {
        HackyConfig.mCurrentIndex = mCurrentIndex;
    }
}
