package com.viettel.bss.viettelpos.v4.commons;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

public class CheckConnectionInfo {
	private final Context mContext;
	private final Activity activity;
	public CheckConnectionInfo(Context mContext) {
		super();
		this.mContext = mContext;
		this.activity = (Activity) mContext;
	}
	public boolean isNetworkOnline() {
		boolean val = false;
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivityManager == null) {
				return false;
			}
			final android.net.NetworkInfo mobile = connectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mobile != null && mobile.isAvailable() && mobile.isConnected()) {
				val = true;
			} 
			/*
			 * else { final android.net.NetworkInfo wifi = connectivityManager
			 * .getNetworkInfo(ConnectivityManager.TYPE_WIFI); if (wifi != null
			 * && wifi.isAvailable() && wifi.isConnected()) { val = true; } }
			 */
		} catch (Exception e) {
			Log.e("Exception", e.toString(), e);
		}

		return val;

	    }  
}
