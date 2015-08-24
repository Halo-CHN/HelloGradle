package com.chn.halo.view.bottomtabbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 *
 * @description 自定义ViewPager
 *
 * @author Halo-CHN
 *
 * @mail halo-chn@outlook.com
 *
 * @date 2015年7月8日
 *
 * @version 1.0
 *
 */
public class HaloViewPager extends ViewPager {

	/* 可滑动标志位 */
	private boolean isSlipping = true;

	public HaloViewPager(Context context) {
		super(context);
	}

	public HaloViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent eventArg) {
		if (!isSlipping) {
			return false;
		}
		return super.onInterceptTouchEvent(eventArg);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent eventArg) {
		/* 去掉注释启用左右滑动切换 */
		if (!isSlipping) {
			return false;
		}
		return super.onTouchEvent(eventArg);
	}

	/**
	 * @Title: setSlipping
	 * @param isSlipping
	 *            是否禁止滑动切换
	 */
	public void setSlipping(boolean isSlipping) {
		this.isSlipping = isSlipping;
	}
}
