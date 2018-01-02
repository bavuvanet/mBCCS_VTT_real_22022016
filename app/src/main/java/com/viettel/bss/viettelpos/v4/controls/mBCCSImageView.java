package com.viettel.bss.viettelpos.v4.controls;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by toancx on 5/24/2017.
 */
public class mBCCSImageView extends ImageView{
    private int widthExt = 0;

    public mBCCSImageView(Context context) {
        super(context);
    }

    public mBCCSImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public mBCCSImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public mBCCSImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();
        if(d != null){
            widthExt = MeasureSpec.getSize(widthMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public int getWidthExt() {
        return widthExt;
    }

    public void setWidthExt(int widthExt) {
        this.widthExt = widthExt;
    }
}
