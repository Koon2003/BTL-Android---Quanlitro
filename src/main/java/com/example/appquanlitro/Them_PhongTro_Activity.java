package com.example.appquanlitro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Them_PhongTro_Activity extends AppCompatActivity {
    private EditText editTenPhong, editDienTich, editMoTa, editGiaTien;
    private Button btnSave;
    private ImageView img1, img2, img3, img4, img5;
    private List<Bitmap> selectedImages = new ArrayList<>();
    private List<String> imagePaths = new ArrayList<>(); // Danh sách lưu đường dẫn ảnh
    private static final int PICK_IMAGE_REQUEST = 1;
    private int currentImageIndex = -1; // Để theo dõi hình ảnh hiện tại đang được chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_phongtro);

        editTenPhong = findViewById(R.id.editTenPhong);
        editDienTich = findViewById(R.id.editDienTich);
        editMoTa = findViewById(R.id.editMoTa);
        editGiaTien = findViewById(R.id.editGiaTien);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        btnSave = findViewById(R.id.btnSave);

        // Thiết lập sự kiện click cho từng ImageView
        img1.setOnClickListener(v -> openImageChooser(0));
        img2.setOnClickListener(v -> openImageChooser(1));
        img3.setOnClickListener(v -> openImageChooser(2));
        img4.setOnClickListener(v -> openImageChooser(3));
        img5.setOnClickListener(v -> openImageChooser(4));

        // Thiết lập sự kiện long click để xóa hình ảnh
        img1.setOnLongClickListener(v -> removeImage(0));
        img2.setOnLongClickListener(v -> removeImage(1));
        img3.setOnLongClickListener(v -> removeImage(2));
        img4.setOnLongClickListener(v -> removeImage(3));
        img5.setOnLongClickListener(v -> removeImage(4));

        btnSave.setOnClickListener(v -> savePhongTro());
    }

    private void openImageChooser(int index) {
        currentImageIndex = index; // Ghi nhớ chỉ số hình ảnh hiện tại
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn hình ảnh"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                if (currentImageIndex >= 0 && currentImageIndex < 5) {
                    selectedImages.add(bitmap);
                    String imagePath = saveImageToFile(bitmap, "image" + currentImageIndex + ".png");
                    imagePaths.add(imagePath); // Lưu đường dẫn ảnh
                    displayImage(bitmap, currentImageIndex); // Hiển thị hình ảnh
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Không thể lấy hình ảnh", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void displayImage(Bitmap bitmap, int index) {
        switch (index) {
            case 0:
                img1.setImageBitmap(bitmap);
                break;
            case 1:
                img2.setImageBitmap(bitmap);
                break;
            case 2:
                img3.setImageBitmap(bitmap);
                break;
            case 3:
                img4.setImageBitmap(bitmap);
                break;
            case 4:
                img5.setImageBitmap(bitmap);
                break;
        }
    }

    private boolean removeImage(int index) {
        if (index >= 0 && index < selectedImages.size()) {
            // Xóa hình ảnh từ danh sách
            selectedImages.remove(index);
            imagePaths.remove(index); // Xóa đường dẫn ảnh
            displayImage(null, index); // Xóa hình ảnh khỏi ImageView
            Toast.makeText(this, "Đã xóa hình ảnh", Toast.LENGTH_SHORT).show();
            return true; // Trả về true nếu xử lý thành công
        }
        return false; // Trả về false nếu không thành công
    }

    private void savePhongTro() {
        String tenPhong = editTenPhong.getText().toString();
        String dienTich = editDienTich.getText().toString();
        String moTa = editMoTa.getText().toString();
        String giaTien = editGiaTien.getText().toString();

        // Kiểm tra thông tin
        if (tenPhong.isEmpty() || dienTich.isEmpty() || moTa.isEmpty() || giaTien.isEmpty() || imagePaths.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin và chọn hình ảnh!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lưu thông tin phòng trọ vào cơ sở dữ liệu
        Database database = new Database(this, "quanlitro.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS phongtro(id INTEGER PRIMARY KEY AUTOINCREMENT, tenphong TEXT, dientich TEXT, mota TEXT, giatien TEXT, anh1Path TEXT, anh2Path TEXT, anh3Path TEXT, anh4Path TEXT, anh5Path TEXT)");
        database.insertDataPhongTro(tenPhong, Integer.parseInt(dienTich), moTa, Double.parseDouble(giaTien), imagePaths);

        Toast.makeText(this, "Thêm phòng trọ thành công!", Toast.LENGTH_SHORT).show();

        // Reset các trường nhập liệu
        editTenPhong.setText("");
        editDienTich.setText("");
        editMoTa.setText("");
        editGiaTien.setText("");
        img1.setImageResource(0);
        img2.setImageResource(0);
        img3.setImageResource(0);
        img4.setImageResource(0);
        img5.setImageResource(0);
        selectedImages.clear(); // Xóa danh sách hình ảnh đã chọn
        imagePaths.clear(); // Xóa danh sách đường dẫn ảnh

        // Quay lại trang quản lý phòng trọ
        Intent intent = new Intent(Them_PhongTro_Activity.this, PhongTro_Admin_Activity.class);
        startActivity(intent);
        finish(); // Kết thúc activity hiện tại
    }

    private String saveImageToFile(Bitmap bitmap, String fileName) {
        File directory = new File(getFilesDir(), "images");
        if (!directory.exists()) {
            directory.mkdirs(); // Tạo thư mục nếu chưa tồn tại
        }
        File file = new File(directory, fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            return file.getAbsolutePath(); // Trả về đường dẫn đến hình ảnh
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi
        }
    }
}