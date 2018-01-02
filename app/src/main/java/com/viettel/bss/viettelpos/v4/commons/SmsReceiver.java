package com.viettel.bss.viettelpos.v4.commons;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		// ---get the SMS message passed in---
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String messageReceived = "";

		if (bundle != null) {
			// ---retrieve the SMS message received---
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for (int i = 0; i < msgs.length; i++) {
				msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
				String sender = msgs[i].getOriginatingAddress();
				Toast.makeText(context, sender, Toast.LENGTH_SHORT).show();
				Log.e("SENDER", sender);
				if (Constant.EXCHANGE_ADDRESS.equals(sender)
						|| sender.equals("+" + Constant.EXCHANGE_ADDRESS)) {
					messageReceived += msgs[i].getMessageBody().toString();
					Intent in = new Intent(Constant.REGISTER_RECEIVER)
							.putExtra("msg_body", messageReceived);
					context.sendBroadcast(in);
					abortBroadcast();
				}
				Log.e("content", messageReceived);
			}
		}
	}
}