package com.chn.halo.view.photoview;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chn.halo.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @author 张伟光
 * @ClassName: HackyViewLayout
 * @date 2016-1-15
 * @Description: 自定义图片查看组件
 */
public class HackyViewLayout extends FrameLayout {

    public HackyViewLayout(Context context) {
        super(context);
    }

    public HackyViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HackyViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        HackyConfig.resetCurrentIndex();
        //加载布局
        LayoutInflater.from(context).inflate(R.layout.cus_layout_hacky_viewpager, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
//                .showImageOnFail(R.mipmap.pic_miss)
//                .showImageForEmptyUri(R.mipmap.pic_miss)
                .cacheOnDisk(true)
                .build();
        mImageLoader = ImageLoader.getInstance();
        initView();
    }

    private HackyViewPager mHackyViewPager;

    private SamplePagerAdapter mSamplePagerAdapter;

    private TextView mHackyTextView;

    private OnTapHandler mOnTapHandler;

    /**
     * @param onTapHandler 单击图片回调处理
     */
    public void setOnTapHandler(OnTapHandler onTapHandler) {
        this.mOnTapHandler = onTapHandler;
    }

    private boolean mCanTapToActivity = false;

    /**
     * @param tapToActivity 是否跳转至查看大图(默认：false)
     */
    public void setCanTapToActivity(boolean tapToActivity) {
        this.mCanTapToActivity = tapToActivity;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        if (null == mHackyViewPager) {
            mHackyViewPager = (HackyViewPager) findViewById(R.id.mHackyViewPager);
        }
        if (null == mHackyTextView) {
            mHackyTextView = (TextView) findViewById(R.id.mHackyTextView);
        }
    }

    /**
     * 刷新图片位置
     */
    public void refreshIndex() {
        if (null != mSamplePagerAdapter) {
            mSamplePagerAdapter.mIsUpdated = false;
            mSamplePagerAdapter.finishUpdate(this);
        }
    }

    ImageLoader mImageLoader;
    //声明默认配置
    DisplayImageOptions defaultOptions;

    /**
     * 设置图片
     *
     * @param drawables 图片集合
     * @param titles    备注集合
     */
    public void setDrawables(String[] drawables, String[] titles) {
        if (null == mSamplePagerAdapter) {
            mSamplePagerAdapter = new SamplePagerAdapter(drawables, titles);
            mHackyViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    HackyConfig.setCurrentIndex(++position);
                    setPosition(HackyConfig.getCurrentIndex());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            if (null != mHackyViewPager) {
                mHackyViewPager.setAdapter(mSamplePagerAdapter);
            }
        } else {
            mSamplePagerAdapter.setDrawables(drawables);
            mSamplePagerAdapter.setmTitles(titles);
        }
    }

    /**
     * 设置当前下标
     *
     * @param position 位置
     */
    private void setPosition(int position) {
        if (null != mSamplePagerAdapter && mSamplePagerAdapter.getCount() > 0) {
            if (null != mHackyTextView) {
                mHackyTextView.setText(position + "/" + mSamplePagerAdapter.getCount());
            }
        }
    }

    /**
     * 查看图片意图
     */
    Intent hackyIntent;

    /**
     * 跳转至图片查看页
     */
    private void toHackyActivity() {
        if (mCanTapToActivity) {
            if (null == hackyIntent) {
                hackyIntent = new Intent(getContext(), HackyViewActivity.class);
            }
            if (null != mSamplePagerAdapter) {
                hackyIntent.putExtra(HackyConfig.HACKY_PHOTO_ARRAY, mSamplePagerAdapter.getDrawables());
                hackyIntent.putExtra(HackyConfig.HACKY_TITLE_ARRAY, mSamplePagerAdapter.getmTitles());
                hackyIntent.putExtra(HackyConfig.HACKY_PHOTO_POSITION, HackyConfig.getCurrentIndex());
                getContext().startActivity(hackyIntent);
            }
        }
    }

    private ImageView.ScaleType mScaleType = ImageView.ScaleType.CENTER_CROP;

    /**
     * @param scaleType 设置图片加载样式
     */
    public void setScaleType(ImageView.ScaleType scaleType) {
        this.mScaleType = scaleType;
    }

    /**
     * 设置图片
     *
     * @param drawables      图片集合
     * @param selectionIndex 默认显示位置
     */
    public void setDrawables(String[] drawables, String[] titles, int selectionIndex) {
        setDrawables(drawables, titles);
        if (null != mSamplePagerAdapter)
            mSamplePagerAdapter.setSelectionIndex(selectionIndex);
    }

    private boolean mSupportZoom = true;

    /**
     * @param supportZoom 图片是否支持缩放操作(默认：true)
     */
    public void setSupportZoom(boolean supportZoom) {
        this.mSupportZoom = supportZoom;
    }

    class SamplePagerAdapter extends PagerAdapter {
        private String[] mTitles;
        private String[] mDrawables;
        public boolean mIsUpdated;
        PhotoViewAttacher.OnPhotoTapListener onPhotoTapListener;
        PhotoViewAttacher.OnViewTapListener onViewTapListener;
        OnClickListener onClickListener;

        public String[] getmTitles() {
            return mTitles;
        }

        public void setmTitles(String[] mTitles) {
            this.mTitles = mTitles;
            this.mIsUpdated = false;
            notifyDataSetChanged();
        }

        public void setSelectionIndex(int selectionIndex) {
            HackyConfig.setCurrentIndex(selectionIndex);
        }

        public void setDrawables(String[] drawables) {
            this.mDrawables = drawables;
            this.mIsUpdated = false;
            notifyDataSetChanged();
        }

        public String[] getDrawables() {
            return this.mDrawables;
        }

        public SamplePagerAdapter(String[] drawables, String[] titles) {
            this.mTitles = titles;
            this.mDrawables = drawables;
            this.mIsUpdated = false;
            if (mSupportZoom) {
                onPhotoTapListener = new PhotoViewAttacher.OnPhotoTapListener() {
                    @Override
                    public void onPhotoTap(View view, float x, float y) {
                        if (null != mOnTapHandler) {
                            mOnTapHandler.handleTap();
                        }
                        toHackyActivity();
                    }
                };

                onViewTapListener = new PhotoViewAttacher.OnViewTapListener() {
                    @Override
                    public void onViewTap(View view, float x, float y) {
                        if (null != mOnTapHandler) {
                            mOnTapHandler.handleTap();
                        }
                        toHackyActivity();
                    }
                };
            } else {
                onClickListener = new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toHackyActivity();
                    }
                };
            }
        }

        @Override
        public int getCount() {
            return mDrawables.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            photoView.setScaleType(mScaleType);
            photoView.setZoomable(mSupportZoom);
            //photoView.setOnPhotoTapListener(onPhotoTapListener);
            photoView.setOnViewTapListener(onViewTapListener);
            if (!photoView.canZoom() && mCanTapToActivity) {
                photoView.setOnClickListener(onClickListener);
            }
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mImageLoader.displayImage(mDrawables[position], photoView, defaultOptions);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
            if (!this.mIsUpdated) {
                if (HackyConfig.getCurrentIndex() <= 0) {
                    HackyConfig.setCurrentIndex(1);
                }
                if (getCount() > 0 && HackyConfig.getCurrentIndex() > 0 && HackyConfig.getCurrentIndex() <= getCount()) {
                    mHackyViewPager.setCurrentItem(HackyConfig.getCurrentIndex() - 1);
                    setPosition(HackyConfig.getCurrentIndex());
                }
                this.mIsUpdated = true;
            }
        }
    }

    public interface OnTapHandler {
        void handleTap();
    }
}