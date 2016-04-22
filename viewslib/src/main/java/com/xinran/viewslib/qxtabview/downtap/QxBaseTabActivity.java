package com.xinran.viewslib.qxtabview.downtap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinran.viewslib.R;

import java.util.ArrayList;

/**
 * Created by qixinh on 16/4/15.
 */
public abstract class QxBaseTabActivity extends FragmentActivity {
    public static final String TAG = QxBaseTabActivity.class.getSimpleName();
    private String currentTabTag;
    private String previousTabTag;
    protected final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
    protected final String ACTIVITY_TO_FULL_PATH = "to_activity_full_path";
    protected final String IS_STARTACTIVITY_FORRESUILT = "is_for_result";
    protected TabIndicator tabIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static class ActivityIndicator implements TabIndicator.IndicatorView {

        private final ImageView mImageView;
        private final TextView mTextView;
        private int mIndex;
        private final View mView;

        public ActivityIndicator(Context context, int layoutId, int position) {
            mView = LayoutInflater.from(context).inflate(layoutId, null);
            ((ViewGroup) mView).getChildAt(0).setBackgroundResource(0);
            mImageView = (ImageView) ((ViewGroup) ((ViewGroup) mView).getChildAt(0)).getChildAt(0);
            mTextView = (TextView) ((ViewGroup) ((ViewGroup) mView).getChildAt(0)).getChildAt(1);
        }

        @Override
        public void setText(CharSequence text) {
            mTextView.setText(text);
        }

        @Override
        public void setIcon(int icon) {
            mImageView.setImageResource(icon);
        }

        @Override
        public void setIndex(int index) {
            this.mIndex = index;
        }

        @Override
        public int getIndex() {
            return mIndex;
        }

        @Override
        public void setId(int id) {
            mView.setId(id);
        }

        @Override
        public View getView() {
            return mView;
        }

        @Override
        public void setOnClickListener(OnClickListener l) {
            mView.setOnClickListener(l);
        }

        @Override
        public void setFocusable(boolean b) {
            mView.setFocusable(b);
        }

    }
    public static final class TabInfo {

        public final String text;
        public final int icon;
        public final int id;
        public final String tag;
        public final Class<?> clss;
        public final Bundle args;

