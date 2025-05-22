package com.example.appquanlitro;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class HoSo_NguoiThueTro_Admin_Activity extends AppCompatActivity {
    private Database database;
    private GridView lv;
    private HoSo_NguoiThueTro_Adapter adapter;
    private java.util.List<HoSo_NguoiThueTro> List = new ArrayList<>();
    ImageButton add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hosonguoithuetro_admin);
add=findViewById(R.id.btnAdd);


        lv = findViewById(R.id.listview);
        adapter = new HoSo_NguoiThueTro_Adapter(getApplicationContext(), R.layout.ds_hosonguoithuetro_admin, List);
        lv.setAdapter(adapter);

        database = new Database(this, "quanlitro.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS hosothuetro (maid INTEGER PRIMARY KEY AUTOINCREMENT, mahoso TEXT, hovaten TEXT,ngaysinh TEXT, cccd TEXT, quequan TEXT, sdt TEXT, id INTEGER, giatien TEXT, hinhthucthue TEXT, ngaybatdau TEXT, ngayketthuc TEXT, xacnhanhopdong TEXT,trangthai TEXT)");

        Loaddulieuphongtro();
add.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent intent = new Intent(HoSo_NguoiThueTro_Admin_Activity.this, Them_HoSoNguoiThueTro_Activity.class);
startActivity(intent);

    }
});

    }

    @Override
    protected void onResume() {
        super.onResume();
        Loaddulieuphongtro(); // Tải lại dữ liệu mỗi khi activity được hiển thị
    }

    private void Loaddulieuphongtro() {
        Cursor data = database.GetData("SELECT * FROM hosothuetro");
        List.clear();


        while (data.moveToNext()) {
            String maid = data.getString(0);
            String mahoso = data.getString(1);
            String hovaten = data.getString(2);
            String ngaysinh = data.getString(3);
            String cccd = data.getString(4);
            String queuqna = data.getString(5);
            String sdt = data.getString(6);
            String id = data.getString(7);
            String giatien = data.getString(8);
            String hinhthucthue = data.getString(9);
            String thoigianbatdauthue = data.getString(10);
            String thoigianketthucthue = data.getString(11);
            String xacnhanhopdong = data.getString(12);
String trangthai=data.getString(13);

            // Tạo đối tượng PhongTro và thêm vào danh sách
            HoSo_NguoiThueTro tk = new HoSo_NguoiThueTro(maid,mahoso,hovaten,ngaysinh,cccd,queuqna,sdt,id,giatien,hinhthucthue,thoigianbatdauthue,thoigianketthucthue,xacnhanhopdong,trangthai );
            List.add(tk);
        }
        data.close();
        adapter.notifyDataSetChanged();
    }
}