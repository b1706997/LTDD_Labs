package com.example.lab9_sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class NoteDbHelper extends SQLiteOpenHelper {
    private static final String TAG = "NoteDbHelper";
    private static final String DATABASE_NAME = "mynote1.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NOTE = "note";

    public NoteDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Phương thức này tự động gọi nếu storage chưa có DATABASE_NAME
    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i(TAG, "Create table");
        String queryCreateTable = "CREATE TABLE " + TABLE_NOTE + " ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR (255) NOT NULL, " +
                "date VARCHAR (255) NOT NULL," +
                "place VARCHAR (255) NOT NULL" +
                ")";

        db.execSQL(queryCreateTable);
    }

    //Phương thức này tự động gọi khi đã có DB trên Storage, nhưng phiên bản khác
    //với DATABASE_VERSION
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xoá bảng cũ
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        //Tiến hành tạo bảng mới
        onCreate(db);
    }


    public List<Note> getAllNotes() {

        List<Note> notes = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, name,date,place from note", null);

        //Đến dòng đầu của tập dữ liệu
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int noteID = cursor.getInt(0);
            String noteName = cursor.getString(1);
            String noteDate = cursor.getString(2);
            String notePlace = cursor.getString(3);

            notes.add(new Note(noteID, noteName, noteDate,notePlace));
            cursor.moveToNext();
        }

        cursor.close();

        return notes;
    }


    public Note getNoteByID(int ID) {
        Note note = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, name, date,place from note where id = ?",
                new String[]{ID + ""});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int noteID = cursor.getInt(0);
            String noteName = cursor.getString(1);
            String noteDate = cursor.getString(2);
            String notePlace = cursor.getString(3);

            note = new Note(noteID, noteName, noteDate,notePlace);
        }
        cursor.close();
        return note;
    }

    void updateNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE note SET name=?, date = ?,place=? where id = ?",
                new String[]{note.name,note.date,note.place, note.noteID + ""});
    }

    void insertNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO note (name,date,place ) VALUES (?,?,?)",
                new String[]{note.name,note.date,note.place});
    }

    void deleteNoteByID(int NoteID) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM note where id = ?", new String[]{String.valueOf(NoteID)});
    }


}
