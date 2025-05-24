package com.example.appquanlitro;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class TienDien_Admin_Activity extends AppCompatActivity {

    private Database database;
    private GridView lv;
    private TienDien_Adapter adapter;
    private ArrayList<TienDien> list = new ArrayList<>();
    private ImageButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiendien_admin);

        add = findViewById(R.id.btnAdd);
        lv = findViewById(R.id.listview);

        // Khởi tạo adapter
        adapter = new TienDien_Adapter(this, R.layout.ds_tiendien_admin, list);
        lv.setAdapter(adapter);

        // Khởi tạo database
        database = new Database(this, "quanlitro.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tiendien (iddien INTEGER PRIMARY KEY AUTOINCREMENT,  id INTEGER, dongtiendienthangnam TEXT, sokwtieuthu TEXT, giatien TEXT, tongtien TEXT, trangthai TEXT)");

        // Tải dữ liệu từ database
        loadData();

        // Thiết lập sự kiện click cho nút thêm
        add.setOnClickListener(view -> {
            Intent intent = new Intent(TienDien_Admin_Activity.this, Them_TienDien_Activity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // Tải lại dữ liệu khi quay lại activity
    }

    private void loadData() {
        Cursor dataPhongTro = database.GetData("SELECT * FROM tiendien");
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
            TienDien tienPhong = new TienDien(iddien, idphong, dongtiendienthangnam, sokwtieuthu, giatien, tongtien,trangthai);
            list.add(tienPhong);
        }
        dataPhongTro.close();
        adapter.notifyDataSetChanged(); // Cập nhật giao diện ListView
    }
}