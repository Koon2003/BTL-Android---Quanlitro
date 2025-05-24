package com.example.appquanlitro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TienDien_Adapter_NguoiDung extends BaseAdapter {
    private Context context;
    private List<TienDien> listPhongTro;
    private int layout;
    Database database;



    // Constructor với tham số listener
    public TienDien_Adapter_NguoiDung(Context context, int layout, List<TienDien> listPhongTro) {
        this.context = context;
        this.layout = layout;
        this.listPhongTro = listPhongTro;
        this.database = new Database(context, "quanlitro.db", null, 1); // Khởi tạo đối tượng Database
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


    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.ds_tiendien_nguoidung, parent, false);
        }

        TienDien phongTro = listPhongTro.get(i);

        // Ánh xạ các TextView để hiển thị thông tin
        TextView iddien = convertView.findViewById(R.id.iddien);
        TextView idphong = convertView.findViewById(R.id.id);
        TextView dongtiendienthangnam = convertView.findViewById(R.id.dongtiendienthangnam);
        TextView sokwtieuthu = convertView.findViewById(R.id.sokwtieuthuc);
        TextView giatien = convertView.findViewById(R.id.giatien);
        TextView tongtien = convertView.findViewById(R.id.tongtien);
        TextView trangthai = convertView.findViewById(R.id.trangthai);

        // Hiển thị dữ liệu từ đối tượng phongTro
        iddien.setText(phongTro.getIddien());
        String tenPhong = database.getTenPhongById(phongTro.getId());
        idphong.setText("Phòng"+tenPhong);
        dongtiendienthangnam.setText("Tháng: "+phongTro.getDongtiendienthangnam());
        sokwtieuthu.setText("Số điện tiêu thụ: "+phongTro.getSokwtieuthu() +" số");
        giatien.setText("Giá tiền/1kw: "+phongTro.getGiatien()+" VNĐ");
        tongtien.setText("Tổng: "+phongTro.getTongtien()+" VNĐ");
        trangthai.setText(phongTro.getTrangthai());



        // Đặt màu sắc cho TextView trạng thái dựa trên giá trị
        switch (phongTro.getTrangthai()) {
            case "Chưa đóng":
                trangthai.setTextColor(Color.RED); // Màu đỏ
                trangthai.setTypeface(null, Typeface.BOLD); // Chữ in đậm
                break;
            case "Đã đóng":
                trangthai.setTextColor(Color.BLUE); // Màu đen
                trangthai.setTypeface(null, Typeface.BOLD); // Chữ in đậm
                break;

        }

        // Sửa


        return convertView;
    }

}




