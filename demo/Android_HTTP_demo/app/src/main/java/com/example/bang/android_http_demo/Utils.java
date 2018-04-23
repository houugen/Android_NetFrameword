package com.example.bang.android_http_demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lzm on 2018/4/17.
 */

public class Utils {
    public String converStreamToString(InputStream is) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;
        while((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line + "\n");
        }
        String response = stringBuffer.toString();
        return response;
    }
}
