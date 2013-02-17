package com.sagar.gcma;

import static com.sagar.gcma.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.sagar.gcma.CommonUtilities.EXTRA_MESSAGE;
import static com.sagar.gcma.CommonUtilities.SENDER_ID;

import javax.security.auth.PrivateCredentialPermission;

import com.google.android.gcm.GCMRegistrar;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager.WakeLock;
import android.renderscript.Mesh.Primitive;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity {

	TextView lableMsg;
	AsyncTask<Void, Void, Void> mRegisterTask;
	AlertManager alert2 = new AlertManager();
	ConnectionD conn2;

	public static String name;
	public static String email;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		conn2 = new ConnectionD(getApplicationContext());

		if (!conn2.isConnectingToInternet()) {
			alert2.showAlert(MyActivity.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);

			return;
		}
		Intent intent2 = getIntent();
		name = intent2.getStringExtra("name");
		email = intent2.getStringExtra("email");

		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		lableMsg = (TextView) findViewById(R.id.lableMsg);

		final String regId = GCMRegistrar.getRegistrationId(this);

		if (regId.equals("")) {
			GCMRegistrar.register(this, SENDER_ID);
		} else {
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				Toast.makeText(getApplicationContext(),
						"Already registered with GCM", Toast.LENGTH_LONG)
						.show();
			} else {

				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// Register on our server
						// On server creates a new user
						ServerUtilities.register(context, name, email, regId);
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
	}

	private final BroadcastReceiver handleMessaReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			WakeLocker.acquire(getApplicationContext());
			/**
			 * Take appropriate action on this message depending upon your app
			 * requirement For now i am just displaying it on the screen
			 * */

			// Showing received message
			lableMsg.append(newMessage + "\n");
			Toast.makeText(getApplicationContext(), "New Message" + newMessage,
					Toast.LENGTH_LONG).show();
			WakeLocker.release();
		}
	};

	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(handleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}

}