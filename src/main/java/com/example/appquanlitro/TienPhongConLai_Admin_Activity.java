package com.example.appquanlitro;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TienPhongConLai_Admin_Activity extends AppCompatActivity {

    private Database database;
    private ListView lv;
    private TienPhongConLai_Adapter adapter;
    private ArrayList<TienPhongConLai> list = new ArrayList<>();
    private ImageButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienphongconlai_admin);

        add = findViewById(R.id.btnAdd);
        lv = findViewById(R.id.listview);

        // Khởi tạo adapter
        adapter = new TienPhongConLai_Adapter(this, R.layout.ds_tienphongconlai_admin, list);
        lv.setAdapter(adapter);

        // Khởi tạo database
        database = new Database(this, "quanlitro.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tienphongconlai (maidtienconlai INTEGER PRIMARY KEY AUTOINCREMENT, mahoso TEXT, id INTEGER, giatien TEXT, hovaten TEXT, ngaysinh TEXT, cccdnguoinop TEXT, sdt TEXT, sotienconlai TEXT, trangthai TEXT)");

        loadData();

        // Thiết lập sự kiện click cho nút thêm
        add.setOnClickListener(view -> {
            Intent intent = new Intent(TienPhongConLai_Admin_Activity.this, Them_TienPhongConLai_Activity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // Tải lại dữ liệu khi quay lại activity
    }

    private void loadData() {
        Cursor dataPhongTro = database.GetData("SELECT * FROM tienphongconlai");
        list.clear();

        while (dataPhongTro.moveToNext()) {
            String maidtienconlai = dataPhongTro.getString(0);
            String mahoso = dataPhongTro.getString(1);
            String idPhong = dataPhongTro.getString(2);
            String giaTien = dataPhongTro.getString(3);
            String hoVaTen = dataPhongTro.getString(4);
            String ngaySinh = dataPhongTro.getString(5);
            String cccd = dataPhongTro.getString(6);
            String sdt = dataPhongTro.getString(7);
            String soTienConLai = dataPhongTro.getString(8);
            String trangthai = dataPhongTro.getString(9);

            // Tạo đối tượng TienPhongConLai và thêm vào danh sách
            TienPhongConLai tienPhong = new TienPhongConLai(maidtienconlai, mahoso, idPhong, giaTien, hoVaTen, ngaySinh, cccd, sdt, soTienConLai, trangthai);
            list.add(tienPhong);
        }
        dataPhongTro.close();
        adapter.notifyDataSetChanged(); // Cập nhật giao diện ListView
    }
}