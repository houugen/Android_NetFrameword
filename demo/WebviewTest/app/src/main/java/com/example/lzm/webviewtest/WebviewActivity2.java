package com.example.lzm.webviewtest;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lzm on 2018/4/18.
 */

public class WebviewActivity2 extends Activity {
    private WebView webView;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = (WebView) findViewById(R.id.webView1);
        String url = getIntent().getData().toString();
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, final SslErrorHandler handler, SslError error) {
                checkCAweb(handler, view.getUrl());
            }
        });
    }

    private void checkCAweb(final SslErrorHandler handler, String url) {
        OkHttpClient.Builder builder = setCer(new OkHttpClient.Builder());
        Request request = new Request.Builder().url(url).build();
        builder.build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("lzmtest", e.getMessage());
                handler.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("lzmtest", response.body().string());
                handler.proceed();
            }
        });
    }

    private OkHttpClient.Builder setCer(OkHttpClient.Builder client) {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] x509CertificateArr, String s) throws CertificateException {
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
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            client.sslSocketFactory(sslContext.getSocketFactory());
            client.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }
}
