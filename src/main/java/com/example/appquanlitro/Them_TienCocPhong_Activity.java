package com.example.appquanlitro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Them_TienCocPhong_Activity extends AppCompatActivity {

    private Database database;
    private EditText edtMahoso, edtHovaten, edtHinhThucCoc, edtSoTienDaCoc, edtSoTienConLai;
    private TextView txtIdPhong, txtGiaTien, txtCccd, txtNgaySinh, txtSdt;
    private Button btnSave, btnHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_tiencocphong);

        // Khởi tạo database
        database = new Database(this, "quanlitro.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tiencocphong (maidcoc INTEGER PRIMARY KEY AUTOINCREMENT, mahoso TEXT, id INTEGER, giatien TEXT, hovaten TEXT, ngaysinh TEXT, cccdnguoinop TEXT, sdt TEXT, hinhthuccoc TEXT, sotiendanopcoc TEXT, sotienconlai TEXT)");

        // Ánh xạ view
        edtMahoso = findViewById(R.id.mahoso);
        txtIdPhong = findViewById(R.id.id);
        txtGiaTien = findViewById(R.id.giatien);
        edtHovaten = findViewById(R.id.hovaten);
        txtCccd = findViewById(R.id.cccd);
        txtNgaySinh = findViewById(R.id.ngaysinh);
        txtSdt = findViewById(R.id.sdt);
        edtHinhThucCoc = findViewById(R.id.hinhthuccoc);
        edtSoTienDaCoc = findViewById(R.id.sotiendacoc);
        edtSoTienConLai = findViewById(R.id.sotienconlai);
        btnSave = findViewById(R.id.btnSave);
        btnHuy = findViewById(R.id.btnHuy);

        // Lắng nghe thay đổi trong EditText edtMahoso
        edtMahoso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần xử lý
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Load dữ liệu ngay khi người dùng nhập
                loadThongTinPhong(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không cần xử lý
            }
        });



        // Xử lý khi click vào họ và tên
        edtHovaten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogChonNguoiThue();
            }
        });

        // Xử lý khi click vào hình thức cọc
        edtHinhThucCoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogHinhThucCoc();
            }
        });

        // Xử lý khi click nút Lưu
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themTienCoc();
            }
        });

        // Xử lý khi click nút Hủy
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Load id phòng và giá tiền từ mã hồ sơ khi nhập liệu
    private void loadThongTinPhong(String mahoso) {
        if (mahoso.isEmpty()) {
            txtIdPhong.setText("");
            txtGiaTien.setText("");
            return;
        }

        Cursor cursor = database.GetData("SELECT id, giatien FROM hosothuetro WHERE mahoso LIKE '" + mahoso + "%' LIMIT 1");
        if (cursor != null && cursor.moveToFirst()) {
            String idPhong = cursor.getString(0);
            String giaTien = cursor.getString(1);
            cursor.close();

            txtIdPhong.setText(idPhong);
            txtGiaTien.setText(giaTien);
        } else {
            txtIdPhong.setText("");
            txtGiaTien.setText("");
        }
    }



    // Hiển thị Dialog chọn người thuê
    private void showDialogChonNguoiThue() {
        String mahoso = edtMahoso.getText().toString().trim();
        if (mahoso.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mã hồ sơ trước", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = database.GetData("SELECT hovaten FROM hosothuetro WHERE mahoso = '" + mahoso + "'");
        if (cursor == null || cursor.getCount() == 0) {
            Toast.makeText(this, "Không có người thuê nào", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> danhSachNguoiThue = new ArrayList<>();
        while (cursor.moveToNext()) {
            danhSachNguoiThue.add(cursor.getString(0));
        }
        cursor.close();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn đại diện nộp tiền");
        builder.setItems(danhSachNguoiThue.toArray(new String[0]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenNguoiThue = danhSachNguoiThue.get(which);
                edtHovaten.setText(tenNguoiThue);
                loadThongTinNguoiThue(tenNguoiThue);
            }
        });
        builder.show();
    }

    // Load thông tin người thuê
    private void loadThongTinNguoiThue(String tenNguoiThue) {
        Cursor cursor = database.GetData("SELECT cccd, ngaysinh, sdt FROM hosothuetro WHERE hovaten = '" + tenNguoiThue + "'");
        if (cursor != null && cursor.moveToFirst()) {
            String cccd = cursor.getString(0);
            String ngaySinh = cursor.getString(1);
            String sdt = cursor.getString(2);

            txtCccd.setText(cccd);
            txtNgaySinh.setText(ngaySinh);
            txtSdt.setText(sdt);
            cursor.close();
        }
    }

    // Hiển thị Dialog chọn hình thức cọc
    private void showDialogHinhThucCoc() {
        String[] hinhThucCoc = {"Cọc 1 nửa", "Cọc tất cả"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn hình thức cọc");
        builder.setItems(hinhThucCoc, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String hinhThuc = hinhThucCoc[which];
                edtHinhThucCoc.setText(hinhThuc);
                tinhToanTienCoc(hinhThuc);
            }
        });
        builder.show();
    }

    // Tính toán tiền cọc và tiền còn lại
    private void tinhToanTienCoc(String hinhThuc) {
        String mahoso = edtMahoso.getText().toString().trim();
        if (mahoso.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mã hồ sơ trước", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = database.GetData("SELECT giatien, hinhthucthue FROM hosothuetro WHERE mahoso = '" + mahoso + "'");
        if (cursor != null && cursor.moveToFirst()) {
            int giaTien = Integer.parseInt(cursor.getString(0));
            String hinhThucThue = cursor.getString(1);
            cursor.close();

            int soThang = hinhThucThue.equals("1 năm") ? 12 : 6;
            int tienCoc = 0;

            if (hinhThuc.equals("Cọc 1 nửa")) {
                tienCoc = giaTien * (soThang / 2);
            } else if (hinhThuc.equals("Cọc tất cả")) {
                tienCoc = giaTien * soThang;
            }

            int tienConLai = (giaTien * soThang) - tienCoc;

            edtSoTienDaCoc.setText(String.valueOf(tienCoc));
            edtSoTienConLai.setText(String.valueOf(tienConLai));
        }
    }

    // Thêm tiền cọc vào database
    private void themTienCoc() {
        String mahoso = edtMahoso.getText().toString().trim();
        String idPhong = txtIdPhong.getText().toString().trim();
        String giaTien = txtGiaTien.getText().toString().trim();
        String hovaten = edtHovaten.getText().toString().trim();
        String cccd = txtCccd.getText().toString().trim();
        String ngaySinh = txtNgaySinh.getText().toString().trim();
        String sdt = txtSdt.getText().toString().trim();
        String hinhThucCoc = edtHinhThucCoc.getText().toString().trim();
        String soTienDaCoc = edtSoTienDaCoc.getText().toString().trim();
        String soTienConLai = edtSoTienConLai.getText().toString().trim();

        if (mahoso.isEmpty() || idPhong.isEmpty() || giaTien.isEmpty() || hovaten.isEmpty() ||
                cccd.isEmpty() || ngaySinh.isEmpty() || sdt.isEmpty() || hinhThucCoc.isEmpty() ||
                soTienDaCoc.isEmpty() || soTienConLai.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra xem mahoso đã tồn tại trong bảng tiencocphong chưa
        Cursor cursor = database.GetData("SELECT * FROM tiencocphong WHERE mahoso = '" + mahoso + "'");
        if (cursor != null && cursor.getCount() > 0) {
            Toast.makeText(this, "Mã hồ sơ này đã tồn tại và đã coc tiền rồi. Không thể thêm nữa!", Toast.LENGTH_SHORT).show();
            cursor.close();
            return;
        }
        cursor.close();

        // Tiến hành thêm nếu chưa tồn tại
        boolean isSuccess = database.themTienCoc(mahoso, idPhong, giaTien, hovaten, ngaySinh, cccd, sdt, hinhThucCoc, soTienDaCoc, soTienConLai);
        if (isSuccess) {
            Toast.makeText(this, "Thêm tiền cọc thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Thêm tiền cọc thất bại", Toast.LENGTH_SHORT).show();
        }
    }

}