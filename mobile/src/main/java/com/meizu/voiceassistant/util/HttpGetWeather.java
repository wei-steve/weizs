package com.meizu.voiceassistant.util;

import android.os.AsyncTask;
import android.util.Log;

import com.meizu.voiceassistant.commandType.translate.HttpGetEnglishDateListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by weichangtan on 16/1/11.
 */
public class HttpGetWeather extends AsyncTask<String ,Void, String> {

    String httpUrl = "http://apis.baidu.com/heweather/weather/free";
    String httpArg = "city=beijing";
//    String jsonResult = request(httpUrl, httpArg);
    private HttpGetEnglishDateListener getenglishlistener;
    private String aurl;
    public HttpGetWeather(String aurl,HttpGetEnglishDateListener getenglishlistener){
        this.aurl=aurl;
        this.getenglishlistener=getenglishlistener;
    }


    @Override
    protected String doInBackground(String... params) {
        URL url= null;
        try {
            url = new URL(aurl);
            URLConnection connection=url.openConnection();
            InputStream is=connection.getInputStream();
            InputStreamReader isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            String line;
            StringBuffer sb=new StringBuffer();
            while ((line=br.readLine())!=null){
                sb.append(line);
                Log.d("tag", line);

            }
            br.close();
            isr.close();
            is.close();
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey",  "您自己的apikey");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
