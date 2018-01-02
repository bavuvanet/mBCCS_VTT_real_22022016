package com.viettel.bss.viettelpos.v4.activity;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.FileUtils;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.savelog.SaveLog;

import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Date;

public class BccsApplication extends Application {
	// uncaught exception handler variable
	private UncaughtExceptionHandler defaultUEH;

	// handler listener
	private final Thread.UncaughtExceptionHandler _unCaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
		@Override
		public void uncaughtException(Thread thread, Throwable ex) {

			Date current = new Date();

			SaveLog saveLog = new SaveLog(getApplicationContext(),
					Constant.SYSTEM_NAME, Session.userName, "", CommonActivity
							.findMyLocation(getApplicationContext()).getX(),
					CommonActivity.findMyLocation(getApplicationContext())
							.getY());

			Exception e = (Exception) ex;
			saveLog.saveLogRequest(
					" Crash Exception " + FileUtils.getException(e),

					new Date(), new Date(), Session.userName + "_"
							+ CommonActivity.getDeviceId(getApplicationContext()) + "_"
							+ System.currentTimeMillis());
			
//			saveLog.saveLogException(
//					(Exception) ex,
//					current,
//					current,
//					
//					Session.userName
//							+ "_"
//							+ CommonActivity
//									.getDeviceId(getApplicationContext()) + "_"
//							+ System.currentTimeMillis());

			// re-throw critical exception further to the os (important)
			Log.d("uncaughtException", "Error", ex);
			defaultUEH.uncaughtException(thread, ex);
		}
	};

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();


		AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

		defaultUEH = Thread.getDefaultUncaughtExceptionHandler();

		// setup handler for uncaught exception
		Thread.setDefaultUncaughtExceptionHandler(_unCaughtExceptionHandler);

		try {
			final Typeface customFontTypeface = Typeface.createFromAsset(
					getApplicationContext().getAssets(), "SERIF");

			final Field defaultFontTypefaceField = Typeface.class
					.getDeclaredField("fonts/COUR.TTF");
			defaultFontTypefaceField.setAccessible(true);
			defaultFontTypefaceField.set(null, customFontTypeface);
		} catch (Exception ignored) {

		}

	}

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}