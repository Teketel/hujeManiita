package com.tsegaab.testme;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import static com.tsegaab.testme.CommonUtilitys.SENDER_ID;
import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "===GCMIntentService===";

	public GCMIntentService() {
		super(SENDER_ID);
	}

	@Override
	protected void onMessage(Context arg0, Intent arg1) {
		Log.i(TAG, "new message= ");
	}

	@Override
	protected void onError(Context arg0, String errorId) {
		Log.i(TAG, "Received error: " + errorId);
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		return super.onRecoverableError(context, errorId);
	}

	@Override
	protected void onRegistered(Context arg0, String registrationId) {
		Log.i(TAG, "Device registered: regId = " + registrationId);

	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		Log.i(TAG, "unregistered = " + arg1);

	}

}
