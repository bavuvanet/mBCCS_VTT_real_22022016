package com.viettel.gem.screen.collectinfo.custom.picture;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.gem.base.view.CustomView;

import java.io.File;

import butterknife.BindView;

/**
 * Created by root on 23/11/2017.
 */

public class PictureItemView extends CustomView {

    @BindView(R.id.picture_iv)
    ImageView mPictureIv;

    @BindView(R.id.remove_iv)
    ImageView mRemoveIv;

    String path = "";

    private Context context;

    private Callback mCallback;

    public PictureItemView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.cs_survey_picture_item_view;

    }

    @Override
    public boolean validateView() {
        return null != path && !path.trim().isEmpty();
    }

    public void build(String url, Callback callback) {
        setPath(url);
//        Glide.with(context).load(url).fitCenter().into(mPictureIv);
        Picasso.with(context).load(new File(url)).into(mPictureIv);
        this.mCallback = callback;
        build();
    }

    private void build() {
        mPictureIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onPictureClick(path);
            }
        });
        mRemoveIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onRemoveClick(PictureItemView.this);
            }
        });
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public interface Callback {
        void onPictureClick(String url);

        void onRemoveClick(View view);
    }
}
