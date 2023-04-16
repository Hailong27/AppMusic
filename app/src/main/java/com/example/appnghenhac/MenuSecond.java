package com.example.appnghenhac;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;

import com.example.appnghenhac.models.Singer;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;


public class MenuSecond extends AppCompatActivity {
    public LinearLayout list,list_favorite,list_download;
    private Retrofit retrofit;
    private static final String BASE_URL = "https://64381476894c9029e8ce1a43.mockapi.io/";
    List<Singer> singers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_option);

//        START API
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIService apiService = retrofit.create(APIService.class);
        Call<List<Singer>> call = apiService.getSinger();
        call.enqueue(new Callback<List<Singer>>() {

            @Override
            public void onResponse(Call<List<Singer>> call, Response<List<Singer>> response) {
                if (response.isSuccessful()) {
                    singers = response.body();
                    System.out.println("--------------------------------");
                    System.out.println(singers.get(0).name);
                    // Xử lý dữ liệu trả về ở đây
                } else {
                    // Xử lý lỗi ở đây
                }
            }

            @Override
            public void onFailure(Call<List<Singer>> call, Throwable t) {
                System.out.println("-------------------------------- loi"+t.getMessage());
                t.printStackTrace();
            }
        });
//        END API
        list = (LinearLayout) findViewById(R.id.list);
        list_favorite = (LinearLayout) findViewById(R.id.list_favorite);
        list_download = (LinearLayout) findViewById(R.id.list_download);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuSecond.this,ListActivity.class);
                startActivity(i);
            }
        });
        list_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuSecond.this,ListActivity.class);
                startActivity(i);
            }
        });
        list_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuSecond.this,ListActivity.class);
                startActivity(i);
            }
        });


    }
}