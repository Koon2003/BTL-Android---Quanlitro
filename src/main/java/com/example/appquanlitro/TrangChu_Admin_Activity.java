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
    private ViewPager2 viewPager;
    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_trangchu_admin);
        viewPager = findViewById(R.id.sl1);
        ImageButton btntaikhoan=findViewById(R.id.btntaikhoan);
        ImageButton btnhoso=findViewById(R.id.btnhosothuetro);
        ImageButton btntiendien=findViewById(R.id.btntiendien);
        ImageButton btntienphong=findViewById(R.id.btntienphong);
        ImageButton btntiennuoc=findViewById(R.id.btntiennuoc);
        ImageButton btnlichhenhuy=findViewById(R.id.btnlichhendahuy);
        ImageButton btnlichhendaduyet=findViewById(R.id.btnlichhendaduyet);
        ImageButton btnlichhenchoduyet=findViewById(R.id.btnlichhenchoduyet);
        ImageButton btndangxuat=findViewById(R.id.btndangxuat);
        ImageButton btnphongtro=findViewById(R.id.btnphongtro);
        btnphongtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChu_Admin_Activity.this, PhongTro_Admin_Activity.class);

                startActivity(intent);

            }
        });
        btnlichhenhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChu_Admin_Activity.this, DatLichXemPhongTro_DaHuy_Admin_Activity.class);

                startActivity(intent);

            }
        });
        btnlichhendaduyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChu_Admin_Activity.this, DatLichXemPhongTro_DaDuyet_Admin_Activity.class);

                startActivity(intent);

            }
        });
        btnlichhenchoduyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrangChu_Admin_Activity.this, DatLichXemPhongTro_ChoDuyet_Admin_Activity.class);

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
                Intent intent = new Intent(TrangChu_Admin_Activity.this, TienDien_Admin_Activity.class);
                startActivity(intent);
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
        int[] adImages = {
                R.drawable.sl1,
                R.drawable.sl4,
                R.drawable.sl2,
                R.drawable.sl6,
                R.drawable.sl3,
                R.drawable.sl5
        };
        addImagesToViewPager(adImages);

        // Tạo Handler để tự động chuyển slide
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                currentPage++;
                if (currentPage >= adImages.length) {
                    currentPage = 0; // Reset về đầu
                }
                viewPager.setCurrentItem(currentPage, true);
                handler.postDelayed(this, 6000); // Chuyển sau 6 giây
            }
        };
        handler.postDelayed(runnable, 6000); // Bắt đầu chuyển slide
    }
    private void addImagesToViewPager(int[] adImages) {
        TrangChu_Admin_Activity.ViewPagerAdapter adapter = new TrangChu_Admin_Activity.ViewPagerAdapter(adImages);
        viewPager.setAdapter(adapter);
    }

    private class ViewPagerAdapter extends RecyclerView.Adapter<TrangChu_Admin_Activity.ViewPagerAdapter.SlideViewHolder> {
        private int[] images;

        public ViewPagerAdapter(int[] images) {
            this.images = images;
        }

        @Override
        public TrangChu_Admin_Activity.ViewPagerAdapter.SlideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide, parent, false);
            return new TrangChu_Admin_Activity.ViewPagerAdapter.SlideViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TrangChu_Admin_Activity.ViewPagerAdapter.SlideViewHolder holder, int position) {
            holder.adImage.setImageResource(images[position]);
        }

        @Override
        public int getItemCount() {
            return images.length;
        }

        class SlideViewHolder extends RecyclerView.ViewHolder {
            ImageView adImage;

            SlideViewHolder(View itemView) {
                super(itemView);
                adImage = itemView.findViewById(R.id.sl1); // Đảm bảo ID đúng
            }
        }
    }
}