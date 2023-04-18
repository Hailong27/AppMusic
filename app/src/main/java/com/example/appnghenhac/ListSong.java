package com.example.appnghenhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ListSong extends AppCompatActivity {
    private ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_song);
        btnBack = (ImageView) findViewById(R.id.list_song_back);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListSong.this,ListActivity.class);
                startActivity(i);
            }
        });
    }
}