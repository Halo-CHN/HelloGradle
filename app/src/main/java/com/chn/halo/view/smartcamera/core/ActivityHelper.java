package com.chn.halo.view.smartcamera.core;

import android.app.Activity;


public class ActivityHelper {
    final static String TAG = ActivityHelper.class.getSimpleName();

    /**
     * 对应的Activity
     */
    private Activity mActivity;


    public ActivityHelper(Activity activity) {
        mActivity = activity;
    }
}
