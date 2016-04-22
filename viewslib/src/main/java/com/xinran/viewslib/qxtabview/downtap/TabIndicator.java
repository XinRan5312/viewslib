package com.xinran.viewslib.qxtabview.downtap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinran.viewslib.R;

/**
 * Created by qixinh on 16/4/15.
 */
public class TabIndicator extends HorizontalScrollView {

    private static final CharSequence EMPTY_TITLE = "";
    private Runnable mTabSelector;
    private final LinearLayout mTabLayout;
    private int mMaxTabWidth;
    private int mSelectedTabIndex;
    private boolean mAnimate;
    private IndicatorFactory mIndicatorFactory;
    private IndicatorAdapter mAdapter;
    private OnTabChangeListener mOnTabChangeListener;
    private BeforeTabChangeListener mBeforeTabChangeListener;

    private final Paint paint;

    public static interface BeforeTabChangeListener {
        /**
         * 在setCurrentTab之前调用
         * @return true正常通过;false拦截
         */
        boolean beforeTabChange(int index);
    }

    public interface OnTabChangeListener {
        void onTabChange(View tab, int index);
    }

    public TabIndicator(Context context) {
        this(context, null);
    }

    public TabIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        setHorizontalScrollBarEnabled(false);
        mAnimate = true;
        mTabLayout = new IcsLinearLayout(context);
        addView(mTabLayout, new ViewGroup.LayoutParams(-2, -1));

