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

public class TienCocPhong_NguoiDung_Activity extends AppCompatActivity {

    private Database database;
    private ListView lv;
    private TienCocPhong_NguoiDung_Adapter adapter;
    private java.util.List<TienCocPhong> List = new ArrayList<>();

    String tendn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiencocphong_nguoidung);


        lv = findViewById(R.id.listview);
        adapter = new TienCocPhong_NguoiDung_Adapter(getApplicationContext(), R.layout.ds_tiencocphong_nguoidung, List);
        lv.setAdapter(adapter);

        database = new Database(this, "quanlitro.db", null, 1);

        database.QueryData("CREATE TABLE IF NOT EXISTS tiencocphong (maidcoc INTEGER PRIMARY KEY AUTOINCREMENT, mahoso TEXT, id INTEGER, giatien TEXT, hovaten TEXT, ngaysinh TEXT, cccdnguoinop TEXT, sdt TEXT, hinhthuccoc TEXT, sotiendanopcoc TEXT, sotienconlai TEXT)");
        TextView textTendn=findViewById(R.id.tendn);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        tendn = sharedPreferences.getString("tendn", null);

        // Kiểm tra tên đăng nhập
        if (tendn != null) {
            textTendn.setText(tendn);
        } else {
            Intent intent = new Intent(TienCocPhong_NguoiDung_Activity.this, Login_Activity.class);
            startActivity(intent);
            finish(); // Kết thúc activity nếu chưa đăng nhập
            return;
        }
        // Tải dữ liệu từ database
        LoaddulieuTiencocphong();




    }

    @Override
    protected void onResume() {
        super.onResume();
        LoaddulieuTiencocphong(); // Tải lại dữ liệu khi quay lại activity
    }

    private void LoaddulieuTiencocphong() {
        // Bước 1: Kiểm tra tên đăng nhập trong bảng taikhoan
        Cursor cursorTaiKhoan = database.GetData("SELECT * FROM taikhoan WHERE tendn = ?", new String[]{tendn});

        if (cursorTaiKhoan.moveToFirst()) {
            // Lấy thông tin từ bảng taikhoan
            int indexHoVaTen = cursorTaiKhoan.getColumnIndex("hovaten");
            if (indexHoVaTen != -1) {
                String hovaten = cursorTaiKhoan.getString(indexHoVaTen);

                // Bước 2: Lấy mahoso từ bảng hosothuetro
                Cursor cursorHoSo = database.GetData("SELECT mahoso FROM hosothuetro WHERE hovaten = ?", new String[]{hovaten});

                ArrayList<String> maHoSoList = new ArrayList<>();
                while (cursorHoSo.moveToNext()) {
                    int indexMaHoSo = cursorHoSo.getColumnIndex("mahoso");
                    if (indexMaHoSo != -1) {
                        maHoSoList.add(cursorHoSo.getString(indexMaHoSo));
                    }
                }
                cursorHoSo.close();

                // Bước 3: Lấy dữ liệu từ bảng tiencocphong theo mahoso
                List.clear();
                for (String mahoso : maHoSoList) {
                    Cursor dataTienCoc = database.GetData("SELECT * FROM tiencocphong WHERE mahoso = ?", new String[]{mahoso});
                    while (dataTienCoc.moveToNext()) {
                        String maidcoc = dataTienCoc.getString(0);
                        String idPhong = dataTienCoc.getString(2);
                        String giaTien = dataTienCoc.getString(3);
                        String hoVaTen = dataTienCoc.getString(4);
                        String ngaySinh = dataTienCoc.getString(5);
                        String cccd = dataTienCoc.getString(6);
                        String sdt = dataTienCoc.getString(7);
                        String hinhThucCoc = dataTienCoc.getString(8);
                        String soTienDaNopCoc = dataTienCoc.getString(9);
                        String soTienConLai = dataTienCoc.getString(10);

                        // Tạo đối tượng TienCocPhong và thêm vào danh sách
                        TienCocPhong tienCocPhong = new TienCocPhong(maidcoc, mahoso, idPhong, giaTien, hoVaTen, ngaySinh, cccd, sdt, hinhThucCoc, soTienDaNopCoc, soTienConLai);
                        List.add(tienCocPhong);
                    }
                    dataTienCoc.close();
                }
            }
        }

        cursorTaiKhoan.close();
        adapter.notifyDataSetChanged(); // Cập nhật giao diện ListView
    }
}
