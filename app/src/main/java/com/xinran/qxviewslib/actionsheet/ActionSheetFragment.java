package com.xinran.qxviewslib.actionsheet;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xinran.qxviewslib.R;

import java.lang.reflect.Method;

/**
 * Created by qixinh on 16/6/23.
 * 来自：http://www.jianshu.com/p/1b548491bd5a
 */
public class ActionSheetFragment extends Fragment {
    //是否已经关闭
    private boolean isDismiss = true;

    private View decorView;
    //添加进入的view
    private View realView;
    //添加进入的第一个view
    private View pop_child_layout;

    //产生一个包含Bundle数据的Fragment，这个方法调用完成之后才会调用Fragment的真正的声明周期，所以以后声明周期的方法可以直接使用Bundle里的数据
    public static ActionSheetFragment newListOne(String title, String[] items) {
        ActionSheetFragment actionSheetFragment = new ActionSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constans.TITLE, title);
        bundle.putStringArray(Constans.ITEMS, items);
        bundle.putInt(Constans.TYPE, 1);
        actionSheetFragment.setArguments(bundle);
        return actionSheetFragment;
    }

    //产生一个包含Bundle数据的Fragment，这个方法调用完成之后才会调用Fragment的真正的声明周期，所以以后声明周期的方法可以直接使用Bundle里的数据
    public static ActionSheetFragment newGridInstance(String title, String[] items, int[] images) {
        ActionSheetFragment fragment = new ActionSheetFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constans.TITLE, title);
        bundle.putStringArray(Constans.ITEMS, items);
        bundle.putIntArray(Constans.IMAGES, images);
        bundle.putInt(Constans.TYPE, 2);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static ActionSheetFragment.Builder build(FragmentManager fragmentManager) {
        ActionSheetFragment.Builder builder = new ActionSheetFragment.Builder(fragmentManager);
        return builder;
    }

    public static class Builder {

        FragmentManager fragmentManager;

        //默认tag，用来校验fragment是否存在
        String tag = "ActionSheetFragment";
        //ActionSheet的Title
        String title = "title";
        //ActionSheet上ListView或者GridLayout上相关文字、图片
        String[] items;
        int[] images;
        //ActionSheet点击后的回调
        OnItemClickListener onItemClickListener;
        //点击取消之后的回调
        OnCancelListener onCancelListener;
        //提供类型，用以区分ListView或者GridLayout
        CHOICE choice;

        public enum CHOICE {
            ITEM, GRID
        }

        public Builder(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
        }

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setItems(String[] items) {
            this.items = items;
            return this;
        }

        public Builder setImages(int[] images) {
            this.images = images;
            return this;
        }

        public Builder setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            this.onCancelListener = onCancelListener;
            return this;
        }

        public Builder setChoice(CHOICE choice) {
            this.choice = choice;
            return this;
        }

        public void showPop() {
            ActionSheetFragment fragment;
            if (choice == CHOICE.ITEM) {
                fragment = ActionSheetFragment.newListOne(title, items);
                fragment.setOnItemClickListener(onItemClickListener);
                fragment.setOnCancelListener(onCancelListener);
                fragment.showActionSheet(fragmentManager, tag);
            }
            if (choice == CHOICE.GRID) {
                fragment = ActionSheetFragment.newGridInstance(title, items, images);
                fragment.setOnItemClickListener(onItemClickListener);
                fragment.showActionSheet(fragmentManager, tag);
            }
        }

    }

    /**
     *
     * 以上都是调用前的准备数据工作，有时activity也可以这样啊，好处就是可以启动生命周期前可以准备点数据
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (manager.isActive()) {
            View focusView = getActivity().getCurrentFocus();
            manager.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        }
        realView = inflater.inflate(R.layout.view_actionsheet, container, false);
        init(realView);
        decorView = getActivity().getWindow().getDecorView();//直接操作DectorView 牛逼就是在这里
        ((ViewGroup) decorView).addView(realView);
        startAnnimation();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void startAnnimation() {
        pop_child_layout.post(new Runnable() {
            @Override
            public void run() {
                final int moveHeight = pop_child_layout.getMeasuredHeight();
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                valueAnimator.setDuration(500);
                valueAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        pop_child_layout.setVisibility(View.VISIBLE);
                    }
                });
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
                        realView.setBackgroundColor((Integer) argbEvaluator.evaluate(animation.getAnimatedFraction(), Color.parseColor("#00000000"), Color.parseColor("#70000000")));
                        //当底部存在导航栏并且decorView获取的高度不包含底部状态栏的时候，需要去掉这个高度差
                        if (getNavBarHeight(pop_child_layout.getContext()) > 0 && decorView.getMeasuredHeight() != getScreenHeight(pop_child_layout.getContext())) {
                            pop_child_layout.setTranslationY((moveHeight + getNavBarHeight(pop_child_layout.getContext())) * (1 - animation.getAnimatedFraction()) - getNavBarHeight(pop_child_layout.getContext()));
                        } else {
                            pop_child_layout.setTranslationY(moveHeight * (1 - animation.getAnimatedFraction()));
                        }
                    }
                });
                valueAnimator.start();
            }
        });
    }

    private void init(View realView) {
        pop_child_layout = $(realView, R.id.pop_child_layout);
        pop_child_layout.setVisibility(View.INVISIBLE);
        realView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissActionSheet();
            }
        });
        TextView pop_title = $(realView, R.id.pop_title);
        pop_title.setText(getArguments().getString(Constans.TITLE));
        int type = getArguments().getInt(Constans.TYPE);
        if (type == 1) {
            TextView pop_cancel = $(realView, R.id.pop_cancel);
            pop_cancel.setVisibility(View.VISIBLE);
            pop_cancel.setText("取消");
            pop_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCancelListener != null) {
                        onCancelListener.onCancelClick();
                    }
                    dismissActionSheet();
                }
            });
            ListView pop_listview = $(realView, R.id.pop_listview);
            //得到其params就可以动态改变其宽高等属性了
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) pop_listview.getLayoutParams();
            int maxHeight = getScreenHeight(getActivity()) - getStatusBarHeight(getActivity()) - dp2px(getActivity(), (45 + 10 + 10)) - dp2px(getActivity(), (45 + 0.5f));
            int dateHeight = dp2px(getActivity(), (45 + 0.5f) * getArguments().getStringArray("items").length);
            if (maxHeight < dateHeight) {
                params.height = maxHeight;
            } else {
                params.height = dateHeight;
            }
            ActionSheetAdapter adapter = new ActionSheetAdapter(getActivity(), getArguments().getStringArray("items"));
            pop_listview.setAdapter(adapter);
            pop_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position);
                        dismissActionSheet();
                    }
                }
            });

        } else if (type == 2) {
            GridLayout pop_grid = $(realView, R.id.pop_grid);
            pop_grid.setVisibility(View.VISIBLE);
            int width = (getScreenWidth(getActivity()) - dp2px(getActivity(), 20)) / 4;
            for (int i = 0; i < getArguments().getStringArray("items").length; i++) {
                final int i_ = i;
                View viewChild = LayoutInflater.from(getActivity()).inflate(R.layout.adapter_share, null, false);
                LinearLayout adapter_share_layout = (LinearLayout) viewChild.findViewById(R.id.adapter_share_layout);
                adapter_share_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(i_);
                        }
                        dismissActionSheet();
                    }
                });
                ImageView adapter_share_image = (ImageView) viewChild.findViewById(R.id.adapter_share_image);
                TextView adapter_share_text = (TextView) viewChild.findViewById(R.id.adapter_share_text);
                adapter_share_image.setImageResource(getArguments().getIntArray("images")[i]);
                adapter_share_text.setText(getArguments().getStringArray("items")[i]);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = width;
                params.height = dp2px(getActivity(), 120);
                params.columnSpec = GridLayout.spec(i % 4);
                params.rowSpec = GridLayout.spec(i / 4);
                pop_grid.addView(viewChild, params);
            }
        }
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 得到屏幕高度
     *
     * @return 单位:px
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 得到屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    private <V extends View> V $(View v, @IdRes int id) {
        return (V) v.findViewById(id);
    }

    private void dismissActionSheet() {
        if (isDismiss) {
            return;
        }
        isDismiss = true;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                getChildFragmentManager().popBackStack();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.remove(ActionSheetFragment.this);
                transaction.commitAllowingStateLoss();
            }
        });
    }

    private void showActionSheet(final FragmentManager manager, final String tag) {
        if (manager.isDestroyed() || !isDismiss) {
            return;
        }
        isDismiss = false;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(ActionSheetFragment.this, tag);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        stopAnnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((ViewGroup) decorView).removeView(realView);
            }
        }, 500);
    }

    private void stopAnnimation() {
        pop_child_layout.post(new Runnable() {
            @Override
            public void run() {
                final int moveHeight = pop_child_layout.getMeasuredHeight();
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                valueAnimator.setDuration(500);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
                        realView.setBackgroundColor((Integer) argbEvaluator.evaluate(animation.getAnimatedFraction(), Color.parseColor("#70000000"), Color.parseColor("#00000000")));
                        if (getNavBarHeight(pop_child_layout.getContext()) > 0 && decorView.getMeasuredHeight() != getScreenHeight(pop_child_layout.getContext())) {
                            pop_child_layout.setTranslationY((moveHeight + getNavBarHeight(pop_child_layout.getContext())) * animation.getAnimatedFraction() - getNavBarHeight(pop_child_layout.getContext()));
                        } else {
                            pop_child_layout.setTranslationY(moveHeight * animation.getAnimatedFraction());
                        }
                    }
                });
                valueAnimator.start();
            }
        });

    }

    /**
     * 获取底部导航栏高度
     *
     * @param context
     * @return
     */
    public static int getNavBarHeight(Context context) {
        int navigationBarHeight = 0;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && checkDeviceHasNavigationBar(context)) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }

        return navigationBarHeight;
    }

    private static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasNavigationBar;
    }


    private OnItemClickListener onItemClickListener;
    private OnCancelListener onCancelListener;

    public interface OnCancelListener {
        void onCancelClick();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }
}
