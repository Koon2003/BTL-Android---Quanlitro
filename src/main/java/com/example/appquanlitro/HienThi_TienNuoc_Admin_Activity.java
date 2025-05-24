package com.example.appquanlitro;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class HienThi_TienNuoc_Admin_Activity extends AppCompatActivity {

    EditText id, dongtiennuocthangnam, sokhoitieuthu, giatien, tongtien, trangthai;

    TextView idnuoc;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hienthi_tiennuoc_admin);

        // Ánh xạ các view
        idnuoc = findViewById(R.id.idnuoc);
        id = findViewById(R.id.id);
        dongtiennuocthangnam = findViewById(R.id.dongtiennuocthangnam);
        sokhoitieuthu = findViewById(R.id.sokhoitieuthu);
        giatien = findViewById(R.id.giatien);
        tongtien = findViewById(R.id.tongtien);
        trangthai = findViewById(R.id.trangthai);


        // Khởi tạo database
        database = new Database(this, "quanlitro.db", null, 1);

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


    }


}