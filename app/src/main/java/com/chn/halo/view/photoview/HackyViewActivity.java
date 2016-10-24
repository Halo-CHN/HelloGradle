package com.chn.halo.view.photoview;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chn.halo.R;


/**
 * @author 张伟光
 * @ClassName: HackyViewActivity
 * @date 2016-1-15
 * @Description: 自定义图片查看页面
 */
public class HackyViewActivity extends Activity implements View.OnClickListener {

    private HackyViewLayout hackyViewLayout;

    private FrameLayout mFlHacky;

    private String[] mTitles;
    private String[] mDrawables;

    private int mCurrentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cus_act_hacky_viewpager);
        hackyViewLayout = (HackyViewLayout) findViewById(R.id.mHvlInActivity);
        if (null != hackyViewLayout) {
            hackyViewLayout.setScaleType(ImageView.ScaleType.FIT_CENTER);
            hackyViewLayout.setOnTapHandler(new HackyViewLayout.OnTapHandler() {
                @Override
                public void handleTap() {
                    finishThis();
                }
            });
        }
        mFlHacky = (FrameLayout) findViewById(R.id.mFlHacky);
        if (null != mFlHacky) {
            mFlHacky.setOnClickListener(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null == mDrawables) {
            mTitles = getIntent().getStringArrayExtra(HackyConfig.HACKY_TITLE_ARRAY);
            mDrawables = getIntent().getStringArrayExtra(HackyConfig.HACKY_PHOTO_ARRAY);
            mCurrentIndex = getIntent().getIntExtra(HackyConfig.HACKY_PHOTO_POSITION, 0);
        }
        if (null != hackyViewLayout && null != mTitles && null != mDrawables) {
            hackyViewLayout.setDrawables(mDrawables, mTitles, mCurrentIndex);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finishThis();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mFlHacky:
                finishThis();
                break;
            default:
                finishThis();
                break;
        }
    }

    private void finishThis() {
        finish();
    }
}
