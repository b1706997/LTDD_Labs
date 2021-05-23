package com.example.tiendien.models;

import android.app.Application;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tiendien.classes.DBHelper;
import com.example.tiendien.classes.SuDung;
import com.example.tiendien.classes.User;
import android.database.Cursor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class KhachHangModel  extends AndroidViewModel {
    private MutableLiveData<HashMap<String, User>> listKhachHang;
    private MutableLiveData<User> currentSuDung;
    private MutableLiveData<Boolean> isShowing;
    private Application app;
    private DBHelper dbHelper;
    public   KhachHangModel(@NonNull Application application) {
        super(application);
        dbHelper = new DBHelper(application);
        isShowing = new MutableLiveData<>(new Boolean(false));
        app = application;
    }
    public LiveData<Boolean> getIsShowing() {
       return this.isShowing;
    }
    public void setIsShowing(boolean bool) {
       this.isShowing.setValue(bool);
    }
    public LiveData<User> getCurrentSuDung() {
        return this.currentSuDung;
    }
    public void setCurrentSuDung(String makh) {
        this.currentSuDung = new MutableLiveData<User>();
        if(listKhachHang==null) {
            this.listKhachHang = new MutableLiveData<HashMap<String, User>>();
            loadKhachHangFromDb();
        }
        if(this.listKhachHang.getValue().get(makh)!=null) {
            this.currentSuDung.setValue(this.listKhachHang.getValue().get(makh));
        }
        else
            this.currentSuDung.setValue(null);
    }
//    public LiveData<ArrayList<SuDung>> getSuDung(String makh) {
//
//
//    }
    public LiveData<HashMap<String,User>> getAll() {
        if(listKhachHang==null) {
            this.listKhachHang = new MutableLiveData<HashMap<String, User>>();
            loadKhachHangFromDb();
        }
            return listKhachHang;
    }
    public void loadKhachHangFromDb() {
        HashMap<String,User> newDs = new HashMap<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("khachhang",new String[] {"makhachhang","ngay","chisodien"},null,null,null,null,null);
        while(cursor.moveToNext()) {
            int makhachhang_col = cursor.getColumnIndex("makhachhang");
            int ngay_col = cursor.getColumnIndex("ngay");
            int chisodien_col = cursor.getColumnIndex("chisodien");
            String makhachhang = cursor.getString(makhachhang_col);
            String ngay = cursor.getString(ngay_col);
            int chisodien = cursor.getInt(chisodien_col);
            if(newDs.get(makhachhang)==null) {
//                didnt have this customer yet
                final SuDung newSuDung = new SuDung(chisodien,ngay);
                ArrayList<SuDung> emptySet = new ArrayList<SuDung>();
                emptySet.add(newSuDung);
                User newKhachHang = new User(makhachhang,emptySet);
                newDs.put(makhachhang,newKhachHang);
            }
            else
            {
                newDs.get(makhachhang).addSuDung(ngay,chisodien);
            }
        }

        db.close();
        cursor.close();
        listKhachHang.setValue(newDs);
    }
    public void addKhachHang(String makh,String ngay,int chisodien) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("chisodien",chisodien);
        values.put("ngay",ngay);
        values.put("makhachhang",makh);
        long id = db.insertWithOnConflict("khachhang",
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

        this.currentSuDung.setValue(new User(makh,app));
    }
}
