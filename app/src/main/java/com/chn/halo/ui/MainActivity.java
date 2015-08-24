package com.chn.halo.ui;

import android.view.View;

import com.chn.halo.R;
import com.chn.halo.core.BaseButterKnifeFragmentActivity;
import com.chn.halo.util.ToastUtils;
import com.chn.halo.view.bottomtabbar.HaloFragmentManager;
import com.chn.halo.view.bottomtabbar.HaloViewPager;
import com.chn.halo.view.bottomtabbar.OnSelectableTextViewClickedListener;
import com.chn.halo.view.bottomtabbar.SelectableBottomTextView;
import com.chn.halo.view.bottomtabbar.SelectableBottomTextViewAttributesEx;

import butterknife.Bind;

/**
 *
 * @description 主页面
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
public class MainActivity extends BaseButterKnifeFragmentActivity {

	@Override
	protected boolean supportBackKey() {
		return false;
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_main;
	}

	@Override
	protected void initializeAfterOnCreate() {
		ToastUtils.show(getThis(), "Welcome To Halo's World.");		
		bottom_tab_home.setOnTextViewClickedListener(bottomListener);
		bottom_tab_account.setOnTextViewClickedListener(bottomListener);
		bottom_tab_more.setOnTextViewClickedListener(bottomListener);
		if (null == homeFragment)
			homeFragment = new HomeFragment();
		if (null == accountFragment)
			accountFragment = new AccountFragment();
		if (null == moreFragment)
			moreFragment = new MoreFragment();
		/* 注意 此处请按控件排列先后顺序添加！！ */
		HaloFragmentManager.getInstance(main_viewpager, this.getSupportFragmentManager()).addFragment(bottom_tab_home, homeFragment);
		HaloFragmentManager.getInstance(main_viewpager, this.getSupportFragmentManager()).addFragment(bottom_tab_account, accountFragment);
		HaloFragmentManager.getInstance(main_viewpager, this.getSupportFragmentManager()).addFragment(bottom_tab_more, moreFragment);
	}

	/**
	 * 底部导航点击事件回调
	 */
	OnSelectableTextViewClickedListener bottomListener = new OnSelectableTextViewClickedListener() {

		@Override
		public void onTextViewClicked(View v) {
			if (!SelectableBottomTextViewAttributesEx.supportClickWhenSelected && v.getId() == SelectableBottomTextViewAttributesEx.onSelectableTextViewID)
				return;
			switch (v.getId()) {
			case R.id.bottom_tab_home:
				break;
			case R.id.bottom_tab_account:
				break;
			case R.id.bottom_tab_more:
				break;
			}
			HaloFragmentManager.getInstance(main_viewpager, getSupportFragmentManager()).clickToChangeFragment(v.getId());
		}
	};

	HomeFragment homeFragment;

	AccountFragment accountFragment;

	MoreFragment moreFragment;

	@Bind(R.id.main_viewpager)
	HaloViewPager main_viewpager;

	@Bind(R.id.bottom_tab_home)
	SelectableBottomTextView bottom_tab_home;

	@Bind(R.id.bottom_tab_account)
	SelectableBottomTextView bottom_tab_account;

	@Bind(R.id.bottom_tab_more)
	SelectableBottomTextView bottom_tab_more;
}