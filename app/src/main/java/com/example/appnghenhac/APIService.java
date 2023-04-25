package com.example.appnghenhac;

import com.example.appnghenhac.models.LoginResponse;
import com.example.appnghenhac.models.Music;
import com.example.appnghenhac.models.PlayList;
import com.example.appnghenhac.models.RegisterRequest;
import com.example.appnghenhac.models.RegisterResponse;
import com.example.appnghenhac.models.Singer;

import java.io.File;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    @GET("singer")
    Call<List<Singer>> getSinger();
    @GET("music")
    Call<List<Music>> getMusic();
    @GET("music/{id}")
    Call<Music> getMusicById(@Path("id") int idMusic);
    @GET("playlist")
    Call<List<PlayList>> getPlaylist();
    @GET("playlist/{id}/musics")
    Call<List<Music>> getMusicByIdPlaylist(@Path("id") int idSong);

    @POST("account/login")
    Call<LoginResponse> login(@Body RequestBody json);

    @POST("account/register")
    Call<RegisterResponse> register(@Body RequestBody json);
    @FormUrlEncoded
    @POST("playlist")
    Call<String> addPlaylist(@Field("name") String name, @Field("file") File file);

    @POST("add")
    Call<String> addMusicToList(@Field("idMusic") int idMusic, @Field("idPlaylist") int idPlaylist);
}



