package com.viettel.bss.viettelpos.v4.commons;

import android.app.AlertDialog;
import android.content.Context;

import com.viettel.bss.viettelpos.v4.R;

class CommontAlert {
	public static void CommontAlert(String type, String sms, Context mconContext){
		if(type.equals(Constant.DISTANCE_NOT_VALID)){
			String title = mconContext.getString(R.string.app_name);
			new AlertDialog.Builder(mconContext)
			   .setTitle(title)
	           .setMessage(sms)
	           .setCancelable(false)
	           .setNegativeButton(R.string.alert_dialog_ok, null)
	           .show();
		}else if(type.equals(Constant.STAFF_HAVE_NOT_LOCATION)){
			
			String title = mconContext.getString(R.string.app_name);
			new AlertDialog.Builder(mconContext)
			   .setTitle(title)
	           .setMessage(sms)
	           .setCancelable(false)
	           .setNegativeButton(R.string.alert_dialog_ok, null)
	           .show();
		}
	}
}
