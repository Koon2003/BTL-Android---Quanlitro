package com.example.appquanlitro;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    //truy vấn không trả kết quả
    public void QueryData(String sql){
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL(sql);

    }

    //truy vấn có kết quả
    public Cursor GetData(String sql){
        SQLiteDatabase database=getReadableDatabase();
        return database.rawQuery(sql,null);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE IF NOT EXISTS datlichxemphongtro (id INTEGER PRIMARY KEY AUTOINCREMENT, tendn VARCHAR(20) ,  hovaten VARCHAR(50),  quequan VARCHAR(50), sdt VARCHAR(15), ngayhen TEXT,trangthai TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS taikhoan (tendn VARCHAR(20) PRIMARY KEY, matkhau VARCHAR(50), hovaten VARCHAR(50),ngaysinh VARCHAR(20), cccd VARCHAR(20), quequan VARCHAR(50), sdt VARCHAR(15), quyen VARCHAR(50))");
        db.execSQL("CREATE TABLE IF NOT EXISTS hosothuetro (maid INTEGER PRIMARY KEY AUTOINCREMENT, mahoso TEXT, hovaten TEXT,ngaysinh TEXT, cccd TEXT, quequan TEXT, sdt TEXT, id INTEGER, giatien TEXT, hinhthucthue TEXT, ngaybatdau TEXT, ngayketthuc TEXT, xacnhanhopdong TEXT,trangthai TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS tiencocphong (maidcoc INTEGER PRIMARY KEY AUTOINCREMENT, mahoso TEXT, id INTEGER, giatien TEXT, hovaten TEXT, ngaysinh TEXT, cccdnguoinop TEXT, sdt TEXT, hinhthuccoc TEXT, sotiendanopcoc TEXT, sotienconlai TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS tienphongconlai (maidtienconlai INTEGER PRIMARY KEY AUTOINCREMENT, mahoso TEXT, id INTEGER, giatien TEXT, hovaten TEXT, ngaysinh TEXT, cccdnguoinop TEXT, sdt TEXT, sotienconlai TEXT, trangthai TEXT)");

    }

}
