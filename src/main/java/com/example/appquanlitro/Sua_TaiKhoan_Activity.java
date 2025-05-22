package com.example.appquanlitro;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Sua_TaiKhoan_Activity extends AppCompatActivity {

    private EditText tendn, matkhau, nhaplaimatkhau, hovaten,ngaysinh, cccd, quequan, sdt, quyen;
    private Database database; // Khai báo đối tượng database
    private String quyenValue; // Biến lưu giá trị quyền đã chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_taikhoan);


        // Lấy dữ liệu từ Intent
        tendn = findViewById(R.id.tendn);
        matkhau = findViewById(R.id.matkhau);
        nhaplaimatkhau = findViewById(R.id.nhaplaimatkhau);
        hovaten = findViewById(R.id.hovaten);
        cccd = findViewById(R.id.cccd);
        quequan = findViewById(R.id.quequan);
        sdt = findViewById(R.id.sdt);
        ngaysinh = findViewById(R.id.ngaysinh);
        quyen = findViewById(R.id.quyen);

        // Nhận dữ liệu từ Intent
        tendn.setText(getIntent().getStringExtra("tendn"));
        String matKhauValue = getIntent().getStringExtra("matkhau");
        matkhau.setText(matKhauValue);
        nhaplaimatkhau.setText(matKhauValue); // Hiển thị mật khẩu trong trường nhập lại mật khẩu
        hovaten.setText(getIntent().getStringExtra("hovaten"));
        cccd.setText(getIntent().getStringExtra("cccd"));
        ngaysinh.setText(getIntent().getStringExtra("ngaysinh"));
        quequan.setText(getIntent().getStringExtra("quequan"));
        sdt.setText(getIntent().getStringExtra("sdt"));
        quyenValue = getIntent().getStringExtra("quyen"); // Lưu giá trị quyền từ Intent
        quyen.setText(quyenValue); // Cập nhật trường quyền

        // Xử lý sự kiện cho nút Cập nhật
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> updateTaiKhoan());

        // Xử lý sự kiện cho nút Hủy
        Button btnHuy = findViewById(R.id.btnHuy);
        btnHuy.setOnClickListener(v -> finish());

        // Xử lý sự kiện cho trường quyền
        quyen.setOnClickListener(v -> showQuyenDialog());
// Trong phương thức onCreate, thêm đoạn mã sau:
        ngaysinh.setOnClickListener(v -> showDatePicker());

        // Xử lý sự kiện cho trường quê quán
        quequan.setOnClickListener(v -> showQueQuanDialog());
    }

    private void updateTaiKhoan() {
        String tendnValue = tendn.getText().toString();
        String matkhauValue = matkhau.getText().toString();
        String nhapLaiMatKhauValue = nhaplaimatkhau.getText().toString();
        String hovatenValue = hovaten.getText().toString();
        String ngaysinhValue = ngaysinh.getText().toString();
        String quequanValue = quequan.getText().toString();
        String sdtValue = sdt.getText().toString();
        String cccdValue = cccd.getText().toString();

        // Kiểm tra dữ liệu hợp lệ
        if (tendnValue.isEmpty() || matkhauValue.isEmpty() || nhapLaiMatKhauValue.isEmpty() ||
                hovatenValue.isEmpty() || quequanValue.isEmpty() || quyenValue == null) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu có khớp không
        if (!matkhauValue.equals(nhapLaiMatKhauValue)) {
            Toast.makeText(this, "Mật khẩu và Nhập lại mật khẩu không khớp.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra số điện thoại
        if (sdtValue.length() < 10 || sdtValue.length() > 11) {
            Toast.makeText(this, "Số điện thoại phải có từ 10 đến 11 chữ số.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra CCCD
        if (cccdValue.length() != 12) {
            Toast.makeText(this, "CCCD phải có đúng 12 chữ số.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cập nhật thông tin tài khoản vào cơ sở dữ liệu
        String sql = "UPDATE taikhoan SET matkhau = ?, hovaten = ?,ngaysinh=?, cccd = ?, quequan = ?, sdt = ?, quyen = ? WHERE tendn = ?";
        // Thực thi câu lệnh
        SQLiteDatabase db = this.openOrCreateDatabase("quanlitro.db", MODE_PRIVATE, null);
        SQLiteStatement statement = db.compileStatement(sql);

        // Gán giá trị cho các tham số
        statement.bindString(1, matkhauValue);
        statement.bindString(2, hovatenValue);
        statement.bindString(3, ngaysinhValue);
        statement.bindString(4, cccdValue);
        statement.bindString(5, quequanValue);
        statement.bindString(6, sdtValue);
        statement.bindString(7, quyenValue);
        statement.bindString(8, tendnValue); // Gán tên đăng nhập làm điều kiện

        long result = statement.executeUpdateDelete();
        db.close();

        if (result == 0) {
            Toast.makeText(this, "Cập nhật không thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Cập nhật tài khoản thành công!", Toast.LENGTH_SHORT).show();
            finish(); // Đóng Activity hiện tại
        }
    }

    private void showQuyenDialog() {
        String[] quyenArray = {"admin", "khách hàng"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn quyền")
                .setItems(quyenArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        quyenValue = quyenArray[which]; // Lưu giá trị quyền đã chọn
                        quyen.setText(quyenValue); // Cập nhật trường quyền
                    }
                });
        builder.show();
    }
    // Phương thức hiển thị DatePicker
    private void showDatePicker() {
        // Lấy ngày hiện tại
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            // Định dạng ngày tháng
            String formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
            ngaysinh.setText(formattedDate); // Cập nhật trường ngày sinh
        }, year, month, day);

        // Hiển thị DatePicker
        datePickerDialog.show();
    }
    private void showQueQuanDialog() {
        String[] queQuanArray = {
                "Hà Nội", "Hà Giang", "Cao Bằng", "Bắc Kạn", "Tuyên Quang",
                "Lào Cai", "Điện Biên", "Lai Châu", "Sơn La", "Yên Bái",
                "Hòa Bình", "Thái Nguyên", "Lạng Sơn", "Quảng Ninh", "Bắc Ninh",
                "Hải Dương", "Hưng Yên", "Hải Phòng", "Nam Định", "Ninh Bình",
                "Thái Bình", "Hà Nam", "Vĩnh Phúc", "Phú Thọ", "Điện Biên",
                "Quảng Bình", "Quảng Trị", "Thừa Thiên Huế", "Đà Nẵng", "Quảng Nam",
                "Quảng Ngãi", "Bình Định", "Phú Yên", "Khánh Hòa", "Ninh Thuận",
                "Bình Thuận", "Kon Tum", "Gia Lai", "Đắk Lắk", "Đắk Nông",
                "Lâm Đồng", "Hồ Chí Minh", "Bình Dương", "Đồng Nai", "Bà Rịa - Vũng Tàu",
                "Tiền Giang", "Bến Tre", "Trà Vinh", "Vĩnh Long", "Đồng Tháp",
                "An Giang", "Kiên Giang", "Cà Mau", "Hậu Giang", "Sóc Trăng",
                "Bạc Liêu", "Cà Mau", "Ninh Thuận"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn quê quán")
                .setItems(queQuanArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        quequan.setText(queQuanArray[which]); // Cập nhật trường quê quán
                    }
                });
        builder.show();
    }
}