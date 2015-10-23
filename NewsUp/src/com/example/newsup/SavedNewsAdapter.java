package com.example.newsup;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SavedNewsAdapter extends ArrayAdapter<MyNews> {
	Context c;
	int layoutFile;
	ArrayList<MyNews> data;

	public SavedNewsAdapter(Context context, int resource,
			ArrayList<MyNews> objects) {

		super(context, resource, objects);
		c = context;
		layoutFile = resource;
		data = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row;
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) c).getLayoutInflater();
			row = inflater.inflate(R.layout.row_layout, parent, false);
		} else {
			row = (View) convertView;
		}

		TextView titleText = (TextView) row.findViewById(R.id.titleText);
		TextView sourceName = (TextView) row.findViewById(R.id.sourceText);
		final String source = data.get(position).getSource();
		final String url = data.get(position).getLinkToNews();
		TextView date = (TextView) row.findViewById(R.id.date);
		TextView description = (TextView) row
				.findViewById(R.id.descriptionText);
		final ImageView newsImage = (ImageView) row.findViewById(R.id.image);
		ImageView sourceImage = (ImageView) row.findViewById(R.id.sourceImage);
		final RelativeLayout rl = (RelativeLayout) row
				.findViewById(R.id.LinearLayout1);
		final int pos = position;
		titleText.setText(data.get(position).getTitle());
		sourceName.setVisibility(View.GONE);

		date.setTextColor(Color.GRAY);
		date.setText(data.get(position).getDate());

		description.setText(data.get(position).getDetails());
		newsImage.setImageResource(R.drawable.news_up_logo);

		if (source.contains("CNN")) {
			data.get(position).setSource("CNN");
			sourceImage.setImageResource(R.drawable.cnn);
		} else if (source.contains("ABC")) {
			data.get(position).setSource("ABC");
			sourceImage.setImageResource(R.drawable.abc);
		}

		rl.setVisibility(View.GONE);

		android.view.View.OnClickListener listener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ConnectivityManager cm = (ConnectivityManager) c
						.getSystemService(Context.CONNECTIVITY_SERVICE);

				NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
				boolean isConnected = activeNetwork != null
						&& activeNetwork.isConnectedOrConnecting();

				if (isConnected) {
					Intent intent = new Intent(c, NewsView.class);
					intent.putExtra("url", url);
					c.startActivity(intent);
				}
				else
					Toast.makeText(c, "Sorry, You're not connected to the internet!", Toast.LENGTH_SHORT).show();
			}
		};

		row.setOnClickListener(listener);
		return row;
	}
}