        TabInfo(String _text, int _icon, int _id, String _tag, Class<?> _class, Bundle _args) {
            text = _text;
            icon = _icon;
            id = _id;
            tag = _tag;
            clss = _class;
            args = _args;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (this.clss == null ? 0 : this.clss.hashCode());
            result = prime * result + (this.tag == null ? 0 : this.tag.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            TabInfo other = (TabInfo) obj;
            if (this.clss == null) {
                if (other.clss != null) {
                    return false;
                }
            } else if (!this.clss.equals(other.clss)) {
                return false;
            }
            if (this.tag == null) {
                if (other.tag != null) {
                    return false;
                }
            } else if (!this.tag.equals(other.tag)) {
                return false;
            }
            return true;
        }

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        tabIndicator = (TabIndicator) findViewById(R.id.tab_indicator);
        String tag = genDefaultTag();
        Bundle myBundle = getIntent().getExtras();
        if (myBundle != null && !TextUtils.isEmpty(myBundle.getString("tab"))) {
            tag = myBundle.getString("tab");
        }
        currentTabTag = tag;
        int currentTab = 0;
        for (int i = 0; i < mTabs.size(); i++) {
            if (mTabs.get(i).tag.equals(tag)) {
                currentTab = i;
                break;
            }
        }
        tabIndicator.setIndicatorFactory(indicatorFactory());
        tabIndicator.setAnimate(false);
        tabIndicator.setAdapter(new TabIndicator.IndicatorAdapter() {

            @Override
            public CharSequence getTitle(int position) {
                return mTabs.get(position).text;
            }

            @Override
            public int getIcon(int position) {
                return mTabs.get(position).icon;
            }

            @Override
            public int getCount() {
                return mTabs.size();
            }

            @Override
            public int getId(int position) {
                return mTabs.get(position).id;
            }
        }, currentTab);
        tabIndicator.setOnTabChangeListener(new TabIndicator.OnTabChangeListener() {

                                                @Override
                                                public void onTabChange(View tab, int index) {
                                                    if (isFinishing()) {
                                                        return;
                                                    }
                                                    FragmentManager fragmentManager = getSupportFragmentManager();
                                                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                                                    for (int i = 0; i < mTabs.size(); i++) {
                                                        Fragment fragment = fragmentManager.findFragmentByTag(mTabs.get(i).tag);
                                                        if (fragment != null) {
                                                            if (i == index) {
                                                                transaction.show(fragment);
                                                            } else {
                                                                transaction.hide(fragment);
                                                            }
                                                        } else {
                                                            if (i == index) {
                                                                try {
                                                                    Bundle bundle = mTabs.get(index).args;
                                                                    if (bundle == null)
                                                                        throw new NullPointerException(TAG + ":因为是跳转到新的Activity，所以收到的Bundle不能是null");
                                                                    Class<? extends Activity> cls = (Class<? extends Activity>) Class.forName(bundle.getString(ACTIVITY_TO_FULL_PATH));
                                                                    if (mStartActivityLisener == null) {
                                                                        Intent b = new Intent();
                                                                        b.putExtras(bundle);
                                                                        b.setClass(QxBaseTabActivity.this, cls);
                                                                        startActivity(b);
                                                                    } else {
                                                                        if (bundle.getBoolean(IS_STARTACTIVITY_FORRESUILT)) {
                                                                            mStartActivityLisener.startToActivityForReult(cls);
                                                                        } else {
                                                                            mStartActivityLisener.startToActivity(cls);
                                                                        }
                                                                    }
                                                                } catch (ClassNotFoundException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        }
                                                    }
                                                    try {
                                                        transaction.commitAllowingStateLoss();
                                                        previousTabTag = currentTabTag;
                                                        currentTabTag = mTabs.get(index).tag;
                                                        onTabChanged(index);
                                                    } catch (IllegalStateException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }

        );

        setCurrentTabByTag(currentTabTag);
    }

    protected abstract TabIndicator.IndicatorFactory indicatorFactory();

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", currentTabTag);
    }

    public void addTab(@NonNull String tag, @NonNull String text, @NonNull int icon, @NonNull int id, Class<? extends Fragment> clss, Bundle args) {

        TabInfo tabInfo = new TabInfo(text, icon, id, tag, clss, args);
        if (!mTabs.contains(tabInfo)) {
            mTabs.add(tabInfo);
        }
        if (clss == null) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag(tag) == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.qx_fragment, Fragment.instantiate(this, clss.getName(), args), tag);
            transaction.commitAllowingStateLoss();
        }
    }

    public String genDefaultTag() {
        return "";
    }

    /**
     * 页面切换监听
     *
     * @param position
     */
    protected void onTabChanged(int position) {
        //打点用
    }

    public void setCurrentTab(final int position) {
        new Handler().post(new Runnable() {

            @Override
            public void run() {
                tabIndicator.setCurrentItem(position);
            }
        });
    }

    public void setCurrentTabByTag(String tag) {
        int i;
        for (i = 0; i < mTabs.size(); i++) {
            if (mTabs.get(i).tag.equals(tag)) {

                setCurrentTab(i);
                return;
            }
        }
    }

    public String getPreviousTabTag() {
        return previousTabTag;
    }

    protected IStartToActivityLisener mStartActivityLisener;

    protected void setIStartActivityLisener(IStartToActivityLisener startActivityLisener) {
        this.mStartActivityLisener = startActivityLisener;
    }

    public interface IStartToActivityLisener {

        public abstract void startToActivity(Class<? extends Activity> cls);

        public abstract void startToActivityForReult(Class<? extends Activity> cls);
    }
}
