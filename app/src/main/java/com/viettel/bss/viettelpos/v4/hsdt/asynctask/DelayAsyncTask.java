package com.viettel.bss.viettelpos.v4.hsdt.asynctask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsyncTaskCommonSupper;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;

import java.io.ByteArrayOutputStream;

/**
 * Created by Toancx on 11/15/2017.
 */

public class DelayAsyncTask extends AsyncTaskCommonSupper<Integer, Void, String> {

    public DelayAsyncTask(Activity context,
                          OnPostExecuteListener<String> listener,
                          View.OnClickListener moveLogInAct) {
        super(context, listener, moveLogInAct);

        this.progress.setMessage("Đang xử lý ảnh...");
    }

    @Override
    protected String doInBackground(Integer... params) {
        return delayView(params[0]);
    }

    private String delayView(Integer time) {
        int timeValue = 1000;
        if (!CommonActivity.isNullOrEmpty(time)) {
            timeValue = time;
        }
        try {
            Thread.sleep(timeValue);
        } catch (Exception ex) {
            Log.e("GalleryFragment", ex.getMessage());
        }
        return "1";
    }
}