package com.example.newsup;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class SavedNews extends Activity {

	ListView lv;
	ArrayList<MyNews> allNews;
	DatabaseHandler news;
	SavedNewsAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_news);
		lv = (ListView) findViewById(R.id.listView1);
		news = new DatabaseHandler(this);
		List<MyNews> n = news.getAllNews();
		allNews = new ArrayList<MyNews>();
		allNews.addAll(n);
		adapter = new SavedNewsAdapter(this,
				android.R.layout.simple_list_item_1, allNews);
		lv.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_about:
			Toast.makeText(this, "NewsUp - Saved News from Database - Created by: Fasih",
					Toast.LENGTH_LONG).show();
			return true;
		case R.id.action_refresh:
			Toast.makeText(this, "Refresh Button Clicked", Toast.LENGTH_SHORT)
					.show();
			allNews.clear();
			adapter.notifyDataSetChanged();
			List<MyNews> n = news.getAllNews();
			allNews.addAll(n);
			adapter.notifyDataSetChanged();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
