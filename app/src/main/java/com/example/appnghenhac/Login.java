package com.example.appnghenhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnghenhac.models.LoginRequest;
import com.example.appnghenhac.models.LoginResponse;
import com.example.appnghenhac.models.Singer;
import com.google.gson.Gson;


import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    TextView dang_ky_txt;
    Button dang_nhap;
    EditText email;
    EditText password;
    private Retrofit retrofit;
    LoginResponse loginResponse;
    private static final String BASE_URL = "http:192.168.56.1:8082/api/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dang_ky_txt = (TextView) findViewById(R.id.dang_ky_txt);
        email = (EditText) findViewById(R.id.email_edit_text);
        password = (EditText) findViewById(R.id.password_edit_text);
        dang_nhap = (Button) findViewById(R.id.dang_nhap_btn);
        dang_nhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                OkHttpClient.Builder builder = new OkHttpClient.Builder();
//                builder.connectTimeout(60, TimeUnit.SECONDS);
//                builder.readTimeout(60, TimeUnit.SECONDS);
//
//                // Add interceptor for HTTPS communication
//                if (BuildConfig.DEBUG) {
//                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//                    builder.addInterceptor(loggingInterceptor);
//                }
                if(email.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    System.out.println("Vao day roi");
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                }
                else {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
//                        .client(builder.build())
                            .build();
                    APIService apiService = retrofit.create(APIService.class);
                    LoginRequest loginRequest = new LoginRequest(email.getText().toString(), password.getText().toString());
                    Gson gson = new Gson();
                    String json = gson.toJson(loginRequest);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
                    Call<LoginResponse> call = apiService.login(body);
                    call.enqueue(new Callback<LoginResponse>() {

                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()) {

                                loginResponse = response.body();
                                System.out.println("------------------------------------");
                                String token = loginResponse.getToken();
                                System.out.println(token);
                                SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("token", token);
                                editor.apply();
                                Intent i = new Intent(Login.this,Frame4.class);
                                startActivity(i);


                                // Xử lý dữ liệu trả về ở đây
                            } else {
                                // Xử lý lỗi ở đây
                                Toast.makeText(getApplicationContext(), "Tài khoản không chính xác.", Toast.LENGTH_SHORT).show();
                                System.out.println("day");
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            System.out.println("loi "+t.getMessage());
                        }
                    });
                    System.out.println(email.getText());

                }

            }
        });
        dang_ky_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });
    }

}