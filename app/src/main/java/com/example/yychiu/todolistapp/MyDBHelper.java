package com.example.yychiu.todolistapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yychiu on 2017/11/29.
 */

public class MyDBHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "mydb.db";
    private static final int VERSION = 2;
    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE main.exp "+"(_id INTEGER PRIMARY KEY NOT NULL, "+
                "cdate DATETIME NOT NULL ,"+"info VARCHAR , "+"done BOOLEAN NOT NULL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS exp");

        // Create tables again
        onCreate(db);

    }
}
