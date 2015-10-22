package com.example.newsup;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class SavedNews extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saved_news);
		ListView lv = (ListView)findViewById(R.id.listView1);
		DatabaseHandler news=new DatabaseHandler(this);
		List<MyNews> n = news.getAllNews();
		ArrayList<MyNews> allNews = new ArrayList<MyNews>();
		allNews.addAll(n);
		SavedNewsAdapter adapter = new SavedNewsAdapter(this, android.R.layout.simple_list_item_1, allNews);
		lv.setAdapter(adapter);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.saved_news, menu);
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
