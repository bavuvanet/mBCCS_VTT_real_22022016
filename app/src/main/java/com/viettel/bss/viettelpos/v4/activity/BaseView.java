package com.viettel.bss.viettelpos.v4.activity;

/**
 * Created by NHAT on 22/05/2015.
 */
public interface BaseView {
    /**
     * show progress view.
     */
    void showProgressDialog();

    /**
     * hide progress view.
     */
    void dismissProgressDialog();

    void onSuccess(Object o);

    void onError(Object o);
}
