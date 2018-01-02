package com.viettel.bss.viettelpos.v4.ui.image.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by Gil on 07/06/2014.
 */
public class ImageInternalFetcher extends ImageResizer {

    private Context mContext;

    public ImageInternalFetcher(Context context, int imageWidth, int imageHeight) {
        super(context, imageWidth, imageHeight);
        init(context);
    }

    public ImageInternalFetcher(Context context, int imageSize) {
        super(context, imageSize);
        init(context);
    }

    private void init(Context context){
        mContext = context;
    }



    private Bitmap processBitmap(Uri uri){
        return decodeSampledBitmapFromFile(uri.getPath(), mImageWidth, mImageHeight, getImageCache());
    }

    @Override
    protected Bitmap processBitmap(Object data) {
        return processBitmap((Uri)data);
    }
}
