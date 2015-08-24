package com.chn.halo.core;

import butterknife.ButterKnife;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * @description 自定义Fragment - 已实现ButterKnife注入
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
public abstract class BaseButterKnifeFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = null;
		try {
			view = inflater.inflate(getLayoutResId(), container, false);
			if (null != view) {
				/* ButterKnife注入 */
				ButterKnife.bind(this, view);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initializeAfterOnCreate();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		/* 回收 */
		ButterKnife.unbind(this);
	}
	
	/**
	 * 
	 * @return 得到当前类
	 */
	protected BaseButterKnifeFragmentActivity getThis() {
		return (BaseButterKnifeFragmentActivity) this.getActivity();
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
	 * 可在此方法中进行变量等的初始化
	 */
	protected abstract void initializeAfterOnCreate();

}
