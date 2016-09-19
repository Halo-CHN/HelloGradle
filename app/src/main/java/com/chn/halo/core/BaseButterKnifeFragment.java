package com.chn.halo.core;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chn.halo.view.progressbar.CustomProgressDialog;

import butterknife.ButterKnife;

/**
 * @author Halo-CHN
 * @version 1.0
 * @description 自定义Fragment - 已实现ButterKnife注入
 * @mail halo-chn@outlook.com
 * @date 2014年12月11日
 */
public abstract class BaseButterKnifeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        try {
            view = inflater.inflate(getLayoutResId(), container, false);
            if (null != view) {
                /* ButterKnife注入 */
                ButterKnife.bind(this, view);
            }
            customProgressDialog = new CustomProgressDialog(getThis());
            initializeAfterOnCreate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private CustomProgressDialog customProgressDialog;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != customProgressDialog && customProgressDialog.isShowing()) {
            customProgressDialog.dismiss("");
        }
        /* 回收 */
        ButterKnife.unbind(this);
    }

    /**
     * show progressbar
     *
     * @param msg 消息
     */
    protected void showProgressBar(CharSequence msg) {
        if (null != customProgressDialog) {
            customProgressDialog.show(msg);
        }
    }

    protected void showProgressBar(CharSequence msg, boolean cancleable) {
        if (null != customProgressDialog) {
            customProgressDialog.show(msg, cancleable);
        }
    }

    /**
     * 隐藏progressbar
     */
    protected void dismissProgressBar() {
        if (null != customProgressDialog) {
            customProgressDialog.dismiss("");
        }
    }

    /**
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
     * <p/>
     * 可在此方法中进行变量等的初始化
     */
    protected void initializeAfterOnCreate(){}
}