package com.gps.camera.timestamp.photo.geotag.map.camera.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String CREATE_TABLE = "create table notes(_nid INTEGER PRIMARY KEY AUTOINCREMENT, n_note TEXT NOT NULL);";
    static final String DB_NAME = "notecam.DB";
    static final int DB_VERSION = 1;
    public static final String N_NOTE = "n_note";
    public static final String TABLE_NOTE = "notes";
    public static final String _NID = "_nid";

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(CREATE_TABLE);
    }
}
