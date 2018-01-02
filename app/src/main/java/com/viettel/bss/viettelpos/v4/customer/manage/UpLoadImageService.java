package com.viettel.bss.viettelpos.v4.customer.manage;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpLoadImageService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		String pathFileZip = intent.getStringExtra("Path_FileZip");
		Log.d("TagService", "onBind : " + pathFileZip);
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		String pathFileZip = intent.getStringExtra("Path_FileZip");
		Log.d("TagService", "onStartCommand : " + pathFileZip);
		return super.onStartCommand(intent, flags, startId);

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
