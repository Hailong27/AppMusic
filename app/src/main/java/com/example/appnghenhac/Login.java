package com.example.appnghenhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appnghenhac.models.LoginRequest;
import com.example.appnghenhac.models.LoginResponse;
import com.example.appnghenhac.models.Singer;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
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
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
//                        .client(builder.build())
                        .build();
                APIService apiService = retrofit.create(APIService.class);
                LoginRequest loginRequest = new LoginRequest("example3@example.com", "P@ssword123");
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

                            // Xử lý dữ liệu trả về ở đây
                        } else {
                            // Xử lý lỗi ở đây
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