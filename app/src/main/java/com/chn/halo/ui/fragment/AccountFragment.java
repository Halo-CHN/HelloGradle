package com.chn.halo.ui.fragment;

import com.chn.halo.R;
import com.chn.halo.core.BaseButterKnifeFragment;
import com.chn.halo.util.ToastUtils;

import butterknife.OnClick;

public class AccountFragment extends BaseButterKnifeFragment {

	@Override
	protected int getLayoutResId() {
		return R.layout.fragment_account;
	}

	@Override
	protected void initializeAfterOnCreate() {

	}

	@OnClick(R.id.main_btn_test)
	void Test() {
		ToastUtils.show(getThis(), "Account");
	}
}
