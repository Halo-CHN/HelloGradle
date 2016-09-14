package com.chn.halo.view.bottomtabbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.TextView;

import com.chn.halo.R;

/**
 * @author Halo-CHN
 * @version 1.0
 * @description 自定义底部导航可选择 TextView 控件
 * @mail halo-chn@outlook.com
 * @date 2015年7月8日
 */
public class SelectableBottomTextView extends TextView implements View.OnClickListener {
    private boolean selectedDefault = false;
    private int selectedColor;
    private int unselectedColor;
    private int iconSelectedColorFilter;
    private int iconUnSelectedColorFilter;
    private Drawable selectedIcon;
    private Drawable unselectedIcon;
    private OnSelectableTextViewClickedListener listener;

    public SelectableBottomTextView(Context context) {
        super(context);
    }

    public SelectableBottomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

		/* 获取XML布局文件中定义的属性值 */
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SelectableView);

        selectedColor = a.getColor(R.styleable.SelectableView_text_selected_color, 0x000000);
        unselectedColor = a.getColor(R.styleable.SelectableView_text_unselected_color, 0x000000);
        iconSelectedColorFilter = a.getColor(R.styleable.SelectableView_icon_selected_color_filter, 0x000000);
        iconUnSelectedColorFilter = a.getColor(R.styleable.SelectableView_icon_unselected_color_filter, 0x000000);
        selectedIcon = a.getDrawable(R.styleable.SelectableView_selected_icon);

        unselectedIcon = a.getDrawable(R.styleable.SelectableView_unselected_icon);
        selectedDefault = a.getBoolean(R.styleable.SelectableView_default_selected, selectedDefault);

        setTextViewSelected(selectedDefault);
        setOnClickListener(this);
        a.recycle();
    }

    public void setTextViewSelected(boolean isSelected) {
        this.selectedDefault = isSelected;
        setTextViewSelected();
    }

    public void setOnTextViewClickedListener(OnSelectableTextViewClickedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        ViewParent parent = getParent();
        if (parent instanceof BottomTextViewGroup) {
            BottomTextViewGroup group = (BottomTextViewGroup) parent;
            group.onTextViewSelectedChanged(group, getId());
        }
        if (listener != null) {
            listener.onTextViewClicked(view);
        }
    }

    private void setTextViewSelected() {
        if (selectedDefault) {
            setTextColor(selectedColor);
            if (null != selectedIcon) {
                selectedIcon.setColorFilter(iconSelectedColorFilter, PorterDuff.Mode.SRC_ATOP);
                setCompoundDrawablesWithIntrinsicBounds(null, selectedIcon, null, null);
            }
        } else {
            setTextColor(unselectedColor);
            if (null != unselectedIcon) {
                unselectedIcon.setColorFilter(iconUnSelectedColorFilter, PorterDuff.Mode.SRC_ATOP);
                setCompoundDrawablesWithIntrinsicBounds(null, unselectedIcon, null, null);
            }
        }
    }
}