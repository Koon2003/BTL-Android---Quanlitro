package com.example.appquanlitro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TienCocPhong_NguoiDung_Adapter extends BaseAdapter {
    private Context context;
    private List<TienCocPhong> listPhongTro;
    private int layout;
    Database database;



    // Constructor với tham số listener
    public TienCocPhong_NguoiDung_Adapter(Context context, int layout, List<TienCocPhong> listPhongTro) {
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
        View viewtemp = (convertView == null)
                ? LayoutInflater.from(parent.getContext()).inflate(R.layout.ds_tiencocphong_nguoidung, parent, false)
                : convertView;

        TienCocPhong phongTro = listPhongTro.get(i);

        // Ánh xạ các TextView để hiển thị thông tin
        TextView maidcoc = viewtemp.findViewById(R.id.maidcoc);
        TextView txtMahoso = viewtemp.findViewById(R.id.mahoso);
        TextView txtId = viewtemp.findViewById(R.id.id);
        TextView txtGiaTien = viewtemp.findViewById(R.id.giatien);
        TextView txtHoVaTen = viewtemp.findViewById(R.id.hovaten);
        TextView txtNgaySinh = viewtemp.findViewById(R.id.ngaysinh);
        TextView txtCccd = viewtemp.findViewById(R.id.cccd);
        TextView txtSdt = viewtemp.findViewById(R.id.sdt);
        TextView txtHinhThucCoc = viewtemp.findViewById(R.id.hinhthuccoc);
        TextView txtSoTienDaCoc = viewtemp.findViewById(R.id.sotiendacoc);
        TextView txtSoTienConLai = viewtemp.findViewById(R.id.sotienconlai);

        // Hiển thị dữ liệu từ đối tượng phongTro
        maidcoc.setText(phongTro.getMaidcoc());
        txtMahoso.setText(phongTro.getMahoso());
        String tenPhong = database.getTenPhongById(phongTro.getId());
        txtId.setText("Phòng: "+tenPhong);

        txtGiaTien.setText("Giá tiền/tháng: "+phongTro.getGiatien() + " VNĐ");
        txtHoVaTen.setText("Họ và tên: "+phongTro.getHovaten());
        txtNgaySinh.setText("Ngày sinh: "+phongTro.getNgaysinh());
        txtCccd.setText("CCCD: "+phongTro.getCccdnguoinop());
        txtSdt.setText("SĐT: "+phongTro.getSdt());
        txtHinhThucCoc.setText("Hình thức cọc: "+phongTro.getHinhthuccoc());
        txtSoTienDaCoc.setText("Số tiền cọc: "+phongTro.getSotiendanopcoc() +" VNĐ");
        txtSoTienConLai.setText("Số tiền còn lại: "+phongTro.getSotienconlai() +"VNĐ");


        return viewtemp;
    }

}
