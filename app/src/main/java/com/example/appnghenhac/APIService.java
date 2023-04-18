package com.example.appnghenhac;

import com.example.appnghenhac.models.LoginResponse;
import com.example.appnghenhac.models.Music;
import com.example.appnghenhac.models.PlayList;
import com.example.appnghenhac.models.Singer;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @GET("singer")
    Call<List<Singer>> getSinger();
    @GET("music")
    Call<List<Music>> getMusic();
    @GET("playlist")
    Call<List<PlayList>> getPlaylist();

    @POST("account/login")
    Call<LoginResponse> login(@Body RequestBody json);

}



