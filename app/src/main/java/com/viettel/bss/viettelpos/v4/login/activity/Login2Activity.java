package com.viettel.bss.viettelpos.v4.login.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.viettel.bss.viettelpos.v4.R;

import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

public class Login2Activity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout);
		if (CommonActivity.isNetworkConnected(Login2Activity.this)) {
//			final Handler handler = new Handler();
//			Timer timer = new Timer();
//			TimerTask doAsynchronousTask = new TimerTask() {
//				@Override
//				public void run() {
//					handler.post(new Runnable() {
//						public void run() {
//							try {
//								startService(new Intent(
//										getApplicationContext(),
//										RunSynSerice.class));
//							} catch (Exception e) {
//							}
//						}
//					});
//				}
//			};
//			timer.schedule(doAsynchronousTask, 0, 300 * 1000);

		}

		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// AlertDialogManager manager = new
		// AlertDialogManager(Login2Activity.this,this);
		// manager.showAlertDialog(getResources().getString(R.string.title_dialog_logout)
		// , getResources().getString(R.string.content_dialog_logout));
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

	@Override
	public void onClick(View v) {

	}
}
