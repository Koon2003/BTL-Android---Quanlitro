package com.example.appquanlitro;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.io.File;
import java.util.List;

public class PhongTroDeXuat_Adapter extends BaseAdapter {
    private Context context;
    private List<PhongTro> listPhongTro;
    private OnItemClickListener listener; // Interface để xử lý sự kiện click

    // Interface để xử lý sự kiện click
    public interface OnItemClickListener {
        void onItemClick(PhongTro phongTro);
    }

    // Constructor với tham số listener
    public PhongTroDeXuat_Adapter(Context context, List<PhongTro> listPhongTro, OnItemClickListener listener) {
        this.context = context;
        this.listPhongTro = listPhongTro;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return listPhongTro.size();
    }

    @Override
    public Object getItem(int position) {
        return listPhongTro.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewtemp;
        if (convertView == null) {
            viewtemp = LayoutInflater.from(parent.getContext()).inflate(R.layout.ds_trodexuat, parent, false);
        } else {
            viewtemp = convertView;
        }

        PhongTro phongTro = listPhongTro.get(position);

        TextView id = viewtemp.findViewById(R.id.id);
        TextView tenPhong = viewtemp.findViewById(R.id.tenphong);
        TextView dienTich = viewtemp.findViewById(R.id.dientich);
        TextView giaTien = viewtemp.findViewById(R.id.giatien);
        TextView moTa = viewtemp.findViewById(R.id.mota);
        ImageView img1 = viewtemp.findViewById(R.id.img1);
        ImageView img2 = viewtemp.findViewById(R.id.img2);
        ImageView img3 = viewtemp.findViewById(R.id.img3);
        ImageView img4 = viewtemp.findViewById(R.id.img4);
        ImageView img5 = viewtemp.findViewById(R.id.img5);

        id.setText(String.valueOf(phongTro.getId()));
        tenPhong.setText(phongTro.getTenPhong());
        dienTich.setText(phongTro.getDienTich());
        giaTien.setText(phongTro.getGiaTien());
        moTa.setText(phongTro.getMoTa());

        // Load ảnh từ đường dẫn
        setImageViewFromPath(img1, phongTro.getImg1Path());
        setImageViewFromPath(img2, phongTro.getImg2Path());
        setImageViewFromPath(img3, phongTro.getImg3Path());
        setImageViewFromPath(img4, phongTro.getImg4Path());
        setImageViewFromPath(img5, phongTro.getImg5Path());

        // Xử lý sự kiện click
        viewtemp.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(phongTro);
            }
        });

        return viewtemp;
    }

    // Phương thức để thiết lập hình ảnh từ đường dẫn
    private void setImageViewFromPath(ImageView imageView, String path) {
        File imgFile = new File(path);
        if (imgFile.exists()) {
            Glide.with(context)
                    .load(imgFile)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.img); // Ảnh mặc định
        }
    }
}