package com.example.appquanlitro;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Them_HoSoNguoiThueTro_Activity extends AppCompatActivity {

    EditText mahoso, cccd, phongthue, giatien, hinhthucthue, ngaybatdauthue, ngayketthucthue, trangthai;
    TextView  hovaten, sdt, quequan, ngaysinh;
    Button btnAdd, btnHuy;

    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_hosonguoithuetro);

        // Ánh xạ các view
        mahoso = findViewById(R.id.mahoso);
        hovaten = findViewById(R.id.hovaten);
        ngaysinh = findViewById(R.id.ngaysinh);
        cccd = findViewById(R.id.cccd);
        sdt = findViewById(R.id.sdt);
        quequan = findViewById(R.id.quequan);
        phongthue = findViewById(R.id.id);
        giatien = findViewById(R.id.giatien);
        hinhthucthue = findViewById(R.id.hinhthucthue);
        ngaybatdauthue = findViewById(R.id.ngaybatdauthue);
        ngayketthucthue = findViewById(R.id.ngayketthucthue);
        trangthai = findViewById(R.id.trangthai);
        btnAdd = findViewById(R.id.btnSave);
        btnHuy = findViewById(R.id.btnHuy);

        // Khởi tạo database
        database = new Database(this, "quanlitro.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS hosothuetro (maid INTEGER PRIMARY KEY AUTOINCREMENT, mahoso TEXT, hovaten TEXT, ngaysinh TEXT, cccd TEXT, quequan TEXT, sdt TEXT, id INTEGER, giatien TEXT, hinhthucthue TEXT, ngaybatdau TEXT, ngayketthuc TEXT, xacnhanhopdong TEXT, trangthai TEXT)");

        // Xử lý sự kiện chọn phòng
        phongthue.setOnClickListener(v -> showDanhSachPhongDialog());

        // Xử lý sự kiện khi nhập CCCD
        cccd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 12) {
                    String cccdText = s.toString();
                    loadThongTinTuCCCD(cccdText);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        // Xử lý sự kiện chọn ngày bắt đầu thuê
        ngaybatdauthue.setOnClickListener(v -> showDatePicker());

        // Xử lý sự kiện chọn hình thức thuê
        hinhthucthue.setOnClickListener(v -> showHinhThucThueDialog());

        // Xử lý sự kiện khi nhấn nút "Thêm"
        btnAdd.setOnClickListener(v -> saveHoSoThueTro());

        // Xử lý sự kiện khi nhấn nút "Hủy"
        btnHuy.setOnClickListener(view -> {
            Intent intent = new Intent(Them_HoSoNguoiThueTro_Activity.this, HoSo_NguoiThueTro_Admin_Activity.class);
            startActivity(intent);
        });
    }

    // Hiển thị dialog chọn phòng
    private void showDanhSachPhongDialog() {
        Cursor cursor = database.GetData("SELECT id, tenphong, giatien FROM phongtro");
        if (cursor == null) {
            Toast.makeText(this, "Không có dữ liệu phòng", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> danhSachPhong = new ArrayList<>();
        List<Integer> danhSachId = new ArrayList<>();
        List<Long> danhSachGiaTien = new ArrayList<>();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String tenPhong = cursor.getString(1);
            long giaTien = cursor.getLong(2);
            danhSachPhong.add(tenPhong + " - " + giaTien + " VNĐ");
            danhSachId.add(id);
            danhSachGiaTien.add(giaTien);
        }
        cursor.close();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn phòng");
        builder.setItems(danhSachPhong.toArray(new String[0]), (dialog, which) -> {
            phongthue.setText(danhSachPhong.get(which)); // Hiển thị "Tên phòng - Giá tiền VNĐ"
            phongthue.setTag(danhSachId.get(which)); // Lưu ID phòng vào tag để xử lý khi lưu
            giatien.setText(String.valueOf(danhSachGiaTien.get(which)));
        });
        builder.show();
    }

    // Kiểm tra xem CCCD có đang thuê phòng khác không
    private boolean isCCCDStillRenting(String cccd, String ngayBatDauMoi) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date ngayBatDauMoiDate = sdf.parse(ngayBatDauMoi);

            Cursor cursor = database.GetData("SELECT ngayketthuc FROM hosothuetro WHERE cccd = '" + cccd + "'");
            while (cursor.moveToNext()) {
                Date ngayKetThucCu = sdf.parse(cursor.getString(0));
                if (ngayKetThucCu != null && ngayKetThucCu.compareTo(ngayBatDauMoiDate) >= 0) {
                    cursor.close();
                    return true;
                }
            }
            cursor.close();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Load thông tin từ CCCD
    private void loadThongTinTuCCCD(String cccd) {
        Cursor cursor = database.GetData("SELECT hovaten, ngaysinh, sdt, quequan FROM taikhoan WHERE cccd = '" + cccd + "'");
        if (cursor != null && cursor.moveToFirst()) {
            hovaten.setText(cursor.getString(0));
            ngaysinh.setText(cursor.getString(1));
            sdt.setText(cursor.getString(2));
            quequan.setText(cursor.getString(3));
            cursor.close();
        } else {
            Toast.makeText(this, "CCCD không tồn tại trong hệ thống", Toast.LENGTH_SHORT).show();
        }
    }

    // Hiển thị DatePickerDialog
    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            String formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
            ngaybatdauthue.setText(formattedDate);
            calculateEndDate(formattedDate);
        }, year, month, day);

        // 🔒 Chỉ cho phép chọn ngày từ hôm nay trở đi
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());

        datePickerDialog.show();
    }


    // Hiển thị dialog chọn hình thức thuê
    private void showHinhThucThueDialog() {
        final String[] hinhThucThue = {"6 tháng", "1 năm"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn hình thức thuê");
        builder.setItems(hinhThucThue, (dialog, which) -> {
            hinhthucthue.setText(hinhThucThue[which]);
            calculateEndDate(ngaybatdauthue.getText().toString());
        });
        builder.show();
    }



    // Tính toán ngày kết thúc thuê
    private void calculateEndDate(String startDate) {
        if (startDate.isEmpty() || hinhthucthue.getText().toString().isEmpty()) return;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date date = sdf.parse(startDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            if (hinhthucthue.getText().toString().equals("6 tháng")) {
                calendar.add(Calendar.MONTH, 6);
            } else if (hinhthucthue.getText().toString().equals("1 năm")) {
                calendar.add(Calendar.YEAR, 1);
            }

            String endDate = sdf.format(calendar.getTime());
            ngayketthucthue.setText(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    // Lưu hồ sơ thuê trọ
    private void saveHoSoThueTro() {
        try {
            String cccdText = cccd.getText().toString();
            String phongThueText = phongthue.getTag() != null ? phongthue.getTag().toString() : "";            String ngayBatDauText = ngaybatdauthue.getText().toString();
            String maHoSoText = mahoso.getText().toString();

            // Kiểm tra các trường dữ liệu
            if (maHoSoText.isEmpty() || hovaten.getText().toString().isEmpty() || ngaysinh.getText().toString().isEmpty() ||
                    cccdText.isEmpty() || quequan.getText().toString().isEmpty() || sdt.getText().toString().isEmpty() ||
                    phongThueText.isEmpty() || giatien.getText().toString().isEmpty() || hinhthucthue.getText().toString().isEmpty() ||
                    ngayBatDauText.isEmpty() || ngayketthucthue.getText().toString().isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra CCCD có tồn tại không
            if (!checkCCCDExists(cccdText)) {
                Toast.makeText(this, "CCCD không tồn tại trong hệ thống", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kiểm tra CCCD có đang thuê phòng khác không
            if (isCCCDStillRenting(cccdText, ngayBatDauText)) {
                Toast.makeText(this, "Người này vẫn đang có hợp đồng thuê khác rồi!", Toast.LENGTH_LONG).show();
                return;
            }

            // Kiểm tra xem mahoso đã tồn tại chưa
            if (isMaHoSoExists(maHoSoText)) {
                // Nếu mahoso tồn tại, kiểm tra CCCD có khác người trước không
                if (isCCCDDifferent(cccdText, maHoSoText)) {
                    // Kiểm tra số lượng CCCD cho phép trong phòng
                    int soLuongCCCDAllowed = getSoLuongCCCDAllowed(phongThueText);
                    if (soLuongCCCDAllowed == -1) {
                        Toast.makeText(this, "Phòng không tồn tại", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Kiểm tra số lượng CCCD hiện tại
                    if (!checkSoLuongCCCD(phongThueText, soLuongCCCDAllowed)) {
                        return; // Không đủ chỗ
                    }

                    // Kiểm tra hợp đồng đã tồn tại
                    if (!isContractDetailsMatch(maHoSoText, phongThueText, ngayBatDauText, hinhthucthue.getText().toString())) {
                        Toast.makeText(this, "Thông tin hợp đồng không khớp!", Toast.LENGTH_LONG).show();
                        return;
                    }
                } else {
                    Toast.makeText(this, "CCCD đã tồn tại trong hồ sơ này. Vui lòng nhập CCCD khác.", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            // Thêm hồ sơ vào database
            database.QueryData("INSERT INTO hosothuetro (mahoso, hovaten, ngaysinh, cccd, quequan, sdt, id, giatien, hinhthucthue, ngaybatdau, ngayketthuc, xacnhanhopdong, trangthai) VALUES ('" +
                    maHoSoText + "', '" +
                    hovaten.getText().toString() + "', '" +
                    ngaysinh.getText().toString() + "', '" +
                    cccdText + "', '" +
                    quequan.getText().toString() + "', '" +
                    sdt.getText().toString() + "', " +
                    phongThueText + ", '" +
                    giatien.getText().toString() + "', '" +
                    hinhthucthue.getText().toString() + "', '" +
                    ngayBatDauText + "', '" +
                    ngayketthucthue.getText().toString() + "', 'Khách Đã Xác Nhận', 'Đang thuê');");

            Toast.makeText(this, "Thêm hồ sơ thành công", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Đã xảy ra lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    // Kiểm tra CCCD có đang thuê với trạng thái "Đang Thuê"
    private boolean checkCCCDInActiveContracts(String cccd) {
        Cursor cursor = database.GetData("SELECT * FROM hosothuetro WHERE cccd = '" + cccd + "' AND trangthai = 'Đang thuê'");
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Kiểm tra CCCD có khác người trước không
    private boolean isCCCDDifferent(String cccd, String maHoSo) {
        Cursor cursor = database.GetData("SELECT cccd FROM hosothuetro WHERE mahoso = '" + maHoSo + "'");
        if (cursor.moveToFirst()) {
            String existingCCCD = cursor.getString(0);
            cursor.close();
            return !existingCCCD.equals(cccd);
        }
        cursor.close();
        return false;
    }

    // Kiểm tra CCCD có tồn tại không
    private boolean checkCCCDExists(String cccd) {
        Cursor cursor = database.GetData("SELECT * FROM taikhoan WHERE cccd = '" + cccd + "'");
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Kiểm tra mã hồ sơ đã tồn tại chưa
    private boolean isMaHoSoExists(String maHoSo) {
        Cursor cursor = database.GetData("SELECT * FROM hosothuetro WHERE mahoso = '" + maHoSo + "'");
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Kiểm tra diện tích phòng thuê tối đa
    private int getSoLuongCCCDAllowed(String phongThue) {
        Cursor cursor = database.GetData("SELECT dientich FROM phongtro WHERE id = " + phongThue);
        if (cursor.moveToFirst()) {
            int dientich = cursor.getInt(0);
            cursor.close();
            if (dientich < 13) return 1;
            else if (dientich <= 26) return 2;
            else if (dientich <= 38) return 3;
        }
        return -1;
    }

    private boolean checkSoLuongCCCD(String phongThue, int soLuongCCCDAllowed) {
        Cursor cursor = database.GetData("SELECT COUNT(*) FROM hosothuetro WHERE id = " + phongThue);
        if (cursor.moveToFirst()) {
            int soLuongCCCDHienTai = cursor.getInt(0);
            cursor.close();

            if (soLuongCCCDHienTai >= soLuongCCCDAllowed) {
                Toast.makeText(this, "Phòng này chỉ được phép tối đa " + soLuongCCCDAllowed + " người. Hiện tại đã có " + soLuongCCCDHienTai + " người.", Toast.LENGTH_LONG).show();
                return false;
            }
            return true;
        }
        return false;
    }

    // Kiểm tra thông tin hợp đồng có khớp không
    private boolean isContractDetailsMatch(String maHoSo, String phongThue, String ngayBatDau, String hinhThucThue) {
        Cursor cursor = database.GetData("SELECT * FROM hosothuetro WHERE mahoso = '" + maHoSo + "'");
        if (cursor.moveToFirst()) {
            int phongThueIndex = cursor.getColumnIndex("id");
            int ngayBatDauIndex = cursor.getColumnIndex("ngaybatdau");
            int hinhThucThueIndex = cursor.getColumnIndex("hinhthucthue");

            if (phongThueIndex != -1 && ngayBatDauIndex != -1 && hinhThucThueIndex != -1) {
                String existingPhongThue = cursor.getString(phongThueIndex);
                String existingNgayBatDau = cursor.getString(ngayBatDauIndex);
                String existingHinhThucThue = cursor.getString(hinhThucThueIndex);
                cursor.close();
                return existingPhongThue.equals(phongThue) && existingNgayBatDau.equals(ngayBatDau) && existingHinhThucThue.equals(hinhThucThue);
            }
        }
        cursor.close();
        return false;
    }
}