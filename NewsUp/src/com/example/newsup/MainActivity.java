package com.example.newsup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ArrayList<MyNews> allNews;
	MyAdapter adapter;
	ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		allNews = new ArrayList<MyNews>();
		list = (ListView) findViewById(R.id.listView1);
		adapter = new MyAdapter(this, android.R.layout.simple_list_item_1,
				allNews);
		list.setAdapter(adapter);

		if (isConnected()) {
			GetNews gn = new GetNews(list, this, allNews, adapter);
			gn.execute("http://rss.cnn.com/rss/edition.rss", "CNN");

			GetNews gn1 = new GetNews(list, this, allNews, adapter);
			gn1.execute("http://feeds.abcnews.com/abcnews/topstories", "ABC");
		} else {
			Toast.makeText(this, "Sorry, you're not connected to the internet",
					Toast.LENGTH_SHORT).show();
			// list.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	boolean isConnected() {
		ConnectivityManager cm = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		return (activeNetwork != null && activeNetwork
				.isConnectedOrConnecting());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_about:
			Toast.makeText(this, "NewsUp - Created by: Fasih",
					Toast.LENGTH_LONG).show();
			return true;
		case R.id.action_refresh:
			Toast.makeText(this, "Refresh Button Clicked", Toast.LENGTH_SHORT)
					.show();
			allNews.clear();
			adapter.notifyDataSetChanged();
			if (isConnected()) {
				GetNews gn = new GetNews(list, this, allNews, adapter);
				gn.execute("http://rss.cnn.com/rss/edition.rss", "CNN");

				GetNews gn1 = new GetNews(list, this, allNews, adapter);
				gn1.execute("http://feeds.abcnews.com/abcnews/topstories",
						"ABC");
			} else {
				Toast.makeText(this,
						"Sorry, you're not connected to the internet",
						Toast.LENGTH_SHORT).show();
				// list.setVisibility(View.GONE);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public void onViewSavedBtnClicked(View v) {
		Intent intent = new Intent(this, SavedNews.class);
		startActivity(intent);
	}
}
