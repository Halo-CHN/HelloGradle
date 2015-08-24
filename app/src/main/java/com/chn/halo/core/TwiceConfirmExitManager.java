package com.chn.halo.core;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * 
 * @description 两次确认退出应用程序 
 *
 * @author Halo-CHN
 *
 * @mail halo-chn@outlook.com
 *
 * @date 2015年7月7日
 *
 * @version 1.0
 *
 */
public class TwiceConfirmExitManager {

	private static TwiceConfirmExitManager instance;

	private TwiceConfirmExitManager() {
	}

	public static TwiceConfirmExitManager getInstance() {
		if (null == instance) {
			synchronized (TwiceConfirmExitManager.class) {
				if (null == instance) {
					instance = new TwiceConfirmExitManager();
				}
			}
		}
		return instance;
	}

	private long timeStamp = 0;

	/**
	 * 
	 * @param context
	 *            当前页
	 * @param activities
	 *            当前应用中已经打开的Activity
	 */
	public void showToast(Context context, List<? extends Activity> activities) {

		if ((System.currentTimeMillis() - timeStamp) < 2000) {// Toast.LENGTH_SHORT
																// 的显示时间大概2秒
			if (null != activities && activities.size() > 0) {
				for (Activity ac : activities) {
					if (null != ac)
						ac.finish();
				}
			}
			System.exit(0);
		} else {
			timeStamp = System.currentTimeMillis();
			Toast.makeText(context.getApplicationContext(), "再按一次返回将退出程序", Toast.LENGTH_SHORT).show();
		}
	}
}
