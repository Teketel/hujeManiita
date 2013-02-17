package com.tsegaab.testme;

import static com.tsegaab.testme.CommonUtilitys.SENDER_ID;
import com.google.android.gcm.GCMRegistrar;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private String TAG = "** pushAndroidActivity **";
	private TextView mDisplay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		checkNotNull(SENDER_ID, "SENDER_ID");
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		setContentView(R.layout.activity_main);
		mDisplay = (TextView) findViewById(R.id.display);

		final String regId = GCMRegistrar.getRegistrationId(this);
		Log.i(TAG, "registration id =====; " + regId);
		if (regId.equals("")) {
			GCMRegistrar.register(this, SENDER_ID);
		} else {
			Log.v(TAG, "Already registered");
		}
		
		mDisplay.setText("ffffff&nbsp; "+regId);
	}

	private void checkNotNull(String senderId, String name) {
		if (senderId == null) {
			throw new NullPointerException(getString(R.string.error_config,
					name));
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		GCMRegistrar.unregister(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
