package com.chn.halo.custom.CircleProgressButton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.chn.halo.R;


/**
 * @author Spring Email:spring0101@foxmail.com
 * @ClassName:ZoomAnimationRelativeLayout
 * @date 2015/12/2
 * @Description:首页按下效果自定义类
 */
@SuppressLint("HandlerLeak")
public class ZoomAnimationRelativeLayout extends RelativeLayout {

    /**
     * 缩放动画工具
     */
    private ScaleAnimationHelper scaleAnimationHelper;
    /**
     * 缩放默认缩放大小
     */
    private float scale_size = 1.0f;

    private OnViewClick mOnViewClick;

    public ZoomAnimationRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.ZoomAnimationRelativeLayout);
        scale_size = ta.getFloat(R.styleable.ZoomAnimationRelativeLayout_scale_size,
                1.0f);
        scaleAnimationHelper = new ScaleAnimationHelper(scale_size);
    }

    public ZoomAnimationRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.ZoomAnimationRelativeLayout);
        scale_size = ta.getFloat(R.styleable.ZoomAnimationRelativeLayout_scale_size,
                1.0f);
        scaleAnimationHelper = new ScaleAnimationHelper(scale_size);
    }

    public void setOnViewClick(OnViewClick onViewClick) {
        mOnViewClick = onViewClick;
    }

    public void setDuring(int during) {
        if (null != scaleAnimationHelper) {
            scaleAnimationHelper.setDURATION(during);
        }
    }

    private OnTouchListener mOnTouchListener;

    public void addOnTouchListener(OnTouchListener onTouchListener) {
        this.mOnTouchListener = onTouchListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (null != mOnTouchListener) {
            mOnTouchListener.onTouch(null, event);
        }
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                scaleAnimationHelper.ScaleInAnimation(ZoomAnimationRelativeLayout.this);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int[] location = new int[2];
                ZoomAnimationRelativeLayout.this.getLocationOnScreen(location);

                if (inRangeOfView(ZoomAnimationRelativeLayout.this, event)
                        && mOnViewClick != null) {
                    Log.e("water", "is range");
                    mOnViewClick.onClick(ZoomAnimationRelativeLayout.this);
                }

                scaleAnimationHelper.ScaleOutAnimation(ZoomAnimationRelativeLayout.this);
                break;
            case MotionEvent.ACTION_CANCEL:
                // 滑动出去不会调用action_up,调用action_cancel
                scaleAnimationHelper.ScaleOutAnimation(ZoomAnimationRelativeLayout.this);
                break;
        }
        return true;
    }

    private boolean inRangeOfView(View view, MotionEvent ev) {

        float X = ev.getRawX();
        float Y = ev.getRawY();

        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int view_fromx = location[0];
        int view_tox = location[0] + view.getWidth();

        int view_fromy = location[1];
        int view_toy = location[1] + view.getHeight();

        if (X < view_fromx || X > (view_tox) || Y < view_fromy
                || Y > (view_toy)) {
            return false;
        }
        return true;
    }

    public interface OnViewClick {
        public void onClick(View view);
    }
}
