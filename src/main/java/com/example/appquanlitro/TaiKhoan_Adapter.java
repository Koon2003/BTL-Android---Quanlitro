package com.example.appquanlitro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log; // Thêm import cho Log
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TaiKhoan_Adapter extends BaseAdapter {
    private static final String TAG = "TaiKhoan_Adapter"; // Đặt một tag cho log
    private Context context;
    private int layout;
    private List<TaiKhoan> List;
    private Database database; // Thêm tham chiếu đến Database

    public TaiKhoan_Adapter(Context context, int layout, List<TaiKhoan> List) {
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

    private int clickCount = 0; // Biến đếm số lần nhấn
    private boolean areButtonsVisible = false; // Biến trạng thái hiển thị

    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewtemp;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            viewtemp = inflater.inflate(layout, viewGroup, false);
        } else {
            viewtemp = view;
        }

        TaiKhoan tt = List.get(i);
        TextView tendn = viewtemp.findViewById(R.id.tendn);
        TextView matkhau = viewtemp.findViewById(R.id.matkhau);
        TextView hovaten = viewtemp.findViewById(R.id.hovaten);
        TextView ngaysinh = viewtemp.findViewById(R.id.ngaysinh);
        TextView cccd = viewtemp.findViewById(R.id.cccd);
        TextView quequan = viewtemp.findViewById(R.id.quequan);
        TextView sdt = viewtemp.findViewById(R.id.sdt);
        TextView quyen = viewtemp.findViewById(R.id.quyen);
        ImageView imght = viewtemp.findViewById(R.id.img);
        ImageButton xoa = viewtemp.findViewById(R.id.imgxoa);
        ImageButton sua = viewtemp.findViewById(R.id.imgsua);

        // Hiển thị dữ liệu
        tendn.setText(tt.getTendn());
        matkhau.setText(tt.getMatkhau());
        hovaten.setText(tt.getHovaten());
        ngaysinh.setText(tt.getNgaysinh());
        cccd.setText(tt.getCccd());
        quequan.setText(tt.getQuequan());
        sdt.setText(tt.getSdt());
        quyen.setText(tt.getQuyen());

        // Kiểm tra quyền và cập nhật giao diện
        if ("admin".equalsIgnoreCase(tt.getQuyen())) {
            imght.setImageResource(R.drawable.admin); // Hình admin
            quyen.setTextColor(Color.RED); // Màu đỏ cho admin
        } else if ("user".equalsIgnoreCase(tt.getQuyen())) {
            imght.setImageResource(R.drawable.user); // Hình user
            quyen.setTextColor(context.getResources().getColor(R.color.black)); // Màu đen cho user
        }

        // Ghi log thông tin tài khoản
        Log.d(TAG, "Hiển thị tài khoản: " + tt.getTendn());
        sua.setVisibility(View.GONE);
        xoa.setVisibility(View.GONE);

        // Xử lý sự kiện khi nhấn vào item
        viewtemp.setOnClickListener(v -> {
            clickCount++;

            // Reset clickCount nếu không có nhấn thêm sau 800ms
            new android.os.Handler().postDelayed(() -> {
                if (clickCount == 1) {
                    // Xử lý sự kiện nhấn một lần
                    Intent intent = new Intent(context, HienThi_ThongTIn_TaiKhoan_Admin_Activity.class);
                    intent.putExtra("tendn", tt.getTendn());
                    intent.putExtra("matkhau", tt.getMatkhau());
                    intent.putExtra("hovaten", tt.getHovaten());
                    intent.putExtra("ngaysinh", tt.getNgaysinh());
                    intent.putExtra("cccd", tt.getCccd());
                    intent.putExtra("quequan", tt.getQuequan());
                    intent.putExtra("sdt", tt.getSdt());
                    intent.putExtra("quyen", tt.getQuyen());

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

        // Xử lý sự kiện cho nút Sửa
        sua.setOnClickListener(v -> {
            Intent intent = new Intent(context, Sua_TaiKhoan_Activity.class);
            intent.putExtra("tendn", tt.getTendn());
            intent.putExtra("matkhau", tt.getMatkhau());
            intent.putExtra("hovaten", tt.getHovaten());
            intent.putExtra("ngaysinh", tt.getNgaysinh());
            intent.putExtra("cccd", tt.getCccd());
            intent.putExtra("quequan", tt.getQuequan());
            intent.putExtra("sdt", tt.getSdt());
            intent.putExtra("quyen", tt.getQuyen());

            // Thêm cờ FLAG_ACTIVITY_NEW_TASK
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            Log.d(TAG, "Khởi động Activity sửa tài khoản: " + tt.getTendn());
        });

        // Xử lý sự kiện cho nút xóa
        xoa.setOnClickListener(v -> {
            Toast.makeText(context, "Bạn đã nhấn nút xóa", Toast.LENGTH_SHORT).show();
            int rowsDeleted = database.deleteTaiKhoan(tt.getTendn());
            Log.d(TAG, "Xóa tài khoản: " + tt.getTendn() + ", Số hàng đã xóa: " + rowsDeleted);
            if (rowsDeleted > 0) {
                List.remove(i); // Xóa đối tượng khỏi danh sách
                notifyDataSetChanged(); // Cập nhật danh sách
                Toast.makeText(context, "Đã xóa tài khoản thành công!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Không tìm thấy tài khoản để xóa.", Toast.LENGTH_SHORT).show();
            }
        });

        return viewtemp;
    }}