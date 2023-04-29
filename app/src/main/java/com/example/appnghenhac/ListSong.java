package com.example.appnghenhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.appnghenhac.HelperAPI.AuthInterceptor;
import com.example.appnghenhac.models.Music;
import com.example.appnghenhac.models.PlayList;

import org.w3c.dom.ls.LSOutput;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListSong extends AppCompatActivity {
    private ImageView btnBack;
    private TextView playlist_name;
    private Retrofit retrofit;
    private List<Music> musics;
    private ListView listSong;
    private SongAdaper songAdaper;
    private static final String BASE_URL = "http://192.168.56.1:8082/api/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_song);
        btnBack = (ImageView) findViewById(R.id.list_song_back);
        listSong = (ListView) findViewById(R.id.list_song);
//        Get id  playlist
        Intent i = getIntent();
        int idSong = i.getIntExtra("idPlaylist",0);
        String namePlaylist = i.getStringExtra("namePlaylist");
        playlist_name = (TextView) findViewById(R.id.playlist_name);
        playlist_name.setText(namePlaylist);


//        API
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new AuthInterceptor(token));
        System.out.println(token);
        //        START API
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<List<Music>> call = apiService.getMusicByIdPlaylist(idSong);
        call.enqueue(new Callback<List<Music>>() {


            @Override
            public void onResponse(Call<List<Music>> call, Response<List<Music>> response) {
                if(response.isSuccessful()){
                    musics = response.body();
//                    System.out.println(musics.get(0).nameMusic);
                    songAdaper = new SongAdaper(musics,idSong,token);
                    listSong.setAdapter(songAdaper);
                }
                else {
                    System.out.println("loi");
                }
            }

            @Override
            public void onFailure(Call<List<Music>> call, Throwable t) {

            }
        });
//  End API
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListSong.this,ListActivity.class);
                startActivity(i);
            }
        });
        listSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Music music_temp = (Music) songAdaper.getItem(i);
                Intent intent = new Intent(ListSong.this,MainActivity.class);
                //based on item add info to intent
                intent.putExtra("idSong", music_temp.id);
                startActivity(intent);


            }
        });
    }
}