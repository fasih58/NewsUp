package com.example.newsup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class NewsView extends Activity {

	String title;
	String desc;
	String source;
	String date;
	String link;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_view);
		Intent intent = getIntent();
		String url = intent.getStringExtra("url").toString();
		title = intent.getStringExtra("title").toString();
		desc = intent.getStringExtra("desc").toString();
		source = intent.getStringExtra("source").toString();
		date = intent.getStringExtra("date").toString();
		link = intent.getStringExtra("link").toString();
		WebView webview = (WebView) findViewById(R.id.webView1);
		WebSettings websettings = webview.getSettings();
		websettings.setJavaScriptEnabled(false);
		webview.loadUrl(url);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.news_view, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_share_desc:
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, "Title: " + title
					+ "\nDescription: " + desc + "\nSource: " + source
					+ "\nDate: " + date + "\nLink: " + link + "\n\n");
			sendIntent.setType("text/plain");
			startActivity(sendIntent);
			Toast.makeText(this, "Share button Clicked", Toast.LENGTH_SHORT)
					.show();
			return true;
		default:
			return true;
		}
	}
}
