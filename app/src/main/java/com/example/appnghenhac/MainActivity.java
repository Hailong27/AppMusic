package com.example.appnghenhac;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnghenhac.models.Music;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{

    private Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.56.1:8082/api/";
    ImageView btn_back, disk_img, choi_nhac, shuffle, repeat, tym, dot_change, previous, next, btn_add, btn_download;
    TextView timeMusic, timeCurrent, txtNameMusic;
    View line_music;
    private int lastX;
    private Music music;
    private boolean isRepeat = false;
    private Animation animation;
    private  String urlMusic = "http://192.168.56.1:8082/music/";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        txtNameMusic = (TextView)findViewById(R.id.txtNameMusic);
        btn_back = (ImageView)findViewById(R.id.btn_back);
        btn_add = (ImageView)findViewById(R.id.btn_add);
        btn_download = (ImageView)findViewById(R.id.btn_download);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        disk_img.startAnimation(animation);

        Intent i = getIntent();
        int idMusic = i.getIntExtra("idSong",0);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);


        Call<Music> call = apiService.getMusicById(idMusic);
        call.enqueue(new Callback<Music>() {
            @Override
            public void onResponse(Call<Music> call, Response<Music> response) {
                if(response.isSuccessful()){
                    music = response.body();
                    txtNameMusic.setText(music.nameMusic);
                    String path = "http://192.168.56.1:8082/music/"+music.fileMusic;
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(path);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        mediaPlayer.prepare();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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

                    btn_back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mHandler.removeCallbacksAndMessages(null);
                            mediaPlayer.release();
                            Intent i = new Intent(MainActivity.this, Frame4.class);
                            startActivity(i);
                        }
                    });

                    btn_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ModalListManager modalManager = new ModalListManager();
                            modalManager.showModal(view.getContext());
                        }
                    });

                    btn_download.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String fileUrl = "http://192.168.127.1:8082/music/" + music.fileMusic;
                            String fileName = music.nameMusic;
                            new DownloadTask().execute(fileUrl, fileName);
                        }
                    });
                }
                else {
                    System.out.println("Loi");
                }
            }
            @Override
            public void onFailure(Call<Music> call, Throwable t) {

            }
        });
    }
    private class DownloadTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String fileUrl = params[0];
            String fileName = params[1];
            try {
                URL url = new URL(fileUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream inputStream = conn.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(fileName);

                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }

                outputStream.close();
                inputStream.close();
                return "Download completed";
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "Malformed URL exception";
            } catch (IOException e) {
                e.printStackTrace();
                return "IO exception";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
        }
    }

}
