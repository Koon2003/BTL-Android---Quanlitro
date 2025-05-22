package com.example.appquanlitro;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class TrangChu_Admin_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_trangchu_admin);
        ImageButton btntaikhoan=findViewById(R.id.btntaikhoan);
        ImageButton btnhoso=findViewById(R.id.btnhosothuetro);
        ImageButton btntiendien=findViewById(R.id.btntiendien);
        ImageButton btntienphong=findViewById(R.id.btntienphong);
        ImageButton btntiennuoc=findViewById(R.id.btntiennuoc);
        ImageButton btndangxuat=findViewById(R.id.btndangxuat);
        ImageButton btnphongtro=findViewById(R.id.btnphongtro);
        btnphongtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChu_Admin_Activity.this, PhongTro_Admin_Activity.class);
                startActivity(intent);
            }
        });

        btntienphong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo Dialog
                // Tạo Dialog
                Dialog dialog = new Dialog(TrangChu_Admin_Activity.this);
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
                        Intent intent = new Intent(TrangChu_Admin_Activity.this, TienCocPhong_Admin_Activity.class);

                        startActivity(intent);
                        dialog.dismiss(); // Đóng Dialog
                    }
                });


                btntiennoptiep.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TrangChu_Admin_Activity.this, TienPhongConLai_Admin_Activity.class);
                        startActivity(intent);
                        dialog.dismiss(); // Đóng Dialog
                    }
                });

                // Hiển thị Dialog
                dialog.show();
            }
        });
        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChu_Admin_Activity.this, Login_Activity.class);
                startActivity(intent);
            }
        });
        btntaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChu_Admin_Activity.this, TaiKhoan_Admin_Activity.class);
                startActivity(intent);
            }
        });
        btnhoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChu_Admin_Activity.this, HoSo_NguoiThueTro_Admin_Activity.class);
                startActivity(intent);
            }
        });
        btntiendien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btntiennuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChu_Admin_Activity.this, TienNuoc_Admin_Activity.class);
                startActivity(intent);
            }
        });

        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChu_Admin_Activity.this, Login_Activity.class);
                startActivity(intent);
            }
        });

    }
}