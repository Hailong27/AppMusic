package com.example.appnghenhac;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity{

    ImageView disk_img, choi_nhac, shuffle, repeat, tym, dot_change, previous, next;
    TextView timeMusic, timeCurrent;
    View line_music;
    private int lastX;
    private boolean isRepeat = false;
    private Animation animation;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent i = getIntent();
//        Integer position = Integer.parseInt(i.getStringExtra("index"));
//
//        System.out.println("xxx"+position);

        disk_img = (ImageView)findViewById(R.id.disk_img);
        choi_nhac = (ImageView)findViewById(R.id.choi_nhac);
        shuffle = (ImageView)findViewById(R.id.shuffle);
        dot_change = (ImageView)findViewById(R.id.dot_change);
        repeat = (ImageView)findViewById(R.id.repeat);
        tym = (ImageView)findViewById(R.id.tym);
        previous = (ImageView)findViewById(R.id.previous);
        next = (ImageView)findViewById(R.id.next);
        timeMusic = (TextView)findViewById(R.id.timeMusic) ;
        timeCurrent = (TextView)findViewById(R.id.timeCurrent);
        line_music = (View)findViewById(R.id.line_music);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        disk_img.startAnimation(animation);

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.haanhtuan);
        mediaPlayer.start();
        mediaPlayer.setLooping(isRepeat);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            if (isRepeat) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            } else {
                String nextSongPath = "android.resource://" + getPackageName() + "/raw/haanhtuan";
                try {
                    mediaPlayer.reset();
                    mediaPlayer.setDataSource(MainActivity.this, Uri.parse(nextSongPath));
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    int time = mediaPlayer.getDuration() / 1000;
    int minute = time / 60;
    int second = time % 60;
    String t = String.format("%02d:%02d", minute, second);
    timeMusic.setText(t);

    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int time = mediaPlayer.getDuration()/1000;
            int currentPositionInMillis = mediaPlayer.getCurrentPosition();
            int currentPositionInSec = currentPositionInMillis / 1000;
            if(currentPositionInSec > time) currentPositionInSec = time;
            int minutes = currentPositionInSec / 60;
            int seconds = currentPositionInSec % 60;
            String currentPositionFormatted = String.format("%02d:%02d", minutes, seconds);
            timeCurrent.setText(currentPositionFormatted);

            ViewGroup.LayoutParams layoutParams = line_music.getLayoutParams();
            layoutParams.width = currentPositionInMillis * 380/time;
            layoutParams.width = currentPositionInSec * 955/time;
            line_music.setLayoutParams(layoutParams);
            dot_change.layout(currentPositionInSec * 955/time+50, dot_change.getTop(), currentPositionInSec * 955/time + 50 + dot_change.getWidth(), dot_change.getBottom());
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

    previous.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String nextSongPath = "android.resource://" + getPackageName() + "/raw/conhautrondoi";
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(MainActivity.this, Uri.parse(nextSongPath));
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    next.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String nextSongPath = "android.resource://" + getPackageName() + "/raw/dencungtan";
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(MainActivity.this, Uri.parse(nextSongPath));
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
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
            isRepeat = !isRepeat;
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
        dot_change.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Lưu lại vị trí x khi người dùng chạm vào màn hình
                        lastX = (int) event.getRawX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        // Tính khoảng cách di chuyển theo phương ngang
                        int deltaX = (int) event.getRawX() - lastX;

                        // Cập nhật vị trí hiển thị mới cho ImageView
                        int newX = dot_change.getLeft() + deltaX;
                        if(newX > 1000) newX = 1000;
                        if(newX < 50) newX = 50;
                        dot_change.layout(newX, dot_change.getTop(), newX + dot_change.getWidth(), dot_change.getBottom());
                        // Lưu lại vị trí mới cho lần chạm tiếp theo
                        lastX = (int) event.getRawX();
                        mediaPlayer.seekTo((newX-50)*time*1000/950);
                        break;
                }
                return true;
            }
        });
    }

}
