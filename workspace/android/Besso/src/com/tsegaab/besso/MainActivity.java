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

public class MainActivity extends Activity implements OnClickListener{
	EditText userEditText;
	EditText emailEditText;

	Button continueBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		continueBtn = (Button) findViewById(R.id.button1);
		continueBtn.setOnClickListener(this);
		userEditText=(EditText) findViewById(R.id.userName);
		emailEditText=(EditText) findViewById(R.id.userName);
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
	
		Intent i = new Intent(getApplicationContext(), Center.class);
		
		i.putExtra("name", userEditText.getText().toString());
		i.putExtra("email", emailEditText.getText().toString());
		startActivity(i);
		finish();
		
	}

}
