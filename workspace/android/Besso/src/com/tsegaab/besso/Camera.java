package com.tsegaab.besso;

import com.tsegaab.besso.mjpeg.MjpegInputStream;
import com.tsegaab.besso.mjpeg.MjpegView;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

;

public class Camera extends Activity {

	private MjpegView mv;
	AsyncTask<Void, Void, Void> startStreamertask;
	AsyncTask<Void, Void, Void> stopStreamertask;
	final Context context = this;
	private CheckConnection cd;
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		final String URL = "http://10.5.35.99:8081/play";
		cd = new CheckConnection(getApplicationContext());
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		mv = new MjpegView(this);
		
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			Toast.makeText(this,
					"Internet Error!, Please connect to a working Network.",
					Toast.LENGTH_LONG).show();
			finish();
		}
		
		setContentView(mv);

		startStreamertask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				ServerSide.streamer(context, "", "1", "qwerty");
				mv.setSource(MjpegInputStream.read(URL));
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				ServerSide.streamer(context, "", "0", "qwerty");
				startStreamertask = null;
			}

		};
		startStreamertask.execute(null, null, null);
		mv.setDisplayMode(MjpegView.SIZE_BEST_FIT);
		mv.showFps(true);
	}

	public void onPause() {
		super.onPause();
		mv.stopPlayback();
	}

	public void onDestroy() {
		
		stopStreaming();
		if (startStreamertask != null) {
			startStreamertask.cancel(true);
		}
		if (stopStreamertask != null) {
			stopStreamertask.cancel(true);
		}
		super.onDestroy();
	}

	private void stopStreaming() {
		stopStreamertask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				ServerSide.streamer(context, "", "0", "qwerty");
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				stopStreamertask = null;
			}

		};
		stopStreamertask.execute(null, null, null);

	}

}