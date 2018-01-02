package com.viettel.bss.viettelpos.v4.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.viettel.bss.viettelpos.v4.R;

class AlertDialogManager {
	private final Context mContext;
	private final Activity activity;
	public AlertDialogManager(Context context,Activity activity){
		this.mContext = context;
		this.activity = activity;
	}
	public void showAlertDialog( String title, String message) {
		final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);
		// Setting Dialog Message
		alertDialog.setMessage(message);

			// Setting alert dialog icon
			alertDialog.setIcon(R.drawable.fail);

		// Setting OK Button
		alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				activity.finish();
			}
		});
		alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				alertDialog.dismiss();
				
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}
}
