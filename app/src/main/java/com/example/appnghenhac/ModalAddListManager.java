package com.example.appnghenhac;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.appnghenhac.HelperAPI.AuthInterceptor;
import com.example.appnghenhac.models.PlayList;

import java.io.File;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ModalAddListManager {
    private Dialog modal;
    private Activity activity;

    public ModalAddListManager(Activity activity) {
        this.activity = activity;
    }

    private Retrofit retrofit;
    private  String token;
    private static final String BASE_URL = "http://192.168.127.1:8082/api/";
    private  boolean checkCloseModal = false;
    public void closeModal(){
        if(checkCloseModal == true){
            modal.dismiss();
        }
    }
    public void showModal(Context context) {
        modal = new Dialog(context);
        modal.setContentView(R.layout.modal_add_list);
        modal.setCancelable(false);
        modal.show();

        EditText title = modal.findViewById(R.id.modal_title);
        Button okButton = modal.findViewById(R.id.modal_ok_button);
        Button closeButton = modal.findViewById(R.id.modal_close_button);
        SharedPreferences sharedPreferences = activity.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        System.out.println(token);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modal.dismiss();
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                modal.dismiss();

                OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
                clientBuilder.addInterceptor(new AuthInterceptor(token));
//                System.out.println(token);
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(clientBuilder.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                File fileImg = new File("./drawable/hat.jpg");
                APIService apiService = retrofit.create(APIService.class);
                Call<String> call = apiService.addPlaylist(title.getText().toString(),fileImg);
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
//                closeModal();
                activity.recreate();
                modal.dismiss();




            }
        });


    }

    public void hideModal() {
        modal.dismiss();
    }
}
