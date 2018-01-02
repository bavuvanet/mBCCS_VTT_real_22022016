package com.viettel.bss.viettelpos.v4.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.listener.OnClickListener;
import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by toancx on 5/24/2017.
 */
public class ImageUtils {
    public static void loadImageHorizontal(Context mContext, LayoutInflater inflater, LinearLayout thumbnails, ArrayList<String> lstData) {
        for (int i = 0; i < lstData.size(); i++) {
            View view = inflater.inflate(R.layout.pager_gallery_item, thumbnails, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            Log.d("loadImageHorizontal", "" + thumbnails.getHeight());
            if(thumbnails.getHeight() == 0 && thumbnails.getWidth() == 0){
                int width = View.MeasureSpec.getSize((int) mContext.getResources().getDimension(R.dimen.height_stock_model));
                Log.d("loadImageHorizontal", "width = " + width);
                Picasso.with(mContext)
                        .load(new File(lstData.get(i)))
                        .centerCrop()
                        .resize(width, width)
                        .into(imageView);
            } else {
                Picasso.with(mContext)
                        .load(new File(lstData.get(i)))
                        .centerCrop()
                        .resize(thumbnails.getHeight(), thumbnails.getHeight())
                        .into(imageView);
            }
            thumbnails.addView(view);
        }
    }

    public static void loadImageHorizontal(Context mContext, LayoutInflater inflater,
                                           LinearLayout thumbnails, final ArrayList<String> lstData,
                                           final OnClickListener onItemClickListener) {
        for (int i = 0; i < lstData.size(); i++) {
            final View view = inflater.inflate(R.layout.pager_gallery_item, thumbnails, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            Log.d("loadImageHorizontal", "" + thumbnails.getHeight());
            if(thumbnails.getHeight() == 0 && thumbnails.getWidth() == 0){
                int width = View.MeasureSpec.getSize((int) mContext.getResources().getDimension(R.dimen.height_stock_model));
                Log.d("loadImageHorizontal", "width = " + width);
                Picasso.with(mContext)
                        .load(new File(lstData.get(i)))
                        .centerCrop()
                        .resize(width, width)
                        .into(imageView);
            } else {
                Picasso.with(mContext)
                        .load(new File(lstData.get(i)))
                        .centerCrop()
                        .resize(thumbnails.getHeight(), thumbnails.getHeight())
                        .into(imageView);
            }

            final int position = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.onClick(view, lstData.get(position));
                    }
                }
            });
            thumbnails.addView(view);
        }
    }

    public static Bitmap writeTextOnDrawable(Context context, Bitmap bitmap, String text) throws Exception {
        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTypeface(tf);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(convertToPixels(context, 20));

        Rect textRect = new Rect();
        paint.getTextBounds(text, 0, text.length(), textRect);

        Canvas canvas = new Canvas(bitmap);

        //If the text is bigger than the canvas , reduce the font size
        //the padding on either sides is considered as 4, so as to appropriately fit in the text
        //Scaling needs to be used for different dpi's
        if(textRect.width() >= (canvas.getWidth() - 4))
            paint.setTextSize(convertToPixels(context, 7));

        //Calculate the positions
        int xPos = (canvas.getWidth() / 2) - 2;     //-2 is for regulating the x position offset

        //"- ((paint.descent() + paint.ascent()) / 2)" is the distance from the baseline to the center.
        int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)) ;

        canvas.drawText(text, xPos, yPos, paint);

        return bitmap;
    }

    private static int convertToPixels(Context context, int nDP)
    {
        final float conversionScale = context.getResources().getDisplayMetrics().density;

        return (int) ((nDP * conversionScale) + 0.5f) ;

    }


}
