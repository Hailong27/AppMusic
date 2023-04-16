package com.example.appnghenhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class StartScreen extends AppCompatActivity {

    ImageView imgTaiNghe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        imgTaiNghe = (ImageView)findViewById(R.id.imgTaiNghe);

        Animation zoomOutAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_out);
        imgTaiNghe.startAnimation(zoomOutAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartScreen.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}