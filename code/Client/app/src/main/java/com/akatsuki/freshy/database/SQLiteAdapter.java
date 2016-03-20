package com.akatsuki.freshy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.akatsuki.freshy.model.ActionBig;

import java.util.ArrayList;
import java.util.List;

public class SQLiteAdapter {
  //columns za Main tablicu
  static final String KEY_URL = "url";
  static final String KEY_WORDS = "words";
  static final String KEY_NAME = "name";
  static final String KEY_IMG = "img";
  static final String KEY_VIDEO = "video";
  static final String KEY_AUDIO = "audio";
  static final String KEY_LINK = "link";
  static final String KEY_TEXT = "text";
  static final String KEY_ACTIVE = "active";
  static final String KEY_SHA = "sha";
  static final String KEY_DELETED = "deleted";

  static final String TABLE_MAIN = "main";

  static final String DATABASE_NAME = "freshy.db";
  static final int DATABASE_VERSION = 1;

  static final String CREATE_TABLE_MAIN =
          "create table main (url text primary key, " +
                  "words text not null, " +
                  "name text not null, " +
                  "img text not null, " +
                  "video text not null, " +
                  "audio text not null, " +
                  "link text not null, " +
                  "text text not null, " +
                  "active text not null, " +
                  "sha text not null," +
                  "deleted text not null)";

  final Context context;

  DatabaseHelper DBHelper;
  SQLiteDatabase db;

  public SQLiteAdapter(Context ctx) {
    this.context = ctx;
    DBHelper = new DatabaseHelper(context);
  }

  //opens the database
  public SQLiteAdapter open() {
    db = DBHelper.getWritableDatabase();
    return this;
  }

  //closes the database
  public void close() {
    DBHelper.close();
  }

  public boolean create(ActionBig action) {
    if (exists(action.getUrl())) {
      return update(action);
    } else {
      ContentValues values = new ContentValues();
      values.put(KEY_URL, action.getUrl());
      values.put(KEY_WORDS, action.getWords());
      values.put(KEY_NAME, action.getName());
      values.put(KEY_IMG, action.isImage());
      values.put(KEY_VIDEO, action.isVideo());
      values.put(KEY_AUDIO, action.isAudio());
      values.put(KEY_LINK, action.isLink());
      values.put(KEY_TEXT, action.isText());
      values.put(KEY_ACTIVE, "false");
      values.put(KEY_SHA, "");
      values.put(KEY_DELETED, "false");
      long id = db.insert(TABLE_MAIN, null, values);
      Log.i("ROW ID", String.valueOf(id));
      return id != -1;
    }
  }

  public boolean update(ActionBig action) {
    Log.i("UPDAT", "aslkjdlksajd");
    ContentValues values = new ContentValues();
    values.put(KEY_WORDS, action.getWords());
    values.put(KEY_NAME, action.getName());
    values.put(KEY_IMG, action.isImage());
    values.put(KEY_VIDEO, action.isVideo());
    values.put(KEY_AUDIO, action.isAudio());
    values.put(KEY_LINK, action.isLink());
    values.put(KEY_TEXT, action.isText());
    values.put(KEY_DELETED, "false");
//    return db.update(TABLE_MAIN, values, KEY_URL + " like '" + action.getUrl() + "'", null) > 0;
    return db.update(TABLE_MAIN, values, KEY_URL + "=" + action.getUrl(), null) > 0;
  }

  public boolean pause(ActionBig action) {
    ContentValues values = new ContentValues();
    values.put(KEY_ACTIVE, "false");
    return db.update(TABLE_MAIN, values, KEY_URL + " like '" + action.getUrl() + "'", null) > 0;
  }

  public boolean resume(ActionBig action, String sha) {
    ContentValues values = new ContentValues();
    values.put(KEY_ACTIVE, "true");
    values.put(KEY_SHA, sha);
    return db.update(TABLE_MAIN, values, KEY_URL + " like '" + action.getUrl() + "'", null) > 0;
  }

  public boolean delete(ActionBig action) {
    ContentValues values = new ContentValues();
    values.put(KEY_DELETED, "true");
    return db.update(TABLE_MAIN, values, KEY_URL + " like '" + action.getUrl() + "'", null) > 0;
//    return db.delete(TABLE_MAIN, KEY_URL + " like '" + action.getUrl() + "'", null) > 0;
  }

  public boolean exists(String url) {
//    Cursor mCursor = db.query(true, TABLE_MAIN, new String[]{KEY_URL}, KEY_URL + "=" + url, null, null, null, null, null);
    Cursor mCursor = db.rawQuery("select url from main WHERE url like '" + url + "'", null);

    if (mCursor.getCount() > 0) {
      Log.i("SQLITE", "url exists");
      return true;
    }
    Log.i("SQLITE", "url DOES NOT exists");
    return false;
  }

  public List<ActionBig> getExistingData() {
    List<ActionBig> list = new ArrayList<>();
    Cursor cursor = db.query(false, TABLE_MAIN, null, null, null, null, null, null, null);

    if (cursor.moveToFirst()) {
      Log.i("GET EXIST DATA", "CURSOR MOVED TO 1st");
      while (cursor.isAfterLast() == false) {

        if (Boolean.parseBoolean(cursor.getString(10)) == false) {
          String url = cursor.getString(0);
          String words = cursor.getString(1);
          String name = cursor.getString(2);
          boolean img = Boolean.parseBoolean(cursor.getString(3));
          boolean video = Boolean.parseBoolean(cursor.getString(4));
          boolean audio = Boolean.parseBoolean(cursor.getString(5));
          boolean link = Boolean.parseBoolean(cursor.getString(6));
          boolean text = Boolean.parseBoolean(cursor.getString(7));
          boolean active = Boolean.parseBoolean(cursor.getString(8));
          String sha = cursor.getString(9);

          ActionBig action = new ActionBig(url, words, name, img, video, audio, link, text, active);
          action.setSha(sha);

          list.add(action);
        }
        cursor.moveToNext();
      }
    }

    return list;
  }

  public boolean setSha(String url, String sha) {
    ContentValues values = new ContentValues();
    values.put(KEY_SHA, sha);
    return db.update(TABLE_MAIN, values, KEY_URL + " like '" + url + "'", null) > 0;
  }

  public void dropTable(){
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAIN);
    db.execSQL(CREATE_TABLE_MAIN);
  }

  private static class DatabaseHelper extends SQLiteOpenHelper {
    DatabaseHelper(Context context) {
      super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      try {
        db.execSQL(CREATE_TABLE_MAIN);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAIN);
      onCreate(db);
    }
  }
}
