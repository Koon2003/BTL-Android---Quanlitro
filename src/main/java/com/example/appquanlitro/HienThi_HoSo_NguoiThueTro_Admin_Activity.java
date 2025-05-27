package com.example.appquanlitro;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HienThi_HoSo_NguoiThueTro_Admin_Activity extends AppCompatActivity {

    EditText mahoso,phongthue,cccd,giatien,hinhthucthue,ngaybatdauthue,ngayketthucthue,trangthai;
    TextView maid,hovaten,sdt,quequan,ngaysinh;

    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hienthi_hoso_nguoithuetro_admin);
        maid=findViewById(R.id.maid);
        phongthue=findViewById(R.id.id);
        mahoso=findViewById(R.id.mahoso);
        cccd=findViewById(R.id.cccd);
        quequan=findViewById(R.id.quequan);
        sdt=findViewById(R.id.sdt);
        hovaten=findViewById(R.id.hovaten);

        giatien=findViewById(R.id.giatien);
        hinhthucthue=findViewById(R.id.hinhthucthue);
        ngaybatdauthue=findViewById(R.id.ngaybatdauthue);
        ngayketthucthue=findViewById(R.id.ngayketthucthue);
        ngaysinh=findViewById(R.id.ngaysinh);
        trangthai=findViewById(R.id.trangthai);
        database = new Database(this, "quanlitro.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS hosothuetro (maid INTEGER PRIMARY KEY AUTOINCREMENT, mahoso TEXT, hovaten TEXT,ngaysinh TEXT, cccd TEXT, quequan TEXT, sdt TEXT, id INTEGER, giatien TEXT, hinhthucthue TEXT, ngaybatdau TEXT, ngayketthuc TEXT, xacnhanhopdong TEXT,trangthai TEXT)");

        // Lấy dữ liệu từ Intent
        String maid1 = getIntent().getStringExtra("maid");
        if (maid1 == null) {
            Toast.makeText(this, "ID hồ sơ không hợp lệ!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        maid.setText("maid: " + maid1);
        mahoso.setText(getIntent().getStringExtra("mahoso"));
        hovaten.setText(getIntent().getStringExtra("hovaten"));
        ngaysinh.setText(getIntent().getStringExtra("ngaysinh"));
        cccd.setText(getIntent().getStringExtra("cccd"));
        quequan.setText(getIntent().getStringExtra("quequan"));
        sdt.setText(getIntent().getStringExtra("sdt"));
        phongthue.setText(getIntent().getStringExtra("tenphong"));
        giatien.setText(getIntent().getStringExtra("giatien"));
        hinhthucthue.setText(getIntent().getStringExtra("hinhthucthue"));
        ngaybatdauthue.setText(getIntent().getStringExtra("ngaybatdau"));
        ngayketthucthue.setText(getIntent().getStringExtra("ngayketthuc"));
        trangthai.setText(getIntent().getStringExtra("trangthai"));

    }


}







