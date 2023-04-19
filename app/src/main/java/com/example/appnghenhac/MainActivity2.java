package com.example.appnghenhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.appnghenhac.models.Music;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity2 extends AppCompatActivity {

    private Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.127.1:8082/api/";

    private Music music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


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
                    String path = "http://192.168.127.1:8082/music/"+music.fileMusic;
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
}