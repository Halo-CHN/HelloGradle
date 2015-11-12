package com.chn.halo.view.smartcamera.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.WindowManager;

import com.chn.halo.R;
import com.chn.halo.view.progressbar.CustomProgressDialog;

/**
 * Description:
 * Version: 1.0
 * Author: Halo-CHN
 * Email: halo-chn@outlook.com
 * Date: 15/11/12
 */
public class BaseActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customProgressDialog = new CustomProgressDialog(this);
        initWindow();

    }

    @TargetApi(19)
    private void initWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public int getStatusBarColor() {
        return getColorPrimary();
    }

    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    private CustomProgressDialog customProgressDialog;

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
}