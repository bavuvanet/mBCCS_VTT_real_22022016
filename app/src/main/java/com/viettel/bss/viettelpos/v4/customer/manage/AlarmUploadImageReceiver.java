package com.viettel.bss.viettelpos.v4.customer.manage;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Session;

public class AlarmUploadImageReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent service = new Intent(context, UploadImageIntentService.class);
		context.startService(service);
	}

	public static void startServiceUploadImage(Context context) {

		try {
			AlarmManager alarmMgr = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			Intent intent = new Intent(context, AlarmUploadImageReceiver.class);
			intent.putExtra("token", Session.getToken());

			Log.d(Constant.TAG,
					"AlarmUploadImageReceiver startServiceUploadImage  Session.getToken() "
							+ Session.getToken());
//			SharedPreferences preferences = context.getSharedPreferences(
//					Define.PRE_NAME, Activity.MODE_PRIVATE);
//			String tracking = preferences.getString(Define.KEY_TRACKING, "0");
//			if ("2".equalsIgnoreCase(tracking)) {
//				SaveLog saveLog = new SaveLog(context, Constant.SYSTEM_NAME,
//						Session.userName, "uploadImageOffline", CommonActivity
//								.findMyLocation(context).getX(), CommonActivity
//								.findMyLocation(context).getY());
//				saveLog.saveLogRequest(
//						"start uploadImage: " + Session.getToken(),
//						new Date(),
//						new Date(),
//						Session.userName + "_"
//								+ CommonActivity.getDeviceId(context) + "_"
//								+ System.currentTimeMillis());
//			}
			// Set the alarm to start at 8:30 a.m.
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);

			PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);
			// setRepeating() lets you specify a precise custom interval--in
			// this case,
			// 20 minutes.
			alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
					calendar.getTimeInMillis(), Constant.TIME_UPLOAD_IMAGE,
					alarmIntent);

		} catch (Exception e) {
			e.printStackTrace();
			SharedPreferences preferences = context.getSharedPreferences(
					Define.PRE_NAME, Activity.MODE_PRIVATE);
//			String tracking = preferences.getString(Define.KEY_TRACKING, "0");
//			if ("2".equalsIgnoreCase(tracking)) {
//				SaveLog saveLog = new SaveLog(context, Constant.SYSTEM_NAME,
//						Session.userName, "uploadImageOffline", CommonActivity
//								.findMyLocation(context).getX(), CommonActivity
//								.findMyLocation(context).getY());
//				Date timeEnd = new Date();
//
//				saveLog.saveLogRequest(
//						"error uploadImage: " + e,
//						new Date(),
//						timeEnd,
//						Session.userName + "_"
//								+ CommonActivity.getDeviceId(context) + "_"
//								+ System.currentTimeMillis());
//			}
		}
	}

	public static void stopServiceUploadImage(Context context) {
		try {
			Intent intent = new Intent(context, AlarmUploadImageReceiver.class);
			PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);
			AlarmManager alarmManager = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			alarmManager.cancel(alarmIntent);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
