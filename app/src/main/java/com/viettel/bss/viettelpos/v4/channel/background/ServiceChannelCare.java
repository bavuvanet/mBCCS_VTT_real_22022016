package com.viettel.bss.viettelpos.v4.channel.background;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ServiceChannelCare extends Service {
	// private final Context context;
	// private final Activity activity;
	// private final Staff staff;
	// private final Location myLocation;
	// private final ArrayList<ChannelContentCareOJ> arrChannelContent;
	// private Context context;
	// private Activity activity;

	// private final Staff staff;
	// private final Location myLocation;
	// private final ArrayList<ChannelContentCareOJ> arrChannelContent;
	// public ServiceChannelCare(Staff staff,
	// ArrayList<ChannelContentCareOJ> arrChannelContent,
	// Location myLocation) {
	// this.staff = staff;
	// this.arrChannelContent = arrChannelContent;
	// this.myLocation = myLocation;
	//
	// }
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		Log.d("service channel care", "started");
		// updateVisit(staff, arrChannelContent, myLocation);
		AsyncUpdateChannelCare asyncUpdateChannelCare = new AsyncUpdateChannelCare(
				this, ContentCareOJ.getmStaff(),
				ContentCareOJ.getArrChannelContent(),
				ContentCareOJ.getMyLocation());
		asyncUpdateChannelCare.execute();
		return START_STICKY;
	}// onStartCommand

}
