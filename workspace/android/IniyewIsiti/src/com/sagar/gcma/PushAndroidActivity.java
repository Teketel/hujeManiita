package com.sagar.gcma;

import static com.sagar.gcma.CommonUtilities.TAG;
import static com.sagar.gcma.CommonUtilities.SENDER_ID;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;

public class PushAndroidActivity extends Activity {
	
	private TextView mDisplay;
	
	private AlertManager alert = new AlertManager();
	ConnectionD  connection;
	
	EditText nameTxt, emailTxt;
	Button regButton;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		connection = new ConnectionD();
		
		if( !connection.isConnectingToInternet()) {
			alert.showAlert(PushAndroidActivity.this, "Internet Connection Error", "Please connect to working Internet connection", false);
			return;
		}
		
		nameTxt = findViewById(R.id.nameTxt);
		emailTxt = findViewById(R.id.emailTxt);
		regButton = findViewById(R.id.regButton);
		
		regButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name = nameTxt.getText().toString();
				String email = emailTxt.getText().toString();
				if(name.trim().length() > 0 && email.trim().length() > 0) {
					Intent intent = new Intent(getApplicationContext(), MyActivity.class);
					intent.putExtra("name", name);
					intent.putExtra("email", email);
					startActivity(intent);
					finish();
				}
				else {
					alert.showAlert(PushAndroidActivity.this, "Registration Error!", "Please enter your details", false);
					
				}
				
			}
		});
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);

		
		mDisplay = (TextView) findViewById(R.id.display);

		final String regId = GCMRegistrar.getRegistrationId(this);
		Log.i(TAG, "registration id =====" + regId +"&");

		if (regId.equals("")) {
			GCMRegistrar.register(this, SENDER_ID);
		} else {
			Log.v(TAG, "Already registered");
		}

		mDisplay.setText("ffffff& " + regId);
	}

	private void checkNotNull(Object reference, String name) {
		if (reference == null) {
			throw new NullPointerException(getString(R.string.error_config,
					name));
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		GCMRegistrar.unregister(this);
	}
}
