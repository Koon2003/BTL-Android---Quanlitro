package com.example.appquanlitro;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TrangCaNhan_NguoiDung_Activity extends AppCompatActivity {
    String tendn;
    Database database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_canhan_nguoidung);

        TextView textTendn = findViewById(R.id.tendn);
        TextView hovaten = findViewById(R.id.hovaten);
        TextView ngaysinh = findViewById(R.id.ngaysinh);
        TextView sdt = findViewById(R.id.sdt);
        TextView quequan = findViewById(R.id.quequan);
        TextView cccd = findViewById(R.id.cccd);
        ImageButton btntiennuoc=findViewById(R.id.btntiennuoc);
        ImageButton btntienphong=findViewById(R.id.btntienphong);

        // Lấy tên đăng nhập từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        tendn = sharedPreferences.getString("tendn", null);

        // Kiểm tra tên đăng nhập
        if (tendn != null) {
            textTendn.setText(tendn);
        } else {
            Intent intent = new Intent(TrangCaNhan_NguoiDung_Activity.this, Login_Activity.class);
            startActivity(intent);
            finish(); // Kết thúc activity nếu chưa đăng nhập
            return;
        }

        database = new Database(this, "quanlitro.db", null, 1);
        // Tải dữ liệu tài khoản
        Loaddulieutaikhoan(hovaten, ngaysinh, sdt, quequan, cccd);


        btntiennuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangCaNhan_NguoiDung_Activity.this, TienNuoc_NguoiDung_Activity.class);

                intent.putExtra("tendn", tendn);
                // Khởi chạy Activity
                startActivity(intent);
            }
        });

        btntienphong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo Dialog
                // Tạo Dialog
                Dialog dialog = new Dialog(TrangCaNhan_NguoiDung_Activity.this);
                dialog.setContentView(R.layout.item_2loai_tienphong); // Sử dụng layout ds_tienphong
                dialog.setTitle("Chọn hành động");

                // Thiết lập chiều rộng của Dialog bằng chiều rộng màn hình
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                Window window = dialog.getWindow();
                if (window != null) {
                    layoutParams.copyFrom(window.getAttributes());
                    // Thiết lập chiều rộng bằng chiều rộng màn hình
                    layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                    layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(layoutParams);
                }
                // Ánh xạ các nút trong Dialog
                ImageButton btntiendatcoc = dialog.findViewById(R.id.btntiencoc);
                ImageButton btntiennoptiep = dialog.findViewById(R.id.btntienconlai);

                // Xử lý sự kiện khi ấn vào nút "Đặt cọc"
                btntiendatcoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TrangCaNhan_NguoiDung_Activity.this, TienCocPhong_NguoiDung_Activity.class);
                        intent.putExtra("tendn", tendn); // Truyền tendn sang
                        startActivity(intent);
                        dialog.dismiss(); // Đóng Dialog
                    }
                });

// Xử lý sự kiện khi ấn vào nút "Nộp tiếp"
                btntiennoptiep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TrangCaNhan_NguoiDung_Activity.this, TienPhongConLai_NguoiDung_Activity.class);
                        intent.putExtra("tendn", tendn); // Truyền tendn sang
                        startActivity(intent);
                        dialog.dismiss(); // Đóng Dialog
                    }
                });

                // Hiển thị Dialog
                dialog.show();
            }
        });

    private void Loaddulieutaikhoan(TextView hovaten, TextView ngaysinh, TextView sdt, TextView quequan, TextView cccd) {
        // Truy vấn dữ liệu từ bảng taikhoan dựa trên tendn
        Cursor dataTaiKhoan = database.GetData("SELECT * FROM taikhoan WHERE tendn = '" + tendn + "'");

        if (dataTaiKhoan != null && dataTaiKhoan.moveToFirst()) {
            // Lấy thông tin từ Cursor
            String hoten = dataTaiKhoan.getString(2);
            String ngaySinh = dataTaiKhoan.getString(3);
            String soDienThoai = dataTaiKhoan.getString(6);
            String queQuan = dataTaiKhoan.getString(5);
            String canCuocCongDan = dataTaiKhoan.getString(4); // Lấy trường CCCD

            // Hiển thị thông tin lên các TextView
            hovaten.setText(hoten);
            ngaysinh.setText(ngaySinh);
            sdt.setText(soDienThoai);
            quequan.setText(queQuan);
            cccd.setText(canCuocCongDan); // Hiển thị CCCD
        } else {
            // Nếu không có dữ liệu, hiển thị thông báo hoặc xử lý phù hợp
            hovaten.setText("Không có thông tin");
            ngaysinh.setText("Không có thông tin");
            sdt.setText("Không có thông tin");
            quequan.setText("Không có thông tin");
            cccd.setText("Không có thông tin"); // Hiển thị thông báo cho CCCD
        }

        if (dataTaiKhoan != null) {
            dataTaiKhoan.close();
        }
    }
}