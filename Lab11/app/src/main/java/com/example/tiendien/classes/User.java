package com.example.tiendien.classes;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class User {
    public String makhachhang;
    public ArrayList<SuDung> listSuDung;
    public User(String makhachhang,ArrayList<SuDung> sd) {
        this.makhachhang = makhachhang;
        this.listSuDung = sd;
    }
    public User(String makh, Application application) {
        DBHelper dbHelper = new DBHelper(application);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from khachhang where makhachhang = ?",new String[]{makh});
        this.listSuDung = new ArrayList<>();
        while(cursor.moveToNext()) {
            int makhachhang_col = cursor.getColumnIndex("makhachhang");
            int ngay_col = cursor.getColumnIndex("ngay");
            int chisodien_col = cursor.getColumnIndex("chisodien");
            String makhachhang = cursor.getString(makhachhang_col);
            String ngay = cursor.getString(ngay_col);
            int chisodien = cursor.getInt(chisodien_col);
            this.makhachhang = makhachhang;
            SuDung sd = new SuDung(chisodien,ngay);
            this.listSuDung.add(sd);
        }
        cursor.close();
    }
    public void addSuDung(String ngay,int chiso) {
        listSuDung.add(new SuDung(chiso,ngay));
    }
}
