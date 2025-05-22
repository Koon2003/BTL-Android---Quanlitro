package com.example.appquanlitro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class HienThi_TienCocPhong_Admin_Activity extends AppCompatActivity {

    private Database database;
    private EditText edtMahoso, edtHovaten, edtHinhThucCoc, edtSoTienDaCoc, edtSoTienConLai;
    private TextView txtIdPhong, txtGiaTien, txtCccd, txtNgaySinh, txtSdt;

    String maidcoc; // ID của tiền cọc

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hienthi_tiencocphong_admin);

        // Khởi tạo database
        database = new Database(this, "quanlitro.db", null, 1);

        // Ánh xạ view
        edtMahoso = findViewById(R.id.mahoso);
        txtIdPhong = findViewById(R.id.id);
        txtGiaTien = findViewById(R.id.giatien);
        edtHovaten = findViewById(R.id.hovaten);
        txtCccd = findViewById(R.id.cccd);
        txtNgaySinh = findViewById(R.id.ngaysinh);
        txtSdt = findViewById(R.id.sdt);
        edtHinhThucCoc = findViewById(R.id.hinhthuccoc);
        edtSoTienDaCoc = findViewById(R.id.sotiendacoc);
        edtSoTienConLai = findViewById(R.id.sotienconlai);


        // Nhận dữ liệu từ Intent
        maidcoc = getIntent().getStringExtra("maidcoc");
        String mahoso = getIntent().getStringExtra("mahoso");
        String idPhong = getIntent().getStringExtra("id");
        String giaTien = getIntent().getStringExtra("giatien");
        String hovaten = getIntent().getStringExtra("hovaten");
        String ngaysinh = getIntent().getStringExtra("ngaysinh");
        String cccd = getIntent().getStringExtra("cccdnguoinop");
        String sdt = getIntent().getStringExtra("sdt");
        String hinhthuccoc = getIntent().getStringExtra("hinhthuccoc");
        String sotiendanopcoc = getIntent().getStringExtra("sotiendanopcoc");
        String sotienconlai = getIntent().getStringExtra("sotienconlai");

        // Thiết lập dữ liệu vào các trường
        edtMahoso.setText(mahoso);
        txtIdPhong.setText(String.valueOf(idPhong));
        txtGiaTien.setText(giaTien);
        edtHovaten.setText(hovaten);
        txtCccd.setText(cccd);
        txtNgaySinh.setText(ngaysinh);
        txtSdt.setText(sdt);
        edtHinhThucCoc.setText(hinhthuccoc);
        edtSoTienDaCoc.setText(sotiendanopcoc);
        edtSoTienConLai.setText(sotienconlai);
    }
}