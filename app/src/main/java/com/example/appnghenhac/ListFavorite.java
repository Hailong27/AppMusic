package com.example.appnghenhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ListFavorite extends AppCompatActivity {
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_favorite);
        btnBack = (ImageView) findViewById(R.id.list_favorite_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListFavorite.this,MenuSecond.class);
                startActivity(i);
            }
        });
    }
}