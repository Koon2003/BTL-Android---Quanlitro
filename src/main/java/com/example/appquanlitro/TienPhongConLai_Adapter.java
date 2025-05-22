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

public class TienPhongConLai_Adapter extends BaseAdapter {
    private Context context;
    private List<TienPhongConLai> listPhongTro;
    private int layout;
    Database database;



    // Constructor với tham số listener
    public TienPhongConLai_Adapter(Context context, int layout, List<TienPhongConLai> listPhongTro) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.ds_tienphongconlai_admin, parent, false);
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
        txtGiaTien.setText("Giá tiền/tháng : "+phongTro.getGiatien()+" VNĐ");
        txtHoVaTen.setText("Họ và tên: "+phongTro.getHovaten());
        txtNgaySinh.setText(phongTro.getNgaysinh());
        txtCccd.setText("CCCD: "+phongTro.getCccdnguoinop());
        txtSdt.setText(phongTro.getSdt());
        txtSoTienConLai.setText("Số tiền còn lại: "+phongTro.getSotienconlaiphaidong()+" VNĐ");
        trangthai.setText("Trạng thái : "+phongTro.getTrangthai());

        ImageButton sua = convertView.findViewById(R.id.imgsua);
        ImageButton xoa = convertView.findViewById(R.id.imgxoa);

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

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, HienThi_TienPhongConLai_Admin_Activity.class);
            intent.putExtra("maidtienconlai", phongTro.getMaidtienconlai());
            intent.putExtra("mahoso", phongTro.getMahoso());
            intent.putExtra("id", phongTro.getId());
            intent.putExtra("giatien", phongTro.getGiatien());
            intent.putExtra("hovaten", phongTro.getHovaten());
            intent.putExtra("ngaysinh", phongTro.getNgaysinh());
            intent.putExtra("cccdnguoinop", phongTro.getCccdnguoinop());
            intent.putExtra("sdt", phongTro.getSdt());
            intent.putExtra("sotienconlai", phongTro.getSotienconlaiphaidong());
            intent.putExtra("trangthai", phongTro.getTrangthai());

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        });

        // Sửa
        sua.setOnClickListener(v -> {
            Intent intent = new Intent(context, Sua_TienPhongConLai_Activity.class);
            intent.putExtra("maidtienconlai", phongTro.getMaidtienconlai());
            intent.putExtra("mahoso", phongTro.getMahoso());
            intent.putExtra("id", phongTro.getId());
            intent.putExtra("giatien", phongTro.getGiatien());
            intent.putExtra("hovaten", phongTro.getHovaten());
            intent.putExtra("ngaysinh", phongTro.getNgaysinh());
            intent.putExtra("cccdnguoinop", phongTro.getCccdnguoinop());
            intent.putExtra("sdt", phongTro.getSdt());
            intent.putExtra("sotienconlai", phongTro.getSotienconlaiphaidong());
            intent.putExtra("trangthai", phongTro.getTrangthai());

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        // Xóa
        xoa.setOnClickListener(v -> {
            Toast.makeText(context, "Bạn đã nhấn nút xóa", Toast.LENGTH_SHORT).show();
            int rowsDeleted = database.deletetienPhongConlai(phongTro.getMaidtienconlai());

            if (rowsDeleted > 0) {
                listPhongTro.remove(i);
                notifyDataSetChanged();
                Toast.makeText(context, "Đã xóa bill tiền phòng còn lại thành công!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Không tìm thấy hồ sơ thuê để xóa.", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

}



