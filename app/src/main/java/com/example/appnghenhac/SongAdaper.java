package com.example.appnghenhac;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appnghenhac.HelperAPI.AuthInterceptor;
import com.example.appnghenhac.models.Music;
import com.example.appnghenhac.models.PlayList;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SongAdaper extends BaseAdapter {
    List<Music> musicList;
    private static final String BASE_URL = "http://192.168.56.1:8082/api/";
    private Retrofit retrofit;
    public ImageView btn_delete;
    private int idSong;
    private String token;
    private  Context context;

    public SongAdaper(List<Music> musicList,int idSong,String token) {

        this.musicList = musicList;
        this.idSong = idSong;
        this.token = token;
//        this.context = context;
    }

    @Override
    public int getCount() {
        return musicList.size();
    }

    @Override
    public Object getItem(int i) {
        return musicList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return musicList.get(i).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewSong;
        if (convertView == null) {
            viewSong = View.inflate(parent.getContext(), R.layout.song_view, null);
        } else viewSong = convertView;

        //Bind sữ liệu phần tử vào View
        Music music = (Music) getItem(position);
        ((TextView) viewSong.findViewById(R.id.stt_song)).setText(String.valueOf(position+1));
        ((TextView) viewSong.findViewById(R.id.name_song)).setText(music.nameMusic);
        ((TextView) viewSong.findViewById(R.id.singer_song)).setText(music.singer.nameSinger);

        btn_delete = (ImageView) viewSong.findViewById(R.id.sv_btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Click remove");
                OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
                clientBuilder.addInterceptor(new AuthInterceptor(token));
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(clientBuilder.build())
//                                .addConverterFactory(GsonConverterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .build();
                APIService apiService = retrofit.create(APIService.class);
                Call<String> call = apiService.deleteMusicFromPlaylist(idSong,music.getId());
                call.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        System.out.println(response.message());
                        System.out.println(response.raw());
                        if(response.isSuccessful()){
                            System.out.println("thanh cong");
                            musicList.remove(position);
                            notifyDataSetChanged();

                        }
                        else {
                            System.out.println("that bai");
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
            }
        });
        return viewSong;
    }
}
