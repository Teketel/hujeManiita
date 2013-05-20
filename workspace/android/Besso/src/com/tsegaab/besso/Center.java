package com.tsegaab.besso;

import com.google.android.gcm.GCMRegistrar;
import static com.tsegaab.besso.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.tsegaab.besso.CommonUtilities.EXTRA_MESSAGE;
import static com.tsegaab.besso.CommonUtilities.SENDER_ID;
import static com.tsegaab.besso.CommonUtilities.SERVER_URL;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioRecord.OnRecordPositionUpdateListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Center extends Activity implements OnClickListener {
	Button lightsControl;
	Button cameraControl;
	Button DoorControl;
	AsyncTask<Void, Void, Void> mRegisterTask;
	AsyncTask<Void, Void, Void> getStatusTask;

	AlertHandler alert = new AlertHandler();
	CheckConnection cd;

	public static String name;
	public static String passWd;
	private String[] r_status;
	private String s;
	private TextView noConnection;
	HomeStatus homeStatus;
	SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.center_activity);
		Intent i = getIntent();
		name = i.getStringExtra("name");
		passWd = i.getStringExtra("passwd");

		cd = new CheckConnection(getApplicationContext());
		noConnection = (TextView) findViewById(R.id.noConntextView3);
		lightsControl = (Button) findViewById(R.id.Lights);
		lightsControl.setOnClickListener(this);
		cameraControl = (Button) findViewById(R.id.Camera);
		cameraControl.setOnClickListener(this);
		DoorControl = (Button) findViewById(R.id.Door);
		DoorControl.setOnClickListener(this);
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		homeStatus = new HomeStatus(pref);
		
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			noConnection.setVisibility(1);
		}
		noConnection.setVisibility(0);
		getLightState();
	
		GCMRegistrar.checkDevice(this);
	
		GCMRegistrar.checkManifest(this);
	
		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));

		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);

		// Check if regid already presents
		if (regId.equals("")) {
			// Registration is not present, register now with GCM
			GCMRegistrar.register(this, SENDER_ID);
		} else {
			// Device is already registered on GCM
			if (GCMRegistrar.isRegisteredOnServer(this)) {
	
			} else {
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
		
						ServerSide.register(context, name, passWd, regId);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
			}
		}
		// getLightState();
	}


	private void getLightState() {
		getStatusTask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				s = ServerSide.getStatus(passWd);
				Log.i("New", s);
				if ((s == null) || (s.equalsIgnoreCase("Un able to get Status"))) {
					return null;
				}
				homeStatus.update(s.split(","));
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				mRegisterTask = null;
			}

		};
		getStatusTask.execute(null, null, null);

	}

	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());
			Toast.makeText(getApplicationContext(),
					"New Message: " + newMessage, Toast.LENGTH_LONG).show();

			// Releasing wake lock
			WakeLocker.release();
		}
	};

	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		if (getStatusTask != null) {
			getStatusTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			startActivity(new Intent(this, MyPreferences.class));
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// updatePref();
		// v.startAnimation(anim);
		if (v.getId() == R.id.Lights) {
			Intent i = new Intent(getApplicationContext(), Control.class);
			i.putExtra("sentStatus", r_status);
			startActivity(i);
			// finish();
		} else if (v.getId() == R.id.Camera) {
			Intent i = new Intent(getApplicationContext(), Camera.class);
			startActivity(i);
			// finish();
		} else if (v.getId() == R.id.Door) {
			Intent i = new Intent(getApplicationContext(), DoorControl.class);
			startActivity(i);
			// finish();
		}

	}

	@Override
	public void onResume() {
		// getLightState();
		super.onResume();
	}

	private void updatePref() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		name = pref.getString("userName", "Please set useName");
		passWd = pref.getString("userPw", "Please ser userPassword");
		SERVER_URL = pref.getString("serverIP", "Please set serverIP");
	}

}