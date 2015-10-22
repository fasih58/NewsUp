package com.example.newsup;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "savedNews";

	// Saved News' table name
	private static final String TABLE_SAVED_NEWS = "mySavedNews";

	// News Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_TITLE = "name";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_SOURCE = "sourceChannel";
	private static final String KEY_IMAGE_LINK = "image_link";
	private static final String KEY_TYPE = "type";
	private static final String KEY_DATE = "date";
	private static final String KEY_LINKNEWS = "news_link";

	Context c;

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		c = context;
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SAVED_NEWS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
				+ KEY_DESCRIPTION + " TEXT," + KEY_SOURCE + " TEXT,"
				+ KEY_IMAGE_LINK + " TEXT," + KEY_TYPE + " TEXT," + KEY_DATE
				+ " TEXT," + KEY_LINKNEWS + " TEXT" + ")";

		db.execSQL(CREATE_CONTACTS_TABLE);
		Toast.makeText(c, "Table for Saving News created.", Toast.LENGTH_LONG)
				.show();
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVED_NEWS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new contact
	void addNews(MyNews mynews) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_DATE, mynews.getDate());
		values.put(KEY_DESCRIPTION, mynews.getDetails());
		values.put(KEY_IMAGE_LINK, mynews.getNewsImage());
		values.put(KEY_LINKNEWS, mynews.getLinkToNews());
		values.put(KEY_SOURCE, mynews.getSource());
		values.put(KEY_TITLE, mynews.getTitle());
		values.put(KEY_TYPE, mynews.getTypeOfNews());

		try {
			Toast.makeText(c, "In  addNews" + mynews.getTitle(),
					Toast.LENGTH_SHORT).show();
			// Inserting Row
			db.insert(TABLE_SAVED_NEWS, null, values);
		} catch (Exception e) {
			// TODO: handle exception
		}
		db.close(); // Closing database connection
	}

	// Getting single contact
	MyNews getNews(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_SAVED_NEWS, new String[] { KEY_TITLE,
				KEY_DESCRIPTION, KEY_SOURCE, KEY_IMAGE_LINK, KEY_TYPE,
				KEY_DATE, KEY_LINKNEWS }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			Toast.makeText(c, "Cursor of getNews(id) is not NULL",
					Toast.LENGTH_SHORT).show();
		} else
			Toast.makeText(c, "Cursor of getNews(id) is NULL",
					Toast.LENGTH_SHORT).show();
		MyNews news = new MyNews(cursor.getString(0), cursor.getString(1),
				cursor.getString(2), "", cursor.getString(3),
				cursor.getString(4), cursor.getString(5), cursor.getString(6));
		// return contact
		
		return news;
	}

	// Getting All Contacts
	public List<MyNews> getAllNews() {
		List<MyNews> newsList = new ArrayList<MyNews>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_SAVED_NEWS;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				MyNews news = new MyNews(cursor.getString(1),
						cursor.getString(2), cursor.getString(3), "",
						cursor.getString(4), cursor.getString(5),
						cursor.getString(6), cursor.getString(7));
				// Adding contact to list
				newsList.add(news);
			} while (cursor.moveToNext());
		}
		
		db.close();

		// return contact list
		return newsList;
	}

	// Updating single contact
	/*
	 * public int updateContact(MyNews ) { SQLiteDatabase db =
	 * this.getWritableDatabase();
	 * 
	 * ContentValues values = new ContentValues(); values.put(KEY_NAME,
	 * contact.getName()); values.put(KEY_PH_NO, contact.getPhoneNumber());
	 * 
	 * // updating row return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
	 * new String[] { String.valueOf(contact.getID()) });
	 * 
	 * }
	 */

	// Deleting single contact
	public void deleteNews(MyNews news) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_SAVED_NEWS, KEY_TITLE + " = ?",
				new String[] { news.getTitle() });
		db.close();
	}

	// Getting contacts Count
	public int getNewsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_SAVED_NEWS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		// cursor.close();

		// return count
		return cursor.getCount();
	}
}