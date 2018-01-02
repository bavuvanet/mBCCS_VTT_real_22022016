package com.viettel.bss.viettelpos.v4.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.viettel.bss.viettelpos.v4.R;

import butterknife.ButterKnife;

/**
 * Created by NHAT on 30/05/2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
    }

    protected abstract int getLayoutResource();

    @Override
    public void showProgressDialog() {
        if (mProgress == null) {
            mProgress = new ProgressDialog(this);
            mProgress.setCancelable(false);
            mProgress.setIndeterminate(false);
            mProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgress.setMessage(getResources().getString(
                    R.string.processing));
        }
        mProgress.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgress != null && mProgress.isShowing()) {
            mProgress.dismiss();
        }
    }
}
