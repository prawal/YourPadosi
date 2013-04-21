package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseController extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "YourPadosi.db";
	private static final int DATABASE_VERSION = 1;
	private static final String USER_DATA = "create table settings (settings text unique key, "
			+ "value text null);";
	
	public DatabaseController(Context context) {

		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(USER_DATA);

		//Log.d("onCreate", "Create");

	}
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(this.getClass().getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS settings");
		database.execSQL("DROP TABLE IF EXISTS friend_list");
		database.execSQL("DROP TABLE IF EXISTS comment");
		onCreate(database);
	}
	public void initializeSettings() {
		try {
			SQLiteDatabase db = getWritableDatabase();
			ContentValues cv = new ContentValues();

			// for the settings table (integer settings)
			cv.put("settings", "Token");
			cv.put("value", "");
			db.insert(null, "settings" , cv);
			cv.clear();

	

			db.close();
		} catch (Exception ex) {
			Log.e("initializeSettings", "ERROR: " + ex.getMessage());
		}
	}
	public void setTokenSetting(String value) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put("value", value);
		//Log.d("setPhoneNumberSetting", "SET TO " + value);
		db.update("settings", cv, "settings = \"Token\"",null );
		db.close();
	}

	public String getTokenSetting() {
		try {
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.query("settings", null,
					"settings = \"Token\"", null, null, null, null);

			String nTmp = "";
			if (cursor != null) {

				if (cursor.moveToFirst()) {
					//Log.d("getPhoneNumberSetting", "Inside move first");
					do {
						nTmp = cursor.getString(1);
					} while (cursor.moveToNext());
				}
			}
			db.close();
			cursor.close();
			return nTmp;
		} catch (Exception e) {
			Log.d("getTokenSetting", e.getMessage());
			return "";
		}
	}
		public boolean isFirstRun() {
			SQLiteDatabase db = getReadableDatabase();
			Cursor cursor = db.rawQuery(
					"SELECT value FROM settings where settings=\"Token\"",
					null);
			String strTmp = null;
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						strTmp = cursor.getString(0);
					} while (cursor.moveToNext());
				}
			}

			cursor.close();
			db.close();

			if (strTmp == null || strTmp.equals("NEW")) {
				return true;
			} else {
				return false;
			}
		}
	}