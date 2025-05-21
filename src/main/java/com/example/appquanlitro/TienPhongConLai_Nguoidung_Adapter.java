package com.example.appquanlitro;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TienPhongConLai_Nguoidung_Adapter extends BaseAdapter {
    private Context context;
    private List<TienPhongConLai> listPhongTro;
    private int layout;
    Database database;



    // Constructor với tham số listener
    public TienPhongConLai_Nguoidung_Adapter(Context context, int layout, List<TienPhongConLai> listPhongTro) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.ds_tienphongconlai_nguoidung, parent, false);
        }

        TienPhongConLai phongTro = listPhongTro.get(i);

        // Ánh xạ các TextView để hiển thị thông tin
        TextView maidtienconlai = convertView.findViewById(R.id.maidtienconlai);
        TextView txtMahoso = convertView.findViewById(R.id.mahoso);
        TextView txtId = convertView.findViewById(R.id.id);
        TextView txtGiaTien = convertView.findViewById(R.id.giatien);
        TextView txtHoVaTen = convertView.findViewById(R.id.hovaten);
        TextView txtNgaySinh = convertView.findViewById(R.id.ngaysinh);
        TextView txtCccd = convertView.findViewById(R.id.cccd);
        TextView txtSdt = convertView.findViewById(R.id.sdt);
        TextView txtSoTienConLai = convertView.findViewById(R.id.sotienconlai);
        TextView trangthai = convertView.findViewById(R.id.trangthai);

        // Hiển thị dữ liệu từ đối tượng phongTro
        maidtienconlai.setText(phongTro.getMaidtienconlai());
        txtMahoso.setText(phongTro.getMahoso());
        String tenPhong = database.getTenPhongById(phongTro.getId());
        txtId.setText("Phòng: "+tenPhong);
        txtGiaTien.setText("Giá tiền/tháng: "+phongTro.getGiatien()+" VNĐ");
        txtHoVaTen.setText("Họ tên: "+phongTro.getHovaten());
        txtNgaySinh.setText("Ngày sinh: "+phongTro.getNgaysinh());
        txtCccd.setText("CCCD: "+phongTro.getCccdnguoinop());
        txtSdt.setText("SĐT: "+phongTro.getSdt());
        txtSoTienConLai.setText("Số tiền còn lại: "+phongTro.getSotienconlaiphaidong()+" VNĐ");
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



        return convertView;
    }

}


