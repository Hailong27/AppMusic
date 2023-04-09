package com.example.appnghenhac;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView disk_img, choi_nhac, shuffle, repeat, tym;
    TextView timeMusic, timeCurrent;
    View line_music;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        disk_img = (ImageView)findViewById(R.id.disk_img);
        choi_nhac = (ImageView)findViewById(R.id.choi_nhac);
        shuffle = (ImageView)findViewById(R.id.shuffle);
        repeat = (ImageView)findViewById(R.id.repeat);
        tym = (ImageView)findViewById(R.id.tym);
        timeMusic = (TextView)findViewById(R.id.timeMusic) ;
        timeCurrent = (TextView)findViewById(R.id.timeCurrent);
        line_music = (View)findViewById(R.id.line_music);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        disk_img.startAnimation(animation);

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.haanhtuan);
        mediaPlayer.start();

        int time = mediaPlayer.getDuration() / 1000;
        int minute = time / 60;
        int second = time % 60;
        String t = String.format("%02d:%02d", minute, second);
        timeMusic.setText(t);

        Handler mHandler = new Handler();
        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                int time = mediaPlayer.getDuration();
                int currentPositionInMillis = mediaPlayer.getCurrentPosition();
                int currentPositionInSec = currentPositionInMillis / 1000;
                int minutes = currentPositionInSec / 60;
                int seconds = currentPositionInSec % 60;
                String currentPositionFormatted = String.format("%02d:%02d", minutes, seconds);
                timeCurrent.setText(currentPositionFormatted);

                ViewGroup.LayoutParams layoutParams = line_music.getLayoutParams();
                layoutParams.width = currentPositionInMillis * 380/time;
                line_music.setLayoutParams(layoutParams);

                mHandler.postDelayed(this, 100);
            }
        };
        mHandler.postDelayed(mRunnable, 100);

        choi_nhac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choi_nhac.getDrawable().getConstantState() ==
                    getResources().getDrawable(R.drawable.baseline_play_circle_outline_24).getConstantState()) {
                    mediaPlayer.start();
                    disk_img.startAnimation(animation);
                    choi_nhac.setImageResource(R.drawable.baseline_pause_circle_outline_24);
                } else {
                    mediaPlayer.pause();
                    animation.cancel();
                    choi_nhac.setImageResource(R.drawable.baseline_play_circle_outline_24);
                }
            }
        });

        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(shuffle.getAlpha() < 1){
                    shuffle.setAlpha(1f);
                } else{
                    shuffle.setAlpha(0.5f);
                }
            }
        });

        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(repeat.getAlpha() < 1){
                    repeat.setAlpha(1f);
                } else{
                    repeat.setAlpha(0.5f);
                }
            }
        });

        tym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tym.getDrawable().getConstantState() ==
                        getResources().getDrawable(R.drawable.baseline_favorite_border_24).getConstantState()) {
                    tym.setImageResource(R.drawable.baseline_favorite_24);
                } else {
                    tym.setImageResource(R.drawable.baseline_favorite_border_24);
                }
            }
        });

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(MainActivity.this, Login.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 3000);

    }

}

