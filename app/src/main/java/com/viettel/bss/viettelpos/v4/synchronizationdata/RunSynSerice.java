package com.viettel.bss.viettelpos.v4.synchronizationdata;

import java.util.ArrayList;

import com.viettel.savelog.util.Log;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class RunSynSerice extends Service {
	private ArrayList<OjectSyn> arayLisOjectSyns = null;

	@Override
	public IBinder onBind(Intent arg0) {

		return null;
	}

	@Override
	public void onCreate() {
		Log.d("oncreate Service");

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// run service
		SynchoronizationManager synManager = new SynchoronizationManager(
				RunSynSerice.this);
		arayLisOjectSyns = synManager.OnGetListMaxOraRow();
		String token = "xxx";
		if (intent != null) {
			token = intent.getStringExtra("KEY_SESSION_TOKEN");
		}

		SynchronizationForService syndaData = new SynchronizationForService(
				RunSynSerice.this, arayLisOjectSyns, token);
		syndaData.execute(arayLisOjectSyns);

		return super.onStartCommand(intent, flags, startId);
	}

}
