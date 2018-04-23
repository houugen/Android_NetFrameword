package com.example.bang.android_http_demo;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by lzm on 2018/4/17.
 */

public class MyHttpURLConnect {
    Utils utils = new Utils();

    public void useHttpURLConnection(String url) {
        try {
            InputStream mInputStream = null;
            URL mUrl = new URL(url);
            HttpURLConnection mHttpURLConnection = (HttpURLConnection)mUrl.openConnection();
            mInputStream = mHttpURLConnection.getInputStream();
            String respose = utils.converStreamToString(mInputStream);
            Log.i("lzm", respose);
            mInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void useHttpURLConnectionByProxy(String url) {
        try {
            InputStream mInputStream = null;
            URL mUrl = new URL(url);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("172.16.104.11", 8888));
            HttpURLConnection mHttpURLConnection = (HttpURLConnection)mUrl.openConnection(proxy);
            mInputStream = mHttpURLConnection.getInputStream();
            String respose = utils.converStreamToString(mInputStream);
            Log.i("lzm", respose);
            mInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void useHttpsURLConnection(String url) {
        TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

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
                return true;
            }
        };
//        //ssl 证书校验
//        TrustManager[] trustAllCerts = new TrustManager[] {
//                new MyX509TrustManager(context)
//        };
//        // 域名校验
//        HostnameVerifier hostnameVerifier = new MyHostnameVerifier();
        try {
            SSLContext instance = SSLContext.getInstance("TLS");
            instance.init(null, trustAllCerts, new SecureRandom());
            URL mUrl = new URL(url);
//            HttpsURLConnection.setDefaultSSLSocketFactory(instance.getSocketFactory());
//            HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) mUrl.openConnection();
            httpsURLConnection.setSSLSocketFactory(instance.getSocketFactory());
            httpsURLConnection.setHostnameVerifier(hostnameVerifier);
            InputStream mInputStream = httpsURLConnection.getInputStream();
            String respose = utils.converStreamToString(mInputStream);
            Log.i("lzm", respose);
            mInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
