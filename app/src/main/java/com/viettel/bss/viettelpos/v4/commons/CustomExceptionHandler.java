package com.viettel.bss.viettelpos.v4.commons;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.util.Log;

class CustomExceptionHandler implements UncaughtExceptionHandler {
	private final UncaughtExceptionHandler defaultUEH;
	private final Activity activity;

	public CustomExceptionHandler(Activity activity) {
		this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
		this.activity = activity;
	}

	@Override
	public void uncaughtException(Thread arg0, Throwable arg1) {

		Log.e(CustomExceptionHandler.class.getName(), "Exception", arg1);
		defaultUEH.uncaughtException(arg0, arg1);
	}

}
