package com.example.appnghenhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.appnghenhac.models.Music;
import com.example.appnghenhac.models.PlayList;
import com.example.appnghenhac.models.Singer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Frame4 extends AppCompatActivity {
    private Retrofit retrofit;
    private ListView listMusic;
    private MusicF4Adapter musicF4Adapter;
    private static final String BASE_URL = "http://192.168.127.1:8082/api/";
    private LinearLayout btnHome;
    private  LinearLayout btnListOption;
    private  LinearLayout btnUserInfo;
    List<Music> musics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame4);
        listMusic = (ListView) findViewById(R.id.list_music_f4);

        //        START API
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<List<Music>> call = apiService.getMusic();
        call.enqueue(new Callback<List<Music>>() {


            @Override
            public void onResponse(Call<List<Music>> call, Response<List<Music>> response) {
                if (response.isSuccessful()) {
                    musics = response.body();
                    System.out.println("--------------------------------");
                    System.out.println(musics.get(0).getNameMusic());
                    musicF4Adapter = new MusicF4Adapter(musics);

                    listMusic.setAdapter(musicF4Adapter);
                    // Xử lý dữ liệu trả về ở đây
                } else {
                    // Xử lý lỗi ở đây
                }
            }

            @Override
            public void onFailure(Call<List<Music>> call, Throwable t) {

            }
        });
        listMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Music music_temp = (Music) musicF4Adapter.getItem(i);
                Intent intent = new Intent(Frame4.this,MainActivity.class);
                //based on item add info to intent
                intent.putExtra("idSong", music_temp.id);
                startActivity(intent);


            }
        });
        //        END API
//        Button footer
        btnListOption = (LinearLayout) findViewById(R.id.btn_list_option);
        btnHome = (LinearLayout) findViewById(R.id.btn_home_list);
        btnUserInfo = (LinearLayout) findViewById(R.id.btn_user_info);

        btnListOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Frame4.this,MenuSecond.class);
                startActivity(i);
            }
        });
        btnUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Frame4.this,HoSoNguoiDung.class);
                startActivity(i);
            }
        });

    }
}