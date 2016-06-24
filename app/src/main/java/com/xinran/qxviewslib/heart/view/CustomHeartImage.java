package com.xinran.qxviewslib.heart.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.xinran.qxviewslib.R;

/**
 * Created by qixinh on 16/6/24.
 */
public class CustomHeartImage extends ImageView {
    private Bitmap bitmap;
    public CustomHeartImage(Context context) {
        super(context);
        init();
    }
    public CustomHeartImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomHeartImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.heart);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(bitmap.getWidth(),bitmap.getHeight());
    }
    public void changeBgColor(int color){
        setImageBitmap(creatColorBitmap(color));
    }

    private Bitmap creatColorBitmap(int color) {
        int with=bitmap.getWidth();
        int height=bitmap.getHeight();
        Bitmap newBitmap=Bitmap.createBitmap(with, height, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(newBitmap);
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        canvas.drawBitmap(bitmap, 0, 0, paint);//吸收这个old的bitmap的营养给newBitmap，就是把老的bitmap画在新的bitmap上
        canvas.drawColor(color, PorterDuff.Mode.SRC_ATOP);//有十六种Porter-Duff效果图:http://www.cnblogs.com/jacktu/archive/2012/01/02/2310326.html
        canvas.setBitmap(null);
        return newBitmap;
    }
}
