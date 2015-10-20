package com.example.newsup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	ArrayList<MyNews> allNews;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		allNews = new ArrayList<MyNews>();
		ListView list = (ListView)findViewById(R.id.listView1);
		MyAdapter adapter = new MyAdapter(this, android.R.layout.simple_list_item_1, allNews);
		list.setAdapter(adapter);
		
		GetNews gn = new GetNews(list, this, allNews, adapter);
		gn.execute("http://rss.cnn.com/rss/edition.rss", "CNN");
		
		GetNews gn1 = new GetNews(list, this, allNews, adapter);
		gn1.execute("http://feeds.abcnews.com/abcnews/topstories", "ABC");	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	public  void onViewSavedBtnClicked(View v)
	{
		Intent intent = new Intent(this, SavedNews.class);
		startActivity(intent);
	}
}
