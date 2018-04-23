package com.example.bang.android_http_demo;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by lzm on 2018/4/17.
 */

public interface HtmlService {
    @GET("/")
    Call<String> getHtml();

    @FormUrlEncoded
    @POST("/login")
    Call<String> doLogin(@Field("username")String name, @Field("password") String password);
}
