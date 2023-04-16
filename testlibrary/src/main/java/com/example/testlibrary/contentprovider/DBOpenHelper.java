package com.example.testlibrary.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Date:2023/4/16
 * Time:17:36
 * author:joker
 */
class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_CREATE = "create table " +
            "peopleinfo" + "(" + People.KEY_ID + " integer primary key autoincrement, " +
            People.KEY_NAME + " text not null, " + People.KEY_AGE + " integer, " +
            People.KEY_HEIGHT + " float);";

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "peopleinfo");
        onCreate(db);
    }
}
