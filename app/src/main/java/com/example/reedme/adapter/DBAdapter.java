package com.example.reedme.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    private static final String DATABASE_NAME = "VegiesDB";
    private static final String DATABASE_TABLE = "Notification";
    private static final int DATABASE_VERSION = 1;
    public static final String KEY_ID = "id";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_TITLE = "title";
    private static final String TAG = "DBAdapter";
    private DatabaseHelper DBHelper;
    private final Context context;
    private SQLiteDatabase db;

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DBAdapter.DATABASE_NAME, null, DBAdapter.DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL("CREATE TABLE Notification(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, message TEXT)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(DBAdapter.TAG, new StringBuilder(String.valueOf(oldVersion)).append(" to ").append(newVersion).append(", which will destroy all old data").toString());
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }

    public DBAdapter(Context ctx) {
        this.context = ctx;
        this.DBHelper = new DatabaseHelper(this.context);
    }

    public DBAdapter open() throws SQLException {
        this.db = this.DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.DBHelper.close();
    }

    public long insertNotification(String title, String message) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE, title);
        initialValues.put(KEY_MESSAGE, message);
        return this.db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteNotification(long id) {
        return this.db.delete(DATABASE_TABLE, new StringBuilder("id=").append(id).toString(), null) > 0;
    }

    public Cursor getAllNotifications() {
        Cursor cursor = null;
        try {
            cursor = this.db.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_TITLE, KEY_MESSAGE}, null, null, null, null, "id DESC");
        } catch (Exception ex) {
            Log.e("Vegies", ex.getMessage());
        }
        return cursor;
    }

    public Cursor getNotification(long id) throws SQLException {
        Cursor mCursor = this.db.query(true, DATABASE_TABLE, new String[]{KEY_ID, KEY_TITLE, KEY_MESSAGE}, "id=" + id, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateNotification(long id, String title, String message) {
        ContentValues args = new ContentValues();
        args.put(KEY_TITLE, title);
        args.put(KEY_MESSAGE, message);
        return this.db.update(DATABASE_TABLE, args, new StringBuilder("id=").append(id).toString(), null) > 0;
    }
}
