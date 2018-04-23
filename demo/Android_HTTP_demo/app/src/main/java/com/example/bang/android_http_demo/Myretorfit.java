package com.example.bang.android_http_demo;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by lzm on 2018/4/17.
 */

public class Myretorfit {
    // retrofit
    public void useRetrofitGET(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(url)
                .build();
        HtmlService htmlService = retrofit.create(HtmlService.class);
        Call<String> call = htmlService.getHtml();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Log.i("lzm", response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.i("lzm", throwable.getMessage());
            }
        });
    }

    public void useRetrofitPOST(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(url)
                .build();
        HtmlService htmlService = retrofit.create(HtmlService.class);
        Call<String> call = htmlService.doLogin("admin", "admin");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Log.i("lzm", response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.i("lzm", throwable.getMessage());
            }
        });
    }
}
