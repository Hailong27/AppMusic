package com.example.appnghenhac;

import androidx.annotation.ColorInt;
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
import com.example.appnghenhac.models.RegisterRequest;
import com.example.appnghenhac.models.RegisterResponse;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    TextView dang_nhap_txt;
    Button btn_dang_ky;
    EditText email_edit_text, password_edit_text, confirm_edit_text;

    private Retrofit retrofit;
    RegisterResponse registerResponse;
    private static final String BASE_URL = "http:192.168.127.1:8082/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dang_nhap_txt = (TextView) findViewById(R.id.dang_nhap_txt);
        btn_dang_ky = (Button)findViewById(R.id.btn_dang_ky);
        email_edit_text = (EditText)findViewById(R.id.email_edit_text);
        password_edit_text =(EditText)findViewById(R.id.password_edit_text);
        confirm_edit_text = (EditText)findViewById(R.id.confirm_edit_text);

        btn_dang_ky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email_edit_text.getText().toString().isEmpty() || password_edit_text.getText().toString().isEmpty() || confirm_edit_text.getText().toString().isEmpty()){
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
                    RegisterRequest registerRequest = new RegisterRequest(email_edit_text.getText().toString(), password_edit_text.getText().toString(), confirm_edit_text.getText().toString());
                    Gson gson = new Gson();
                    String json = gson.toJson(registerRequest);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
                    Call<RegisterResponse> call = apiService.register(body);
                    call.enqueue(new Callback<RegisterResponse>() {


                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                            if (response.isSuccessful()) {

                                registerResponse = response.body();
                                System.out.println("------------------------------------");
                                String token = registerResponse.getToken();
                                System.out.println(token);
                                SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("token", token);
                                editor.apply();
                                Intent i = new Intent(Register.this,Login.class);
                                startActivity(i);

                                // Xử lý dữ liệu trả về ở đây
                            } else {
                                // Xử lý lỗi ở đây
                                Toast.makeText(getApplicationContext(), "Mật khẩu không khớp.", Toast.LENGTH_SHORT).show();
                                System.out.println("day");
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                            System.out.println("loi "+t.getMessage());
                        }
                    });
                }
            }
        });

        dang_nhap_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this, Login.class);
                startActivity(i);
            }
        });
    }
}