package com.example.appnghenhac;

import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appnghenhac.HelperAPI.AuthInterceptor;
import com.example.appnghenhac.models.PlayList;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class PlayListAdapter extends BaseAdapter {
    final List<PlayList> playLists;
    private static final String BASE_URL = "http://192.168.56.1:8082/api/";
    private Retrofit retrofit;
    private String token;

    public PlayListAdapter(List<PlayList> playLists,String token) {

        this.token = token;
        this.playLists = playLists;
    }
    private ImageView btn_delete;

    @Override
    public int getCount() {
        return playLists.size();
    }

    @Override
    public Object getItem(int i) {
        return playLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return playLists.get(i).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewPlaylist;
        if (convertView == null) {
            viewPlaylist = View.inflate(parent.getContext(), R.layout.music_view, null);
        } else viewPlaylist = convertView;

        //Bind sữ liệu phần tử vào View
        PlayList playList = (PlayList) getItem(position);
        ((TextView) viewPlaylist.findViewById(R.id.name_playlist)).setText(playList.name);
        btn_delete = (ImageView) viewPlaylist.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                System.out.println(position);
                OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
                clientBuilder.addInterceptor(new AuthInterceptor(token));
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(clientBuilder.build())
//                                .addConverterFactory(GsonConverterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .build();
                APIService apiService = retrofit.create(APIService.class);
                Call<String> call = apiService.deletePlaylist(playList.id);
                call.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        System.out.println(response.raw());
                        if(response.isSuccessful())
                        {
                            System.out.println("Thanh cong");
                            playLists.remove(position);
                            notifyDataSetChanged();
                        }
                        else {
                            System.out.println("That bai");
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
            }
        });

        return viewPlaylist;
    }

}
