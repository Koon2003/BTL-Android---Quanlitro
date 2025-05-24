package com.example.appquanlitro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class TienDien_NguoiDung_Activity extends AppCompatActivity {
    private Database database;
    private ListView lv;
    private TienDien_Adapter_NguoiDung adapter;
    private ArrayList<TienDien> list = new ArrayList<>();

    String tendn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiendien_nguoidung);


        lv = findViewById(R.id.listview);

        // Khởi tạo adapter
        adapter = new TienDien_Adapter_NguoiDung(this, R.layout.ds_tiendien_nguoidung, list);
        lv.setAdapter(adapter);

        // Khởi tạo database
        database = new Database(this, "quanlitro.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tiendien (iddien INTEGER PRIMARY KEY AUTOINCREMENT,  id INTEGER, dongtiendienthangnam TEXT, sokwtieuthu TEXT, giatien TEXT, tongtien TEXT, trangthai TEXT)");

        TextView textTendn=findViewById(R.id.tendn);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        tendn = sharedPreferences.getString("tendn", null);

        // Kiểm tra tên đăng nhập
        if (tendn != null) {
            textTendn.setText(tendn);
        } else {
            Intent intent = new Intent(TienDien_NguoiDung_Activity.this, Login_Activity.class);
            startActivity(intent);
            finish(); // Kết thúc activity nếu chưa đăng nhập
            return;
        }
        // Tải dữ liệu từ database
        loadData();




    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(); // Tải lại dữ liệu khi quay lại activity
    }

    private void loadData() {
        // Lấy CCCD từ bảng taikhoan dựa vào tendn
        Cursor cursorTaiKhoan = database.GetData("SELECT cccd FROM taikhoan WHERE tendn = ?", new String[]{tendn});

        if (cursorTaiKhoan != null && cursorTaiKhoan.moveToFirst()) {
            int indexCCCD = cursorTaiKhoan.getColumnIndex("cccd");
            if (indexCCCD != -1) {
                String cccd = cursorTaiKhoan.getString(indexCCCD);

                // Lấy danh sách id từ bảng hosothuetro dựa vào cccd
                Cursor cursorHoSo = database.GetData("SELECT id FROM hosothuetro WHERE cccd = ?", new String[]{cccd});
                ArrayList<String> idList = new ArrayList<>();

                if (cursorHoSo != null) {
                    while (cursorHoSo.moveToNext()) {
                        int indexId = cursorHoSo.getColumnIndex("id");
                        if (indexId != -1) {
                            idList.add(cursorHoSo.getString(indexId));
                        }
                    }
                    cursorHoSo.close();
                }

                // Lấy dữ liệu từ bảng tiendien theo id
                list.clear();
                for (String id : idList) {
                    Cursor dataTienDien = database.GetData("SELECT * FROM tiendien WHERE id = ?", new String[]{id});
                    if (dataTienDien != null) {
                        while (dataTienDien.moveToNext()) {
                            String iddien = dataTienDien.getString(0);
                            String idDien = dataTienDien.getString(1);
                            String dongtiendienthangnam = dataTienDien.getString(2);
                            String sokwtieuthu = dataTienDien.getString(3);
                            String giatien = dataTienDien.getString(4);
                            String tongtien = dataTienDien.getString(5);
                            String trangthai = dataTienDien.getString(6);

                            // Tạo đối tượng TienDien và thêm vào danh sách
                            TienDien tienDien = new TienDien(iddien, idDien, dongtiendienthangnam, sokwtieuthu, giatien, tongtien, trangthai);
                            list.add(tienDien);
                        }
                        dataTienDien.close();
                    }
                }
            }
        }

        if (cursorTaiKhoan != null) {
            cursorTaiKhoan.close();
        }

        adapter.notifyDataSetChanged();
    }

}