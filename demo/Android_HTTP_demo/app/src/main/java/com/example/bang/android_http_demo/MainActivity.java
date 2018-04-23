package com.example.bang.android_http_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;

import android.text.TextUtils;
import android.util.Log;
import android.view.animation.ScaleAnimation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        useHttpUrlConnectionGetThread();
    }

    private void useHttpUrlConnectionGetThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // HttpClient
                MyHttpClient myHttpClient = new MyHttpClient();
//                myHttpClient.useHttpClientGet("http://www.freebuf.com");
//                myHttpClient.useHttpClientGetByProxy("http://www.freebuf.com");
//                myHttpClient.useHttpClientGet("https://www.github.com");
//                myHttpClient.useHttpClientGet("https://192.168.31.76:1234");

                // HttpURLConnection
                MyHttpURLConnect myHttpURLConnect = new MyHttpURLConnect();
//                myHttpURLConnect.useHttpURLConnection("http://www.freebuf.com");
//                myHttpURLConnect.useHttpURLConnectionByProxy("http://www.freebuf.com");
//                myHttpURLConnect.useHttpURLConnection("https://www.github.com");
//                myHttpURLConnect.useHttpURLConnection("https://192.168.31.76:1234");
//                myHttpURLConnect.useHttpsURLConnection("https://192.168.31.76:1234");

                // volley
                MyVolley myVolley = new MyVolley();
//                myVolley.useVolley("http://www.freebuf.com", MainActivity.this);
//                myVolley.useVolley("https://www.github.com", MainActivity.this);
//                myVolley.useVolley("https://192.168.31.76:1234",MainActivity.this);

                // okHttp
                MyOkHttp myOkHttp = new MyOkHttp();
//                myOkHttp.useOkHttp("http://www.freebuf.com");
//                myOkHttp.useAsyncOkHttp("http://www.freebuf.com");
//                myOkHttp.useAsyncOkHttp("https://www.github.com");
//                myOkHttp.useAsyncOkHttp("https://192.168.31.76:1234");
//                myOkHttp.useAsyncOkHttpByProxy("http://www.freebuf.com");
//                myOkHttp.useUnsafeOkHttp("https://192.168.31.76:1234");

                // retrofit
                Myretorfit myretorfit = new Myretorfit();
//                myretorfit.useRetrofitGET("http://192.168.1.7:1234");
//                myretorfit.useRetrofitPOST("http://192.168.1.7:1234");
            }
        }).start();
    }
}