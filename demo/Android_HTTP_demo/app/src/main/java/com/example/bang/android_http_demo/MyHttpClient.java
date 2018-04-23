package com.example.bang.android_http_demo;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lzm on 2018/4/17.
 */

public class MyHttpClient {
    Utils utils = new Utils();

    public void useHttpClientGet(String url) {
        try {
            HttpGet mHttpGet = new HttpGet(url);
            HttpClient mHttpClient = new DefaultHttpClient();
            HttpResponse mHttpResponse = mHttpClient.execute(mHttpGet);
            HttpEntity mHttpEntity = mHttpResponse.getEntity();
            if (null != mHttpEntity) {
                InputStream mInputStream = mHttpEntity.getContent();
                String respose = utils.converStreamToString(mInputStream);
                Log.i("lzm", respose);
                mInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void useHttpClientGetByProxy(String url) {
        try {
            HttpHost proxy = new HttpHost("172.16.104.11", 8888, "HTTP");
            HttpParams httpParams = new BasicHttpParams();
            httpParams.setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
            HttpGet mHttpGet = new HttpGet(url);
            HttpClient mHttpClient = new DefaultHttpClient(httpParams);
            HttpResponse mHttpResponse = mHttpClient.execute(mHttpGet);
            HttpEntity mHttpEntity = mHttpResponse.getEntity();
            if (null != mHttpEntity) {
                InputStream mInputStream = mHttpEntity.getContent();
                String respose = utils.converStreamToString(mInputStream);
                Log.i("lzm", respose);
                mInputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
