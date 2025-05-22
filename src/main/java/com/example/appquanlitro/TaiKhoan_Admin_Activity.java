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

public class TaiKhoan_Admin_Activity extends AppCompatActivity {
    private Database database;
    private GridView lv;
    private TaiKhoan_Adapter adapter;
    private List<TaiKhoan> List = new ArrayList<>();
    ImageButton add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_taikhoan_admin);
        add = findViewById(R.id.btnAdd);
        lv = findViewById(R.id.listview);
        adapter = new TaiKhoan_Adapter(getApplicationContext(), R.layout.ds_taikhoan, List);
        lv.setAdapter(adapter);

        database = new Database(this, "quanlitro.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS taikhoan (tendn VARCHAR(20) PRIMARY KEY, matkhau VARCHAR(50), hovaten VARCHAR(50),ngaysinh VARCHAR(20), cccd VARCHAR(20), quequan VARCHAR(50), sdt VARCHAR(15), quyen VARCHAR(50))");

        Loaddulieutaikhoan();

        add.setOnClickListener(view -> {
            Intent intent = new Intent(TaiKhoan_Admin_Activity.this, Them_TaiKhoan_Activity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Loaddulieutaikhoan(); // Tải lại dữ liệu mỗi khi activity được hiển thị
    }

    private void Loaddulieutaikhoan() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String tendnAdmin = sharedPreferences.getString("tendn", null);

        if (tendnAdmin == null) return; // Tránh lỗi nếu chưa đăng nhập
        Cursor dataPhongTro = database.GetData("SELECT * FROM taikhoan WHERE tendn != '" + tendnAdmin + "'");
        List.clear();

        while (dataPhongTro.moveToNext()) {
            String tendn = dataPhongTro.getString(0);
            String matkhau = dataPhongTro.getString(1);
            String hovaten = dataPhongTro.getString(2);
            String ngaysinh = dataPhongTro.getString(3);
            String cccd = dataPhongTro.getString(4);
            String quequan = dataPhongTro.getString(5);
            String sdt = dataPhongTro.getString(6);
            String quyen = dataPhongTro.getString(7);

            // Tạo đối tượng PhongTro và thêm vào danh sách
            TaiKhoan tk = new TaiKhoan(tendn, matkhau, hovaten,ngaysinh, cccd, quequan, sdt, quyen);
            List.add(tk);
        }
        dataPhongTro.close();
        adapter.notifyDataSetChanged();
    }
}