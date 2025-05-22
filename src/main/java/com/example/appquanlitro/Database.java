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

    // Phương thức để chèn dữ liệu vào bảng phongtro
    public void insertDataPhongTro(String tenPhong, int dienTich, String moTa, double giaTien, List<String> imagePaths) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO phongtro (tenphong, dientich, mota, giatien, anh1Path, anh2Path, anh3Path, anh4Path, anh5Path) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);

        statement.bindString(1, tenPhong);
        statement.bindLong(2, dienTich);
        statement.bindString(3, moTa);
        statement.bindDouble(4, giaTien);

        // Gán giá trị cho các đường dẫn ảnh
        for (int i = 0; i < 5; i++) {
            if (i < imagePaths.size()) {
                statement.bindString(5 + i, imagePaths.get(i));
            } else {
                statement.bindNull(5 + i); // Gán null nếu không có hình ảnh
            }
        }

        statement.executeInsert();
        db.close();
    }

    public String getTenPhongById(String idPhong) {
        String tenPhong = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT tenphong FROM phongtro WHERE id = ?", new String[]{idPhong});

        if (cursor.moveToFirst()) {
            tenPhong = cursor.getString(0);
        }
        cursor.close();
        db.close();

        return tenPhong;
    }

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
        db.execSQL("CREATE TABLE IF NOT EXISTS tiennuoc (idnuoc INTEGER PRIMARY KEY AUTOINCREMENT,  id INTEGER, dongtiennuocthangnam TEXT, sokhoitieuthu TEXT, giatien TEXT, tongtien TEXT, trangthai TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS phongtro(id INTEGER PRIMARY KEY AUTOINCREMENT, tenphong TEXT, dientich TEXT, mota TEXT, giatien TEXT, anh1Path TEXT, anh2Path TEXT, anh3Path TEXT, anh4Path TEXT, anh5Path TEXT)");

    }
    public Cursor GetData(String query, String[] params) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, params);
    }
    public void QueryData(String query, String[] params) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query, params);
    }



    public int deletetienCocPhong(String tendn) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete("tiencocphong", "maidcoc = ?", new String[]{tendn});
        db.close();
        return rowsDeleted; // Trả về số hàng đã xóa
    }
    public int deletetienPhongConlai(String tendn) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete("tienphongconlai", "maidtienconlai = ?", new String[]{tendn});
        db.close();
        return rowsDeleted; // Trả về số hàng đã xóa
    }

    public int deletetienTienNuoc(String idnuoc) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete("tiennuoc", "idnuoc = ?", new String[]{idnuoc});
        db.close();
        return rowsDeleted; // Trả về số hàng đã xóa
    }

    public int deleteAllTienNuoc() {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = 0;
        try {
            db.beginTransaction();
            rowsDeleted = db.delete("tiennuoc", null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
        return rowsDeleted;
    }


    public String getTenPhongById(String idPhong) {
        String tenPhong = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT tenphong FROM phongtro WHERE id = ?", new String[]{idPhong});

        if (cursor.moveToFirst()) {
            tenPhong = cursor.getString(0);
        }
        cursor.close();
        db.close();

        return tenPhong;
    }


    public boolean themTienCoc(String mahoso, String idPhong, String giaTien, String hovaten,
                               String ngaySinh, String cccd, String sdt, String hinhThucCoc,
                               String soTienDaCoc, String soTienConLai) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO tiencocphong (mahoso, id, giatien, hovaten, ngaysinh, cccdnguoinop, sdt, hinhthuccoc, sotiendanopcoc, sotienconlai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);

        try {
            db.beginTransaction(); // Bắt đầu transaction

            // Bind giá trị vào statement
            statement.bindString(1, mahoso);
            statement.bindString(2, idPhong);
            statement.bindString(3, giaTien);
            statement.bindString(4, hovaten);
            statement.bindString(5, ngaySinh);
            statement.bindString(6, cccd);
            statement.bindString(7, sdt);
            statement.bindString(8, hinhThucCoc);
            statement.bindString(9, soTienDaCoc);
            statement.bindString(10, soTienConLai);

            statement.executeInsert(); // Thực hiện chèn dữ liệu
            db.setTransactionSuccessful(); // Đánh dấu transaction thành công
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            statement.close(); // Đóng statement
            db.endTransaction(); // Kết thúc transaction
            db.close(); // Đóng database
        }
    }

    public boolean themTienNopPhongCOnlai(String mahoso, String idPhong, String giaTien, String hovaten,
                                          String ngaySinh, String cccd, String sdt, String soTienConLai,String trangthai) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO tienphongconlai (mahoso, id, giatien, hovaten, ngaysinh, cccdnguoinop, sdt,  sotienconlai,trangthai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);

        try {
            db.beginTransaction(); // Bắt đầu transaction

            // Bind giá trị vào statement
            statement.bindString(1, mahoso);
            statement.bindString(2, idPhong);
            statement.bindString(3, giaTien);
            statement.bindString(4, hovaten);
            statement.bindString(5, ngaySinh);
            statement.bindString(6, cccd);
            statement.bindString(7, sdt);
            statement.bindString(8, soTienConLai);
            statement.bindString(9, trangthai);


            statement.executeInsert(); // Thực hiện chèn dữ liệu
            db.setTransactionSuccessful(); // Đánh dấu transaction thành công
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            statement.close(); // Đóng statement
            db.endTransaction(); // Kết thúc transaction
            db.close(); // Đóng database
        }
    }

    public boolean capNhatTienCoc(String maidcoc, String mahoso, String idPhong, String giaTien, String hovaten, String ngaySinh, String cccd, String sdt, String hinhThucCoc, String soTienDaCoc, String soTienConLai) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mahoso", mahoso);
        values.put("id", idPhong);
        values.put("giatien", giaTien);
        values.put("hovaten", hovaten);
        values.put("ngaysinh", ngaySinh);
        values.put("cccdnguoinop", cccd);
        values.put("sdt", sdt);
        values.put("hinhthuccoc", hinhThucCoc);
        values.put("sotiendanopcoc", soTienDaCoc);
        values.put("sotienconlai", soTienConLai);

        // Update row
        int result = db.update("tiencocphong", values, "maidcoc = ?", new String[]{String.valueOf(maidcoc)});
        db.close();
        return result > 0; // Trả về true nếu cập nhật thành công
    }

}
