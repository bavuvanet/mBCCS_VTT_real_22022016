package com.viettel.bss.viettelpos.v4.commons.auto;

import android.content.Context;
import android.content.SharedPreferences;

import com.viettel.bss.viettelpos.v4.commons.Session;


public class PrefUtil {
    public static final String PREF_APP_NAME = "PREF_APP_NAME";
    public static final String PRE_ID = "ID_";

    private static SharedPreferences getSharePref(final Context context) {
        return context.getSharedPreferences(PREF_APP_NAME + Session.userName, Context.MODE_PRIVATE);
    }

    public static void clear(final Context context, final String key) {
        SharedPreferences pref = getSharePref(context);
        pref.edit().remove(PRE_ID + key).commit();
    }

    public static String getString(final Context context, final String key) {
        SharedPreferences pref = getSharePref(context);
        String data = null;
        if (pref != null) {
            data = pref.getString(PRE_ID + key, null);
        }
        return data;
    }

    public static int getInt(final Context context, final String key) {
        SharedPreferences pref = getSharePref(context);
        int data = 0;
        if (pref != null) {
            data = pref.getInt(PRE_ID + key, 0);
        }
        return data;
    }

    public static long getLong(final Context context, final String key) {
        SharedPreferences pref = getSharePref(context);
        long data = 0;
        if (pref != null) {
            data = pref.getLong(PRE_ID + key, 0);
        }
        return data;
    }

    public static boolean getBoolean(final Context context, final String key) {
        SharedPreferences pref = getSharePref(context);
        boolean data = true;
        if (pref != null) {
            data = pref.getBoolean(PRE_ID + key, true);
        }
        return data;
    }

    public static void save(final Context context, final String key, final String data) {
        SharedPreferences pref = getSharePref(context);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(PRE_ID + key, data);
        edit.commit();
    }

    public static void save(final Context context, final String key, final int data) {
        SharedPreferences pref = getSharePref(context);
        SharedPreferences.Editor edit = pref.edit();
        edit.putInt(PRE_ID + key, data);
        edit.commit();
    }

    public static void save(final Context context, final String key, final long data) {
        SharedPreferences pref = getSharePref(context);
        SharedPreferences.Editor edit = pref.edit();
        edit.putLong(PRE_ID + key, data);
        edit.commit();
    }

    public static void save(final Context context, final String key, final boolean data) {
        SharedPreferences pref = getSharePref(context);
        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean(PRE_ID + key, data);
        edit.commit();
    }
}
