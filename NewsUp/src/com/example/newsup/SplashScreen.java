package com.example.newsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

public class SplashScreen extends Activity {

	private final int SPLASH_LENGTH = 10000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		 new Handler().postDelayed(new Runnable(){
	            @Override
	            public void run() {
	                /* Create an Intent that will start the Menu-Activity. */
	            	ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);
	            	for(int i = 0; i < 1000; i+=10)
	            		pb.setProgress(i);
	                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
	                SplashScreen.this.startActivity(mainIntent);
	                SplashScreen.this.finish();
	            }
	        }, SPLASH_LENGTH);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}