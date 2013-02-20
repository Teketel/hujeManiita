package com.tsegaab.besso;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.VideoView;
import static com.tsegaab.besso.CommonUtilities.SERVER_URL;;

public class Camera extends Activity {
WebView w;
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_activity);
		w=(WebView)findViewById(R.id.webVideo);
		w.getSettings().setJavaScriptEnabled(true);
		w.loadUrl("file:///android_asset/client.html");
		
		
		 VideoView myVideoView = (VideoView)findViewById(R.id.videoView1);
		 String httpLiveUrl = SERVER_URL + "/webcam.mjpeg";
		 myVideoView.setVideoPath(httpLiveUrl);
		 myVideoView.setMediaController(new MediaController(this));
		 myVideoView.requestFocus();
		 myVideoView.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}