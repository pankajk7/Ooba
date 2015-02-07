package com.ooba.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import com.ooba.NewsActivity;
import com.ooba.entity.FaqEntity;
import com.ooba.entity.NewsEntity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.AvoidXfermode.Mode;
import android.webkit.WebChromeClient.CustomViewCallback;

public class MySqliteHelper extends SQLiteOpenHelper {

	Context context;
	private SQLiteDatabase database;

	public MySqliteHelper(Context context) {

		super(context, "ooba", null, 1);

		try {

			this.context = context;

			database = context.openOrCreateDatabase("ooba",
					Context.MODE_PRIVATE, null);
			createTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dropTable() {
		try {

			String query = "drop table RecordConversation";
			database.execSQL(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createTable() {
		try {

			String faq = "CREATE TABLE IF NOT EXISTS faqs (title TEXT,content TEXT);";
			String news = "CREATE TABLE IF NOT EXISTS news (Id INTEGER PRIMARY KEY AUTOINCREMENT ,title TEXT,content TEXT)";

			database.execSQL(faq);
			database.execSQL(news);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteAll() {
		database.execSQL("Delete from RecordConversation");
		database.close();
	}

	public void removeAllFaq() {
		database.execSQL("Delete from faqs");
	}

	public void removeAllnews() {
		database.execSQL("Delete from news");
	}

	public int insertFaq(String title, String content) {
		try {

			ContentValues values = new ContentValues(2);
			values.put("title", title);
			values.put("content", content);
			database.insert("faqs", null, values);
			return 1;

		} catch (Exception e) {

			e.printStackTrace();
			return 0;
		}
	}

	public int insertNews(String title, String content) {
		try {

			ContentValues values = new ContentValues(2);
			values.put("title", title);
			values.put("content", content);
			database.insert("news", null, values);
			return 1;
		} catch (Exception e) {

			e.printStackTrace();
			return 0;
		}
	}

	public void closeDatabase() {
		database.close();
	}

	public ArrayList<FaqEntity> getAllfaqs() {
		try {

			ArrayList<FaqEntity> faqsArrayList = new ArrayList<FaqEntity>();
			FaqEntity entity;

			Cursor cursor = database.rawQuery("Select title,content from faqs",
					null);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {

				entity = new FaqEntity(cursor.getString(0), cursor.getString(1));
				faqsArrayList.add(entity);
				cursor.moveToNext();
			}

			cursor.close();
			return faqsArrayList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<NewsEntity> getAllNews() {
		try {

			ArrayList<NewsEntity> faqsArrayList = new ArrayList<NewsEntity>();
			NewsEntity entity;

			Cursor cursor = database.rawQuery("Select title,content from news",
					null);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {

				String title = cursor.getString(0);
				String content = cursor.getString(1);
				entity = new NewsEntity(0, title, content);
				faqsArrayList.add(entity);
				cursor.moveToNext();
			}

			cursor.close();
			return faqsArrayList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

}
