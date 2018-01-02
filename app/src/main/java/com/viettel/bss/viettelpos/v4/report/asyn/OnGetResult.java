package com.viettel.bss.viettelpos.v4.report.asyn;

/**
 * Created by Toancx on 1/7/2017.
 */

public interface OnGetResult {
    void onSuccess(Object... object);

    void onFail(Object... object);
}
