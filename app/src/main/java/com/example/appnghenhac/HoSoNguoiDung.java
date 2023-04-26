package com.example.appnghenhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import retrofit2.Retrofit;

public class HoSoNguoiDung extends AppCompatActivity {
    private Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.56.1:8082/api/";

    private LinearLayout btnHome;
    private  LinearLayout btnListOption;
    private LinearLayout btnModifyInfo,btnModifyPassword,btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ho_so_nguoi_dung);

        btnHome = (LinearLayout) findViewById(R.id.btnHome);
        btnListOption = (LinearLayout) findViewById(R.id.btnListOption);
        btnModifyInfo = (LinearLayout) findViewById(R.id.modify_info);
        btnModifyPassword = (LinearLayout) findViewById(R.id.modify_pw);
        btnLogout = (LinearLayout) findViewById(R.id.logout);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HoSoNguoiDung.this,Frame4.class);
                startActivity(i);
            }
        });
        btnListOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HoSoNguoiDung.this,MenuSecond.class);
                startActivity(i);
            }
        });
        btnModifyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HoSoNguoiDung.this,ChinhSuaHoSo.class);
                startActivity(i);
            }
        });
        btnModifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HoSoNguoiDung.this,DoiMatKhau.class);
                startActivity(i);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Đăng xuất");
            }
        });

    }
}