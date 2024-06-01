package com.gps.camera.timestamp.photo.geotag.map.camera.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.gps.camera.timestamp.photo.geotag.map.data.models.NoteModel;
import java.util.ArrayList;

public class DBManager {
    private Context context;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public DBManager(Context context2) {
        this.context = context2;
    }

    public DBManager open() throws SQLException {
        DatabaseHelper databaseHelper = new DatabaseHelper(this.context);
        this.dbHelper = databaseHelper;
        this.database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.dbHelper.close();
    }

    public void insert_Tag(String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.N_NOTE, str);
        open();
        this.database.insert(DatabaseHelper.TABLE_NOTE, null, contentValues);
        close();
    }

    public ArrayList<NoteModel> getuserdata_Tag() {
        ArrayList<NoteModel> arrayList = new ArrayList<>();
        DatabaseHelper databaseHelper = new DatabaseHelper(this.context);
        this.dbHelper = databaseHelper;
        SQLiteDatabase readableDatabase = databaseHelper.getReadableDatabase();
        this.database = readableDatabase;
        Cursor query = readableDatabase.query(DatabaseHelper.TABLE_NOTE, new String[]{DatabaseHelper._NID, DatabaseHelper.N_NOTE}, null, null, null, null, null);
        int columnIndex = query.getColumnIndex(DatabaseHelper._NID);
        int columnIndex2 = query.getColumnIndex(DatabaseHelper.N_NOTE);
        query.moveToFirst();
        while (!query.isAfterLast()) {
            arrayList.add(new NoteModel(query.getInt(columnIndex), query.getString(columnIndex2)));
            query.moveToNext();
        }
        query.close();
        this.dbHelper.close();
        return arrayList;
    }

}
