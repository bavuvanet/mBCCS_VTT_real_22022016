package com.viettel.bss.viettelpos.v4.commons.auto;


import android.util.Log;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by diepdc on 15/04/2015.
 */
public final class LogUtils {
    private static final boolean DEBUG = true;

    /**
     * Use Log.i(tag, msg).
     *
     * @param tag for log showed.
     * @param msg information message.
     */
    public static void i(final String tag, final String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    /**
     * Use Log.e(tag, msg).
     *
     * @param tag for log showed.
     * @param msg error message.
     */
    public static void e(final String tag, final String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    /**
     * Use Log.e(tag, msg, throwable).
     *
     * @param tag for log showed.
     * @param msg error message.
     * @param tr  throws showed.
     */
    public static void e(final String tag, final String msg, final Throwable tr) {
        if (DEBUG) {
            Log.e(tag, msg, tr);
        }
    }

    /**
     * Use Log.v(tag, msg).
     *
     * @param tag for log showed.
     * @param msg warning message.
     */
    public static void v(final String tag, final String msg) {
        if (DEBUG) {
            Log.v(tag, msg);
        }
    }

    /**
     * Use Log.d(tag, msg).
     *
     * @param tag for log showed.
     * @param msg debug message.
     */
    public static void d(final String tag, final String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    /**
     * Use System.out.println(tag, msg).
     *
     * @param tag for log showed.
     * @param msg message.
     */
    public static void out(final String tag, final String msg) {
        if (DEBUG) {
            System.out.println(tag + " ===> " + msg);
        }
    }

    /**
     * Use e.printStackTrace();
     */
    public static void printStackTrace(Exception e) {
        if (DEBUG) {
            Logger logger = Logger.getLogger(LogUtils.class.getName());
            String message = "Exception!";
            logger.log(Level.ALL, message, e);
            e.printStackTrace();
            System.out.println("12345 Exception: " + e.getMessage());
        }
    }

    public static void printStackTrace(OutOfMemoryError e) {
        if (DEBUG) {
            Logger logger = Logger.getLogger(LogUtils.class.getName());
            String message = "OutOfMemoryError!";
            logger.log(Level.ALL, message, e);
            e.printStackTrace();
        }
    }
}
