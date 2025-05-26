package com.example.appquanlitro;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PhongTro_Adapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<PhongTro> List;
    private Database database; // Thêm tham chiếu đến Database

    public PhongTro_Adapter(Context context, int layout, List<PhongTro> List) {
        this.context = context;
        this.layout = layout;
        this.List = List;
        this.database = new Database(context, "quanlitro.db", null, 1); // Khởi tạo database

    }

    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public Object getItem(int position) {
        return List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewtemp;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            viewtemp = inflater.inflate(layout, viewGroup, false);
        } else {
            viewtemp = view;
        }

        PhongTro tt = List.get(i);
        TextView id = viewtemp.findViewById(R.id.id);
        TextView tenphong = viewtemp.findViewById(R.id.tenphong);
        TextView dientich = viewtemp.findViewById(R.id.dientich);
        TextView mota = viewtemp.findViewById(R.id.mota);
        TextView giatien = viewtemp.findViewById(R.id.giatien);
        ImageView anh1 = viewtemp.findViewById(R.id.img1);
        ImageView anh2 = viewtemp.findViewById(R.id.img2);
        ImageView anh3 = viewtemp.findViewById(R.id.img3);
        ImageView anh4 = viewtemp.findViewById(R.id.img4);
        ImageView anh5 = viewtemp.findViewById(R.id.img5);


        // Hiển thị dữ liệu
        id.setText(tt.getId());
        tenphong.setText(tt.getTenPhong());
        dientich.setText(tt.getDienTich());
        mota.setText(tt.getMoTa());
        giatien.setText(tt.getGiaTien());

        // Hiển thị hình ảnh cho từng ImageView
        setImageViewFromPath(anh1, tt.getImg1Path());
        setImageViewFromPath(anh2, tt.getImg2Path());
        setImageViewFromPath(anh3, tt.getImg3Path());
        setImageViewFromPath(anh4, tt.getImg4Path());
        setImageViewFromPath(anh5, tt.getImg5Path());


        viewtemp.setOnClickListener(v -> {
            Intent intent = new Intent(context, HienThi_ThongTin_PhongTro_Admin_Activity.class);
            intent.putExtra("id", tt.getId());
            intent.putExtra("tenphong", tt.getTenPhong());
            intent.putExtra("dientich", tt.getDienTich());
            intent.putExtra("mota", tt.getMoTa());
            intent.putExtra("giatien", tt.getGiaTien());
            intent.putExtra("anh1Path", tt.getImg1Path());
            intent.putExtra("anh2Path", tt.getImg2Path());
            intent.putExtra("anh3Path", tt.getImg3Path());
            intent.putExtra("anh4Path", tt.getImg4Path());
            intent.putExtra("anh5Path", tt.getImg5Path());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);


        });
        ImageButton sua = viewtemp.findViewById(R.id.icon_edit);
        ImageButton xoa = viewtemp.findViewById(R.id.icon_xoa);
        // Sửa
        sua.setOnClickListener(v -> {
            Intent intent = new Intent(context, Sua_PhongTro.class);
            intent.putExtra("id", tt.getId());
            intent.putExtra("tenphong", tt.getTenPhong());
            intent.putExtra("dientich", tt.getDienTich());
            intent.putExtra("mota", tt.getMoTa());
            intent.putExtra("giatien", tt.getGiaTien());
            intent.putExtra("anh1Path", tt.getImg1Path());
            intent.putExtra("anh2Path", tt.getImg2Path());
            intent.putExtra("anh3Path", tt.getImg3Path());
            intent.putExtra("anh4Path", tt.getImg4Path());
            intent.putExtra("anh5Path", tt.getImg5Path());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        });

        // Xóa
        xoa.setOnClickListener(v -> {
            if (context instanceof Activity) {
                new AlertDialog.Builder((Activity) context)
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc muốn xóa phòng này không?")
                        .setPositiveButton("OK", (dialog, which) -> {
                            int rowsDeleted = database.deletePhongTro(tt.getId());

                            if (rowsDeleted > 0) {
                                List.remove(i);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Đã xóa phòng trọ thành công!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Không tìm thấy phòng trọ để xóa.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                        .show();
            } else {
                Toast.makeText(context, "Không thể hiển thị hộp thoại xác nhận (context không hợp lệ)", Toast.LENGTH_SHORT).show();
            }
        });




        return viewtemp;
    }

    private void setImageViewFromPath(ImageView imageView, String imgPath) {
        if (imgPath != null && !imgPath.isEmpty()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.main); // Hình ảnh mặc định nếu không có ảnh
        }
    }
}