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

public class HienThi_TienDien_Admin_Activity extends AppCompatActivity {

    EditText id, dongtiendienthangnam, sokwtieuthu, giatien, tongtien, trangthai;

    TextView iddien;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hienthi_tiendien_admin);

        // Ánh xạ các view
        iddien = findViewById(R.id.iddien);
        id = findViewById(R.id.id);
        dongtiendienthangnam = findViewById(R.id.dongtiendienthangnam);
        sokwtieuthu = findViewById(R.id.sokwtieuthu);
        giatien = findViewById(R.id.giatien);
        tongtien = findViewById(R.id.tongtien);
        trangthai = findViewById(R.id.trangthai);


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


    }

}