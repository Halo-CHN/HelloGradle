package com.chn.halo.core;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.util.DisplayMetrics;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

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
        /*初始化Fresco*/
        Fresco.initialize(this);
        initImageLoader();
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

    /**
     * ImageLoader是根据ImageView的height，width确定图片的宽高。 如果经常出现OOM，进行以下设置
     * ①减少配置之中线程池的大小，(.threadPoolSize).推荐1-5；
     * ②使用.bitmapConfig(Bitmap.config.RGB_565)代替ARGB_8888;
     * ③使用.imageScaleType(ImageScaleType.IN_SAMPLE_INT) 或者
     * try.imageScaleType(ImageScaleType.EXACTLY)；
     * ④避免使用RoundedBitmapDisplayer.他会创建新的ARGB_8888格式的Bitmap对象；
     * ⑤使用.memoryCache(new WeakMemoryCache())，不要使用.cacheInMemory();
     * <p>
     * 如何加载本地图片 String imageUri = "http://site.com/image.png"; // from Web
     * String imageUri = "file:///mnt/sdcard/image.png"; // from SD card String
     * imageUri = "content://media/external/audio/albumart/13"; // from content
     * provider String imageUri = "assets://image.png"; // from assets String
     * imageUri = "drawable://" + R.drawable.image; // from drawables (only
     * images, non-9patch)
     */
    public void initImageLoader() {

        //声明默认配置
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .imageScaleType(ImageScaleType.EXACTLY)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .defaultDisplayImageOptions(defaultOptions)
                .denyCacheImageMultipleSizesInMemory()// 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())// 将保存的时候的URI名称用MD5加密
                .diskCache(new UnlimitedDiskCache(StorageUtils.getOwnCacheDirectory(this, "")))
                .diskCacheSize(500 * 1024 * 1024)// 磁盘缓存大小: 500 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)// 设置图片下载和显示的工作队列排序
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .threadPoolSize(3)
                .build();
        ImageLoader.getInstance().init(config);
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
