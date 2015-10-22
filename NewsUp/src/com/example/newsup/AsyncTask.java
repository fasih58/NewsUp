package com.example.newsup;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

class GetNews extends AsyncTask<String, Void, ArrayList<MyNews>> {
	ListView lv;
	Context context;
	ProgressDialog pd;
	ArrayList<MyNews> allNews;
	MyAdapter adapter;
	static int count;

	GetNews(ListView l, Context c, ArrayList<MyNews> data, MyAdapter adap) {
		lv = l;
		context = c;
		allNews = data;
		adapter = adap;
		count = 0;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pd = ProgressDialog.show(context, "", "Fetching News...", false);
		count++;
		Log.e("task No." + count, "e");
	}

	@Override
	protected ArrayList<MyNews> doInBackground(String... params) {
		ArrayList<String> title = new ArrayList<String>();
		ArrayList<String> description = new ArrayList<String>();
		ArrayList<String> date = new ArrayList<String>();
		ArrayList<String> imageLink = new ArrayList<String>();
		ArrayList<String> newsLink = new ArrayList<String>();
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
							String noHTMLString = desc.replaceAll("\\<.*?>","");
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
							imageLink.add(xpp.getAttributeValue(0));
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

		for (int i = 0; i < title.size(); i++) {
			String source;
			if (params[1] == "CNN")
				source = "CNN";
			else
				source = "ABC";
			MyNews obj = new MyNews(title.get(i), description.get(i), source,
					"", imageLink.get(i), "All News", date.get(i),
					newsLink.get(i));

			allNews.add(obj);
		}

		return allNews;
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
	protected void onPostExecute(ArrayList<MyNews> allNews) {
		super.onPostExecute(allNews);
		allNews.remove(0);
		pd.dismiss();
		Collections.shuffle(allNews);
		adapter.notifyDataSetChanged();
	}
}
