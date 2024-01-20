package com.project.takingnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "noteDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "note";
    private static final String ID_COL = "id";
    private static final String TITLE_COL = "title";
    private static final String CONTENT_COL = "content";
    private static final String DATE_COL = "date";

    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE_COL + " TEXT, "
                + CONTENT_COL + " TEXT, "
                + DATE_COL + " DATE)";

        db.execSQL(query);
    }

    public void addNote(String title, String content) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TITLE_COL, title);
        values.put(CONTENT_COL, content);
        values.put(DATE_COL, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        database.insert(TABLE_NAME, null, values);
        database.close();
    }

    public ArrayList<Note> readNote() {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " +TABLE_NAME, null);

        ArrayList<Note> notesList = new ArrayList<>();

        if(cursor.moveToFirst()) {
            do {
                notesList.add(new Note(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }while (cursor.moveToNext());
        }

        cursor.close();
        return notesList;
    }

    public void updateNote(String id, String newTitle, String newContent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TITLE_COL, newTitle);
        values.put(CONTENT_COL, newContent);
        values.put(DATE_COL, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        db.update(TABLE_NAME, values, "id=?", new String[]{id});
        db.close();
    }

    public void deleteNote(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id=?", new String[]{id});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
