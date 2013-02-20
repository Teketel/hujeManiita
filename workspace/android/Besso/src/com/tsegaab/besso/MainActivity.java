package com.tsegaab.besso;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import static com.tsegaab.besso.CommonUtilities.SERVER_URL;

public class MainActivity extends Activity implements OnClickListener {
	EditText userEditText, ipEditText;
	EditText emailEditText;
	CheckConnection cd;
	AlertHandler alert = new AlertHandler();
	Button continueBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cd = new CheckConnection(getApplicationContext());
		// Check if Internet present
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(MainActivity.this,
					"Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}
		continueBtn = (Button) findViewById(R.id.button1);
		continueBtn.setOnClickListener(this);
		userEditText = (EditText) findViewById(R.id.userName);
		emailEditText = (EditText) findViewById(R.id.email);
		ipEditText = (EditText) findViewById(R.id.ipEditText);
		ipEditText.setText("10.5.23.149:8080");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		String name = userEditText.getText().toString();
		String email = emailEditText.getText().toString();
		String sIp = ipEditText.getText().toString();
		if (!sIp.equals("") && !sIp.equals("10.5.23.149:8080")) {
			SERVER_URL = "http://" + sIp;
		}
		if (name.trim().length() > 0 && email.trim().length() > 0) {
			Intent i = new Intent(getApplicationContext(), Center.class);

			i.putExtra("name", name);
			i.putExtra("email", email);
			startActivity(i);
			finish();
		} else {

			alert.showAlertDialog(MainActivity.this, "Registration Error!",
					"Please enter your details", false);
		}
	}

}
