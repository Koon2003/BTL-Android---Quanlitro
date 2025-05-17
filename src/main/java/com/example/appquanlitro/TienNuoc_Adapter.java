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

public class TienNuoc_Adapter extends BaseAdapter {
    private Context context;
    private List<TienNuoc> listPhongTro;
    private int layout;
    Database database;



    // Constructor với tham số listener
    public TienNuoc_Adapter(Context context, int layout, List<TienNuoc> listPhongTro) {
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.ds_tiennuoc_admin, parent, false);
        }

        TienNuoc phongTro = listPhongTro.get(i);

        // Ánh xạ các TextView để hiển thị thông tin
        TextView idnuoc= convertView.findViewById(R.id.idnuoc);
        TextView idphong = convertView.findViewById(R.id.id);
        TextView dongtiennuocthangnam = convertView.findViewById(R.id.dongtiennuocthangnam);
        TextView sokhoitieuthu = convertView.findViewById(R.id.sokhoitieuthuc);
        TextView giatien = convertView.findViewById(R.id.giatien);
        TextView tongtien = convertView.findViewById(R.id.tongtien);
        TextView trangthai = convertView.findViewById(R.id.trangthai);

        // Hiển thị dữ liệu từ đối tượng phongTro
        idnuoc.setText(phongTro.getIdnuoc());
        String tenPhong = database.getTenPhongById(phongTro.getId());
        idphong.setText("Phòng: "+tenPhong);
        dongtiennuocthangnam.setText("Tháng: "+phongTro.getDongtiennuocthangnam());
        sokhoitieuthu.setText(phongTro.getSokhoitieuthu());
        giatien.setText(phongTro.getGiatien());
        tongtien.setText("Tổng: "+phongTro.getTongtien()+" VNĐ");
        trangthai.setText(phongTro.getTrangthai());

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
        sua.setVisibility(View.GONE);
        xoa.setVisibility(View.GONE);

// Xử lý sự kiện khi nhấn vào item
        convertView.setOnClickListener(v -> {
            clickCount++;

            // Reset clickCount nếu không có nhấn thêm sau 800ms
            new android.os.Handler().postDelayed(() -> {
                if (clickCount == 1) {
                    // Xử lý sự kiện nhấn một lần
                    Intent intent = new Intent(context, HienThi_TienNuoc_Admin_Activity.class);
                    intent.putExtra("idnuoc", phongTro.getIdnuoc());
                    intent.putExtra("id", phongTro.getId());
                    intent.putExtra("dongtiennuocthangnam", phongTro.getDongtiennuocthangnam());
                    intent.putExtra("sokhoitieuthu", phongTro.getSokhoitieuthu());
                    intent.putExtra("giatien", phongTro.getGiatien());
                    intent.putExtra("tongtien", phongTro.getTongtien());
                    intent.putExtra("trangthai", phongTro.getTrangthai());

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
            Intent intent = new Intent(context, Sua_TienNuoc_Activity.class);
            intent.putExtra("idnuoc", phongTro.getIdnuoc());
            intent.putExtra("id", phongTro.getId());
            intent.putExtra("dongtiennuocthangnam", phongTro.getDongtiennuocthangnam());
            intent.putExtra("sokhoitieuthu", phongTro.getSokhoitieuthu());
            intent.putExtra("giatien", phongTro.getGiatien());
            intent.putExtra("tongtien", phongTro.getTongtien());
            intent.putExtra("trangthai", phongTro.getTrangthai());

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        // Xóa
        xoa.setOnClickListener(v -> {
            Toast.makeText(context, "Bạn đã nhấn nút xóa", Toast.LENGTH_SHORT).show();
            int rowsDeleted = database.deletetienTienNuoc(phongTro.getIdnuoc());

            if (rowsDeleted > 0) {
                listPhongTro.remove(i);
                notifyDataSetChanged();
                Toast.makeText(context, "Đã xóa bill tiền đien thành công!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Không tìm thấy bill tien diẹn để xóa.", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

}


