package com.gps.camera.timestamp.photo.geotag.map.data.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.gps.camera.timestamp.photo.geotag.map.camera.C1281SP;
import com.gps.camera.timestamp.photo.geotag.map.camera.NotesModal;
import com.gps.camera.timestamp.photo.geotag.map.camera.KeysConstants;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Model.DateTime;
import com.gps.camera.timestamp.photo.geotag.map.data.database.Model.LoctionModel;

import java.util.ArrayList;

public class DateTimeDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DateTime_DB";

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public DateTimeDB(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE DateAll(date_id INTEGER PRIMARY KEY AUTOINCREMENT,date_format TEXT,date_format_ TEXT,type_date INTEGER ,type_custom INTEGER, date_check INTEGER ,date_pos INTEGER )");
        sQLiteDatabase.execSQL("CREATE TABLE LocationAll(location_id INTEGER PRIMARY KEY AUTOINCREMENT,loc_title TEXT,loc_latitude TEXT,loc_longitude TEXT,loc_line_1_address TEXT,loc_line_2_city TEXT,loc_line_3_state TEXT,loc_line_4_country TEXT )");
        sQLiteDatabase.execSQL("CREATE TABLE NoteAll(note_id INTEGER PRIMARY KEY AUTOINCREMENT,title_name TEXT,note_name TEXT )");
    }

    @SuppressLint("Range")
    public int getIdFromFormat(String str) {
        Cursor rawQuery = getWritableDatabase().rawQuery("SELECT  * FROM DateAll WHERE date_format = '" + str + "'", null);
        if (rawQuery.moveToFirst()) {
            return rawQuery.getInt(rawQuery.getColumnIndex("date_id"));
        }
        return -1;
    }

    public long insetDate(String str, String str2, int i, int i2, int i3, int i4) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(C1281SP.DATE_FORMAT, str);
        contentValues.put("date_format_", str2);
        contentValues.put("type_date", Integer.valueOf(i));
        contentValues.put("type_custom", Integer.valueOf(i2));
        contentValues.put("date_check", Integer.valueOf(i3));
        contentValues.put(KeysConstants.DATE_POSITION, Integer.valueOf(i4));
        long insertOrThrow = writableDatabase.insertOrThrow("DateAll", null, contentValues);
        writableDatabase.close();
        return insertOrThrow;
    }

    public long insetLoction(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("loc_title", str);
        contentValues.put("loc_latitude", str2);
        contentValues.put("loc_longitude", str3);
        contentValues.put("loc_line_1_address", str4);
        contentValues.put("loc_line_2_city", str5);
        contentValues.put("loc_line_3_state", str6);
        contentValues.put("loc_line_4_country", str7);
        long insertOrThrow = writableDatabase.insertOrThrow("LocationAll", null, contentValues);
        writableDatabase.close();
        return insertOrThrow;
    }

    public long insetNote(String str, String str2) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("note_name", str);
        contentValues.put("title_name", str2);
        long insertOrThrow = writableDatabase.insertOrThrow("NoteAll", null, contentValues);
        writableDatabase.close();
        return insertOrThrow;
    }

    public int updateNotes(int i, String str, String str2) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title_name", str);
        contentValues.put("note_name", str2);
        return writableDatabase.update("NoteAll", contentValues, "note_id = ?", new String[]{String.valueOf(i)});
    }

    public int updateDate(int i, String str, String str2) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(C1281SP.DATE_FORMAT, str);
        contentValues.put("date_format_", str2);
        return writableDatabase.update("DateAll", contentValues, "date_id = ?", new String[]{String.valueOf(i)});
    }

    public long deleteDate(int i) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        long delete = (long) writableDatabase.delete("DateAll", "date_id = ?", new String[]{String.valueOf(i)});
        writableDatabase.close();
        return delete;
    }

    public long deleteLocation(int i) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        long delete = (long) writableDatabase.delete("LocationAll", "location_id = ?", new String[]{String.valueOf(i)});
        writableDatabase.close();
        return delete;
    }

    public long deleteNote(int i) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        long delete = (long) writableDatabase.delete("NoteAll", "note_id = ?", new String[]{String.valueOf(i)});
        writableDatabase.close();
        return delete;
    }

    @SuppressLint("Range")
    public ArrayList<DateTime> getDates() {
        ArrayList<DateTime> arrayList = new ArrayList<>();
        Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM DateAll", null);
        if (rawQuery.moveToFirst()) {
            do {
                DateTime dateTime = new DateTime();
                dateTime.setDate_id(rawQuery.getInt(rawQuery.getColumnIndex("date_id")));
                dateTime.setDate_format(rawQuery.getString(rawQuery.getColumnIndex(C1281SP.DATE_FORMAT)));
                dateTime.setDate_format_(rawQuery.getString(rawQuery.getColumnIndex("date_format_")));
                dateTime.setDate_type(rawQuery.getInt(rawQuery.getColumnIndex("type_date")));
                dateTime.setDate_custom(rawQuery.getInt(rawQuery.getColumnIndex("type_custom")));
                dateTime.setDate_pos(rawQuery.getInt(rawQuery.getColumnIndex(KeysConstants.DATE_POSITION)));
                dateTime.setIsSelect(rawQuery.getInt(rawQuery.getColumnIndex("date_check")));
                arrayList.add(dateTime);
            } while (rawQuery.moveToNext());
        }
        return arrayList;
    }

    public boolean IsDateAvailable(String str) {
        StringBuilder sb = new StringBuilder("SELECT  * FROM DateAll WHERE date_format = '");
        sb.append(str);
        sb.append("'");
        return getWritableDatabase().rawQuery(sb.toString(), null).getCount() > 0;
    }

    @SuppressLint("Range")
    public ArrayList<LoctionModel> getIsSelectedLoction() {
        ArrayList<LoctionModel> arrayList = new ArrayList<>();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        String str = "loc_line_4_country";
        Cursor query = readableDatabase.query("LocationAll", new String[]{"location", "loc_title", "loc_latitude", "loc_longitude", "loc_line_1_address", "loc_line_2_city", "loc_line_3_state", "loc_line_4_country"}, null, null, null, null, null);
        if (query.moveToFirst()) {
            while (true) {
                LoctionModel loctionModel = new LoctionModel();
                loctionModel.setId(query.getInt(query.getColumnIndex("location")));
                loctionModel.setTitle(query.getString(query.getColumnIndex("loc_title")));
                loctionModel.setLatitude(query.getString(query.getColumnIndex("loc_latitude")));
                loctionModel.setLongitude(query.getString(query.getColumnIndex("loc_longitude")));
                loctionModel.setLoc_line_1(query.getString(query.getColumnIndex("loc_line_1_address")));
                loctionModel.setCity(query.getString(query.getColumnIndex("loc_line_2_city")));
                loctionModel.setState(query.getString(query.getColumnIndex("loc_line_3_state")));
                loctionModel.setCountry(query.getString(query.getColumnIndex(str)));
                arrayList.add(loctionModel);
                if (!query.moveToNext()) {
                    break;
                }
                str = str;
            }
        }
        query.close();
        readableDatabase.close();
        return arrayList;
    }

    @SuppressLint("Range")
    public ArrayList<NotesModal> getNotes() {
        ArrayList<NotesModal> arrayList = new ArrayList<>();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor query = readableDatabase.query("NoteAll", new String[]{"note_id", "note_name", "title_name"}, null, null, null, null, null);
        if (query.moveToFirst()) {
            do {
                NotesModal notesModal = new NotesModal();
                notesModal.setID(query.getInt(query.getColumnIndex("note_id")));
                notesModal.setNotes(query.getString(query.getColumnIndex("note_name")));
                notesModal.setTiitle(query.getString(query.getColumnIndex("title_name")));
                notesModal.setSelected(0);
                arrayList.add(notesModal);
            } while (query.moveToNext());
        }
        query.close();
        readableDatabase.close();
        return arrayList;
    }

    @SuppressLint("Range")
    public DateTime getDateById(int i) {
        Cursor rawQuery = getReadableDatabase().rawQuery(String.valueOf(i), null);
        if (!rawQuery.moveToFirst()) {
            return null;
        }
        DateTime dateTime = new DateTime();
        dateTime.setDate_id(rawQuery.getInt(rawQuery.getColumnIndex("date_id")));
        dateTime.setDate_format(rawQuery.getString(rawQuery.getColumnIndex(C1281SP.DATE_FORMAT)));
        dateTime.setDate_format_(rawQuery.getString(rawQuery.getColumnIndex("date_format_")));
        dateTime.setDate_type(rawQuery.getInt(rawQuery.getColumnIndex("type_date")));
        dateTime.setDate_custom(rawQuery.getInt(rawQuery.getColumnIndex("type_custom")));
        dateTime.setDate_pos(rawQuery.getInt(rawQuery.getColumnIndex(KeysConstants.DATE_POSITION)));
        dateTime.setIsSelect(rawQuery.getInt(rawQuery.getColumnIndex("date_check")));
        return dateTime;
    }

    @SuppressLint("Range")
    public ArrayList<DateTime> getNormalDates() {
        ArrayList<DateTime> arrayList = new ArrayList<>();
        Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM DateAll WHERE type_date = 0 ORDER BY type_date DESC", null);
        if (rawQuery.moveToFirst()) {
            do {
                DateTime dateTime = new DateTime();
                dateTime.setDate_id(rawQuery.getInt(rawQuery.getColumnIndex("date_id")));
                dateTime.setDate_format(rawQuery.getString(rawQuery.getColumnIndex(C1281SP.DATE_FORMAT)));
                dateTime.setDate_format_(rawQuery.getString(rawQuery.getColumnIndex("date_format_")));
                dateTime.setDate_type(rawQuery.getInt(rawQuery.getColumnIndex("type_date")));
                dateTime.setDate_custom(rawQuery.getInt(rawQuery.getColumnIndex("type_custom")));
                dateTime.setDate_pos(rawQuery.getInt(rawQuery.getColumnIndex(KeysConstants.DATE_POSITION)));
                dateTime.setIsSelect(rawQuery.getInt(rawQuery.getColumnIndex("date_check")));
                arrayList.add(dateTime);
            } while (rawQuery.moveToNext());
        }
        return arrayList;
    }

    @SuppressLint("Range")
    public DateTime getSelectdOne() {
        Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM DateAll WHERE date_check = 1", null);
        if (!rawQuery.moveToFirst()) {
            return null;
        }
        DateTime dateTime = new DateTime();
        dateTime.setDate_id(rawQuery.getInt(rawQuery.getColumnIndex("date_id")));
        dateTime.setDate_format(rawQuery.getString(rawQuery.getColumnIndex("")));
        dateTime.setDate_format_(rawQuery.getString(rawQuery.getColumnIndex("date_format_")));
        dateTime.setDate_type(rawQuery.getInt(rawQuery.getColumnIndex("type_date")));
        dateTime.setDate_custom(rawQuery.getInt(rawQuery.getColumnIndex("type_custom")));
        dateTime.setDate_pos(rawQuery.getInt(rawQuery.getColumnIndex(KeysConstants.DATE_POSITION)));
        dateTime.setIsSelect(rawQuery.getInt(rawQuery.getColumnIndex("date_check")));
        return dateTime;
    }

    @SuppressLint("Range")
    public ArrayList<DateTime> getHorozontalNormal() {
        ArrayList<DateTime> arrayList = new ArrayList<>();
        Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM DateAll WHERE type_date = 0 OR type_date = 1 ORDER BY type_date DESC", null);
        if (rawQuery.moveToFirst()) {
            do {
                DateTime dateTime = new DateTime();
                dateTime.setDate_id(rawQuery.getInt(rawQuery.getColumnIndex("date_id")));
                dateTime.setDate_format(rawQuery.getString(rawQuery.getColumnIndex(C1281SP.DATE_FORMAT)));
                dateTime.setDate_format_(rawQuery.getString(rawQuery.getColumnIndex("date_format_")));
                dateTime.setDate_type(rawQuery.getInt(rawQuery.getColumnIndex("type_date")));
                dateTime.setDate_custom(rawQuery.getInt(rawQuery.getColumnIndex("type_custom")));
                dateTime.setDate_pos(rawQuery.getInt(rawQuery.getColumnIndex(KeysConstants.DATE_POSITION)));
                dateTime.setIsSelect(rawQuery.getInt(rawQuery.getColumnIndex("date_check")));
                arrayList.add(dateTime);
            } while (rawQuery.moveToNext());
        }
        return arrayList;
    }

    @SuppressLint("Range")
    public DateTime getDateFromPos(int i) {
        Cursor rawQuery = getReadableDatabase().rawQuery("SELECT  * FROM DateAll WHERE date_pos = " + i, null);
        if (!rawQuery.moveToFirst()) {
            return null;
        }
        DateTime dateTime = new DateTime();
        dateTime.setDate_id(rawQuery.getInt(rawQuery.getColumnIndex("date_id")));
        dateTime.setDate_format(rawQuery.getString(rawQuery.getColumnIndex(C1281SP.DATE_FORMAT)));
        dateTime.setDate_format_(rawQuery.getString(rawQuery.getColumnIndex("date_format_")));
        dateTime.setDate_type(rawQuery.getInt(rawQuery.getColumnIndex("type_date")));
        dateTime.setDate_custom(rawQuery.getInt(rawQuery.getColumnIndex("type_custom")));
        dateTime.setDate_pos(rawQuery.getInt(rawQuery.getColumnIndex(KeysConstants.DATE_POSITION)));
        dateTime.setIsSelect(rawQuery.getInt(rawQuery.getColumnIndex("date_check")));
        return dateTime;
    }
}
