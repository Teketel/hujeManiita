package com.tsegaab.besso;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import static com.tsegaab.besso.CommonUtilities.SERVER_URL;

public class MainActivity extends Activity implements OnClickListener {
	EditText userEditText, ipEditText;
	EditText passwdEditText;
	CheckConnection cd;
	AlertHandler alert = new AlertHandler();
	Button continueBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		/*
		 * if ( !pref.getBoolean("fristTime", true)){ Intent i = new
		 * Intent(getApplicationContext(), Center.class); startActivity(i);
		 * finish(); }
		 */
		setContentView(R.layout.activity_main);
		cd = new CheckConnection(getApplicationContext());
		continueBtn = (Button) findViewById(R.id.button1);
		continueBtn.setOnClickListener(this);
		userEditText = (EditText) findViewById(R.id.userName);
		passwdEditText = (EditText) findViewById(R.id.pw_field);
		ipEditText = (EditText) findViewById(R.id.ipEditText);
		ipEditText.setText("10.5.23.149:8080");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(MainActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}
		String name = userEditText.getText().toString();
		String passWd = passwdEditText.getText().toString();
		String sIp = ipEditText.getText().toString();
		if (!sIp.equals("") && !sIp.equals("10.5.23.149:8080")) {
			SERVER_URL = "http://" + sIp;
		}
		if (name.trim().length() > 0 && passWd.trim().length() > 0) {
			Intent i = new Intent(getApplicationContext(), Center.class);
			// Save values to my preference file
			SharedPreferences pref = PreferenceManager
					.getDefaultSharedPreferences(this);
			SharedPreferences.Editor prefEditor = pref.edit();
			prefEditor.putBoolean("fristTime", false);
			prefEditor.putString("userName", name);
			prefEditor.putString("userPw", passWd);
			prefEditor.putString("serverIP", sIp);
			prefEditor.commit();
			startActivity(i);
			finish();
		} else {

			alert.showAlertDialog(MainActivity.this, "Registration Error!",
					"Please enter your details", false);
		}
	}

}
