package com.example.appnghenhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnghenhac.HelperAPI.AuthInterceptor;
import com.example.appnghenhac.Validate.ValidateForm;
import com.example.appnghenhac.models.PlayList;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class DoiMatKhau extends AppCompatActivity {
    private  ImageView btnBack;
    private EditText current_password,new_password,confirm_password;
    private TextView btnUpdate;
    private Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.56.1:8082/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        btnBack = (ImageView) findViewById(R.id.mdf_pw_back);
        current_password = (EditText) findViewById(R.id.dmk_password);
        new_password = (EditText) findViewById(R.id.dmk_new_password);
        confirm_password = (EditText) findViewById(R.id.dmk_confirm_password);
        btnUpdate = (TextView) findViewById(R.id.dmk_update);
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DoiMatKhau.this,HoSoNguoiDung.class);
                startActivity(i);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cpass = current_password.getText().toString();
                String npass = new_password.getText().toString();
                String cfpass = confirm_password.getText().toString();
                if(cpass.equals("") || npass.equals("") || cfpass.equals("")){
                    Toast.makeText(getApplicationContext(),"Bạn chưa nhập đầy đủ thông tin.",Toast.LENGTH_SHORT).show();

                }
                else {
                    if(ValidateForm.validateConfirmPassword(npass,cfpass)){
                        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
                        clientBuilder.addInterceptor(new AuthInterceptor(token));
                        retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .client(clientBuilder.build())
//                                .addConverterFactory(GsonConverterFactory.create())
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .build();
                        APIService apiService = retrofit.create(APIService.class);
                        Call<String> call = apiService.changePassword(cfpass,npass,cpass);
                        System.out.println("Vao day roi");
                        call.enqueue(new Callback<String>() {

                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                System.out.println("Thanh cong");
                                if(response.isSuccessful()){
                                    Intent i = new Intent(DoiMatKhau.this,HoSoNguoiDung.class);
                                    startActivity(i);
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                System.out.println("That bai");
                                Toast.makeText(getApplicationContext(),"Mật khẩu chưa chính xác",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Mật khẩu chưa trùng khớp.",Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }
}