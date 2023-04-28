package com.example.appnghenhac;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appnghenhac.HelperAPI.AuthInterceptor;
import com.example.appnghenhac.Validate.ValidateForm;
import com.example.appnghenhac.models.LoginResponse;
import com.example.appnghenhac.models.User;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChinhSuaHoSo extends AppCompatActivity {

    private Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.56.1:8082/api/";
    private ImageView btnBack;
    private EditText name;
    private EditText email;
    private EditText phoneNumber;
    private LinearLayout btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_ho_so);
        btnBack = (ImageView) findViewById(R.id.mdf_info_back);
        btnUpdate = (LinearLayout) findViewById(R.id.cshs_update);

        name = (EditText) findViewById(R.id.cshs_name);
        email = (EditText) findViewById(R.id.cshs_email);
        phoneNumber = (EditText) findViewById(R.id.cshs_phone_number);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        User userLogin = (User) bundle.getSerializable("userLogin");
        if(userLogin.getName() != null){
            name.setText(userLogin.getName());
        }
        else{
            name.setText("");
        }

        if(userLogin.getEmail() != null){
            email.setText(userLogin.getEmail());
        }
        else{
            email.setText("");
        }

        if(userLogin.getPhoneNumber() != null){
            phoneNumber.setText(userLogin.getPhoneNumber());
        }
        else{
            phoneNumber.setText("");
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChinhSuaHoSo.this,HoSoNguoiDung.class);
                startActivity(i);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().equals("") || email.getText().toString().equals("") || phoneNumber.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Bạn chưa nhập đủ thông tin người dùng.",Toast.LENGTH_SHORT).show();
                }
                else {
                    if(ValidateForm.isValidEmail(email.getText().toString()) && ValidateForm.validatePhoneNumber(phoneNumber.getText().toString())){
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
                        String str_name = name.getText().toString();
                        String str_email = email.getText().toString();
                        String str_phone = phoneNumber.getText().toString();
                        String str_dob = "08/01/2001"; // Default dob
                        APIService apiService = retrofit.create(APIService.class);
                        Call<String> call = apiService.updateProfile(str_name,str_email,str_phone,str_dob);
                        call.enqueue(new Callback<String>() {

                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.isSuccessful()){
                                    System.out.println("Thanh cong");

                                }
                                else {
                                    System.out.println("Co loi");
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Intent i = new Intent(ChinhSuaHoSo.this,HoSoNguoiDung.class);
                                startActivity(i);
                                System.out.println("loi");
                                System.out.println(t.getMessage());
                            }
                        });
//
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Email hoặc số điện thoại chưa đúng định dạng",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}