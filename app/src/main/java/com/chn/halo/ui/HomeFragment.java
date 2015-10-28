package com.chn.halo.ui;

import com.chn.halo.R;
import com.chn.halo.core.BaseButterKnifeFragment;
import com.chn.halo.net.BaiDuApiStoreClientProvider;
import com.chn.halo.net.HaloAsyncHttpResponseHandler;
import com.chn.halo.util.ToastUtils;
import com.chn.halo.GlobalConfig;

import butterknife.OnClick;

/**
 * @author Halo-CHN
 * @version 1.0
 * @description 主页
 * @mail halo-chn@outlook.com
 * @date 2015年7月13日
 */
public class HomeFragment extends BaseButterKnifeFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initializeAfterOnCreate() {

    }

    @OnClick(R.id.main_btn_test)
    void Test() {
        getBeijing();
        ToastUtils.show(getThis(), "Home");
    }

    private void getBeijing() {
        showProgressBar("");
        BaiDuApiStoreClientProvider.getInstance().get(getThis(), GlobalConfig.baiDuTrafficEventUrl,
                BaiDuApiStoreClientProvider.getInstance().getTafficEventParams("北京"), new HaloAsyncHttpResponseHandler() {

                    @Override
                    public void callback(String response) {
                        ToastUtils.show(getThis(), response);
                        dismissProgressBar();
                    }
                });
    }
}
