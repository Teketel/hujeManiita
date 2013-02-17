package com.tsegaab.myhomesecurer;

import android.R.string;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SeriousBroadcastReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (action.equals("com.google.android.c2dm.intent.REGISTRATION")) {
			String regId = intent.getStringExtra("registration_id");
			Log.i("uo", regId);
			String handledError = intent.getStringExtra("error");
			Log.i("uo", handledError);
			String unregId = intent.getStringExtra("unregistered");
		} else if(action.equals("com.google.android.c2dm.intent.RECEIVE")) {
			String data1 = intent.getStringExtra("data1");
			String data2 = intent.getStringExtra("data2");
			
		}
	}

}
