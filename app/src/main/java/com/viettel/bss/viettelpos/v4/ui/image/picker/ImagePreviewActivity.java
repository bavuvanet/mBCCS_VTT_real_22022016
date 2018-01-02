package com.viettel.bss.viettelpos.v4.ui.image.picker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define.CHANNEL_UPDATE_IMAGE;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.ui.image.utils.ImageInternalFetcher;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImagePreviewActivity extends Activity {

    private static final int INTENT_REQUEST_GET_N_IMAGES = 14;
    private Context mContext;
    private ViewGroup mSelectedImagesContainer;
    private Uri[] uris;
    private int imageId = -1;
    private static int LIMIT_MAX = 4;
    private ArrayList<FileObj> fileObjs = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.pp__preview_image);
        mSelectedImagesContainer = (ViewGroup) findViewById(R.id.selected_photos_container);
        mContext = ImagePreviewActivity.this;

        if (bundle == null) {
            bundle = getIntent().getExtras();
        }
        if (bundle != null && bundle.containsKey("imageId")) {
            imageId = bundle.getInt("imageId", -1);
        }
        Log.d(Constant.TAG, "onCreate() imageId: " + imageId);

        if (bundle != null && bundle.containsKey("fileObjs")) {
            fileObjs = (ArrayList<FileObj>) bundle.getSerializable("fileObjs");
        }
        if (bundle != null && bundle.containsKey("limitImage")) {
            LIMIT_MAX = bundle.getInt("limitImage");
        } else {
            LIMIT_MAX = 4;
        }
        Log.d(Constant.TAG, "onCreate() fileObjs: " + fileObjs.size());

        uris = new Uri[fileObjs.size()];
        for (int i = 0; i < fileObjs.size(); i++) {
            FileObj fileObj = fileObjs.get(i);
            Uri uri = Uri.parse(fileObj.getFile().getPath());
            uris[i] = uri;
        }

        Button getNImages = (Button) findViewById(R.id.get_n_images);
        getNImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImagePickerActivity.class);
                intent.putExtra("imageId", imageId);
                intent.putExtra(ImagePickerActivity.EXTRA_IMAGE_URIS, uris);
                Config config = new Config.Builder()
                        .setSelectionLimit(LIMIT_MAX)    // set photo selection limit. Default unlimited selection.
                        .build();
                ImagePickerActivity.setConfig(config);
                startActivityForResult(intent, INTENT_REQUEST_GET_N_IMAGES);
            }
        });

        Button pp__btn_ok = (Button) findViewById(R.id.pp__btn_ok);
        pp__btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.e("XXXXXXXXXXXXX", "YYYYYYYYYYYYYXXXXXXXXXXXX");
                Intent intent = new Intent();
                intent.putExtra("imageId", imageId);
                intent.putExtra(ImagePickerActivity.EXTRA_IMAGE_URIS, uris);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        if (uris != null && uris.length >= LIMIT_MAX) {
            getNImages.setText(getResources().getString(R.string.pick_N_images));
        } else {
            getNImages.setText(getResources().getString(R.string.pick_more_images));
        }

        showMedia();
    }

    @Override
    protected void onActivityResult(int requestCode, int resuleCode, Intent data) {
        super.onActivityResult(requestCode, resuleCode, data);

        if (resuleCode == Activity.RESULT_OK) {
            if (requestCode == INTENT_REQUEST_GET_N_IMAGES) {
                Parcelable[] parcelableUris = data.getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
                if (parcelableUris == null) {
                    return;
                }
                // Java doesn't allow array casting, this is a little hack
                uris = new Uri[parcelableUris.length];
                System.arraycopy(parcelableUris, 0, uris, 0, parcelableUris.length);

                Intent intent = new Intent();
                intent.putExtra("imageId", imageId);
                intent.putExtra(ImagePickerActivity.EXTRA_IMAGE_URIS, uris);
                setResult(Activity.RESULT_OK, intent);

                finish();
            }
        } else if (resuleCode == Activity.RESULT_CANCELED) {
            if (requestCode == INTENT_REQUEST_GET_N_IMAGES) {
                finish();
            }
        }
    }

    private void showMedia() {
        // Remove all views before adding the new ones.

        if (uris.length > 0) {
            mSelectedImagesContainer.setVisibility(View.VISIBLE);
        }

        for (Uri uri : uris) {
            Log.i(Constant.TAG, " uri: " + uri);
            @SuppressLint("InflateParams") View imageHolder = LayoutInflater.from(this).inflate(R.layout.media_layout, null);
            ImageView thumbnail = (ImageView) imageHolder.findViewById(R.id.media_image);

            if (!uri.toString().contains("content://")) {
                // probably a relative uri
                uri = Uri.fromFile(new File(uri.toString()));
            }
            Bitmap myBitmap = decodeSampledBitmapFromResource(uri.getPath(), 100, 100);
            thumbnail.setImageBitmap(myBitmap);
            mSelectedImagesContainer.addView(imageHolder);
            int wdpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    600, getResources().getDisplayMetrics());
            int htpx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    400, getResources().getDisplayMetrics());
            thumbnail.setLayoutParams(new FrameLayout.LayoutParams(wdpx, htpx));
        }
    }

    public static void pickImage(Activity activity, HashMap<String, ArrayList<FileObj>> hashmapFileObj, int imageId) {
        Log.d(Constant.TAG, "pickImage() imageId: " + imageId);

        ArrayList<FileObj> fileObjs;
        FileObj fileObj;
        if (CommonActivity.isNullOrEmpty(hashmapFileObj.get(String.valueOf(imageId)))) {
            fileObjs = new ArrayList<>();
        } else {
            fileObjs = new ArrayList<>(hashmapFileObj.get(String.valueOf(imageId)));
        }

        // remove cac file ko phai file anh
        if (!CommonActivity.isNullOrEmpty(fileObjs)) {
            for (int index = fileObjs.size() - 1; index >= 0; index--) {
                fileObj = fileObjs.get(index);
                if (!Constant.IMG_EXT_JPG.equals(fileObj.getFileExtention().toLowerCase())
                        && !Constant.IMG_EXT_PNG.equals(fileObjs.get(index).getFileExtention())) {
                    fileObjs.remove(fileObj);
                }
            }
        }

        if (CommonActivity.isNullOrEmpty(fileObjs)) {
            // show view choose image
            Config config = new Config.Builder().setSelectionLimit(4).build();
            Intent intent = new Intent(activity, ImagePickerActivity.class);
            intent.putExtra("imageId", imageId);
            ImagePickerActivity.setConfig(config);
            activity.startActivityForResult(intent, CHANNEL_UPDATE_IMAGE.PICK_IMAGE);
        } else {
            // show preview image
            Intent intent = new Intent(activity, ImagePreviewActivity.class);
            intent.putExtra("imageId", imageId);
            intent.putExtra("fileObjs", fileObjs);
            activity.startActivityForResult(intent, CHANNEL_UPDATE_IMAGE.PICK_IMAGE);
        }
    }

    public static void pickImage(Activity activity, HashMap<String, ArrayList<FileObj>> hashmapFileObj, String imageId) {
        Log.d(Constant.TAG, "pickImage() imageId: " + imageId);

        if (hashmapFileObj.containsKey(String.valueOf(imageId))) {
            ArrayList<FileObj> fileObjs = hashmapFileObj.get(String.valueOf(imageId));

            Intent intent = new Intent(activity, ImagePreviewActivity.class);
            intent.putExtra("imageId", imageId);
            intent.putExtra("fileObjs", fileObjs);

            activity.startActivityForResult(intent, CHANNEL_UPDATE_IMAGE.PICK_IMAGE);
        } else {
            Config config = new Config.Builder().setSelectionLimit(4).build();

            Intent intent = new Intent(activity, ImagePickerActivity.class);
            intent.putExtra("imageId", imageId);

            ImagePickerActivity.setConfig(config);
            activity.startActivityForResult(intent, CHANNEL_UPDATE_IMAGE.PICK_IMAGE);
        }
    }

    public static void pickImage(Activity activity, List<File> lstFile,
                                 String imageId) {
        Log.d(Constant.TAG, "pickImage() imageId: " + imageId);

        if (!CommonActivity.isNullOrEmpty(lstFile)) {
            ArrayList<FileObj> fileObjs = new ArrayList<FileObj>();
            for (File file : lstFile) {
                FileObj obj = new FileObj();
                obj.setFile(file);
                fileObjs.add(obj);
            }
            Intent intent = new Intent(activity, ImagePreviewActivity.class);
            intent.putExtra("imageId", imageId);
            intent.putExtra("fileObjs", fileObjs);

            activity.startActivityForResult(intent,
                    CHANNEL_UPDATE_IMAGE.PICK_IMAGE);
        } else {
            Config config = new Config.Builder().setSelectionLimit(4).build();

            Intent intent = new Intent(activity, ImagePickerActivity.class);
            intent.putExtra("imageId", Integer.parseInt(imageId));

            ImagePickerActivity.setConfig(config);
            activity.startActivityForResult(intent,
                    CHANNEL_UPDATE_IMAGE.PICK_IMAGE);
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
