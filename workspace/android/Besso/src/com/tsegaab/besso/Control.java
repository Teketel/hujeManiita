package com.tsegaab.besso;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.widget.Toast;

public class Control extends Activity implements
		CompoundButton.OnCheckedChangeListener {
	Switch livingSw, bedSw, outDoorSw;
	AsyncTask<Void, Void, Void> mRegisterTask;
	public static String Message;
	public static String STATUS;
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		final Context context = this;
		final String state = (isChecked ? "on" : "off");
		// TODO Auto-generated method stub
		if (buttonView == livingSw) {
			mRegisterTask = new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					// Register on our server
					// On server creates a new user
					ServerSide.roomLight(context, "living_room", state);
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					mRegisterTask = null;
				}

			};
			mRegisterTask.execute(null, null, null);
			Toast.makeText(
					this,
					Message + " Setting Living room light " + state,
					Toast.LENGTH_LONG).show();
		}
		if (buttonView == bedSw) {		
			mRegisterTask = new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					// Register on our server
					// On server creates a new user
					ServerSide.roomLight(getApplicationContext(), "bed_room", state);
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					mRegisterTask = null;
				}

			};
			mRegisterTask.execute(null, null, null);
			Toast.makeText(
					this,
					Message + " Setting Bed room light " + state,
					Toast.LENGTH_LONG).show();
			
		}
		if (buttonView == outDoorSw) {
			
			mRegisterTask = new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... params) {
					
					ServerSide.roomLight(context, "outdoor", state);
					return null;
				}

				@Override
				protected void onPostExecute(Void result) {
					mRegisterTask = null;
				}

			};
			mRegisterTask.execute(null, null, null);
			Toast.makeText(
					this,
					Message + " Setting Outdoor light " + state,
					Toast.LENGTH_LONG).show();
		}
	}
}
