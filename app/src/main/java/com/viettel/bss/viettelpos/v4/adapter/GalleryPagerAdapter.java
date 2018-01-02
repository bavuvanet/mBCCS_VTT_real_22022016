package com.viettel.bss.viettelpos.v4.adapter;

import android.content.Context;
import android.support.v4.view.*;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by toancx on 5/23/2017.
 */
public class GalleryPagerAdapter extends android.support.v4.view.PagerAdapter {
    Context mContext;
    LayoutInflater inflater;
    ArrayList<String> lstData = new ArrayList<>();
    LinearLayout thumbnails;

    public GalleryPagerAdapter(Context mContext, ArrayList<String> lstData, LinearLayout thumbnails) {
        this.mContext = mContext;
        this.lstData = lstData;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.thumbnails = thumbnails;
    }

    @Override
    public int getCount() {
        return lstData == null ? 0 : lstData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = inflater.inflate(R.layout.pager_gallery_item, container, false);
        container.addView(itemView);

        // Get the border size to show around each image
        int borderSize = thumbnails.getPaddingTop();

        // Get the size of the actual thumbnail image
        int thumbnailSize = thumbnails.getHeight();

        // Set the thumbnail layout parameters. Adjust as required
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(thumbnailSize, thumbnailSize);
        params.setMargins(0, 0, borderSize, 0);

        final ImageView thumbView = new ImageView(mContext);
        thumbView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        thumbView.setLayoutParams(params);
        thumbView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(Constant.TAG, "Thumbnail clicked");
            }
        });
        thumbnails.addView(thumbView);

        Picasso.with(mContext)
                .load(new File(lstData.get(position)))
                .centerCrop()
//                .resize(thumbnailSize, thumbnailSize)
                .into(thumbView);
        return itemView;
    }
}
