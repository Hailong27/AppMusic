package com.example.appnghenhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.appnghenhac.HelperAPI.AuthInterceptor;
import com.example.appnghenhac.models.PlayList;
import com.example.appnghenhac.models.User;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HoSoNguoiDung extends AppCompatActivity {
    private Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.56.1:8082/api/";
    private TextView profileName;

    private LinearLayout btnHome;
    private  LinearLayout btnListOption;
    private LinearLayout btnModifyInfo,btnModifyPassword,btnLogout;
    private User userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ho_so_nguoi_dung);

        btnHome = (LinearLayout) findViewById(R.id.btnHome);
        btnListOption = (LinearLayout) findViewById(R.id.btnListOption);
        btnModifyInfo = (LinearLayout) findViewById(R.id.modify_info);
        btnModifyPassword = (LinearLayout) findViewById(R.id.modify_pw);
        btnLogout = (LinearLayout) findViewById(R.id.logout);
        profileName = (TextView) findViewById(R.id.profile_name);
//        API
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new AuthInterceptor(token));

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        Call<User> call = apiService.getUserLogin();
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    userLogin = response.body();
                    if(userLogin.getName() != null){
                        profileName.setText(userLogin.getName());
                    }
                    else {
                        profileName.setText("Người dùng mặc định");
                    }
                }
                else {
                    System.out.println("Loi");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HoSoNguoiDung.this,Frame4.class);
                startActivity(i);
            }
        });
        btnListOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HoSoNguoiDung.this,MenuSecond.class);
                startActivity(i);
            }
        });
        btnModifyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HoSoNguoiDung.this,ChinhSuaHoSo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("userLogin", userLogin); // Đưa đối tượng vào Bundle
                i.putExtras(bundle);
                startActivity(i);
            }
        });
        btnModifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HoSoNguoiDung.this,DoiMatKhau.class);
                startActivity(i);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent i = new Intent(HoSoNguoiDung.this,Login.class);
                startActivity(i);
                System.out.println("Đăng xuất");
            }
        });

    }
}