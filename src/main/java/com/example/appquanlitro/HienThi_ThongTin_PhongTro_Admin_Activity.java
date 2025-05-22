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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HienThi_ThongTin_PhongTro_Admin_Activity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText editTenPhong, editDienTich, editMoTa, editGiaTien;
    private ImageView img1, img2, img3, img4, img5;
    private TextView id;
    private int currentImageIndex;
    private List<String> imagePaths = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hienthi_thongtin_phongtro_admin);

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
