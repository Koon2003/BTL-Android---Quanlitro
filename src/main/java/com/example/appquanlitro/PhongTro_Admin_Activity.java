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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PhongTro_Admin_Activity extends AppCompatActivity {
    private Database database;
    private GridView lv;
    private PhongTro_Adapter adapter;
    private List<PhongTro> List = new ArrayList<>();
    ImageButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phongtro_admin);

        add = findViewById(R.id.btnAdd);
        lv = findViewById(R.id.listview);
        adapter = new PhongTro_Adapter(getApplicationContext(), R.layout.ds_phongtro, List);
        lv.setAdapter(adapter);

        database = new Database(this, "quanlitro.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS phongtro(id INTEGER PRIMARY KEY AUTOINCREMENT, tenphong TEXT, dientich TEXT, mota TEXT, giatien TEXT, anh1Path TEXT, anh2Path TEXT, anh3Path TEXT, anh4Path TEXT, anh5Path TEXT)");

        Loaddulieuphongtro();

        add.setOnClickListener(view -> {
            Intent intent = new Intent(PhongTro_Admin_Activity.this, Them_PhongTro_Activity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Loaddulieuphongtro(); // Tải lại dữ liệu mỗi khi activity được hiển thị
    }

    private void Loaddulieuphongtro() {
        Cursor dataPhongTro = database.GetData("SELECT * FROM phongtro");
        List.clear();
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        while (dataPhongTro.moveToNext()) {
            String id = dataPhongTro.getString(0);
            String tenphong = dataPhongTro.getString(1);
            String dientich = dataPhongTro.getString(2);
            String mota = dataPhongTro.getString(3);
            double giatienDouble = dataPhongTro.getDouble(4);
            String giatien = decimalFormat.format(giatienDouble);

            // Lấy đường dẫn ảnh từ cơ sở dữ liệu
            String img1Path = dataPhongTro.getString(5);
            String img2Path = dataPhongTro.getString(6);
            String img3Path = dataPhongTro.getString(7);
            String img4Path = dataPhongTro.getString(8);
            String img5Path = dataPhongTro.getString(9);

            // Tạo đối tượng PhongTro và thêm vào danh sách
            PhongTro phongTro = new PhongTro(id, tenphong, dientich, mota, giatien, img1Path, img2Path, img3Path, img4Path, img5Path);
            List.add(phongTro);
        }
        dataPhongTro.close();
        adapter.notifyDataSetChanged();
    }
}