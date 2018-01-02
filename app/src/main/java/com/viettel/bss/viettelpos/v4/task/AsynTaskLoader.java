package com.viettel.bss.viettelpos.v4.task;

import android.content.Context;

import com.viettel.bss.viettelpos.v4.listener.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.ui.image.utils.AsyncTask;

/**
 * Created by Toancx on 2/9/2017.
 */

public class AsynTaskLoader extends AsyncTask<Void, Void, Void> {
    OnItemClickListener onItemClickListener;
//    ProgressDialog progressDialog;

    public AsynTaskLoader(Context context, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;

//        progressDialog = new ProgressDialog(context);
//        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        onItemClickListener.onItemClick(new Object());
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
//        progressDialog.dismiss();
        super.onPostExecute(aVoid);
    }
}
