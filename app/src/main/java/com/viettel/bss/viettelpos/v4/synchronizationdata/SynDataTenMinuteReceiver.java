package com.viettel.bss.viettelpos.v4.synchronizationdata;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SynDataTenMinuteReceiver extends BroadcastReceiver {

	// fuction synthronation 10 minute / 1lan
	@Override
	public void onReceive(final Context context, Intent intent) {
		// define oject synmanager

		PendingIntent service = null;
		 Intent intentForService = new Intent(context.getApplicationContext(),
		 RunSynSerice.class);
		 final AlarmManager alarmManager = (AlarmManager) context
		 .getSystemService(Context.ALARM_SERVICE);
		 final Calendar time = Calendar.getInstance();
		 time.set(Calendar.MINUTE, 0);
		 time.set(Calendar.SECOND, 0);
		 time.set(Calendar.MILLISECOND, 0);
		 if (service == null) {
		 service = PendingIntent.getService(context, 0, intentForService,
		 PendingIntent.FLAG_CANCEL_CURRENT);
		 }
		
		 alarmManager.setRepeating(AlarmManager.RTC, time.getTime().getTime(),
		 600*1000, service);
//
//		final Handler handler = new Handler();
//		Timer timer = new Timer();
//		TimerTask doAsynchronousTask = new TimerTask() {
//			@Override
//			public void run() {
//				handler.post(new Runnable() {
//					public void run() {
//						try {
//							SynchoronizationManager synManager = new SynchoronizationManager(
//									context);
//							ArrayList<OjectSyn> arayLisOjectSyns = synManager
//									.OnGetListMaxOraRow();
//							SynchronizationForService syndaData = new SynchronizationForService(
//									context, arayLisOjectSyns);
//							syndaData.execute(arayLisOjectSyns);
//						} catch (Exception e) {
//						}
//					}
//				});
//			}
//		};
//		timer.schedule(doAsynchronousTask, 0, 600000);
//
//	}
	}
}
