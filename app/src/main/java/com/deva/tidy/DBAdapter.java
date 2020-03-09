package com.deva.tidy;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Time;
import java.sql.Timestamp;

public class DBAdapter {
    static final String KEY_ROWID = "_id";
    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "TidyDB";
    static final String DATABASE_TABLE = "tasks";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE =
            "create table tasks (_id integer primary key autoincrement, "
                    + "name text not null, due text not null, isdone integer not null);";
    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS tasks");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }
    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }


    public long insertTask(String name, Timestamp due, boolean isDone)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put("name", name);
        initialValues.put("due", due.toString());
        initialValues.put("isdone", isDone ? 1:0);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteTask(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }


    public Cursor getAllTasks()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, "name",
                "due", "isdone"}, null, null, null, null, null);
    }
    //---retrieves a particular contact---
    public Cursor getTask(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, "name",
                                "due", "isdone"}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }


    public boolean updateTask(long rowId, String name, Timestamp due, boolean isDone)
    {
        ContentValues args = new ContentValues();
        args.put("name", name);
        args.put("due", due.toString());
        args.put("isdone", isDone ? 1:0);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}

