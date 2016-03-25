package com.chn.halo.ui.activity;

import com.chn.halo.R;
import com.chn.halo.core.BaseButterKnifeActivity;
import com.chn.halo.util.StringEx;
import com.chn.halo.util.ToastUtils;

public class SecondActivity extends BaseButterKnifeActivity {

	@Override
	protected boolean supportBackKey() {
		return true;
	}

	@Override
	protected int getLayoutResId() {
		return R.layout.activity_second;
	}

	@Override
	protected void initializeAfterOnCreate() {
		ToastUtils.show(getThis(),
				StringEx.checkNull(getIntent().getStringExtra("TEST")));
	}
}