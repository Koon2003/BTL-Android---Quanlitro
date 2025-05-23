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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Sua_TienDien_Activity extends AppCompatActivity {

    EditText id, dongtiendienthangnam, sokwtieuthu, giatien, tongtien, trangthai;
    Button btnUpdate, btnHuy;
TextView iddien;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_tiendien);

        // Ánh xạ các view
        iddien = findViewById(R.id.iddien);
        id = findViewById(R.id.id);
        dongtiendienthangnam = findViewById(R.id.dongtiendienthangnam);
        sokwtieuthu = findViewById(R.id.sokwtieuthu);
        giatien = findViewById(R.id.giatien);
        tongtien = findViewById(R.id.tongtien);
        trangthai = findViewById(R.id.trangthai);
        btnUpdate = findViewById(R.id.btnSave);
        btnHuy = findViewById(R.id.btnHuy);

        // Khởi tạo database
        database = new Database(this, "quanlitro.db", null, 1);

        // Nhận dữ liệu từ Intent
        String iddien1 = getIntent().getStringExtra("iddien");
        String id1 = getIntent().getStringExtra("id");
        String dongtiendienthangnam1 = getIntent().getStringExtra("dongtiendienthangnam");
        String sokwtieuthu1 = getIntent().getStringExtra("sokwtieuthu");
        String giatien1 = getIntent().getStringExtra("giatien");
        String tongtien1 = getIntent().getStringExtra("tongtien");
        String trangthai1 = getIntent().getStringExtra("trangthai");


        // Thiết lập dữ liệu vào các trường
        iddien.setText(iddien1);
        id.setText(String.valueOf(id1));
        dongtiendienthangnam.setText(dongtiendienthangnam1);
        sokwtieuthu.setText(sokwtieuthu1);
        giatien.setText(giatien1);
        tongtien.setText(tongtien1);
        trangthai.setText(trangthai1);



        // Xử lý sự kiện nút chọn trạng thái
        trangthai.setOnClickListener(v -> showTrangThaiDialog());

        btnUpdate.setOnClickListener(view -> {
            // Xử lý lưu dữ liệu
            updateTienDien();
        });
        // Xử lý sự kiện cho trường dongtiendienthangnam
        dongtiendienthangnam.setOnClickListener(v -> showMonthYearPicker());

        btnHuy.setOnClickListener(view -> {
            Intent intent = new Intent(Sua_TienDien_Activity.this, TienDien_Admin_Activity.class);
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                // Chuyển selectedMonth từ 0-11 sang 1-12
                String formattedDate = String.format("%02d/%04d", selectedMonth + 1, selectedYear);
                dongtiendienthangnam.setText(formattedDate);
            }
        }, year, month, day);

        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getDatePicker().setSpinnersShown(true);

        datePickerDialog.show();
    }
    private void calculateTotal() {
        try {
            int soKWtieuThu = Integer.parseInt(sokwtieuthu.getText().toString());
            int giaTien = Integer.parseInt(giatien.getText().toString());
            int tongTien = soKWtieuThu * giaTien;
            tongtien.setText(String.valueOf(tongTien));
        } catch (NumberFormatException e) {
            // Xử lý khi không có giá trị hợp lệ
            tongtien.setText("0");
        }
    }

    private void updateTienDien() {
        // Lưu dữ liệu vào bảng tiendien
        String iddienText = iddien.getText().toString();
        String idText = id.getText().toString();
        String dongTienDienThangNamText = dongtiendienthangnam.getText().toString();
        String sokwText = sokwtieuthu.getText().toString();
        String giaTienText = giatien.getText().toString();
        String tongTienText = tongtien.getText().toString();
        String trangThaiText = trangthai.getText().toString();

        if (iddienText.isEmpty() || dongTienDienThangNamText.isEmpty() || sokwText.isEmpty() || giaTienText.isEmpty() || tongTienText.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Câu lệnh cập nhật
        database.QueryData("UPDATE tiendien SET id = " + idText +
                ", dongtiendienthangnam = '" + dongTienDienThangNamText +
                "', sokwtieuthu = " + sokwText +
                ", giatien = " + giaTienText +
                ", tongtien = " + tongTienText +
                ", trangthai = '" + trangThaiText +
                "' WHERE iddien = " + iddienText + ";");

        Toast.makeText(this, "Cập nhật thông tin tiền điện thành công!", Toast.LENGTH_SHORT).show();
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
        List<String> danhSachId = new ArrayList<>(); // Danh sách để lưu ID

        while (cursor.moveToNext()) {
            danhSachId.add(cursor.getString(0)); // Lưu ID
            danhSachPhong.add(cursor.getString(1)); // Lưu tên phòng
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