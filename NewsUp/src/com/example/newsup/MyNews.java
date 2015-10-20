package com.example.newsup;

import java.sql.Date;

import android.media.Image;

public class MyNews {
	String title;
	String details;
	String source;
	String sourceImg;
	String newsImage;
	String typeOfNews;
	String date;
	String linkToNews;

	public MyNews(String title, String details, String source,
			String sourceImg, String newsImage, String typeOfNews, String date,
			String linkToNews) {
		super();
		this.title = title;
		this.details = details;
		this.source = source;
		this.sourceImg = sourceImg;
		this.newsImage = newsImage;
		this.typeOfNews = typeOfNews;
		this.date = date;
		this.linkToNews = linkToNews;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getTypeOfNews() {
		return typeOfNews;
	}

	public void setTypeOfNews(String typeOfNews) {
		this.typeOfNews = typeOfNews;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLinkToNews() {
		return linkToNews;
	}

	public void setLinkToNews(String linkToNews) {
		this.linkToNews = linkToNews;
	}
}
