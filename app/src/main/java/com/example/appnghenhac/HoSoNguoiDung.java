package com.example.appnghenhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class HoSoNguoiDung extends AppCompatActivity {

    private LinearLayout btnHome;
    private  LinearLayout btnListOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ho_so_nguoi_dung);

        btnHome = (LinearLayout) findViewById(R.id.btnHome);
        btnListOption = (LinearLayout) findViewById(R.id.btnListOption);

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

    }
}