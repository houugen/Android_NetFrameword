package com.example.lzm.webviewtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.http.SslCertificate;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by lzm on 2018/1/10.
 */

public class WebviewActivity extends Activity {
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(WebviewActivity.this);
                SslCertificate sslCertificate = error.getCertificate();

                switch (error.getPrimaryError()) {
                    case SslError.SSL_DATE_INVALID:
                        Log.i("lzm", SslError.SSL_DATE_INVALID + " ssl date invalid");
                        break;
                    case SslError.SSL_IDMISMATCH:
                        Log.i("lzm", SslError.SSL_IDMISMATCH + " hostname dismatch");
                        break;
                    case SslError.SSL_EXPIRED:
                        Log.i("lzm", SslError.SSL_EXPIRED + " cert has expired");
                        break;
                    case SslError.SSL_UNTRUSTED:
                        Log.i("lzm", SslError.SSL_UNTRUSTED + " cert is not trusted");
                        break;
                    case SslError.SSL_INVALID:
                        Log.i("lzm", SslError.SSL_INVALID + " cert is invalid");
                        break;
                    case SslError.SSL_NOTYETVALID:
                        Log.i("lzm", SslError.SSL_NOTYETVALID + " cert is not yet valid");
                        break;
                }

                builder.setTitle("ssl error");
                builder.setMessage("ssl error code: " + error.getPrimaryError());
                builder.setPositiveButton("proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        handler.proceed();
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        handler.cancel();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}
