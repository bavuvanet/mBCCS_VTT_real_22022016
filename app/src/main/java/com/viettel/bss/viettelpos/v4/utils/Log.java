package com.viettel.bss.viettelpos.v4.utils;

import com.viettel.bss.viettelpos.v4.commons.Constant;

/**
 * Created by Toancx on 2/13/2017.
 */

public class Log {
    public static final String TAG = "AddDataMenu";

    public static void i(String string) {
        if (Constant.LOG)
            android.util.Log.i(TAG, string);
    }

    public static void e(String string) {
        if (Constant.LOG)
            android.util.Log.e(TAG, string);
    }

    public static void d(String string) {
        if (Constant.LOG)
            android.util.Log.d(TAG, string);
    }

    public static void v(String string) {
        if (Constant.LOG)
            android.util.Log.v(TAG, string);
    }

    public static void w(String string) {
        if (Constant.LOG)
            android.util.Log.w(TAG, string);
    }

    public static void out(String tag, String mess) {
        if (Constant.LOG)
            android.util.Log.w(tag != null ? tag : TAG, mess);
    }
}
