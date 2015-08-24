package com.chn.halo.core;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.List;

/**
 *
 * @description Application扩展
 *
 * @author Halo-CHN
 *
 * @mail halo-chn@outlook.com
 *
 * @date 2015年6月18日
 *
 * @version 1.0
 *
 */
public class ApplicationEx extends Application {

	/* 单例 */
	private static ApplicationEx instance;

	private ApplicationEx() {
		instance = this;
	}

	public static ApplicationEx getInstance() {
		return instance;
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

	public static void initImageLoader(Context context) {
		
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, "halo/img");//缓存路径

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()// 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())// 将保存的时候的URI名称用MD5加密
				.diskCacheSize(50 * 1024 * 1024) // 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO) // 设置图片下载和显示的工作队列排序
				.diskCache(new UnlimitedDiskCache(cacheDir))// 自定义缓存路径
				/**
				 * FileCountLimitedDiscCache（可以设定缓存图片的个数，当超过设定值，删除掉最先加入到硬盘的文件）
				 * LimitedAgeDiscCache（设定文件存活的最长时间，当超过这个值，就删除该文件）
				 * TotalSizeLimitedDiscCache（设定缓存bitmap的最大值，当超过这个值，删除最先加入到硬盘的文件）
				 * UnlimitedDiscCache（这个缓存类没有任何的限制）
				 */
				// .writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

	/**
	 * ImageLoader是根据ImageView的height，width确定图片的宽高。 如果经常出现OOM，进行以下设置
	 * ①减少配置之中线程池的大小，(.threadPoolSize).推荐1-5；
	 * ②使用.bitmapConfig(Bitmap.config.RGB_565)代替ARGB_8888;
	 * ③使用.imageScaleType(ImageScaleType.IN_SAMPLE_INT) 或者
	 * try.imageScaleType(ImageScaleType.EXACTLY)；
	 * ④避免使用RoundedBitmapDisplayer.他会创建新的ARGB_8888格式的Bitmap对象；
	 * ⑤使用.memoryCache(new WeakMemoryCache())，不要使用.cacheInMemory();
	 * 
	 * 如何加载本地图片 String imageUri = "http://site.com/image.png"; // from Web
	 * String imageUri = "file:///mnt/sdcard/image.png"; // from SD card String
	 * imageUri = "content://media/external/audio/albumart/13"; // from content
	 * provider String imageUri = "assets://image.png"; // from assets String
	 * imageUri = "drawable://" + R.drawable.image; // from drawables (only
	 * images, non-9patch)
	 */

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
