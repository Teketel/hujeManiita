package com.tsegaab.besso;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

public class Control extends Activity implements
		CompoundButton.OnCheckedChangeListener {
	Switch livingSw, bedSw, outDoorSw;
	AsyncTask<Void, Void, Void> changeStateTask;
	AsyncTask<Void, Void, Void> updateStateTask;
	public static String Message = "Wait till ";
	public static String STATUS;
	private String[] cStatus = new String[3];
	TextToSpeech tts;
	CheckConnection cd;
	private HomeStatus status;
	private SharedPreferences pref;
	String email;
	boolean isSetting = false;
	private TextView noConnection;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.control_activity);
		livingSw = (Switch) findViewById(R.id.living_room_swch);
		bedSw = (Switch) findViewById(R.id.bed_room_swch);
		outDoorSw = (Switch) findViewById(R.id.out_door_swch);
		livingSw.setOnCheckedChangeListener(this);
		bedSw.setOnCheckedChangeListener(this);
		outDoorSw.setOnCheckedChangeListener(this);
		noConnection = (TextView) findViewById(R.id.noConntextView);
		cd = new CheckConnection(getApplicationContext());
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		email = pref.getString("email", "passw0rd");
		status = new HomeStatus(pref);

		// CHECK FOR CONNECTINAND show message

		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			noConnection.setVisibility(View.VISIBLE);
		}
		noConnection.setVisibility(View.INVISIBLE);

		updateLightState();
		// get sent current status from center class

		tts = new TextToSpeech(Control.this, new TextToSpeech.OnInitListener() {

			@Override
			public void onInit(int status) {
				if (status != TextToSpeech.ERROR) {
					tts.setLanguage(Locale.US);

				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void updateLightState() {

		isSetting = true;

		cStatus = status.getStatus();
		Log.v("Besso", "Inside updateLightState" + cStatus[0] + cStatus[1]
				+ cStatus[2]);
		if (cStatus[0].toString().equals("1"))
			livingSw.setChecked(true);
		else
			livingSw.setChecked(false);
		if (cStatus[1].toString().equals("1"))
			bedSw.setChecked(true);
		else
			bedSw.setChecked(false);
		if (cStatus[2].toString().equals("1"))
			outDoorSw.setChecked(true);
		else
			outDoorSw.setChecked(false);
		isSetting = false;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		final Context context = this;
		final String state = (isChecked ? "on" : "off");
		// TODO Auto-generated method stub

		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			Toast.makeText(this,
					"Internet Error!, Please connect to a working Network.",
					Toast.LENGTH_LONG).show();
			tts.speak("Internet Error!, Please connect to a working Network.",
					TextToSpeech.QUEUE_FLUSH, null);
			noConnection.setVisibility(View.VISIBLE);
			return;
		}
		noConnection.setVisibility(View.INVISIBLE);
		if (isSetting) {
			return;
		}
		if (buttonView == livingSw) {
			tts.speak("Switching Living Room lights to " + state,
					TextToSpeech.QUEUE_FLUSH, null);
			changeStateTask = new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					// Register on our server
					// On server creates a new user
					ServerSide.roomLight(context, "living_room", state, email);
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					changeStateTask = null;
				}

			};
			changeStateTask.execute(null, null, null);
			Toast.makeText(this,
					Message + " Setting Living room light " + state,
					Toast.LENGTH_LONG).show();
			tts.speak(Message + " Setting Living rooom light " + state,
					TextToSpeech.QUEUE_FLUSH, null);
		}
		if (buttonView == bedSw) {
			tts.speak("Switching Bed Room lights to " + state,
					TextToSpeech.QUEUE_FLUSH, null);
			changeStateTask = new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					// Register on our server
					// On server creates a new user
					ServerSide.roomLight(getApplicationContext(), "bed_room",
							state, email);
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					changeStateTask = null;
				}

			};
			changeStateTask.execute(null, null, null);
			Toast.makeText(this, Message + " Setting Bed room light " + state,
					Toast.LENGTH_LONG).show();
			tts.speak(Message + " Setting Bed room light " + state,
					TextToSpeech.QUEUE_FLUSH, null);

		}
		if (buttonView == outDoorSw) {
			tts.speak("Switching Outdoor lights to " + state,
					TextToSpeech.QUEUE_FLUSH, null);
			changeStateTask = new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {

					ServerSide.roomLight(context, "outdoor", state, email);
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					changeStateTask = null;
				}

			};
			changeStateTask.execute(null, null, null);
			Toast.makeText(this, Message + " Setting Outdoor light " + state,
					Toast.LENGTH_LONG).show();
			tts.speak(Message + " Setting Outdoor light " + state,
					TextToSpeech.QUEUE_FLUSH, null);
		}
	}

	@Override
	protected void onDestroy() {
		if (tts != null) {
			tts.stop();
			tts.shutdown();
		}

		if (changeStateTask != null) {
			changeStateTask.cancel(true);
		}
		if (updateStateTask != null) {
			updateStateTask.cancel(true);
		}
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// updateLightState();
		super.onResume();
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		email = pref.getString("email", "passw0rd");
	}

}
