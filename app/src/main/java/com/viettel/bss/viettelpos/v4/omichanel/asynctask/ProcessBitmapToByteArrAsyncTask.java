package com.viettel.bss.viettelpos.v4.omichanel.asynctask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsyncTaskCommonSupper;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import java.io.ByteArrayOutputStream;

/**
 * Created by Toancx on 11/14/2017.
 */

public class ProcessBitmapToByteArrAsyncTask extends AsyncTaskCommonSupper<Bitmap, Void, byte[]> {

    public ProcessBitmapToByteArrAsyncTask(Activity context,
                                           OnPostExecuteListener<byte[]> listener,
                                           View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);

        this.progress.setMessage("Đang xử lý ảnh...");
    }

    @Override
    protected byte[] doInBackground(Bitmap... params) {
        return getByteArr(params[0]);
    }

    private byte[] getByteArr(Bitmap bitmap) {
        if (CommonActivity.isNullOrEmpty(bitmap)) {
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}