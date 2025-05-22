package com.example.appquanlitro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TrangChu_NguoiDung_Activity extends AppCompatActivity {

    private GridView gridview;
    private PhongTroDeXuat_Adapter adapterGr;

    private ArrayList<PhongTro> Listgr;
    private Database database;
String tendn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu_nguoidung);


        TextView textTendn=findViewById(R.id.tendn);
        // Lấy tên đăng nhập từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
     tendn = sharedPreferences.getString("tendn", null);

        // Kiểm tra tên đăng nhập
        if (tendn != null) {
            textTendn.setText(tendn);
        } else {
            Intent intent = new Intent(TrangChu_NguoiDung_Activity.this, Login_Activity.class);
            startActivity(intent);
            finish(); // Kết thúc activity nếu chưa đăng nhập
            return;
        }



        gridview = findViewById(R.id.listdexuat);



        Listgr = new ArrayList<>();
        database = new Database(this, "quanlitro.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS phongtro(id INTEGER PRIMARY KEY AUTOINCREMENT, tenphong TEXT, dientich TEXT, mota TEXT, giatien TEXT, anh1Path TEXT, anh2Path TEXT, anh3Path TEXT, anh4Path TEXT, anh5Path TEXT)");



        adapterGr = new PhongTroDeXuat_Adapter(this, Listgr, phongTro -> {
            Intent intent = new Intent(TrangChu_NguoiDung_Activity.this, ChiTietPhongTro_NguoiDung_Activity.class);
            intent.putExtra("TEN_PHONG", phongTro.getTenPhong());
            intent.putExtra("DIEN_TICH", phongTro.getDienTich());
            intent.putExtra("MO_TA", phongTro.getMoTa());
            intent.putExtra("GIA_TIEN", phongTro.getGiaTien());
            intent.putExtra("IMG1_PATH", phongTro.getImg1Path());
            intent.putExtra("IMG2_PATH", phongTro.getImg2Path());
            intent.putExtra("IMG3_PATH", phongTro.getImg3Path());
            intent.putExtra("IMG4_PATH", phongTro.getImg4Path());
            intent.putExtra("IMG5_PATH", phongTro.getImg5Path());
            intent.putExtra("tendn", tendn);
            startActivity(intent);
        });
        gridview.setAdapter(adapterGr);


        loadPhongTroDeXuat();


        ImageButton btncanhan=findViewById(R.id.btncanhan);
        btncanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChu_NguoiDung_Activity.this, TrangCaNhan_NguoiDung_Activity.class);

                intent.putExtra("tendn", tendn);
                startActivity(intent);
            }
        });
    }



    private void loadPhongTroDeXuat() {
        Cursor dataPhongTro = database.GetData("SELECT * FROM phongtro ORDER BY random() LIMIT 10");
        Listgr.clear(); // Xóa dữ liệu cũ trong Listgr
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
            Listgr.add(phongTro);
        }
        dataPhongTro.close();
        adapterGr.notifyDataSetChanged(); // Cập nhật adapter cho Listgr
    }

}