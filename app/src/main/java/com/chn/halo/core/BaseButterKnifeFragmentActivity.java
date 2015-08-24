package com.chn.halo.core;

import butterknife.ButterKnife;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.Window;

/**
 *
 * @description 自定义FragmentActivity基类 - 实现ButterKnife注入
 *
 * @author Halo-CHN
 *
 * @mail halo-chn@outlook.com
 *
 * @date 2014年12月11日
 *
 * @version 1.0
 *
 */
public abstract class BaseButterKnifeFragmentActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* 添加Activity到管理类中 */
		ActivityController.add(this);
		// 去掉信息栏
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// 去掉标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		/* 设置显示的资源 */
		setContentView(getLayoutResId());
		/* 注入 */
		ButterKnife.bind(this);

		initializeAfterOnCreate();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityController.remove(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (!supportBackKey()) {
			if (event.getAction() == KeyEvent.ACTION_DOWN
					&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
				TwiceConfirmExitManager.getInstance().showToast(this,
						ActivityController.getAllActivities());
				return true;// 消费掉后退键
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 
	 * @return 得到当前类
	 */
	protected BaseButterKnifeFragmentActivity getThis() {
		return this;
	}

	/**
	 * 
	 * @return 是否支持后退键 true-可后退 false-双次退出程序;默认为true，可在子类中重载此方法修改返回结果
	 * 
	 */
	protected boolean supportBackKey()
	{
		return true;
	}

	/**
	 * 得到初始化Fragment的View所加载的Layout资源
	 * 
	 * @return 资源 layoutId
	 */
	protected abstract int getLayoutResId();

	/**
	 * OnCreate处理完毕后,立即执行此方法
	 * 
	 * 可在此方法中进行变量初始化等行为
	 */
	protected abstract void initializeAfterOnCreate();
}
