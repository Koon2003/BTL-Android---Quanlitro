package com.example.appquanlitro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TienPhongConLai_NguoiDung_Activity extends AppCompatActivity {

    private Database database;
    private ListView lv;
    private TienPhongConLai_Nguoidung_Adapter adapter;
    private ArrayList<TienPhongConLai> list = new ArrayList<>();

    String tendn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienphongconlai_nguoidung);


        lv = findViewById(R.id.listview);

        // Khởi tạo adapter
        adapter = new TienPhongConLai_Nguoidung_Adapter(this, R.layout.ds_tienphongconlai_nguoidung, list);
        lv.setAdapter(adapter);

        // Khởi tạo database
        database = new Database(this, "quanlitro.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tienphongconlai (maidtienconlai INTEGER PRIMARY KEY AUTOINCREMENT, mahoso TEXT, id INTEGER, giatien TEXT, hovaten TEXT, ngaysinh TEXT, cccdnguoinop TEXT, sdt TEXT, sotienconlai TEXT, trangthai TEXT)");
        TextView textTendn=findViewById(R.id.tendn);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        tendn = sharedPreferences.getString("tendn", null);

        // Kiểm tra tên đăng nhập
        if (tendn != null) {
            textTendn.setText(tendn);
        } else {
            Intent intent = new Intent(TienPhongConLai_NguoiDung_Activity.this, Login_Activity.class);
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
        Cursor cursorTaiKhoan = database.GetData("SELECT * FROM taikhoan WHERE tendn = ?", new String[]{tendn});

        if (cursorTaiKhoan != null && cursorTaiKhoan.moveToFirst()) {
            int indexHoVaTen = cursorTaiKhoan.getColumnIndex("hovaten");
            if (indexHoVaTen != -1) {
                String hovaten = cursorTaiKhoan.getString(indexHoVaTen);

                Cursor cursorHoSo = database.GetData("SELECT mahoso FROM hosothuetro WHERE hovaten = ?", new String[]{hovaten});
                ArrayList<String> maHoSoList = new ArrayList<>();

                if (cursorHoSo != null) {
                    while (cursorHoSo.moveToNext()) {
                        int indexMaHoSo = cursorHoSo.getColumnIndex("mahoso");
                        if (indexMaHoSo != -1) {
                            maHoSoList.add(cursorHoSo.getString(indexMaHoSo));
                        }
                    }
                    cursorHoSo.close();
                }

                // Lấy dữ liệu từ bảng tienphongconlai theo mahoso
                list.clear();
                for (String mahoso : maHoSoList) {
                    Cursor dataPhongTro = database.GetData("SELECT * FROM tienphongconlai WHERE mahoso = ?", new String[]{mahoso});
                    if (dataPhongTro != null) {
                        while (dataPhongTro.moveToNext()) {
                            String maidtienconlai = dataPhongTro.getString(0);
                            String idPhong = dataPhongTro.getString(2);
                            String giaTien = dataPhongTro.getString(3);
                            String hoVaTen = dataPhongTro.getString(4);
                            String ngaySinh = dataPhongTro.getString(5);
                            String cccd = dataPhongTro.getString(6);
                            String sdt = dataPhongTro.getString(7);
                            String soTienConLai = dataPhongTro.getString(8);
                            String trangthai = dataPhongTro.getString(9);

                            TienPhongConLai tienPhong = new TienPhongConLai(maidtienconlai, mahoso, idPhong, giaTien, hoVaTen, ngaySinh, cccd, sdt, soTienConLai, trangthai);
                            list.add(tienPhong);
                        }
                        dataPhongTro.close();
                    }
                }
            }
        }

        if (cursorTaiKhoan != null) {
            cursorTaiKhoan.close();
        }
        adapter.notifyDataSetChanged(); // Cập nhật giao diện ListView
    }
}