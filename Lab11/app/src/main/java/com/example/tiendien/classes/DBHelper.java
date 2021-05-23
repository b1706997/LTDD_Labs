package com.example.tiendien.classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "UserDbHelper";
    private static final String DATABASE_NAME = "tiendien.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NOTE = "khachhang";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateTable = "CREATE TABLE khachhang ( " +
                "makhachhang VARCHAR(20) NOT NULL, " +
                "ngay VARCHAR(20) NOT NULL, " +
                "chisodien INTEGER NOT NULL" +
                ")";

        db.execSQL(queryCreateTable);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Xoá bảng cũ
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        //Tiến hành tạo bảng mới
        onCreate(db);
    }

}
