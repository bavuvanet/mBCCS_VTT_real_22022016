package com.viettel.bss.viettelpos.v4.ui.image.model;

import com.viettel.bss.viettelpos.v4.commons.Constant;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by Gil on 04/03/2014.
 */
public class Image implements Parcelable {

    public final Uri mUri;
    private final int mOrientation;

    public Image(Uri uri, int orientation) {
        mUri = uri;
        mOrientation = orientation;
    }

    @Override
    public int describeContents() {
    	
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mUri, 0);
        dest.writeInt(this.mOrientation);
    }

    private Image(Parcel in) {
        this.mUri = in.readParcelable(Uri.class.getClassLoader());
        this.mOrientation = in.readInt();
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
    
    public static Image getImageFromContentUri(Activity activity, Uri contentUri) {
		String[] cols = { MediaStore.Images.Media.DATA, MediaStore.Images.ImageColumns.ORIENTATION };

		// can post image
		Cursor cursor = activity.getContentResolver().query(contentUri, cols, null, null, null);

		Uri uri = null;
		int orientation = -1;

		try {
			if (cursor.moveToFirst()) {
				uri = Uri.parse(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
				int orient = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION));
				orientation = orient;
				
				Log.d(Constant.TAG, "getImageFromContentUri: " + contentUri + " uri: " + uri + " orientation:" + orient);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
		}

		return new Image(uri, orientation);
	}
}
