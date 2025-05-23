package com.example.appquanlitro;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class TienCocPhong_Admin_Activity extends AppCompatActivity {

    private Database database;
    private ListView lv;
    private TienCocPhong_Adapter adapter;
    private List<TienCocPhong> List = new ArrayList<>();
    private ImageButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiencocphong_admin);

        add = findViewById(R.id.btnAdd);
        lv = findViewById(R.id.listview);
        adapter = new TienCocPhong_Adapter(getApplicationContext(), R.layout.ds_tiencocphong_admin, List);
        lv.setAdapter(adapter);

        database = new Database(this, "quanlitro.db", null, 1);

        // Tải dữ liệu từ database
        LoaddulieuTiencocphong();

        add.setOnClickListener(view -> {
            Intent intent = new Intent(TienCocPhong_Admin_Activity.this, Them_TienCocPhong_Activity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        LoaddulieuTiencocphong(); // Tải lại dữ liệu khi quay lại activity
    }

    private void LoaddulieuTiencocphong() {
        Cursor dataPhongTro = database.GetData("SELECT * FROM tiencocphong");
        List.clear();

        while (dataPhongTro.moveToNext()) {
            String maidcoc = dataPhongTro.getString(0);
            String mahoso = dataPhongTro.getString(1);
            String idPhong = dataPhongTro.getString(2);
            String giaTien = dataPhongTro.getString(3);
            String hoVaTen = dataPhongTro.getString(4);
            String ngaySinh = dataPhongTro.getString(5);
            String cccd = dataPhongTro.getString(6);
            String sdt = dataPhongTro.getString(7);
            String hinhThucCoc = dataPhongTro.getString(8);
            String soTienDaCoc = dataPhongTro.getString(9);
            String soTienConLai = dataPhongTro.getString(10);

            // Tạo đối tượng TienCocPhong và thêm vào danh sách
            TienCocPhong tienCocPhong = new TienCocPhong(maidcoc, mahoso, idPhong, giaTien, hoVaTen, ngaySinh, cccd, sdt, hinhThucCoc, soTienDaCoc, soTienConLai);
            List.add(tienCocPhong);
        }
        dataPhongTro.close();
        adapter.notifyDataSetChanged(); // Cập nhật giao diện ListView
    }
}
