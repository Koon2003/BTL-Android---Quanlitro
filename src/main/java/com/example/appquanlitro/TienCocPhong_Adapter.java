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

public class TienCocPhong_Adapter extends BaseAdapter {
    private Context context;
    private List<TienCocPhong> listPhongTro;
    private int layout;
    Database database;



    // Constructor với tham số listener
    public TienCocPhong_Adapter(Context context, int layout, List<TienCocPhong> listPhongTro) {
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

    public View getView(int i, View convertView, ViewGroup parent) {
        View viewtemp = (convertView == null)
                ? LayoutInflater.from(parent.getContext()).inflate(R.layout.ds_tiencocphong_admin, parent, false)
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

        txtGiaTien.setText("Giá tiền / tháng: "+phongTro.getGiatien() + " VNĐ");
        txtHoVaTen.setText("Họ và tên: "+phongTro.getHovaten());
        txtNgaySinh.setText(phongTro.getNgaysinh());
        txtCccd.setText("CCCD: "+phongTro.getCccdnguoinop());
        txtSdt.setText(phongTro.getSdt());
        txtHinhThucCoc.setText("Hình thức cọc: "+phongTro.getHinhthuccoc());
        txtSoTienDaCoc.setText("Số tiền cọc: "+phongTro.getSotiendanopcoc() +" VNĐ");
        txtSoTienConLai.setText("Số tiền còn lại: "+phongTro.getSotienconlai() +"VNĐ");

        ImageButton sua = viewtemp.findViewById(R.id.imgsua);
        ImageButton xoa = viewtemp.findViewById(R.id.imgxoa);
        sua.setVisibility(View.GONE);
        xoa.setVisibility(View.GONE);

// Xử lý sự kiện khi nhấn vào item
        viewtemp.setOnClickListener(v -> {
            clickCount++;

            // Reset clickCount nếu không có nhấn thêm sau 800ms
            new android.os.Handler().postDelayed(() -> {
                if (clickCount == 1) {
                    // Xử lý sự kiện nhấn một lần
                    Intent intent = new Intent(context, HienThi_TienCocPhong_Admin_Activity.class);
                    intent.putExtra("maidcoc", phongTro.getMaidcoc());
                    intent.putExtra("mahoso", phongTro.getMahoso());
                    intent.putExtra("id", phongTro.getId());
                    intent.putExtra("giatien", phongTro.getGiatien());
                    intent.putExtra("hovaten", phongTro.getHovaten());
                    intent.putExtra("ngaysinh", phongTro.getNgaysinh());
                    intent.putExtra("cccdnguoinop", phongTro.getCccdnguoinop());
                    intent.putExtra("sdt", phongTro.getSdt());
                    intent.putExtra("hinhthuccoc", phongTro.getHinhthuccoc());
                    intent.putExtra("sotiendanopcoc", phongTro.getSotiendanopcoc());
                    intent.putExtra("sotienconlai", phongTro.getSotienconlai());

                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else if (clickCount == 2) {
                    // Xử lý sự kiện nhấn hai lần
                    if (!areButtonsVisible) {
                        xoa.setVisibility(View.VISIBLE);
                        sua.setVisibility(View.VISIBLE);
                        areButtonsVisible = true;
                    } else {
                        xoa.setVisibility(View.GONE);
                        sua.setVisibility(View.GONE);
                        areButtonsVisible = false;
                    }
                }
                clickCount = 0; // Reset lại biến đếm sau khi xử lý
            }, 800);
        });

        // Sửa
        sua.setOnClickListener(v -> {
            Intent intent = new Intent(context, Sua_TienCocPhong_Activity.class);
            intent.putExtra("maidcoc", phongTro.getMaidcoc());
            intent.putExtra("mahoso", phongTro.getMahoso());
            intent.putExtra("id", phongTro.getId());
            intent.putExtra("giatien", phongTro.getGiatien());
            intent.putExtra("hovaten", phongTro.getHovaten());
            intent.putExtra("ngaysinh", phongTro.getNgaysinh());
            intent.putExtra("cccdnguoinop", phongTro.getCccdnguoinop());
            intent.putExtra("sdt", phongTro.getSdt());
            intent.putExtra("hinhthuccoc", phongTro.getHinhthuccoc());
            intent.putExtra("sotiendanopcoc", phongTro.getSotiendanopcoc());
            intent.putExtra("sotienconlai", phongTro.getSotienconlai());

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        // Xóa
        xoa.setOnClickListener(v -> {
            Toast.makeText(context, "Bạn đã nhấn nút xóa", Toast.LENGTH_SHORT).show();
            int rowsDeleted = database.deletetienCocPhong(phongTro.getMaidcoc());

            if (rowsDeleted > 0) {
                listPhongTro.remove(i);
                notifyDataSetChanged();
                Toast.makeText(context, "Đã xóa bill tiền cọc thành công!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Không tìm thấy hồ sơ thuê để xóa.", Toast.LENGTH_SHORT).show();
            }
        });

        return viewtemp;
    }

}


