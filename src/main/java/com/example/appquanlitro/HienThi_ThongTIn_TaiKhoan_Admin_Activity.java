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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class HienThi_ThongTIn_TaiKhoan_Admin_Activity extends AppCompatActivity {

    private EditText tendn, matkhau, nhaplaimatkhau, hovaten,ngaysinh, cccd, quequan, sdt, quyen;
    private Database database; // Khai báo đối tượng database
    private String quyenValue; // Biến lưu giá trị quyền đã chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hienthi_thongtin_taikhoan_admin);


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
    }
}