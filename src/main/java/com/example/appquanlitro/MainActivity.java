package com.example.appquanlitro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tạo một Handler để chuyển trang sau 3 giây
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Chuyển đến trang LoginActivity
                Intent intent = new Intent(MainActivity.this, Login_Activity.class);
                startActivity(intent);
                finish(); // Kết thúc MainActivity để không quay lại trang này
            }
        }, 3000); // 3000 milliseconds = 3 seconds
    }
}