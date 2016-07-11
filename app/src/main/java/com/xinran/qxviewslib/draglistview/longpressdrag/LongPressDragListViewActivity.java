package com.xinran.qxviewslib.draglistview.longpressdrag;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xinran.qxviewslib.BaseActivity;
import com.xinran.qxviewslib.R;

import java.util.ArrayList;
import java.util.List;

import com.xinran.qxviewslib.draglistview.longpressdrag.DragUITools.OnDragListener;

/**
 * Created by qixinh on 16/7/7.
 */
public class LongPressDragListViewActivity extends BaseActivity {
    String TAG = LongPressDragListViewActivity.class.getSimpleName();
    private ListView list;
    private List<String> data;


    /**
     * 长按拖动的时候，用来显示被拖拽的项目的图片
     */
    private ImageView bar;
    /**
     * 拖动监听器,在拖动的时候监听y并移动bar的位置
     */
    private DragOnTouchListenerHolder dragOnTouchListener;
    /**
     * 最后一次移动到的位置，松开手以后将dragPosition位置的项移动到此位置
     */
    private int lastDropPosition = -1;
    /**
     * 长按需要排序的位置
     */
    private int dragPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_longpressdrag_listview);
        list = (ListView) findViewById(R.id.edit_photo_filter_manage_list_view);
        bar = (ImageView) findViewById(R.id.bar);
        bar.setDrawingCacheEnabled(true);

        dragOnTouchListener = DragUITools.setOnDragListener(list, new OnDragListener() {
            int lastPos = -1;

            @Override
            public void onDrag(View view, MotionEvent event, DragEvent action,
                               int touchSlop) {
                if (action == DragEvent.ACTION_DRAG) {
                    //Log.i(TAG, "move="+action);
                    int y = (int) event.getY();
                    Point parentP = DragUITools.getPos((View) list.getParent());
                    int touchTop = y - parentP.y;
                    int top = touchTop - bar.getMeasuredHeight();
                    bar.layout(0, top, bar.getMeasuredWidth(), top + bar.getMeasuredHeight());
                    //获取图片当前位置的x
                    int targetX = parentP.x + bar.getMeasuredWidth() / 2;
                    int targetY = top + bar.getMeasuredHeight() / 2;
                    int tarPos = list.pointToPosition(targetX, targetY);
                    if (tarPos >= 0)
                        if (lastPos != tarPos) {
                            //Log.i(TAG, "当前pos="+tarPos);
                            View tarView = DragUITools.getViewByPosition(tarPos, list);
                            lastDropPosition = tarPos;
                            if (tarPos > lastPos) {
                                //向后滚动一个item
                                tarView.removeCallbacks(scrollRunnable);
                                scrollRunnable.setDistance(tarView.getMeasuredHeight());
                                tarView.postDelayed(scrollRunnable, 700);
                            } else {
                                //向前滚动一个item
                                tarView.removeCallbacks(scrollRunnable);
                                scrollRunnable.setDistance(-tarView.getMeasuredHeight());
                                tarView.postDelayed(scrollRunnable, 700);
                            }
                            lastPos = tarPos;
                        }
                }
                if (action == DragEvent.ACTION_END) {
                    //Log.i(TAG, "拖动结束 ！！！");
                    if (lastDropPosition != -1) {
                        Log.i(TAG, "要移动的位置=" + dragPosition + " 目标位置:" + lastDropPosition);
                        String dataToRemove = data.remove(dragPosition);
                        data.add(lastDropPosition, dataToRemove);
                        ((ArrayAdapter) list.getAdapter()).notifyDataSetChanged();
                    }
                    //恢复
                    dragPosition = -1;
                    lastDropPosition = -1;
                    bar.setVisibility(View.INVISIBLE);
                    dragOnTouchListener.dragOnTouchListener.setEnable(false);
                }
            }
        }, false, false);

        data = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            data.add("呵呵" + i);
        }
        list.setAdapter(new ArrayAdapter<String>(this, R.layout.listiem, data));


        //长按ListView开始拖动
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int position, long arg3) {
                View itemView = DragUITools.getViewByPosition(position, list);
                lastDropPosition = position;
                dragPosition = position;
                itemView.setDrawingCacheEnabled(true);
                Bitmap b = itemView.getDrawingCache();
                bar.getLayoutParams().width = b.getWidth();
                bar.getLayoutParams().height = b.getHeight();
                //透明度
                bar.setAlpha(175);
                bar.setImageBitmap(b);
                bar.post(new Runnable() {
                    @Override
                    public void run() {
                        Point p = DragUITools.getPos(DragUITools.getViewByPosition(lastDropPosition, list));
                        Point pParent = DragUITools.getPos((View) list.getParent());
                        int top = p.y - pParent.y - bar.getLayoutParams().height / 2;
                        bar.layout(0, top, bar.getMeasuredWidth(), top + bar.getMeasuredHeight());
                        bar.setVisibility(View.VISIBLE);
                        Log.i(TAG, "长按postion=" + lastDropPosition);
                        dragOnTouchListener.dragOnTouchListener.setEnable(true);
                    }
                });
                return true;
            }

        });
    }

    private ScrollRunnable scrollRunnable = new ScrollRunnable();

    private class ScrollRunnable implements Runnable {
        private int distance = 0;

        public void setDistance(int distance) {
            this.distance = distance;
        }

        @Override
        public void run() {
            list.smoothScrollBy(distance, 300);
        }
    }
    private class MyListViewAdapter extends BaseAdapter {

        private LayoutInflater li;

        public MyListViewAdapter(Context context) {
            li = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = li.inflate(R.layout.drag_list_view_item, null);
            TextView tv = (TextView) view.findViewById(R.id.edit_photo_filter_manage_filtername);
            tv.setGravity(Gravity.CENTER);
            tv.setText(data.get(position));
            tv.setTextSize(30);
            return view;
        }

    }
}
