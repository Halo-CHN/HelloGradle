package com.chn.halo.dao;

import android.content.Context;
import de.greenrobot.dao.AbstractDao;

/**
 * 
 * @description 为方便管理GreenDao所封装的基类
 *
 * @author Halo-CHN
 *
 * @mail halo-chn@outlook.com
 *
 * @date 2015年6月18日
 *
 * @version 1.0
 *
 * @param <T>
 *            GreenDao自动对应数据库中表名生成的类
 * @param <D>
 *            GreenDao自动对应数据库中表名生成的DAO类
 */
public abstract class BaseGreenDaoManager<T, D extends AbstractDao<T, ?>> {

	private Context mContext;

	// private DaoSession mDaoSession;

	public abstract D getDao();

	protected Context getContext() {
		return this.mContext;
	}

	protected BaseGreenDaoManager(Context context) {
		this.mContext = context;
		// this.mDaoSession = MyApplication.getInstance().getDaoSession(
		// getContext());
	}

	// protected DaoSession getDaoSession() {
	// if (null != this.mDaoSession) {
	// return this.mDaoSession;
	// } else {
	// return MyApplication.getInstance().getDaoSession(getContext());
	// }
	// }
}
