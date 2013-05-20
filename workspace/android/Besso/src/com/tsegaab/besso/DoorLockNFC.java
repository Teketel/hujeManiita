package com.tsegaab.besso;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.widget.Toast;

public class DoorLockNFC extends Activity {

	private Tag mytag;

	@Override
	protected void onNewIntent(Intent intent) {
		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
			mytag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			Toast.makeText(this, "Detecting Successfull" + mytag.toString(), Toast.LENGTH_LONG).show();
		}
	}
}
