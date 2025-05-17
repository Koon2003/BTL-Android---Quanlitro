package com.example.appquanlitro;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Sua_TienNuoc_Activity extends AppCompatActivity {

    EditText id, dongtiennuocthangnam, sokhoitieuthu, giatien, tongtien, trangthai;
    Button btnUpdate, btnHuy;
    TextView idnuoc;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_tiennuoc);

        // Ánh xạ các view
        idnuoc = findViewById(R.id.idnuoc);
        id = findViewById(R.id.id);
        dongtiennuocthangnam = findViewById(R.id.dongtiennuocthangnam);
        sokhoitieuthu = findViewById(R.id.sokhoitieuthu);
        giatien = findViewById(R.id.giatien);
        tongtien = findViewById(R.id.tongtien);
        trangthai = findViewById(R.id.trangthai);
        btnUpdate = findViewById(R.id.btnSave);
        btnHuy = findViewById(R.id.btnHuy);

        // Khởi tạo database
        database = new Database(this, "quanlitro.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tiennuoc (idnuoc INTEGER PRIMARY KEY AUTOINCREMENT, id INTEGER, dongtiennuocthangnam TEXT, sokhoitieuthu TEXT, giatien TEXT, tongtien TEXT, trangthai TEXT)");

        // Nhận dữ liệu từ Intent
        String idnuoc1 = getIntent().getStringExtra("idnuoc");
        String id1 = getIntent().getStringExtra("id");
        String dongtiennuocthangnam1 = getIntent().getStringExtra("dongtiennuocthangnam");
        String sokhoitieuthu1 = getIntent().getStringExtra("sokhoitieuthu");
        String giatien1 = getIntent().getStringExtra("giatien");
        String tongtien1 = getIntent().getStringExtra("tongtien");
        String trangthai1 = getIntent().getStringExtra("trangthai");

        // Thiết lập dữ liệu vào các trường
        idnuoc.setText(idnuoc1);
        id.setText(id1);
        dongtiennuocthangnam.setText(dongtiennuocthangnam1);
        sokhoitieuthu.setText(sokhoitieuthu1);
        giatien.setText(giatien1);
        tongtien.setText(tongtien1);
        trangthai.setText(trangthai1);

        // Xử lý sự kiện để tính tổng tiền
        sokhoitieuthu.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                calculateTotal();
            }
        });

        giatien.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                calculateTotal();
            }
        });

        // Xử lý sự kiện nút chọn trạng thái
        trangthai.setOnClickListener(v -> showTrangThaiDialog());

        btnUpdate.setOnClickListener(view -> {
            // Xử lý lưu dữ liệu
            updateTienNuoc();
        });

        // Xử lý sự kiện cho trường dongtiennuocthangnam
        dongtiennuocthangnam.setOnClickListener(v -> showMonthYearPicker());

        btnHuy.setOnClickListener(view -> finish());
    }

    // Hiển thị DatePicker chỉ cho phép chọn tháng và năm
    private void showMonthYearPicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = 1;

        // Tạo một DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            String formattedDate = String.format("%02d/%04d", selectedMonth + 1, selectedYear);
            dongtiennuocthangnam.setText(formattedDate);
        }, year, month, day);

        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getDatePicker().setSpinnersShown(true);

        datePickerDialog.show();
    }

    private void calculateTotal() {
        try {
            int soKhoa = Integer.parseInt(sokhoitieuthu.getText().toString());
            int giaTien = Integer.parseInt(giatien.getText().toString());
            int tongTien = soKhoa * giaTien;
            tongtien.setText(String.valueOf(tongTien));
        } catch (NumberFormatException e) {
            tongtien.setText("0");
        }
    }

    private void updateTienNuoc() {
        String idnuocText = idnuoc.getText().toString();
        String idText = id.getText().toString();
        String dongTienNuocThangNamText = dongtiennuocthangnam.getText().toString();
        String sokwText = sokhoitieuthu.getText().toString();
        String giaTienText = giatien.getText().toString();
        String tongTienText = tongtien.getText().toString();
        String trangThaiText = trangthai.getText().toString();

        if (idnuocText.isEmpty() || dongTienNuocThangNamText.isEmpty() || sokwText.isEmpty() || giaTienText.isEmpty() || tongTienText.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Câu lệnh cập nhật
        database.QueryData("UPDATE tiennuoc SET id = " + idText +
                ", dongtiennuocthangnam = '" + dongTienNuocThangNamText +
                "', sokhoitieuthu = '" + sokwText +
                "', giatien = '" + giaTienText +
                "', tongtien = '" + tongTienText +
                "', trangthai = '" + trangThaiText +
                "' WHERE idnuoc = " + idnuocText + ";");

        Toast.makeText(this, "Cập nhật thông tin tiền nước thành công!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showTrangThaiDialog() {
        String[] trangThaiArray = {"Đã đóng", "Chưa Đóng"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn trạng thái bill")
                .setItems(trangThaiArray, (dialog, which) -> trangthai.setText(trangThaiArray[which]));
        builder.show();
    }
}