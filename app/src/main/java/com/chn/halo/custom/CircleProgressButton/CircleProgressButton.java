package com.chn.halo.custom.CircleProgressButton;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chn.halo.R;
import com.github.lzyzsd.circleprogress.DonutProgress;

/**
 * @author Halo-CHN
 * @version 1.0
 * @description 进度条按钮
 * @mail halo-chn@outlook.com
 * @date 2017-3-9
 */

public class CircleProgressButton extends LinearLayout {


    public CircleProgressButton(Context context) {
        this(context, null);
    }

    public CircleProgressButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CircleProgressButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews();
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgressButton, defStyleAttr, 0);
        this.initByAttributes(attributes);
        attributes.recycle();
    }

    private final float default_text_size = 12;

    private void initByAttributes(TypedArray attributes) {
        if (attributes.getString(R.styleable.CircleProgressButton_center_text) != null) {
            donut_progress_text.setText(attributes.getString(R.styleable.CircleProgressButton_center_text));
        }
        donut_progress_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, attributes.getDimension(R.styleable.CircleProgressButton_center_text_size, default_text_size));

        progress_during_time = attributes.getInt(R.styleable.CircleProgressButton_progress_during_time, DEFAULT_PROGRESS_FINISH_TIME);
        scale_during_time = attributes.getInt(R.styleable.CircleProgressButton_scale_during_time, DEFAULT_SCALE_FINISH_TIME);
        mZoomAnimationRelativeLayout.setDuring(scale_during_time);
    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_circle_progress_button, this, true);
        mDonutProgress = (DonutProgress) findViewById(R.id.donut_progress);
        mZoomAnimationRelativeLayout = (ZoomAnimationRelativeLayout) findViewById(R.id.mZoomAnimationRelativeLayout);
        donut_progress_text = (TextView) findViewById(R.id.donut_progress_text);

        mZoomAnimationRelativeLayout.addOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    actionDown();
                    break;
                case MotionEvent.ACTION_UP:
                    actionUp();
                    break;
            }
            return true;
        });
    }

    private DonutProgress mDonutProgress;
    private ZoomAnimationRelativeLayout mZoomAnimationRelativeLayout;
    private TextView donut_progress_text;

    private OnProgressFinished mOnProgressFinished;

    private AsyncTask asyncTask;

    private final int FINISHED = 3436;
    private final int CANCLE = 3437;

    private final int MAX = 100;

    private final int DEFAULT_PROGRESS_FINISH_TIME = 100;
    private int progress_during_time;

    private final int DEFAULT_SCALE_FINISH_TIME = 100;
    private int scale_during_time;

    private boolean isActionUp = false;

    private int currentProgress = 1;

    public void setOnProgressFinished(OnProgressFinished onProgressFinished) {
        this.mOnProgressFinished = onProgressFinished;
    }

    private void actionDown() {
        isActionUp = false;
        asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                if (isActionUp) {

                }
                for (int i = currentProgress; i <= MAX; i += 1) {
                    if (isActionUp) {
                        break;
                    }
                    publishProgress(i);
                    try {
                        Thread.sleep(progress_during_time / MAX);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (i == MAX) {
                        mHandler.sendEmptyMessage(FINISHED);
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Object[] values) {
                int value = (int) values[0];
                setProgress(value);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }
        };
        asyncTask.execute();
    }

    private void actionUp() {
        isActionUp = true;
        mHandler.sendEmptyMessage(CANCLE);
    }

    private void resetProgress(boolean resetDonut) {
        if (null != asyncTask) {
            asyncTask.cancel(true);
            asyncTask = null;
        }
        if (resetDonut) {
            setProgress(0);
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case FINISHED:
                    mZoomAnimationRelativeLayout.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.shape_guide_round_green));
                    mDonutProgress.setFinishedStrokeColor(AppCompatResources.getColorStateList(getContext(), R.color.filter_green).getDefaultColor());
                    resetProgress(false);
                    if (null != mOnProgressFinished) {
                        mOnProgressFinished.progressFinished();
                    }
                    break;
                case CANCLE:
                    mZoomAnimationRelativeLayout.setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.shape_guide_round_red));
                    mDonutProgress.setFinishedStrokeColor(AppCompatResources.getColorStateList(getContext(), R.color.filter_red).getDefaultColor());
                    resetProgress(true);
                    break;
            }
        }
    };

    private void setProgress(int value) {
        mDonutProgress.setProgress(value);
        currentProgress = value;
    }

    public interface OnProgressFinished {
        void progressFinished();
    }
}