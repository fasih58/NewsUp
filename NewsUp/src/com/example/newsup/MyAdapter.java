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

public class MyAdapter extends ArrayAdapter<MyNews> {
	Context c;
	int layoutFile;
	ArrayList<MyNews> data;

	public MyAdapter(Context context, int resource, ArrayList<MyNews> objects) {

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

		TextView txt = (TextView) row.findViewById(R.id.titleText);
		txt.setText(data.get(position).getTitle());

		TextView txt1 = (TextView) row.findViewById(R.id.sourceText);
		String source = data.get(position).getSource();
		final String url = data.get(position).getLinkToNews();

		txt1.setVisibility(txt1.GONE);

		TextView txt2 = (TextView) row.findViewById(R.id.date);
		txt2.setTextColor(Color.GRAY);
		txt2.setText(data.get(position).getDate());

		TextView txt3 = (TextView) row.findViewById(R.id.descriptionText);
		txt3.setText(data.get(position).getDetails());

		ImageView newsImg = (ImageView) row.findViewById(R.id.image);

		try {
			URL url1 = new URL(data.get(position).getNewsImage());
			Log.e("Image Link aaya: ", data.get(position).getNewsImage());
			HttpGet httpRequest = null;
			httpRequest = new HttpGet(url1.toURI());
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = (HttpResponse) httpclient

			.execute(httpRequest);

			HttpEntity entity = response.getEntity();
			BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
			InputStream input = b_entity.getContent();
			Bitmap bitmap = BitmapFactory.decodeStream(input);
			newsImg.setImageBitmap(bitmap);
		} catch (Exception ex) {
		}
		ImageView img = (ImageView) row.findViewById(R.id.sourceImage);
		if (source == "CNN")
			img.setImageResource(R.drawable.cnn);
		else if (source == "ABC")
			img.setImageResource(R.drawable.abc);

	//	ImageView img2 = (ImageView) row.findViewById(R.id.image);
		//img2.setImageResource(R.drawable.ic_launcher);

		final RelativeLayout rl = (RelativeLayout) row
				.findViewById(R.id.LinearLayout1);
		rl.setVisibility(rl.GONE);

		final int pos = position;
		android.view.View.OnClickListener listener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(c, "News Clicked on position " + pos,
						Toast.LENGTH_SHORT).show();
				// ll.setVisibility(0);

				Intent intent = new Intent(c, NewsView.class);
				intent.putExtra("url", url);
				c.startActivity(intent);
			}
		};

		OnLongClickListener longlistener = new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				rl.setVisibility(rl.VISIBLE);
				return true;
			}
		};
		txt.setOnClickListener(listener);
		txt1.setOnClickListener(listener);
		txt2.setOnClickListener(listener);
		txt3.setOnClickListener(listener);
		img.setOnClickListener(listener);
	//	img2.setOnClickListener(listener);
		txt.setOnLongClickListener(longlistener);
		txt1.setOnLongClickListener(longlistener);
		txt2.setOnLongClickListener(longlistener);
		txt3.setOnLongClickListener(longlistener);

		ImageButton shareButton = (ImageButton) row
				.findViewById(R.id.shareButton);
		shareButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(c, "Share Button Clicked " + pos,
						Toast.LENGTH_SHORT).show();
				// ll.setVisibility(0);
			}
		});

		ImageButton starButton = (ImageButton) row
				.findViewById(R.id.starButton);
		starButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatabaseHandler news = new DatabaseHandler(c);
				news.addNews( new MyNews(data.get(pos).getTitle(),
						data.get(pos).getDetails(),
						data.get(pos).getSource(),
						"",
						data.get(pos).getNewsImage(),
						data.get(pos).getTypeOfNews(),
						data.get(pos).getDate(),
						data.get(pos).getLinkToNews()
						));
				
				Toast.makeText(c, "News at position " + pos + " saved locally.",
						Toast.LENGTH_SHORT).show();
				// ll.setVisibility(0);
			}
		});
		return row;
	}
}
