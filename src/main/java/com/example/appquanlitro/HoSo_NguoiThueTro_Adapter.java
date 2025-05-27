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
import java.util.prefs.BackingStoreException;

public class HoSo_NguoiThueTro_Adapter extends BaseAdapter {
    private Context context;
    private List<HoSo_NguoiThueTro> listPhongTro;
    private int layout;
    Database database;

    // Interface để xử lý sự kiện click
    public interface OnItemClickListener {
        void onItemClick(PhongTro phongTro);
    }

    // Constructor với tham số listener
    public HoSo_NguoiThueTro_Adapter(Context context, int layout, List<HoSo_NguoiThueTro> listPhongTro) {
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
    private int clickCount = 0; // Biến đếm số lần nhấn
    private boolean areButtonsVisible = false; // Biến trạng thái hiển thị

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View viewtemp;
        if (convertView == null) {
            viewtemp = LayoutInflater.from(parent.getContext()).inflate(R.layout.ds_hosonguoithuetro_admin, parent, false);
        } else {
            viewtemp = convertView;
        }

        HoSo_NguoiThueTro phongTro = listPhongTro.get(i);

        TextView maid = viewtemp.findViewById(R.id.maid);
        TextView mahoso = viewtemp.findViewById(R.id.mahoso);
        TextView hoten = viewtemp.findViewById(R.id.hovaten);
        TextView ngaysinh = viewtemp.findViewById(R.id.ngaysinh);
        TextView cccd = viewtemp.findViewById(R.id.cccd);
        TextView quequan = viewtemp.findViewById(R.id.quequan);
        TextView sdt = viewtemp.findViewById(R.id.sdt);
        TextView id = viewtemp.findViewById(R.id.id);
        TextView giatien = viewtemp.findViewById(R.id.giatien);
        TextView hinhthucthue = viewtemp.findViewById(R.id.hinhthucthue);
        TextView ngaybatdau = viewtemp.findViewById(R.id.ngaybatdauthue);
        TextView ngayketthuc = viewtemp.findViewById(R.id.ngaykethucthue);
        TextView xacnhanhopdong= viewtemp.findViewById(R.id.xacnhanhopdong);
        TextView trangthai = viewtemp.findViewById(R.id.trangthai);



        maid.setText(String.valueOf(phongTro.getMaid()));
        mahoso.setText(phongTro.getMahoso());
        hoten.setText(phongTro.getHovaten());
        ngaysinh.setText(phongTro.getNgaysinh());
        cccd.setText(phongTro.getCccd());
        quequan.setText(phongTro.getQuequan());
        sdt.setText(phongTro.getSdt());
        trangthai.setText(phongTro.getTrangthai());
        String tenPhong = database.getTenPhongById(phongTro.getId());
        id.setText(tenPhong);

        giatien.setText(phongTro.getGiatien());
        hinhthucthue.setText(phongTro.getHinhthucthue());
        ngaybatdau.setText(phongTro.getNgaybatdau());
        ngayketthuc.setText(phongTro.getNgayketthuc());
        xacnhanhopdong.setText(phongTro.getXacnhanhopdong());
        // Đặt màu sắc cho TextView trạng thái dựa trên giá trị
        switch (phongTro.getXacnhanhopdong()) {
            case "Chờ Khách Check":
                trangthai.setTextColor(Color.RED); // Màu đỏ
                trangthai.setTypeface(null, Typeface.BOLD); // Chữ in đậm
                break;
            case "Khách Đã Xác Nhận":
                trangthai.setTextColor(Color.BLACK); // Màu đen
                trangthai.setTypeface(null, Typeface.BOLD); // Chữ in đậm
                break;


        }
        switch (phongTro.getTrangthai()) {
            case "Đang thuê":
                trangthai.setTextColor(Color.RED); // Màu đỏ
                trangthai.setTypeface(null, Typeface.BOLD); // Chữ in đậm
                break;
            case "Hết Hợp Đồng":
                trangthai.setTextColor(Color.BLACK); // Màu đen
                trangthai.setTypeface(null, Typeface.BOLD); // Chữ in đậm
                break;

        }

        viewtemp.setOnClickListener(v -> {

                    Intent intent = new Intent(context, HienThi_HoSo_NguoiThueTro_Admin_Activity.class);
                    intent.putExtra("maid", phongTro.getMaid());
                    intent.putExtra("mahoso", phongTro.getMahoso());
                    intent.putExtra("hovaten", phongTro.getHovaten());
                    intent.putExtra("ngaysinh", phongTro.getNgaysinh());
                    intent.putExtra("cccd", phongTro.getCccd());
                    intent.putExtra("quequan", phongTro.getQuequan());
                    intent.putExtra("sdt", phongTro.getSdt());
                    intent.putExtra("tenphong", database.getTenPhongById(phongTro.getId()));
                    intent.putExtra("giatien", phongTro.getGiatien());
                    intent.putExtra("hinhthucthue", phongTro.getHinhthucthue());
                    intent.putExtra("ngaybatdau", phongTro.getNgaybatdau());
                    intent.putExtra("ngayketthuc", phongTro.getNgayketthuc());
                    intent.putExtra("xacnhanhopdong", phongTro.getXacnhanhopdong());
                    intent.putExtra("trangthai", phongTro.getTrangthai());

                    // Thêm cờ FLAG_ACTIVITY_NEW_TASK
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

        });


        return viewtemp;
    }

}

