package com.example.appquanlitro;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class Them_TaiKhoan_Activity extends AppCompatActivity {
    EditText tendn, matkhau, hovaten, cccd, quequan,ngaysinh, sdt, nhaplaimatkhau, btnSelectQuyen;
    Button btnAdd,btnHuy; // Nút để chọn quyền
    String quyenValue; // Lưu giá trị quyền đã chọn
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_taikhoan);

        tendn = findViewById(R.id.tendn);
        matkhau = findViewById(R.id.matkhau);
        nhaplaimatkhau = findViewById(R.id.nhaplaimatkhau);
        hovaten = findViewById(R.id.hovaten);
        cccd = findViewById(R.id.cccd);
        quequan = findViewById(R.id.quequan);
        sdt = findViewById(R.id.sdt);
        ngaysinh = findViewById(R.id.ngaysinh);
        btnAdd = findViewById(R.id.btnSave);
        btnHuy=findViewById(R.id.btnHuy);
        btnSelectQuyen = findViewById(R.id.quyen); // Nút để chọn quyền

        database = new Database(this, "quanlitro.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS taikhoan (tendn VARCHAR(20) PRIMARY KEY, matkhau VARCHAR(50), hovaten VARCHAR(50),ngaysinh VARCHAR(20), cccd VARCHAR(20), quequan VARCHAR(50), sdt VARCHAR(15), quyen VARCHAR(50))");

        // Xử lý sự kiện nút chọn quyền
        btnSelectQuyen.setOnClickListener(v -> showQuyenDialog());

        // Xử lý sự kiện nút chọn quê quán
        quequan.setOnClickListener(v -> showQueQuanDialog());
// Trong phương thức onCreate, thêm đoạn mã sau:
        ngaysinh.setOnClickListener(v -> showDatePicker());

        btnAdd.setOnClickListener(v -> saveTaiKhoan());
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Them_TaiKhoan_Activity.this, TaiKhoan_Admin_Activity.class);
                startActivity(intent);
            }
        });
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
    private void showQuyenDialog() {
        String[] quyenArray = {"admin", "khách hàng"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn quyền")
                .setItems(quyenArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        quyenValue = quyenArray[which]; // Lưu giá trị quyền đã chọn
                        btnSelectQuyen.setText(quyenValue); // Cập nhật nút hiển thị quyền
                    }
                });
        builder.show();
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

    private void saveTaiKhoan() {
        String tendnValue = tendn.getText().toString();
        String matkhauValue = matkhau.getText().toString();
        String nhapLaiMatKhauValue = nhaplaimatkhau.getText().toString();
        String hovatenValue = hovaten.getText().toString();
        String ngaysinhValue = ngaysinh.getText().toString();
        String cccdValue = cccd.getText().toString();
        String quequanValue = quequan.getText().toString();
        String sdtValue = sdt.getText().toString();

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

        // Kiểm tra xem tên đăng nhập đã tồn tại chưa
        if (isUsernameExists(tendnValue)) {
            Toast.makeText(this, "Tên đăng nhập đã tồn tại. Vui lòng chọn tên khác.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Chèn dữ liệu vào cơ sở dữ liệu
        database.insertDataTaiKhoan(tendnValue, matkhauValue, hovatenValue,ngaysinhValue, cccdValue, quequanValue, sdtValue, quyenValue);

        Toast.makeText(this, "Tài khoản đã được thêm!", Toast.LENGTH_SHORT).show();

        // Reset các trường nhập liệu
        resetInputFields();

        // Quay lại trang quản lý phòng trọ
        Intent intent = new Intent(Them_TaiKhoan_Activity.this, TaiKhoan_Admin_Activity.class);
        startActivity(intent);
        finish(); // Kết thúc activity hiện tại
    }

    private boolean isUsernameExists(String username) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM taikhoan WHERE tendn = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    private void resetInputFields() {
        tendn.setText("");
        matkhau.setText("");
        nhaplaimatkhau.setText(""); // Reset nhập lại mật khẩu
        hovaten.setText("");
        cccd.setText("");
        ngaysinh.setText("");
        quequan.setText(""); // Reset trường quê quán
        sdt.setText("");
        btnSelectQuyen.setText("Chọn quyền"); // Reset nút chọn quyền
        quyenValue = null; // Reset giá trị quyền
    }
}