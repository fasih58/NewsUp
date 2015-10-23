package com.example.newsup;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

class GetNews extends AsyncTask<String, Void, String> {
	ListView lv;
	Context context;
	ProgressDialog pd;
	ArrayList<MyNews> allNews;
	MyAdapter adapter;
	static int count;
	private ArrayList<String> title;
	private ArrayList<String> description;
	private ArrayList<String> date;
	private ArrayList<String> imageLink;
	private ArrayList<String> newsLink;

	GetNews(ListView l, Context c, ArrayList<MyNews> data, MyAdapter adap) {
		lv = l;
		context = c;
		allNews = data;
		adapter = adap;
		count = 0;
		title = new ArrayList<String>();
		description = new ArrayList<String>();
		date = new ArrayList<String>();
		imageLink = new ArrayList<String>();
		newsLink = new ArrayList<String>();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pd = ProgressDialog.show(context, "", "Fetching News...", false);
		count++;
		Log.e("task No." + count, "e");
	}

	@Override
	protected String doInBackground(String... params) {
		try {
			URL url = new URL(params[0]);

			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(false);
			XmlPullParser xpp = factory.newPullParser();

			xpp.setInput(getInputStream(url), "UTF_8");

			boolean insideItem = false;

			int eventType = xpp.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {

				if (eventType == XmlPullParser.START_TAG) {

					if (xpp.getName().equalsIgnoreCase("item")) {
						insideItem = true;
					} else if (xpp.getName().equalsIgnoreCase("title")) {
						if (insideItem) {
							title.add(xpp.nextText());
						}

					} else if (xpp.getName().equalsIgnoreCase("description")) {
						if (insideItem) {
							String desc = xpp.nextText();
							String noHTMLString = desc
									.replaceAll("\\<.*?>", "");
							description.add(noHTMLString);
						}
					} else if (xpp.getName().equalsIgnoreCase("pubDate")) {
						if (insideItem) {
							date.add(xpp.nextText());
						}
					} else if (xpp.getName().equalsIgnoreCase("url")) {
						//
					} else if (xpp.getName().equalsIgnoreCase("guid")) {
						if (insideItem) {
							newsLink.add(xpp.nextText());
						}
					} else if (xpp.getName()
							.equalsIgnoreCase("media:thumbnail")) {
						if (insideItem) {
							final String link = xpp.getAttributeValue(0);
							// GetPicture gp = new GetPicture(link, adapter,
							// this);
							// gp.execute();
							imageLink.add(link);
						}
					}
				} else if (eventType == XmlPullParser.END_TAG
						&& xpp.getName().equalsIgnoreCase("item")) {
					insideItem = false;
				}
				eventType = xpp.next(); // move to next element

			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.d("Inside", "Before FOR");

		return params[1];
	}

	public void setImageLink(String link) {
		imageLink.add(link);
	}

	public InputStream getInputStream(URL url) {
		try {
			return url.openConnection().getInputStream();
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);

	}

	@Override
	protected void onPostExecute(String params) {
		super.onPostExecute(params);
		for (int i = 0; i < title.size(); i++) {
			String source;
			if (params == "CNN")
				source = "CNN";
			else
				source = "ABC";
			MyNews obj = new MyNews(title.get(i), description.get(i), source,
					"", imageLink.get(i), "All News", date.get(i),
					newsLink.get(i));

			allNews.add(obj);
		}

		allNews.remove(0);
		pd.dismiss();
		Collections.shuffle(allNews);
		adapter.notifyDataSetChanged();
	}
}