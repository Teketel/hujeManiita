package com.tsegaab.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

//import android.graphics.drawable.Drawable.Callback;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.app.Activity;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.TextView;

public class MainActivity extends Activity implements Callback {
MediaPlayer mp;
private SurfaceView mPreview;
private SurfaceHolder holder;
private TextView mTextview;
public static final int SERVERPORT = 8080;
public static String SERVERIP="10.5.23.149";
Socket clientSocket;
private Handler handler = new Handler();
/** Called when the activity is first created. */
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mPreview = (SurfaceView) findViewById(R.id.surfaceView1);
    mTextview = (TextView) findViewById(R.id.textView1);
    holder = mPreview.getHolder();
    holder.addCallback(this);
    holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    mTextview.setText("Attempting to connect");
    mp = new MediaPlayer();
    Thread t = new Thread(){
        public void run(){
            try {
                    clientSocket = new Socket(SERVERIP,SERVERPORT);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mTextview.setText("Connected to server");
                    }
                });
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ParcelFileDescriptor pfd = ParcelFileDescriptor.fromSocket(clientSocket);
                            pfd.getFileDescriptor().sync();
                            mp.setDataSource(pfd.getFileDescriptor());
                            pfd.close();
                            mp.setDisplay(holder);
                            mp.prepareAsync();
                            mp.start();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                });

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    };
    t.start();
}
@Override
public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	// TODO Auto-generated method stub
	
}
@Override
public void surfaceCreated(SurfaceHolder holder) {
	// TODO Auto-generated method stub
	
}
@Override
public void surfaceDestroyed(SurfaceHolder holder) {
	// TODO Auto-generated method stub
	
}
}