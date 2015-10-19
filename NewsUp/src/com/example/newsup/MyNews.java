package com.example.newsup;

import java.sql.Date;

import android.media.Image;

public class MyNews {
	String headline;
	String details;
	String source;
	String sourceImg;
	String newsImage;
	String type;
	String date;
	
	public MyNews(String headline, String details, String source,
			String sourceImg, String newsImage, String type, String date) {
		super();
		this.headline = headline;
		this.details = details;
		this.source = source;
		this.sourceImg = sourceImg;
		this.newsImage = newsImage;
		this.type = type;
		this.date = date;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceImg() {
		return sourceImg;
	}

	public void setSourceImg(String sourceImg) {
		this.sourceImg = sourceImg;
	}

	public String getNewsImage() {
		return newsImage;
	}

	public void setNewsImage(String newsImage) {
		this.newsImage = newsImage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
