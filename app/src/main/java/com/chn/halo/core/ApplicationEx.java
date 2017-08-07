package com.chn.halo.core;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import java.util.List;

/**
 * @author Halo-CHN
 * @version 1.0
 * @description Application扩展
 * @mail halo-chn@outlook.com
 * @date 2015年6月18日
 */
public class ApplicationEx extends Application {

    /* 单例 */
    private static ApplicationEx instance;

    private DisplayMetrics displayMetrics = null;

    public ApplicationEx() {
        instance = this;
    }

    public static ApplicationEx getInstance() {
        if (instance != null && instance instanceof ApplicationEx) {
            return instance;
        } else {
            instance = new ApplicationEx();
            instance.onCreate();
            return instance;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    public float getScreenDensity() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(getResources().getDisplayMetrics());
        }
        return this.displayMetrics.density;
    }

    public int getScreenHeight() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(getResources().getDisplayMetrics());
        }
        return this.displayMetrics.heightPixels;
    }

    public int getScreenWidth() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(getResources().getDisplayMetrics());
        }
        return this.displayMetrics.widthPixels;
    }

    public void setDisplayMetrics(DisplayMetrics DisplayMetrics) {
        this.displayMetrics = DisplayMetrics;
    }

    public int dp2px(float f) {
        return (int) (0.5F + f * getScreenDensity());
    }

    public int px2dp(float pxValue) {
        return (int) (pxValue / getScreenDensity() + 0.5f);
    }

    //获取应用的data/data/....File目录
    public String getFilesDirPath() {
        return getFilesDir().getAbsolutePath();
    }

    //获取应用的data/data/....Cache目录
    public String getCacheDirPath() {
        return getCacheDir().getAbsolutePath();
    }

    /**
     * 取得DaoMaster
     *
     * @param context
     * @return 全局唯一DaoMaster
     */
    // public DaoMaster getDaoMaster(Context context) {
    // if (daoMaster == null) {
    // OpenHelper helper = new DaoMaster.DevOpenHelper(context, dbName,
    // null);
    // daoMaster = new DaoMaster(helper.getWritableDatabase());
    // }
    // return daoMaster;
    // }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return 全局唯一DaoSession
     */
    // public DaoSession getDaoSession(Context context) {
    // if (daoSession == null) {
    // if (daoMaster == null) {
    // daoMaster = getDaoMaster(context);
    // }
    // daoSession = daoMaster.newSession();
    // }
    // return daoSession;
    // }

    // private static DaoMaster daoMaster;
    // private static DaoSession daoSession;
}
