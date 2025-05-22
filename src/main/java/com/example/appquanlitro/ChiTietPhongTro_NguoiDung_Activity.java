package com.example.appquanlitro;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChiTietPhongTro_NguoiDung_Activity extends AppCompatActivity {

    private TextView tenPhong, dienTich, moTa, giaTien;
    private ViewPager2 viewPagerImages;
    private AnhChiTiet_Adapter adapter;

    Database database;
String tendn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_phong_tro);

        // Ánh xạ view
        tenPhong = findViewById(R.id.tenphong);
        dienTich = findViewById(R.id.dientich);
        moTa = findViewById(R.id.mota);
        giaTien = findViewById(R.id.giatien);
        viewPagerImages = findViewById(R.id.viewPagerImages);


        database = new Database(this, "quanlitro.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS datlichxemphongtro (id INTEGER PRIMARY KEY AUTOINCREMENT, tendn VARCHAR(20) ,  hovaten VARCHAR(50),  quequan VARCHAR(50), sdt VARCHAR(15), ngayhen TEXT,trangthai TEXT)");

        TextView textTendn=findViewById(R.id.tendn);
        // Lấy tên đăng nhập từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        tendn = sharedPreferences.getString("tendn", null);

        // Kiểm tra tên đăng nhập
        if (tendn != null) {
            textTendn.setText(tendn);
        } else {
            Intent intent = new Intent(ChiTietPhongTro_NguoiDung_Activity.this, Login_Activity.class);
            startActivity(intent);
            finish(); // Kết thúc activity nếu chưa đăng nhập
            return;
        }
        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String tenPhongStr = intent.getStringExtra("TEN_PHONG");
        String dienTichStr = intent.getStringExtra("DIEN_TICH");
        String moTaStr = intent.getStringExtra("MO_TA");
        String giaTienStr = intent.getStringExtra("GIA_TIEN");
        String img1Path = intent.getStringExtra("IMG1_PATH");
        String img2Path = intent.getStringExtra("IMG2_PATH");
        String img3Path = intent.getStringExtra("IMG3_PATH");
        String img4Path = intent.getStringExtra("IMG4_PATH");
        String img5Path = intent.getStringExtra("IMG5_PATH");

        // Hiển thị dữ liệu
        tenPhong.setText(tenPhongStr);
        dienTich.setText(dienTichStr);
        moTa.setText(moTaStr);
        giaTien.setText(giaTienStr+" VNĐ");

        // Tạo danh sách hình ảnh
        List<String> imagePaths = new ArrayList<>();
        if (img1Path != null) imagePaths.add(img1Path);
        if (img2Path != null) imagePaths.add(img2Path);
        if (img3Path != null) imagePaths.add(img3Path);
        if (img4Path != null) imagePaths.add(img4Path);
        if (img5Path != null) imagePaths.add(img5Path);

        // Cấu hình ViewPager2
        adapter = new AnhChiTiet_Adapter(imagePaths);
        viewPagerImages.setAdapter(adapter);

    }
}