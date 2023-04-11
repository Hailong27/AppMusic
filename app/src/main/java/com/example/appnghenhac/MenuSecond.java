package com.example.appnghenhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MenuSecond extends AppCompatActivity {
    public LinearLayout list,list_favorite,list_download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_option);
        list = (LinearLayout) findViewById(R.id.list);
        list_favorite = (LinearLayout) findViewById(R.id.list_favorite);
        list_download = (LinearLayout) findViewById(R.id.list_download);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuSecond.this,List.class);
                startActivity(i);
            }
        });
        list_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuSecond.this,List.class);
                startActivity(i);
            }
        });
        list_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuSecond.this,List.class);
                startActivity(i);
            }
        });


    }
}