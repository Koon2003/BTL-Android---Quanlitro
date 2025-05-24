package com.example.appquanlitro;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sua_PhongTro extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText editTenPhong, editDienTich, editMoTa, editGiaTien;
    private ImageView img1, img2, img3, img4, img5;
    private Button btnUpdate;
    private TextView id;
    private int currentImageIndex;
    private List<String> imagePaths = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_phongtro);

        // Ánh xạ View
        editTenPhong = findViewById(R.id.editTenPhong);
        editDienTich = findViewById(R.id.editDienTich);
        editMoTa = findViewById(R.id.editMoTa);
        editGiaTien = findViewById(R.id.editGiaTien);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        btnUpdate = findViewById(R.id.btnCapnhat);
        id = findViewById(R.id.id);

        // Lấy dữ liệu từ Intent
        String roomId = getIntent().getStringExtra("id");
        if (roomId == null) {
            Toast.makeText(this, "ID phòng trọ không hợp lệ!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        id.setText("ID: " + roomId);
        editTenPhong.setText(getIntent().getStringExtra("tenphong"));
        editDienTich.setText(getIntent().getStringExtra("dientich"));
        editMoTa.setText(getIntent().getStringExtra("mota"));
        editGiaTien.setText(getIntent().getStringExtra("giatien"));

        // Hiển thị ảnh từ đường dẫn
        setImageViewFromPath(img1, getIntent().getStringExtra("anh1Path"));
        setImageViewFromPath(img2, getIntent().getStringExtra("anh2Path"));
        setImageViewFromPath(img3, getIntent().getStringExtra("anh3Path"));
        setImageViewFromPath(img4, getIntent().getStringExtra("anh4Path"));
        setImageViewFromPath(img5, getIntent().getStringExtra("anh5Path"));

        // Bắt sự kiện chọn ảnh
        img1.setOnClickListener(v -> openImageChooser(1));
        img2.setOnClickListener(v -> openImageChooser(2));
        img3.setOnClickListener(v -> openImageChooser(3));
        img4.setOnClickListener(v -> openImageChooser(4));
        img5.setOnClickListener(v -> openImageChooser(5));

        // Sự kiện cập nhật dữ liệu
        btnUpdate.setOnClickListener(view -> updateRoomData(roomId));
    }

    private void openImageChooser(int index) {
        currentImageIndex = index;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn hình ảnh"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                    // Lấy tên gốc của ảnh
                    String imageName = getFileNameFromUri(imageUri);

                    // Lưu ảnh với tên gốc vào bộ nhớ máy ảo
                    String imagePath = saveImageToFile(bitmap, imageName);

                    switch (currentImageIndex) {
                        case 1: img1.setImageBitmap(bitmap); img1.setTag(imagePath); break;
                        case 2: img2.setImageBitmap(bitmap); img2.setTag(imagePath); break;
                        case 3: img3.setImageBitmap(bitmap); img3.setTag(imagePath); break;
                        case 4: img4.setImageBitmap(bitmap); img4.setTag(imagePath); break;
                        case 5: img5.setImageBitmap(bitmap); img5.setTag(imagePath); break;
                    }
                    imagePaths.add(imagePath);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Không thể lấy hình ảnh", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void setImageViewFromPath(ImageView imageView, String imgPath) {
        if (imgPath != null && !imgPath.isEmpty()) {
            imageView.setImageURI(Uri.fromFile(new File(imgPath)));
            imageView.setTag(imgPath);
            imagePaths.add(imgPath);
        } else {
            imageView.setImageResource(R.drawable.main);
            imageView.setTag(null);
        }
    }

    private void updateRoomData(String roomId) {
        String tenPhong = editTenPhong.getText().toString().trim();
        String dienTich = editDienTich.getText().toString().trim();
        String moTa = editMoTa.getText().toString().trim();
        String giaTien = editGiaTien.getText().toString().trim();

        if (tenPhong.isEmpty() || dienTich.isEmpty() || moTa.isEmpty() || giaTien.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy đường dẫn ảnh từ ImageView
        String img1Path = (String) img1.getTag();
        String img2Path = (String) img2.getTag();
        String img3Path = (String) img3.getTag();
        String img4Path = (String) img4.getTag();
        String img5Path = (String) img5.getTag();

        String sql = "UPDATE phongtro SET tenphong=?, dientich=?, mota=?, giatien=?, anh1Path=?, anh2Path=?, anh3Path=?, anh4Path=?, anh5Path=? WHERE id=?";
        SQLiteDatabase db = this.openOrCreateDatabase("quanlitro.db", MODE_PRIVATE, null);
        SQLiteStatement statement = db.compileStatement(sql);

        statement.bindString(1, tenPhong);
        statement.bindString(2, dienTich);
        statement.bindString(3, moTa);
        statement.bindString(4, giaTien);
        statement.bindString(5, img1Path);
        statement.bindString(6, img2Path);
        statement.bindString(7, img3Path);
        statement.bindString(8, img4Path);
        statement.bindString(9, img5Path);
        statement.bindString(10, roomId);

        long result = statement.executeUpdateDelete();
        db.close();

        Toast.makeText(this, result == 0 ? "Cập nhật thất bại!" : "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
        if (result != 0) finish();
    }

    private String getFileNameFromUri(Uri uri) {
        String result = null;
        try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if (columnIndex != -1) {
                    result = cursor.getString(columnIndex);
                }
            }
        }
        return result == null ? uri.getLastPathSegment() : result;
    }

    private String saveImageToFile(Bitmap bitmap, String fileName) {
        File file = new File(getFilesDir(), fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
