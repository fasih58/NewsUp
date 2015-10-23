package com.example.newsup;

import java.io.InputStream;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

class GetPicture extends AsyncTask<Void, Void, Void> {
	String toProcess;
	MyAdapter adapter;
	String output;
	ImageView toShow;

	GetPicture(String link, MyAdapter adap, ImageView gn) {

		adapter = adap;
		toProcess = link;
		output = "";
		toShow = gn;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	@Override
	protected Void doInBackground(Void... params) {

		try {
			URL url = new URL(toProcess);

			// Log.e("ImageLink: ", url.toString());
			HttpGet httpRequest = null;
			httpRequest = new HttpGet(url.toURI());
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = (HttpResponse) httpclient

			.execute(httpRequest);

			HttpEntity entity = response.getEntity();
			BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
			InputStream input = b_entity.getContent();
			output = input.toString();
			Bitmap bp = BitmapFactory.decodeStream(input);
			if (bp.toString() != null) {
				toShow.setVisibility(View.VISIBLE);
				toShow.setImageBitmap(bp);
				Log.e("Visible kardia", "SHO");
			} else
				toShow.setVisibility(View.INVISIBLE);

		} catch (Exception e) {
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);

	}

	@Override
	protected void onPostExecute(Void params) {
		super.onPostExecute(params);
	}
}

