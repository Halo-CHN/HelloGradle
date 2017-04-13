package com.chn.halo.custom.CircleProgressButton;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;

/**
 * @author Spring Email:spring0101@foxmail.com
 * @ClassName:ScaleAnimationHelper
 * @date 2015/12/2
 * @Description:图片裁剪辅助类
 */
public class ScaleAnimationHelper {
    private static int DURATION = 100;

    public static void setDURATION(int duration) {
        DURATION = duration;
    }

    private float scale;
    ScaleAnimation myAnimation_Scale;

    public ScaleAnimationHelper(float scale_size) {
        scale = scale_size;
    }

    // 放大的类,不需要设置监听器
    public void ScaleOutAnimation(View view) {
        myAnimation_Scale = new ScaleAnimation(scale, 1.0f, scale, 1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        myAnimation_Scale.setInterpolator(new AccelerateInterpolator());
        AnimationSet aa = new AnimationSet(true);
        aa.addAnimation(myAnimation_Scale);
        aa.setDuration(DURATION);
        aa.setFillAfter(true);
        view.startAnimation(aa);
    }

    public void ScaleInAnimation(View view) {
        myAnimation_Scale = new ScaleAnimation(1.0f, scale, 1.0f, scale,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        myAnimation_Scale.setInterpolator(new AccelerateInterpolator());
        AnimationSet aa = new AnimationSet(true);
        aa.addAnimation(myAnimation_Scale);
        aa.setDuration(DURATION);
        aa.setFillAfter(true);
        view.startAnimation(aa);
    }
}
