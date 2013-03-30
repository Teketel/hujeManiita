package com.tsegaab.besso;

import com.tsegaab.besso.mjpeg.MjpegInputStream;
import com.tsegaab.besso.mjpeg.MjpegView;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;;

public class Camera extends Activity {

	private MjpegView mv;
	AsyncTask<Void, Void, Void> mRegisterTask;
	
	public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        //sample public cam
        final String URL = "http://10.5.35.246:8081/play"; 
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        mv = new MjpegView(this);
        setContentView(mv);        
		mRegisterTask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				// Register on our server
				// On server creates a new user
				mv.setSource(MjpegInputStream.read(URL));
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				mRegisterTask = null;
			}

		};
		mRegisterTask.execute(null, null, null);
        mv.setDisplayMode(MjpegView.SIZE_BEST_FIT);
        mv.showFps(true);
	}
	
	public void onPause() {
		super.onPause();
		mv.stopPlayback();
	}

}