        paint = new Paint();
        paint.setStrokeWidth(BitmapHelper.dip2pxF(context, 1.0f));
        paint.setColor(getResources().getColor(R.color.tab_item_color_normal));
    }

    public int getSelectedTabIndex() {
        return this.mSelectedTabIndex;
    }

    private final OnClickListener mTabClickListener = new OnClickListener() {
        public void onClick(View view) {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                for (int i = 0; i < group.getChildCount(); i++) {
                    if (view == group.getChildAt(i)) {
                        setCurrentItem(i);
                    }
                }
            }
        }
    };

	public int getMaxTabWidth() {
		return mMaxTabWidth;
	}

	@Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
        setFillViewport(lockedExpanded);

        final int childCount = mTabLayout.getChildCount();
        if (childCount > 1 && (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST)) {
            if (childCount > 2) {
                mMaxTabWidth = (int) (MeasureSpec.getSize(widthMeasureSpec) * 0.4f);
            } else {
                mMaxTabWidth = MeasureSpec.getSize(widthMeasureSpec) / 2;
            }
        } else {
            mMaxTabWidth = -1;
        }

        final int oldWidth = getMeasuredWidth();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int newWidth = getMeasuredWidth();

        if (lockedExpanded && oldWidth != newWidth) {
            // Recenter the tab display if we're at a new (scrollable) size.
            setCurrentItem(mSelectedTabIndex);
        }
    }

    public boolean isAnimate() {
        return this.mAnimate;
    }

    public void setAnimate(boolean animate) {
        this.mAnimate = animate;
    }

    public IndicatorAdapter getAdapter() {
        return this.mAdapter;
    }

    /**
     * 指定一个指示器的生成工厂，注意这个方法要在setAdapter和notifyDataSetChanged之前调用
     */
    public void setIndicatorFactory(IndicatorFactory factory) {
        this.mIndicatorFactory = factory;
    }

    private void addTab(int index, int id, CharSequence text, int icon) {
        // 没有指定factory时使用默认的
        if (mIndicatorFactory == null) {
            mIndicatorFactory = new DefalutFactory();
        }

        final IndicatorView indicatorView = mIndicatorFactory.createIndicatorView(getContext(), index);
        indicatorView.setId(id);
        indicatorView.setFocusable(true);
        indicatorView.setOnClickListener(mTabClickListener);
        indicatorView.setIndex(index);
        indicatorView.setText(text);
        indicatorView.setIcon(icon);

		mTabLayout.addView((View) indicatorView.getView(), newTabLayoutParam());
	}

	protected LinearLayout.LayoutParams newTabLayoutParam(){
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(0, -1, 1);
		llp.topMargin = (int) BitmapHelper.dip2px(getContext(), 2);
		llp.bottomMargin = (int) BitmapHelper.dip2px(getContext(), 2);
		llp.leftMargin = (int) BitmapHelper.dip2px(getContext(), 2);
		llp.rightMargin = (int) BitmapHelper.dip2px(getContext(), 2);
		return llp;
	}

    public void setAdapter(IndicatorAdapter adapter) {
        mAdapter = adapter;
        if (mAdapter != null) {
            notifyDataSetChanged();
        }
    }

    public void notifyDataSetChanged() {
        mTabLayout.removeAllViews();

        final int count = mAdapter.getCount();
        for (int i = 0; i < count; i++) {
            CharSequence title = mAdapter.getTitle(i);
            if (title == null) {
                title = EMPTY_TITLE;
            }
            addTab(i, mAdapter.getId(i), title, mAdapter.getIcon(i));
        }
        if (mSelectedTabIndex > count) {
            mSelectedTabIndex = count - 1;
        }
        setCurrentItem(mSelectedTabIndex);
        requestLayout();
    }

    public void setAdapter(IndicatorAdapter adapter, int initialPosition) {
        mSelectedTabIndex = initialPosition;
        setAdapter(adapter);
    }

    public void setCurrentItem(int item) {
        boolean beforeChange = true;
        if (mBeforeTabChangeListener != null) {
            beforeChange = mBeforeTabChangeListener.beforeTabChange(item);
        }
        if (beforeChange) {
            mSelectedTabIndex = item;
            final int tabCount = mTabLayout.getChildCount();
            for (int i = 0; i < tabCount; i++) {
                final View child = mTabLayout.getChildAt(i);
                final boolean isSelected = i == mSelectedTabIndex;
                child.setSelected(isSelected);
                if (isSelected) {
                    if (mAnimate) {
                        animateToTab(mSelectedTabIndex);
                    }
                    if (mOnTabChangeListener != null) {
                        mOnTabChangeListener.onTabChange(child, mSelectedTabIndex);
                    }
                }
            }
        }
    }

    private void animateToTab(final int position) {
        final View tabView = mTabLayout.getChildAt(position);
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
        mTabSelector = new Runnable() {
            public void run() {
                final int scrollPos = tabView.getLeft() - (getWidth() - tabView.getWidth()) / 2;
                smoothScrollTo(scrollPos, 0);
                mTabSelector = null;
            }
        };
        post(mTabSelector);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mTabSelector != null) {
            // Re-post the selector we saved
            post(mTabSelector);
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
    }

	protected void drawTabLine(Canvas canvas){
		if (getTag() != null && getTag().equals("main_page")) {
			canvas.drawLine(0, 0, getWidth(), 0, paint);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawTabLine(canvas);
	}

    public static class SavedState extends BaseSavedState {
        public int selectedTabIndex;

        public SavedState(Parcel source) {
            super(source);
            selectedTabIndex = source.readInt();
        }

        public SavedState(Parcelable superState, int selected) {
            super(superState);
            this.selectedTabIndex = selected;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(selectedTabIndex);
        }

        @SuppressWarnings("all")
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

    }

    @Override
    protected Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), mSelectedTabIndex);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setCurrentItem(ss.selectedTabIndex);
    }

    public OnTabChangeListener getOnTabChangeListener() {
        return this.mOnTabChangeListener;
    }

    public void setOnTabChangeListener(OnTabChangeListener onTabChangeListener) {
        this.mOnTabChangeListener = onTabChangeListener;
    }

    public void setBeforeTabChangeListener(BeforeTabChangeListener b) {
        mBeforeTabChangeListener = b;
    }

    private class TabView extends TextView implements IndicatorView {
        private int mIndex;

        public TabView(Context context) {
            super(context, null, R.attr.tabIndicatorStyle);
        }

        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            // Re-measure if we went beyond our maximum size.
            if (mMaxTabWidth > 0 && getMeasuredWidth() > mMaxTabWidth) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(mMaxTabWidth, MeasureSpec.EXACTLY), heightMeasureSpec);
            }
        }

        @Override
        public void setIcon(int icon) {
            setCompoundDrawablesWithIntrinsicBounds(0, icon, 0, 0);
        }

        @Override
        public void setIndex(int index) {
            mIndex = index;
        }

        @Override
        public int getIndex() {
            return mIndex;
        }

        @Override
        public View getView() {
            return this;
        }

    }

    private class DefalutFactory implements IndicatorFactory {

        @Override
        public IndicatorView createIndicatorView(Context context, int position) {
            return new TabIndicator.TabView(context);
        }

    }

    public interface IndicatorFactory {
        IndicatorView createIndicatorView(Context context, int position);
    }

    public interface IndicatorView {

        public void setId(int id);

        public View getView();

        public void setOnClickListener(OnClickListener l);

        public void setFocusable(boolean b);

        public void setText(CharSequence text);

        public void setIcon(int icon);

        public void setIndex(int index);

        public int getIndex();
    }

    public interface IndicatorAdapter {
        int getCount();

        CharSequence getTitle(int position);

        int getIcon(int position);

        int getId(int position);
    }
}
