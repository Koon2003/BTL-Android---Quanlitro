package com.example.appquanlitro;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class Them_TienPhongConLai_Activity extends AppCompatActivity {

    private Database database;
EditText edtMahoso, edtHovaten, edtTrangthai, edtSoTienConLai;
    private TextView txtIdPhong, txtGiaTien, txtCccd, txtNgaySinh, txtSdt;
    private Button btnSave, btnHuy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_tienphongconlai);

        // Khởi tạo database
        database = new Database(this, "quanlitro.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tienphongconlai (maidtienconlai INTEGER PRIMARY KEY AUTOINCREMENT, mahoso TEXT, id INTEGER, giatien TEXT, hovaten TEXT, ngaysinh TEXT, cccdnguoinop TEXT, sdt TEXT,  sotienconlai TEXT,trangthai TEXT)");

        // Ánh xạ view
        edtMahoso = findViewById(R.id.mahoso);
        txtIdPhong = findViewById(R.id.id);
        txtGiaTien = findViewById(R.id.giatien);
        edtHovaten = findViewById(R.id.hovaten);
        txtCccd = findViewById(R.id.cccd);
        txtNgaySinh = findViewById(R.id.ngaysinh);
        txtSdt = findViewById(R.id.sdt);
        edtTrangthai = findViewById(R.id.trangthai);

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

// Thêm listener cho edtTrangthai
        edtTrangthai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogChonTrangThai();
            }
        });

        // Xử lý khi click nút Lưu
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themTienTienconLaiPhaiDong();
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
            edtSoTienConLai.setText(""); // Reset số tiền còn lại
            return;
        }

        Cursor cursor = database.GetData("SELECT id, giatien, sotienconlai FROM tiencocphong WHERE mahoso = '" + mahoso + "' LIMIT 1");
        if (cursor != null && cursor.moveToFirst()) {
            String idPhong = cursor.getString(0);
            String giaTien = cursor.getString(1);
            String soTienConLai = cursor.getString(2); // Lấy số tiền còn lại
            cursor.close();

            txtIdPhong.setText(idPhong);
            txtGiaTien.setText(giaTien);
            edtSoTienConLai.setText(soTienConLai); // Cập nhật số tiền còn lại
        } else {
            txtIdPhong.setText("");
            txtGiaTien.setText("");
            edtSoTienConLai.setText(""); // Reset số tiền còn lại
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


    // Phương thức hiển thị dialog chọn trạng thái
    private void showDialogChonTrangThai() {
        final String[] trangThaiOptions = {"Đã đóng", "Chưa đóng"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn trạng thái");
        builder.setItems(trangThaiOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cập nhật trạng thái vào edtTrangthai
                edtTrangthai.setText(trangThaiOptions[which]);
            }
        });
        builder.show();
    }
    // Thêm tiền cọc vào database
    private void themTienTienconLaiPhaiDong() {
        String mahoso = edtMahoso.getText().toString().trim();
        String idPhong = txtIdPhong.getText().toString().trim();
        String giaTien = txtGiaTien.getText().toString().trim();
        String hovaten = edtHovaten.getText().toString().trim();
        String cccd = txtCccd.getText().toString().trim();
        String ngaySinh = txtNgaySinh.getText().toString().trim();
        String sdt = txtSdt.getText().toString().trim();
        String soTienConLai = edtSoTienConLai.getText().toString().trim();
        String trangthai = edtTrangthai.getText().toString().trim();



        if (mahoso.isEmpty() || idPhong.isEmpty() || giaTien.isEmpty() || hovaten.isEmpty() ||
                cccd.isEmpty() || ngaySinh.isEmpty() || sdt.isEmpty() ||  soTienConLai.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra xem mahoso đã tồn tại trong bảng tiencocphong chưa
        Cursor cursor = database.GetData("SELECT * FROM tienphongconlai WHERE mahoso = '" + mahoso + "'");
        if (cursor != null && cursor.getCount() > 0) {
            Toast.makeText(this, "Mã hồ sơ này đã tồn tại và đã đóng tiền còn lại rồi. Không thể thêm nữa!", Toast.LENGTH_SHORT).show();
            cursor.close();
            return;
        }
        cursor.close();

        // Tiến hành thêm nếu chưa tồn tại
        boolean isSuccess = database.themTienNopPhongCOnlai(mahoso, idPhong, giaTien, hovaten, ngaySinh, cccd, sdt, soTienConLai,trangthai);
        if (isSuccess) {
            Toast.makeText(this, "Thêm tiền  còn lại phải đóng thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Thêm tiền còn lại thất bại", Toast.LENGTH_SHORT).show();
        }
    }

}