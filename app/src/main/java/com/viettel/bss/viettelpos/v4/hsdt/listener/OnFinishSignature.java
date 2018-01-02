package com.viettel.bss.viettelpos.v4.hsdt.listener;

import android.graphics.Bitmap;

/**
 * Created by thinhhq1 on 10/21/2017.
 */

public interface OnFinishSignature  {

    public void onFinish(Bitmap bitmap);
    public void onFinish(Bitmap bitmap, int index);

}
