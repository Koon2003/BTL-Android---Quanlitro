package com.example.appquanlitro;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TienNuoc_Admin_Activity extends AppCompatActivity {
    private Database database;
    private GridView lv;
    private TienNuoc_Adapter adapter;
    private ArrayList<TienNuoc> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiennuoc_admin);


        lv = findViewById(R.id.listview);
        ImageButton add = findViewById(R.id.btnAdd);
        // Khởi tạo adapter
        adapter = new TienNuoc_Adapter(this, R.layout.ds_tiennuoc_admin, list);
        lv.setAdapter(adapter);

        // Khởi tạo database
        database = new Database(this, "quanlitro.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tiennuoc (idnuoc INTEGER PRIMARY KEY AUTOINCREMENT,  id INTEGER, dongtiennuocthangnam TEXT, sokhoitieuthu TEXT, giatien TEXT, tongtien TEXT, trangthai TEXT)");



        loadData();

        add.setOnClickListener(view -> {
            Intent intent = new Intent(TienNuoc_Admin_Activity.this, Them_TienNuoc_Activity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // Tải lại dữ liệu khi quay lại activity
    }

    private void loadData() {
        Cursor dataPhongTro = database.GetData("SELECT * FROM tiennuoc");
        list.clear();

        while (dataPhongTro.moveToNext()) {
            String iddien = dataPhongTro.getString(0);
            String idphong = dataPhongTro.getString(1);
            String dongtiendienthangnam = dataPhongTro.getString(2);
            String sokwtieuthu = dataPhongTro.getString(3);
            String giatien = dataPhongTro.getString(4);
            String tongtien = dataPhongTro.getString(5);
            String trangthai = dataPhongTro.getString(6);


            // Tạo đối tượng TienPhongConLai và thêm vào danh sách
            TienNuoc tienPhong = new TienNuoc(iddien, idphong, dongtiendienthangnam, sokwtieuthu, giatien, tongtien,trangthai);
            list.add(tienPhong);
        }
        dataPhongTro.close();
        adapter.notifyDataSetChanged();
    }
}