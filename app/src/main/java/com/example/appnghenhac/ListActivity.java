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

import com.example.appnghenhac.HelperAPI.AuthInterceptor;
import com.example.appnghenhac.models.Music;
import com.example.appnghenhac.models.PlayList;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListActivity extends AppCompatActivity {
    ImageView button_back;
    private Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.56.1:8082/api/";
    ListView list_playlist;
    PlayListAdapter playListAdapter;
    List<PlayList> playlists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist);
        button_back = (ImageView) findViewById(R.id.button_back);

//        Fake data
//        playlists = new ArrayList<>();
//        PlayList playList = new PlayList(5,"Danh sach bai hat","@drawable/hat");
//        playlists.add(playList);
//
//        playList = new PlayList(1,"Danh sach bai hat","@drawable/hat");
//        playlists.add(playList);
//
//        playList = new PlayList(2,"Danh sach bai hat","@drawable/hat");
//        playlists.add(playList);
//
//        playList = new PlayList(3,"Danh sach bai hat","@drawable/hat");
//        playlists.add(playList);
//
//        playList = new PlayList(4,"Danh sach bai hat","@drawable/hat");
//        playlists.add(playList);
//
//        playList = new PlayList(7,"Danh sach bai hat","@drawable/hat");
//        playlists.add(playList);
//
//        playList = new PlayList(8,"Danh sach bai hat","@drawable/hat");
//        playlists.add(playList);


//        End fake data
//
//        playListAdapter = new PlayListAdapter(playlists);
//        list_playlist = (ListView) findViewById(R.id.list_playlist);
//        list_playlist.setAdapter(playListAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new AuthInterceptor(token));
        System.out.println(token);
        list_playlist = (ListView) findViewById(R.id.list_playlist);
        //        START API
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<List<PlayList>> call = apiService.getPlaylist();
        call.enqueue(new Callback<List<PlayList>>() {


            @Override
            public void onResponse(Call<List<PlayList>> call, Response<List<PlayList>> response) {
                if(response.isSuccessful()){
                    playlists = response.body();
//                    System.out.println(playlists.get(0).name);
                    playListAdapter = new PlayListAdapter(playlists);
                    list_playlist.setAdapter(playListAdapter);
                }
                else {
                    System.out.println("loi");
                }
            }

            @Override
            public void onFailure(Call<List<PlayList>> call, Throwable t) {

            }
        });
        //        END API
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ListActivity.this,MenuSecond.class);
                startActivity(i);
            }
        });
        list_playlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PlayList playList_temp = (PlayList) playListAdapter.getItem(i);
                Intent intent = new Intent(ListActivity.this,ListSong.class);
                //based on item add info to intent
                intent.putExtra("idSong", playList_temp.id);
                startActivity(intent);


            }
        });
    }
}