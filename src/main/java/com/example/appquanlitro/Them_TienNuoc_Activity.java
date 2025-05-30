package com.example.appquanlitro;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Them_TienNuoc_Activity extends AppCompatActivity {

    EditText id, dongtiennuocthangnam, sokhoitieuthu, giatien, tongtien, trangthai;
    Button btnAdd, btnHuy;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_tiennuoc);

        // Ánh xạ các view
        id = findViewById(R.id.id);
        dongtiennuocthangnam = findViewById(R.id.dongtiennuocthangnam);
        sokhoitieuthu = findViewById(R.id.sokhoitieuthu);
        giatien = findViewById(R.id.giatien);
        tongtien = findViewById(R.id.tongtien);
        trangthai = findViewById(R.id.trangthai);
        btnAdd = findViewById(R.id.btnSave);
        btnHuy = findViewById(R.id.btnHuy);

        // Khởi tạo database
        database = new Database(this, "quanlitro.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tiennuoc (idnuoc INTEGER PRIMARY KEY AUTOINCREMENT, id INTEGER, dongtiennuocthangnam TEXT, sokhoitieuthu TEXT, giatien TEXT, tongtien TEXT, trangthai TEXT)");


        // Xử lý sự kiện nút chọn trạng thái
        trangthai.setOnClickListener(v -> showTrangThaiDialog());

        btnAdd.setOnClickListener(view -> {
            addTienNuoc();
        });

        // Xử lý sự kiện cho trường dongtiennuocthangnam
        dongtiennuocthangnam.setOnClickListener(v -> showMonthYearPicker());

        btnHuy.setOnClickListener(view -> {
            Intent intent = new Intent(Them_TienNuoc_Activity.this, TienNuoc_Admin_Activity.class);
            startActivity(intent);
        });

        // Xử lý sự kiện khi nhấn vào ID
        id.setOnClickListener(v -> showDanhSachPhong());
    }

    // Hiển thị DatePicker chỉ cho phép chọn tháng và năm
    private void showMonthYearPicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = 1; // Chỉ định một ngày mặc định, có thể là 1

        // Tạo một DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            // Chuyển selectedMonth từ 0-11 sang 1-12
            String formattedDate = String.format("%02d/%04d", selectedMonth + 1, selectedYear);
            dongtiennuocthangnam.setText(formattedDate);
        }, year, month, day);

        // Ẩn lịch
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getDatePicker().setSpinnersShown(true); // Hiển thị spinner cho tháng và năm

        datePickerDialog.show();
    }


    private void addTienNuoc() {
        // Lưu dữ liệu vào bảng tiennuoc
        String idText = id.getText().toString();
        String dongTiennuocThangNamText = dongtiennuocthangnam.getText().toString();
        String sokhoiText = sokhoitieuthu.getText().toString();
        String giaTienText = giatien.getText().toString();
        String tongTienText = tongtien.getText().toString();
        String trangThaiText = trangthai.getText().toString();

        if (idText.isEmpty() || dongTiennuocThangNamText.isEmpty() || sokhoiText.isEmpty() || giaTienText.isEmpty() || tongTienText.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        database.QueryData("INSERT INTO tiennuoc (id, dongtiennuocthangnam, sokhoitieuthu, giatien, tongtien, trangthai) VALUES (" +
                idText + ", '" +
                dongTiennuocThangNamText + "', '" +
                sokhoiText + "', '" +
                giaTienText + "', '" +
                tongTienText + "', '" +
                trangThaiText + "');");

        Toast.makeText(this, "Thêm thông tin tiền nước thành công!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showTrangThaiDialog() {
        String[] trangThaiArray = {"Đã đóng", "Chưa Đóng"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn trạng thái bill")
                .setItems(trangThaiArray, (dialog, which) -> trangthai.setText(trangThaiArray[which]));
        builder.show();
    }

    private void showDanhSachPhong() {
        // Truy vấn để lấy tên phòng và ID
        Cursor cursor = database.GetData("SELECT id, tenphong FROM phongtro WHERE id IN (SELECT id FROM hosothuetro WHERE trangthai = 'Đang thuê')");
        List<String> danhSachPhong = new ArrayList<>();
        List<String> danhSachId = new ArrayList<>();

        if (cursor.moveToFirst()) { // Kiểm tra xem cursor có dữ liệu không
            do {
                danhSachId.add(cursor.getString(0)); // Lưu ID
                danhSachPhong.add(cursor.getString(1)); // Lưu tên phòng
            } while (cursor.moveToNext());
        }
        cursor.close();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Danh sách phòng đang thuê")
                .setItems(danhSachPhong.toArray(new String[0]), (dialog, which) -> {
                    id.setText(danhSachId.get(which));
                });
        builder.show();
    }
}