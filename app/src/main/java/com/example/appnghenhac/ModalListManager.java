package com.example.appnghenhac;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.appnghenhac.HelperAPI.AuthInterceptor;
import com.example.appnghenhac.models.Music;
import com.example.appnghenhac.models.PlayList;

import java.io.File;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ModalListManager {
    private Dialog modal;
    private Retrofit retrofit;
    private List<PlayList> playLists;
    private ListModalAdapter listModalAdapter;
    private static final String BASE_URL = "http://192.168.127.1:8082/api/";
    private  boolean checkCloseModal = false;
    public void showModal(Context context, int idMusic) {
        modal = new Dialog(context);
        modal.setContentView(R.layout.modal_list);
        modal.setCancelable(false);

        Button closeButton = modal.findViewById(R.id.btn_close);
        ListView modalplaylist = modal.findViewById(R.id.modalplaylist);

        SharedPreferences sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new AuthInterceptor(token));
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
                    playLists = response.body();
                    listModalAdapter = new ListModalAdapter(playLists);
                    modalplaylist.setAdapter(listModalAdapter);
                }
                else {
                    System.out.println("loi");
                }
            }

            @Override
            public void onFailure(Call<List<PlayList>> call, Throwable t) {

            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkCloseModal == true){
                    modal.dismiss();
                }
            }
        });

        modal.show();

        modalplaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PlayList playList_temp = (PlayList) listModalAdapter.getItem(i);
                OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
                clientBuilder.addInterceptor(new AuthInterceptor(token));
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(clientBuilder.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                APIService apiService = retrofit.create(APIService.class);
                Call<String> call = apiService.addMusicToList(idMusic, playList_temp.id);
                call.enqueue(new Callback<String>(){
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if(response.isSuccessful()){
                            String res = response.body();
                            System.out.println(res);
                            checkCloseModal = true;
                        }
                        else {
                            System.out.println("Loi");
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
            }
        });
    }

    public void hideModal() {
        modal.dismiss();
    }


}
