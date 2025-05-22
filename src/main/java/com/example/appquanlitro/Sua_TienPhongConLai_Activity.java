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

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Sua_TienPhongConLai_Activity extends AppCompatActivity {

    private Database database;
    private EditText edtMahoso, edtHovaten, edtSoTienConLai,edtTrangThai;
    private TextView txtIdPhong, txtGiaTien, txtCccd, txtNgaySinh, txtSdt,txtmaidtienphongconlai;
    private Button btnSave, btnHuy;
    String maidtienconlai; // ID của tiền cọc

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_tienphongconlai);

        // Khởi tạo database
        database = new Database(this, "quanlitro.db", null, 1);

        // Ánh xạ view
        txtmaidtienphongconlai=findViewById(R.id.maidtienconlai);
        edtMahoso = findViewById(R.id.mahoso);
        txtIdPhong = findViewById(R.id.id);
        txtGiaTien = findViewById(R.id.giatien);
        edtHovaten = findViewById(R.id.hovaten);
        txtCccd = findViewById(R.id.cccd);
        txtNgaySinh = findViewById(R.id.ngaysinh);
        txtSdt = findViewById(R.id.sdt);
        edtTrangThai = findViewById(R.id.trangthai);

        edtSoTienConLai = findViewById(R.id.sotienconlai);
        btnSave = findViewById(R.id.btnSave);
        btnHuy = findViewById(R.id.btnHuy);

        // Nhận dữ liệu từ Intent
        maidtienconlai = getIntent().getStringExtra("maidtienconlai");
        String mahoso = getIntent().getStringExtra("mahoso");
        String idPhong = getIntent().getStringExtra("id");
        String giaTien = getIntent().getStringExtra("giatien");
        String hovaten = getIntent().getStringExtra("hovaten");
        String ngaysinh = getIntent().getStringExtra("ngaysinh");
        String cccd = getIntent().getStringExtra("cccdnguoinop");
        String sdt = getIntent().getStringExtra("sdt");
        String trangthai = getIntent().getStringExtra("trangthai");

        String sotienconlai = getIntent().getStringExtra("sotienconlai");

        // Thiết lập dữ liệu vào các trường
        edtMahoso.setText(mahoso);
        edtMahoso.setText(mahoso);
        txtIdPhong.setText(String.valueOf(idPhong));
        txtGiaTien.setText(giaTien);
        edtHovaten.setText(hovaten);
        txtCccd.setText(cccd);
        txtNgaySinh.setText(ngaysinh);
        txtSdt.setText(sdt);
        edtTrangThai.setText(trangthai);
        edtSoTienConLai.setText(sotienconlai);
        txtmaidtienphongconlai.setText(maidtienconlai);
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
        edtTrangThai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogChonTrangThai();
            }
        });

        // Xử lý khi click nút Lưu
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTienPhongConLai();
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
    private void updateTienPhongConLai() {
        // Lấy các giá trị từ các trường nhập liệu
        String mahoso = edtMahoso.getText().toString().trim();
        String idPhong = txtIdPhong.getText().toString().trim();
        String giaTien = txtGiaTien.getText().toString().trim();
        String hovaten = edtHovaten.getText().toString().trim();
        String cccd = txtCccd.getText().toString().trim();
        String ngaySinh = txtNgaySinh.getText().toString().trim();
        String sdt = txtSdt.getText().toString().trim();
        String trangThai = edtTrangThai.getText().toString().trim();
        String soTienConLai = edtSoTienConLai.getText().toString().trim();

        // Kiểm tra xem các trường có rỗng không
        if (mahoso.isEmpty() || idPhong.isEmpty() || giaTien.isEmpty() || hovaten.isEmpty() || cccd.isEmpty() || ngaySinh.isEmpty() || sdt.isEmpty() || trangThai.isEmpty() || soTienConLai.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo câu lệnh SQL để cập nhật thông tin
        String sql = "UPDATE tienphongconlai SET " +
                "mahoso = '" + mahoso + "', " +
                "id = '" + idPhong + "', " +
                "giatien = '" + giaTien + "', " +
                "hovaten = '" + hovaten + "', " +
                "ngaysinh = '" + ngaySinh + "', " +
                "cccdnguoinop = '" + cccd + "', " +
                "sdt = '" + sdt + "', " +
                "trangthai = '" + trangThai + "', " +
                "sotienconlai = '" + soTienConLai + "' " +
                "WHERE maidtienconlai = '" + maidtienconlai + "'";

        // Thực thi câu lệnh SQL
        database.QueryData(sql);

        // Thông báo cập nhật thành công
        Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();

        // Đóng activity sau khi cập nhật thành công
        finish();
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
                edtTrangThai.setText(trangThaiOptions[which]);
            }
        });
        builder.show();
    }
}