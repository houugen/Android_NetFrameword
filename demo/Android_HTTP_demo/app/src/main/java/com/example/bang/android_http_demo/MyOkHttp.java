package com.example.bang.android_http_demo;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

/**
 * Created by lzm on 2018/4/18.
 */

public class MyOkHttp {
    public void useOkHttp(String url) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();

        // method 1
        try {
            okhttp3.Response response = mOkHttpClient.newCall(request).execute();
            String body = response.body().string();
            Log.i("lzm","body: " + body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void useAsyncOkHttp(String url) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();

        // method 2: async
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (null != response.cacheResponse()) {
                    String str = response.cacheResponse().toString();
                    Log.i("lzm", "cache---" + str);
                } else {
                    String body = response.body().string();
                    String str = response.networkResponse().toString();
                    Log.i("lzm", "network---" + str);
                }
            }
        });
    }

    public void useAsyncOkHttpByProxy(String url) {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder().proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("112.95.91.101", 8888))).build();
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();

        // method 2: async
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (null != response.cacheResponse()) {
                    String str = response.cacheResponse().toString();
                    Log.i("lzm", "cache---" + str);
                } else {
                    String body = response.body().string();
                    String str = response.networkResponse().toString();
                    Log.i("lzm", "network---" + str);
                }
            }
        });
    }

    public void useUnsafeOkHttp(String url) {
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509CertificateArr, String s) throws CertificateException {
                        // do check
                        if (x509CertificateArr == null) {
                            throw new IllegalArgumentException("check server x509Certificates is null");
                        }
                        if (x509CertificateArr.length < 0) {
                            throw new IllegalArgumentException("check server x509Certificates is empty");
                        }
                        for (X509Certificate cert : x509CertificateArr) {
                            try {
                                cert.checkValidity();
                                String cername = "server1.crt";
                                InputStream is = new BufferedInputStream(getAssets().open(cername));
                                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                                X509Certificate serverCert = (X509Certificate)certificateFactory.generateCertificate(is);
                                cert.verify(serverCert.getPublicKey());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true; //wrong
                // do verify
            }
        };

        // ssl request
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());

            OkHttpClient mOkHttpClient = new OkHttpClient.Builder().hostnameVerifier(hostnameVerifier)
                    .sslSocketFactory(sslContext.getSocketFactory()).build();
            okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();
            mOkHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    String body = response.body().string();
                    Log.i("lzm", "network---" + body);
                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
