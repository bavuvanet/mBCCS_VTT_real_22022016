package com.viettel.bss.viettelpos.v4.omichanel.asynctask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskCommon;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thuandq on 11/09/2017.
 */

public class AsyncTaskLoadImage extends AsyncTask<String, Void, Bitmap> {
    OnPostExecuteListener<Bitmap> listener;
    private String errorCode;
    private String description;

    public AsyncTaskLoadImage(Activity context, OnPostExecuteListener<Bitmap> listener, View.OnClickListener moveLogInAct) {
        this.listener = listener;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        return loadImageFromWebOperations(params[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        listener.onPostExecute(bitmap, errorCode, description);
    }

    public Bitmap loadImageFromWebOperations(String url) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(url).getContent());
            errorCode = "0";
        } catch (MalformedURLException e) {
            errorCode = "1";
            description = e.getMessage();
        } catch (IOException e) {
            errorCode = "1";
            description = e.getMessage();
        }
        return bitmap;
    }
}
